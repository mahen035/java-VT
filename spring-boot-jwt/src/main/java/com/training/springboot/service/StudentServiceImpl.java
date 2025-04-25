package com.training.springboot.service;

import com.training.springboot.exception.MyResourceNotFoundException;
import com.training.springboot.model.Student;
import com.training.springboot.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class StudentServiceImpl implements StudentService{
    @Autowired
    private  StudentRepository repository;

    @Override
    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    @Override
    public Student addStudent(Student student) {
        return repository.save(student);
    }

    @Override
    public Student findStudentById(int id) {
        return repository.findById(id)
                .orElseThrow(()->new MyResourceNotFoundException("Not found"));
    }

    @Override
    public Student findStudentByName(String name) {
        return repository.findByStudName(name);
    }

    @Override
    public Student updateStudent(int id, Student stud) {
        Optional<Student> obj = repository.findById(id);
        Student oldStudent = obj.orElseThrow(()-> new MyResourceNotFoundException("Not Found"));
        oldStudent.setStudName(stud.getStudName());
        oldStudent.setGrade(stud.getGrade());
        return repository.save(oldStudent);
    }

    @Override
    public String deleteStudent(int id) {
        repository.deleteById(id);
        return "Deleted Successfully!!";
    }

    @Override
    public List<Student> getStudentsStartsWithA() {
        List<Student> studList = repository.findAll();

        return studList.stream()
                .filter(student->student.getStudName().startsWith("A"))
                .collect(Collectors.toList());
    }
}
