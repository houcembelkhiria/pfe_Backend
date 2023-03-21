package com.youtube.jwt.service;

import com.youtube.jwt.dao.RegistrationRequestRepository;
import com.youtube.jwt.dao.UserDao;
import com.youtube.jwt.entity.RegistrationRequest;
import com.youtube.jwt.entity.Role;
import com.youtube.jwt.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;



@Service
public class RegistrationRequestService {

    @Autowired
    private RegistrationRequestRepository registrationRequestRepository;

    private UserService userService;

    @Autowired
    private UserDao userDao;
    @Autowired
    private RestTemplate restTemplate;

    private String apiKey="1e0acc9e464efa87b19182eeb64273d6-30344472-98d0f6fe";

    private String domain ="sandboxa4883417903f46b78cf1bf1b96d123dd.mailgun.org";

    private String fromEmail = null;
    private String toEmail = "houcembelkhiria@gmail.com";
    private String subject = "New Registration Request";




    public RegistrationRequest saveRegistrationRequest(RegistrationRequest registrationRequest) {
        registrationRequestRepository.save(registrationRequest);
        RegistrationRequest savedRequest = registrationRequest;

        // Send email
        String text = "hello i'm"+savedRequest.getFirstName() +" "+ savedRequest.getLastName() +" my cin is " + savedRequest.getCin() ;
        String url = "https://api.mailgun.net/v3/" +domain+ "/messages";
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("api", apiKey);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        fromEmail=savedRequest.getEmail();
        map.add("from", fromEmail);
        map.add("to", toEmail);
        map.add("subject", subject);
        map.add("text", text);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        restTemplate.postForEntity(url, request, String.class);

        return savedRequest;
    }





    public List<RegistrationRequest> getAllRegistrationRequests() {
        return registrationRequestRepository.findAll();
    }



    public void deleteRegistrationRequest(Long cin) {
        registrationRequestRepository.deleteById(cin.toString());
    }

    public User acceptRequest(RegistrationRequest registrationRequest){
        User user = new User();
        String cin =registrationRequest.getCin().toString();

        user.setUserName(cin);
        user.setUserPassword(cin);
        user.setUserFirstName(registrationRequest.getFirstName());
        user.setUserLastName(registrationRequest.getLastName());
        userService.registerNewUser(user);
        return userService.registerNewUser(user);
    }

}
