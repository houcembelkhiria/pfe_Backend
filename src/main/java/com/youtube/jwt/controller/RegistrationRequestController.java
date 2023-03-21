package com.youtube.jwt.controller;

import com.youtube.jwt.dao.RegistrationRequestRepository;
import com.youtube.jwt.entity.RegistrationRequest;
import com.youtube.jwt.entity.User;
import com.youtube.jwt.service.RegistrationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
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




    @PostMapping({"/NewRegisterationRequest"})
    public RegistrationRequest saveRegistrationRequest(@RequestBody RegistrationRequest registrationRequest) {
        return registrationRequestService.saveRegistrationRequest(registrationRequest);
    }

    @DeleteMapping("/deleteRequest/{cin}")
    public String deleteRequest(@PathVariable Long cin) {
        registrationRequestService.deleteRegistrationRequest(cin);
        return "Registration request deleted: " + cin;
    }



    @GetMapping("/RegistrationRequests")
    public ResponseEntity<List<RegistrationRequest>> getAllRegistrationRequests() {
        List<RegistrationRequest> registrationRequests = registrationRequestService.getAllRegistrationRequests();
        return ResponseEntity.ok(registrationRequests);
    }

    @PostMapping("/acceptRequest")
    public User acceptRequest(RegistrationRequest registrationRequest) {
        return registrationRequestService.acceptRequest(registrationRequest);
    }
    }
