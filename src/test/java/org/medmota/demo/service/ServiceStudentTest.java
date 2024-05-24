package org.medmota.demo.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.medmota.demo.entities.Student;
import org.medmota.demo.repositories.StudentRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceStudentTest {

    @InjectMocks
    private ServiceStudent serviceStudent;

    @Mock
    private StudentRepository studentRepository;

    Student studentTemp = null;

    @BeforeEach
    void setUp(){
        studentTemp = Student.builder()
                .name("Ahmed")
                .age(17)
                .gender("Male")
                .address("Addresse AZERTY")
                .build();
    }

    @Test
    void should_save_one_student() {
       // when(studentRepository.save(any(Student.class))).thenReturn(studentTemp);
        given(studentRepository.save(any(Student.class))).willReturn(studentTemp);

        Student studentSaved = serviceStudent.saveOneStudent(studentTemp);

        assertNotNull(studentSaved);
        assertEquals(17, studentTemp.getAge());
        assertEquals("Ahmed", studentTemp.getName());
        assertEquals("Male", studentTemp.getGender());
        assertEquals("Addresse AZERTY", studentTemp.getAddress());

        verify(studentRepository, times(1)).save(any(Student.class));
        verifyNoMoreInteractions(studentRepository);
    }

    @Test
    void should_find_and_return_one_student() {
        given(studentRepository.findById(studentTemp.getId())).willReturn(Optional.of(studentTemp));

        Student student = serviceStudent.findOneStudent(studentTemp.getId());

        assertNotNull(student);
        assertEquals(17, student.getAge());
        assertEquals("Ahmed", student.getName());
        assertEquals("Male", student.getGender());
        assertEquals("Addresse AZERTY", student.getAddress());

        verify(studentRepository, times(1)).findById(studentTemp.getId());
        verifyNoMoreInteractions(studentRepository);
    }

    @Test
    void should_delete_one_student(){
        doNothing().when(studentRepository).deleteById(anyLong());

        serviceStudent.deleteOneStudent(1L);
        verify(studentRepository, times(1)).deleteById(anyLong());
        verifyNoMoreInteractions(studentRepository);

    }

    @Test
    void should_find_and_return_all_student() {
        given(studentRepository.findAll()).willReturn(List.of(studentTemp, new Student()));

        List<Student> listOfStds = serviceStudent.findAllStudents();

        assertEquals(2, listOfStds.size());
        assertNotNull(listOfStds);
        verify(studentRepository, times(1)).findAll();
        verifyNoMoreInteractions(studentRepository);
    }

    @Test
    void should_not_found_a_student_that_doesnt_exists() {
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> serviceStudent.findOneStudent(1L));
    }

}