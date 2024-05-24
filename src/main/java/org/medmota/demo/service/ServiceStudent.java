package org.medmota.demo.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.medmota.demo.entities.Student;
import org.medmota.demo.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceStudent {

    private final StudentRepository studentRepository;

    Student saveOneStudent(Student student){
        final var savedStudent = Student.builder()
                .name(student.getName())
                .age(student.getAge())
                .gender(student.getGender())
                .address(student.getAddress())
                .build();
        return this.studentRepository.save(savedStudent);
    }

    Student findOneStudent(Long id){
        return this.studentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    List<Student> findAllStudents(){
        return studentRepository.findAll();
    }

    void deleteOneStudent(Long id){
        this.studentRepository.deleteById(id);
    }
}
