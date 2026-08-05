package in.abhishek.crudSpringBootDemo.service;

import in.abhishek.crudSpringBootDemo.entity.Student;
import in.abhishek.crudSpringBootDemo.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository){
        this.studentRepository=studentRepository;
    }

    public Student createStudent(Student studentReq) {
        studentReq.setDeleted(false);
        Student studentResp=studentRepository.save(studentReq);
        return studentResp;
    }

    public Student getStudent(Long id) {
        Optional<Student> studentResp=studentRepository.findByIdAndDeletedIsFalse(id);
        if(studentResp.isPresent()){
            return studentResp.get();
        }
        return null;
    }

    public List<Student> getAllStudent() {
        List<Student> studentResp=studentRepository.findByDeletedIsFalse();
        if(studentResp.isEmpty()){
            return null;
        }
        return studentResp;
    }

    public Student updateStudent(Long id, Student studentReq) {
        Optional<Student> existingStudent=studentRepository.findByIdAndDeletedIsFalse(id);
        if(existingStudent.isEmpty()){
            return null;
        }
        Student studentToSave=existingStudent.get();
        studentToSave.setName(studentReq.getName());
        studentToSave.setRollno(studentReq.getRollno());
        studentToSave.setAge(studentReq.getAge());
        studentToSave.setEmail(studentReq.getEmail());
        studentToSave.setSubject(studentReq.getSubject());
        studentToSave.setDeleted(false);

        return studentRepository.save(studentToSave);
    }

    public Boolean deleteStudent(Long id) {
        Boolean isExist=studentRepository.existsById(id);
        if(!isExist)    return false;
        studentRepository.deleteById(id);
        return true;
    }

    public Boolean deleteStudentSofl(Long id){
        Optional<Student> existingStudent=studentRepository.findByIdAndDeletedIsFalse(id);
        if(existingStudent.isEmpty())   return false;

        Student studentToSave=existingStudent.get();
        studentToSave.setDeleted(true);
        studentRepository.save(studentToSave);
        return true;
    }
}
