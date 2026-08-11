package in.abhishek.crudSpringBootDemo.service;

import in.abhishek.crudSpringBootDemo.dto.CreateStudentRequestDto;
import in.abhishek.crudSpringBootDemo.dto.CreateStudentResponseDto;
import in.abhishek.crudSpringBootDemo.dto.UpdateStudentRequestDto;
import in.abhishek.crudSpringBootDemo.dto.UpdateStudentResponseDto;
import in.abhishek.crudSpringBootDemo.entity.Student;
import in.abhishek.crudSpringBootDemo.exception.DuplicateResourceException;
import in.abhishek.crudSpringBootDemo.exception.ResourceNotFoundException;
import in.abhishek.crudSpringBootDemo.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository){
        this.studentRepository=studentRepository;
    }

    public CreateStudentResponseDto createStudent(CreateStudentRequestDto studentRequestDto) {
        Student student=mapToEntity(studentRequestDto);

        if(emailExist(student)){
            throw new DuplicateResourceException("Student with email "+student.getEmail()+" already Exisit");
        }

        Student studentResp=studentRepository.save(student);

        return maptoDto(studentResp);
    }

    public CreateStudentResponseDto getStudent(Long id) {
        Student studentResp=studentRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student with id "+id+" not found"));

        return maptoDto(studentResp);
    }

    public List<CreateStudentResponseDto> getAllStudent() {
        List<Student> studentResp=studentRepository.findByDeletedIsFalse();

        return studentResp.stream().map(this::maptoDto).toList();
    }

    public UpdateStudentResponseDto updateStudent(Long id, UpdateStudentRequestDto updateStudentRequestDto) {
        Student existingStudent=studentRepository
                .findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id "+id+" not found"));

        existingStudent.setName(updateStudentRequestDto.getName());
        existingStudent.setRollno(updateStudentRequestDto.getRollno());
        existingStudent.setAge(updateStudentRequestDto.getAge());
        existingStudent.setSubject(updateStudentRequestDto.getSubject());
        existingStudent.setUpdatedAt(LocalDateTime.now());
        existingStudent.setDeleted(false);

        Student savedStudent= studentRepository.save(existingStudent);

        return maptoUpdateDto(savedStudent);

    }


    public void deleteStudent(Long id) {
        Student studentTobeDeleted=studentRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id "+id+" not found"));

        studentRepository.delete(studentTobeDeleted);
    }

    public void deleteStudentSoft(Long id){
        Student studentTobeDeleted=studentRepository
                .findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id "+id+" not found"));

        studentTobeDeleted.setDeleted(true);
        studentRepository.save(studentTobeDeleted);
    }

    private Student mapToEntity(CreateStudentRequestDto studentRequestDto) {
        Student student=new Student();
        student.setName(studentRequestDto.getName());
        student.setAge(studentRequestDto.getAge());
        student.setEmail(studentRequestDto.getEmail());
        student.setRollno(studentRequestDto.getRollno());
        student.setSubject(studentRequestDto.getSubject());

        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());

        student.setDeleted(false);
        return student;
    }

    private CreateStudentResponseDto maptoDto(Student student) {
        CreateStudentResponseDto studentResponseDto=new CreateStudentResponseDto();
        studentResponseDto.setId((student.getId()));
        studentResponseDto.setEmail(student.getEmail());
        studentResponseDto.setName(student.getName());
        studentResponseDto.setAge(student.getAge());
        studentResponseDto.setRollno(student.getRollno());
        studentResponseDto.setSubject(student.getSubject());
        studentResponseDto.setCreatedAt(student.getCreatedAt());
        studentResponseDto.setUpdatedAt(student.getUpdatedAt());

        studentResponseDto.setMessage("Student data saved successfully");
        return studentResponseDto;
    }

    private UpdateStudentResponseDto maptoUpdateDto(Student student) {
        UpdateStudentResponseDto updateStudentResponseDto=new UpdateStudentResponseDto();
        updateStudentResponseDto.setId((student.getId()));
        updateStudentResponseDto.setEmail(student.getEmail());
        updateStudentResponseDto.setName(student.getName());
        updateStudentResponseDto.setAge(student.getAge());
        updateStudentResponseDto.setRollno(student.getRollno());
        updateStudentResponseDto.setSubject(student.getSubject());
        updateStudentResponseDto.setUpdatedAt(student.getUpdatedAt());

        updateStudentResponseDto.setMessage("Student data Updated successfully");
        return updateStudentResponseDto;
    }

    private boolean emailExist(Student student) {
        return studentRepository.existsByEmail(student.getEmail());
    }

}
