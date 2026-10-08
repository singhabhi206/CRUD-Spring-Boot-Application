package in.abhishek.crudSpringBootDemo.dto;

import jakarta.validation.constraints.*;

public class CreateStudentRequestDto {
    @NotBlank(message = "Name can not be Null/Empty/Blank")
    @Size(min=2,max=50,message = "Name length should be between 2 to 50")
    private String name;

    @NotBlank(message = "Email should not be blank")
    @Email(message = "Student email must be valid")
    private String email;

    @Min(value=18,message = "Age should be 18+")
    private int age;

    @NotNull(message = "Roll no. can not be null")
    private int rollno;

    private String subject;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getRollno() {
        return rollno;
    }

    public void setRollno(int rollno) {
        this.rollno = rollno;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
