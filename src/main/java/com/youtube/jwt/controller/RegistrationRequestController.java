package com.youtube.jwt.controller;

import com.youtube.jwt.dao.RegistrationRequestRepository;
import com.youtube.jwt.entity.RegistrationRequest;
import com.youtube.jwt.entity.User;
import com.youtube.jwt.service.RegistrationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
public class RegistrationRequestController {
    @Autowired
    private  RegistrationRequestService registrationRequestService;
    @Autowired
    private RegistrationRequestRepository registrationRequestRepository;

    @Autowired
    private MongoTemplate mongoTemplate;
    @PostMapping({"/NewRegisterationRequest"})
    public RegistrationRequest saveRegistrationRequest(@RequestBody RegistrationRequest registrationRequest) {
        return registrationRequestService.saveRegistrationRequest(registrationRequest);
    }

    @RolesAllowed("Admin")
    @DeleteMapping("/deleteRequest/{userName}")
    public String deleteRegistrationRequest(@PathVariable("userName") String userName) {
        registrationRequestService.deleteRegistrationRequest(userName);
        return "Registration request deleted: " + userName;
    }



    @GetMapping("/RegistrationRequests")
    public ResponseEntity<List<RegistrationRequest>> getAllRegistrationRequests() {
        List<RegistrationRequest> registrationRequests = registrationRequestService.getAllRegistrationRequests();
        return ResponseEntity.ok(registrationRequests);
    }

    /*@PostMapping("/acceptRequest")
    public User acceptRequest(RegistrationRequest registrationRequest) {
        return registrationRequestService.acceptRequest(registrationRequest);
    }*/
    @PostMapping("/acceptRequest")
    public RegistrationRequest acceptRequest(@RequestBody RegistrationRequest registrationRequest) {
        String cin =registrationRequest.getUserName().toString();
        // Save the user to the new collection using the MongoTemplate
        mongoTemplate.save(registrationRequest, "user");

        // Delete the user from the old collection using the UserRepository
        //registrationRequestRepository.deleteById(cin);
        // Return the user object
        return registrationRequest;
    }
    }
