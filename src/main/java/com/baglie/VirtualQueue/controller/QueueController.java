package com.baglie.VirtualQueue.controller;

import com.baglie.VirtualQueue.model.User;
import com.baglie.VirtualQueue.repository.DTOStudent;
import com.baglie.VirtualQueue.repository.StudentRepository;
import com.baglie.VirtualQueue.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QueueController {

    @Autowired
    private StudentService studentService;

    private List<String> userNames = new ArrayList<>();

    private int positionInQueue = 0;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/login")
    @SendTo("/queue/responselogin")
    public User loginUser(@Payload User user){
        User userResult = studentService.saveStudent(user);

        return userResult;
    }

    @MessageMapping("/logout")
    @SendTo("/queue/responselogout")
    public User logout(@Payload User user){
        System.out.println(user);
        studentService.leaveQueue(user.getUsername());
        studentService.logout(user);
        user.setConnected(false);

        return user;
    }

    @MessageMapping("/leave")
    @SendTo("/queue/position")
    public List<DTOStudent> leaveQueue(@Payload User user){

        return studentService.leaveQueue(user.getUsername());
    }

    @MessageMapping("/take")
    @SendTo("/queue/position")
    public List<DTOStudent> takeQueue(@Payload User user){

        return studentService.takeQueue(user.getUsername());
    }

}
