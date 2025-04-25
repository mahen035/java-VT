package com.training.springboot.service;
import com.training.springboot.model.Student;
import java.util.List;
public interface StudentService {
    List<Student> getAllStudents();
    Student addStudent(Student student);
    Student findStudentById(int id);
    Student findStudentByName(String name);
    Student updateStudent(int id, Student stud);
    String deleteStudent(int id);
    List<Student> getStudentsStartsWithA();
}
