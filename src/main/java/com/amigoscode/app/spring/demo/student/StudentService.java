package com.amigoscode.app.spring.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student addStudent(Student student) {
        Optional<Student> studentOptional = studentRepository.
                findStudentByEmail(student.getEmail());

        if (studentOptional.isPresent()) {
            throw new IllegalStateException("email taken");
        }
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new IllegalStateException("student with id: " +  id + " does not exists");
        }
        studentRepository.deleteById(id);
    }

    @Transactional
    public Student updateStudent(Long id, String newName, String newEmail) {
        Student student = studentRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("student with id: " + id + " does not exists"));

        if (newName != null &&
                newName.length() > 0 &&
                !Objects.equals(student.getName(), newName)) {
            student.setName(newName);
        }

        if (newEmail != null &&
                newEmail.length() > 0 &&
                !Objects.equals(student.getEmail(), newEmail)) {
            Optional<Student> studentOptional = studentRepository.
                    findStudentByEmail(newEmail);
            if (studentOptional.isPresent()) {
                throw new IllegalStateException("email " + newEmail + " taken");
            }
            student.setEmail(newEmail);
        }
        return studentRepository.findById(id).orElse(null);
    }
}
