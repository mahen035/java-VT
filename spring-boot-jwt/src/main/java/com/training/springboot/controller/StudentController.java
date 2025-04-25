//package com.training.springboot.controller;
//
//import com.training.springboot.model.Student;
//import com.training.springboot.service.StudentService;
//import com.training.springboot.service.StudentServiceImpl;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@RestController //@Controller + @ResponseBody
//@RequestMapping("/api")
//
//public class StudentController {
//
//   // @Autowired
//   // private StudentRepository repository;  //Autowire this field through constructor
//    private final StudentService service;
//    @Autowired
//    StudentController(StudentService service){
//        this.service = service;
//    }
//
//    @GetMapping("/greet")
//    public String greet() {
//        return "Hello from Spring Boot!!";
//    }
//
//    @GetMapping("/students")
////    public List<Student> getAllStudents() {
////        List<Student> studList = new ArrayList<>();
////        studList.add(new Student(101, "Rohan", "A+"));
////        studList.add(new Student(102, "Sara", "A+"));
////        studList.add(new Student(103, "Priya", "A"));
////        return studList;
////    }
//    //Add a Student
//    @PostMapping("/student")
//    public ResponseEntity<Student> addStudent(@RequestBody Student stud) {
//        //return repository.save(stud); // Who is implementing it ?
//        return new ResponseEntity<>(service.addStudent(stud), HttpStatus.CREATED);
//    }
//
//    // Get All Students
//    @GetMapping("/student")
//    public ResponseEntity<List<Student>> getAllStud() {
//       // return repository.findAll();
//        return new ResponseEntity<>(service.getAllStudents(), HttpStatus.OK);
//    }
//
//    //Get Student by id
//    @GetMapping("/student/{id}")
//    public ResponseEntity<Student> getStudById(@PathVariable int id) {
//        return new ResponseEntity<>(service.findStudentById(id), HttpStatus.FOUND);
//
//    }
//    @GetMapping("/studentName")
//    public ResponseEntity<Student> getStudByname(@RequestParam("studName") String name) {
//        return new ResponseEntity<>(service.findStudentByName(name), HttpStatus.FOUND);
//    }
//    @PutMapping("/student/{id}")
//    public ResponseEntity<Student> updateStudent(@PathVariable("id") int id, @RequestBody Student stud) {
//        return new ResponseEntity<>(service.updateStudent(id,stud), HttpStatus.OK);
//    }
//    @DeleteMapping("/student/{id}")
//    public String deleteById(@PathVariable("id") int id){
//         return service.deleteStudent(id);
//    }
//
//    @GetMapping("/student/startswithA")
//    public ResponseEntity<List<Student>> getStudentsStartWithA(){
//        return new ResponseEntity<>(service.getStudentsStartsWithA(), HttpStatus.FOUND);
//    }
//
//}
//
