package com.baglie.VirtualQueue.service;

import com.baglie.VirtualQueue.model.User;
import com.baglie.VirtualQueue.repository.DTOStudent;
import com.baglie.VirtualQueue.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public User saveStudent(User user) {
        Iterable<DTOStudent> students = studentRepository.findAll();

        long biggestId = findBiggestId(students);

        boolean containsName = false;
        for (DTOStudent studentFromBase : students) {
            if (user.getUsername().equals(studentFromBase.getName())) {
                containsName = true;
            }
        }

        if (containsName) {
            user.setConnected(false);
        } else {
            user.setId(biggestId + 1);
            DTOStudent student = new DTOStudent();
            student.setName(user.getUsername());
            student.setId(biggestId + 1);
            studentRepository.save(student);
            System.out.println(studentRepository.findAll());
            user.setConnected(true);
        }

        return user;
    }

    public List<DTOStudent> takeQueue(String studentName) {

        Iterable<DTOStudent> students = studentRepository.findAll();
        for (DTOStudent student : students) {
            if (studentName.equals(student.getName())){
                long oldId = student.getId();
                String name = student.getName();
                studentRepository.deleteById(oldId);
                DTOStudent newStudent = new DTOStudent();
                newStudent.setName(name);
                newStudent.setId(oldId);
                newStudent.setInQueue(true);
                newStudent.setInQueueTime(new Date(System.currentTimeMillis()));
                studentRepository.save(newStudent);
            }
        }
        List<DTOStudent> studentsInQueue = new ArrayList<>();
        for (DTOStudent student : studentRepository.findAll()){
            if (student.isInQueue()){
                studentsInQueue.add(student);
            }
        }

        studentsInQueue.sort(Comparator.comparing(DTOStudent::getInQueueTime));
        long position = 1;
        for (DTOStudent student : studentsInQueue) {
            student.setPositionInQueue(position);
            position++;
        }

        return studentsInQueue;
    }


    public List<DTOStudent> leaveQueue(String studentName) {
        System.out.println("name " + studentName);
        Iterable<DTOStudent> students = studentRepository.findAll();
        for (DTOStudent student : students) {
            if (studentName.equals(student.getName())){
                long oldId = student.getId();
                String name = student.getName();
                studentRepository.deleteById(oldId);
                DTOStudent newStudent = new DTOStudent();
                newStudent.setName(name);
                newStudent.setId(oldId);
                newStudent.setInQueue(false);
                newStudent.setInQueueTime(null);
                studentRepository.save(newStudent);
            }
        }
        List<DTOStudent> studentsInQueue = new ArrayList<>();
        for (DTOStudent student : studentRepository.findAll()){
            if (student.isInQueue()){
                studentsInQueue.add(student);
            }
        }

        studentsInQueue.sort(Comparator.comparing(DTOStudent::getInQueueTime));
        long position = 1;
        for (DTOStudent student : studentsInQueue) {
            student.setPositionInQueue(position);
            position++;
        }

        return studentsInQueue;
    }

    public void findByName(String name) {
        DTOStudent student = studentRepository.findByName(name);
        System.out.println(student);
    }

    public void deleteAll() {
        studentRepository.deleteAll();
    }

    public Iterable<DTOStudent> findAll() {
        return studentRepository.findAll();
    }

    public void logout(User user) {
        Iterable<DTOStudent> students = studentRepository.findAll();
        for (DTOStudent studentFromBase : students) {
            System.out.println("student from base " + studentFromBase.getName());
            if (user.getUsername().equals(studentFromBase.getName())) {
                studentRepository.deleteById(studentFromBase.getId());
            }
        }
    }

    private long findBiggestId(Iterable<DTOStudent> students){

        long biggestId = 0;
        if (students != null) {
            for (DTOStudent student : students) {
                if (student.getId() > biggestId) {
                    biggestId = student.getId();
                }
            }
        }
        return biggestId;
    }



}
