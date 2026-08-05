package in.abhishek.crudSpringBootDemo.controller;

import in.abhishek.crudSpringBootDemo.entity.Student;
import in.abhishek.crudSpringBootDemo.service.StudentService;
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
    public ResponseEntity<Student> createStudent(@RequestBody Student student){
        Student createdStudent=studentService.createStudent(student);

        // To print on postman console
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdStudent);
    }

    // Read the value of table
    @GetMapping("/get/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id){
        Student studentResp=studentService.getStudent(id);
        if(studentResp==null)   return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        return ResponseEntity.ok(studentResp);
    }

    // Read all student data
    @GetMapping("/getAll")
    public ResponseEntity<List<Student>> getAllStudent(){
        List<Student> studentResp=studentService.getAllStudent();
        if(studentResp.isEmpty())   return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        return ResponseEntity.ok(studentResp);
    }

    // Update the detail
    @PutMapping("/update/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student studentReq){
        Student studentResp=studentService.updateStudent(id,studentReq);
        if(studentResp==null)   return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        return ResponseEntity.ok(studentResp);
    }

    // Delete the data
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id){
        Boolean isDeleted=studentService.deleteStudent(id);
        if(!isDeleted)     return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Record does not exist with id "+id);
        return ResponseEntity.ok("Record Deleted Successfully.");
    }

    // Soft delete
    @PatchMapping("/delete-soft/{id}")
    public ResponseEntity<String> deleteStudentSoft(@PathVariable Long id){
        Boolean isDelete=studentService.deleteStudentSofl(id);
        if(isDelete)    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Record does not exist");

        return ResponseEntity.ok("Record Deleted Successfully.");
    }

}
