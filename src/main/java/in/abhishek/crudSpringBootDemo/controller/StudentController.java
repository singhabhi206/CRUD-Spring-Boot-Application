package in.abhishek.crudSpringBootDemo.controller;

import in.abhishek.crudSpringBootDemo.dto.CreateStudentRequestDto;
import in.abhishek.crudSpringBootDemo.dto.CreateStudentResponseDto;
import in.abhishek.crudSpringBootDemo.dto.UpdateStudentRequestDto;
import in.abhishek.crudSpringBootDemo.dto.UpdateStudentResponseDto;
import in.abhishek.crudSpringBootDemo.entity.Student;
import in.abhishek.crudSpringBootDemo.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService=studentService;
    }

    // Create Student
    @PostMapping
    public ResponseEntity<CreateStudentResponseDto> createStudent(@Valid @RequestBody CreateStudentRequestDto studentRequestDto){
        CreateStudentResponseDto createdStudent=studentService.createStudent(studentRequestDto);
        // To print on postman console
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdStudent);
    }

    // Read the value of table
    @GetMapping("/{id}")
    public ResponseEntity<CreateStudentResponseDto> getStudent(@PathVariable Long id){
        CreateStudentResponseDto studentResp=studentService.getStudent(id);

        return ResponseEntity.ok(studentResp);
    }

    // Read all student data
    @GetMapping
    public ResponseEntity<List<CreateStudentResponseDto>> getAllStudent(){
        List<CreateStudentResponseDto> studentResp=studentService.getAllStudent();

        return ResponseEntity.ok(studentResp);
    }

    // Update the detail
    @PutMapping
    public ResponseEntity<UpdateStudentResponseDto> updateStudent(@PathVariable Long id, @RequestBody UpdateStudentRequestDto updateStudentRequestDto){
        UpdateStudentResponseDto updateStudentResponseDto=studentService.updateStudent(id,updateStudentRequestDto);
        return ResponseEntity.ok(updateStudentResponseDto);
    }

    // Delete the data
    @DeleteMapping
    public ResponseEntity<String> deleteStudent(@PathVariable Long id){
        studentService.deleteStudent(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Soft delete
    @PatchMapping("/delete-soft/{id}")
    public ResponseEntity<String> deleteStudentSoft(@PathVariable Long id){
        studentService.deleteStudentSoft(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
