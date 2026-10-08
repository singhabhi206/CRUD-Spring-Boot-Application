package in.abhishek.crudSpringBootDemo.service;

import in.abhishek.crudSpringBootDemo.dto.CreateStudentRequestDto;
import in.abhishek.crudSpringBootDemo.dto.CreateStudentResponseDto;
import in.abhishek.crudSpringBootDemo.dto.UpdateStudentRequestDto;
import in.abhishek.crudSpringBootDemo.dto.UpdateStudentResponseDto;
import in.abhishek.crudSpringBootDemo.entity.Student;
import in.abhishek.crudSpringBootDemo.exception.DuplicateResourceException;
import in.abhishek.crudSpringBootDemo.exception.ResourceNotFoundException;
import in.abhishek.crudSpringBootDemo.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student student;
    private CreateStudentRequestDto createRequest;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setId(1L);
        student.setName("Abhishek");
        student.setEmail("abhishek@gmail.com");
        student.setAge(21);
        student.setRollno(101);
        student.setSubject("Java");
        student.setDeleted(false);
        student.setCreatedAt(LocalDateTime.now().minusDays(1));
        student.setUpdatedAt(LocalDateTime.now().minusDays(1));

        createRequest = new CreateStudentRequestDto();
        createRequest.setName("Abhishek");
        createRequest.setEmail("abhishek@gmail.com");
        createRequest.setAge(21);
        createRequest.setRollno(101);
        createRequest.setSubject("Java");
    }

    @Test
    void createStudent_shouldCreateStudentSuccessfully() {
        when(studentRepository.existsByEmail("abhishek@gmail.com")).thenReturn(false);
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        CreateStudentResponseDto response = studentService.createStudent(createRequest);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Abhishek", response.getName());
        assertEquals("abhishek@gmail.com", response.getEmail());
        assertEquals("Student data saved successfully", response.getMessage());

        verify(studentRepository).existsByEmail("abhishek@gmail.com");
        verify(studentRepository).save(any(Student.class));
    }

    @Test
    void createStudent_shouldThrowDuplicateResourceException_whenEmailAlreadyExists() {
        when(studentRepository.existsByEmail("abhishek@gmail.com")).thenReturn(true);

        DuplicateResourceException exception = assertThrows(
                DuplicateResourceException.class,
                () -> studentService.createStudent(createRequest)
        );

        assertEquals(
                "Student with email abhishek@gmail.com already Exisit",
                exception.getMessage()
        );

        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void getStudent_shouldReturnStudent_whenIdExists() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        CreateStudentResponseDto response = studentService.getStudent(1L);

        assertEquals(1L, response.getId());
        assertEquals("Abhishek", response.getName());
        assertEquals(101, response.getRollno());

        verify(studentRepository).findById(1L);
    }

    @Test
    void getStudent_shouldThrowResourceNotFoundException_whenIdDoesNotExist() {
        when(studentRepository.findById(99L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> studentService.getStudent(99L)
        );

        assertEquals("Student with id 99 not found", exception.getMessage());
    }

    @Test
    void getAllStudent_shouldReturnOnlyActiveStudentsFromRepository() {
        Student secondStudent = new Student();
        secondStudent.setId(2L);
        secondStudent.setName("Rahul");
        secondStudent.setEmail("rahul@gmail.com");
        secondStudent.setAge(22);
        secondStudent.setRollno(102);
        secondStudent.setSubject("Spring Boot");
        secondStudent.setDeleted(false);

        when(studentRepository.findByDeletedIsFalse())
                .thenReturn(List.of(student, secondStudent));

        List<CreateStudentResponseDto> response = studentService.getAllStudent();

        assertEquals(2, response.size());
        assertEquals("Abhishek", response.get(0).getName());
        assertEquals("Rahul", response.get(1).getName());

        verify(studentRepository).findByDeletedIsFalse();
    }

    @Test
    void updateStudent_shouldUpdateStudentSuccessfully() {
        UpdateStudentRequestDto request = new UpdateStudentRequestDto();
        request.setName("Abhishek Updated");
        request.setAge(22);
        request.setRollno(201);
        request.setSubject("Spring Boot");

        when(studentRepository.findByIdAndDeletedIsFalse(1L))
                .thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        UpdateStudentResponseDto response = studentService.updateStudent(1L, request);

        assertEquals(1L, response.getId());
        assertEquals("Abhishek Updated", response.getName());
        assertEquals(22, response.getAge());
        assertEquals(201, response.getRollno());
        assertEquals("Spring Boot", response.getSubject());
        assertEquals("Student data Updated successfully", response.getMessage());

        assertFalse(student.getDeleted());
        verify(studentRepository).save(student);
    }

    @Test
    void updateStudent_shouldThrowResourceNotFoundException_whenStudentDoesNotExistOrIsDeleted() {
        UpdateStudentRequestDto request = new UpdateStudentRequestDto();
        request.setName("Updated");
        request.setAge(22);
        request.setRollno(201);
        request.setSubject("Java");

        when(studentRepository.findByIdAndDeletedIsFalse(99L))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> studentService.updateStudent(99L, request)
        );

        assertEquals("Student with id 99 not found", exception.getMessage());
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void deleteStudent_shouldDeleteStudentSuccessfully() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        studentService.deleteStudent(1L);

        verify(studentRepository).findById(1L);
        verify(studentRepository).delete(student);
    }

    @Test
    void deleteStudent_shouldThrowResourceNotFoundException_whenStudentDoesNotExist() {
        when(studentRepository.findById(99L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> studentService.deleteStudent(99L)
        );

        assertEquals("Student with id 99 not found", exception.getMessage());
        verify(studentRepository, never()).delete(any(Student.class));
    }

    @Test
    void deleteStudentSoft_shouldMarkStudentAsDeleted() {
        when(studentRepository.findByIdAndDeletedIsFalse(1L))
                .thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        studentService.deleteStudentSoft(1L);

        ArgumentCaptor<Student> captor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(captor.capture());

        assertTrue(captor.getValue().getDeleted());
    }

    @Test
    void deleteStudentSoft_shouldThrowResourceNotFoundException_whenStudentDoesNotExistOrIsAlreadyDeleted() {
        when(studentRepository.findByIdAndDeletedIsFalse(99L))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> studentService.deleteStudentSoft(99L)
        );

        assertEquals("Student with id 99 not found", exception.getMessage());
        verify(studentRepository, never()).save(any(Student.class));
    }
}
