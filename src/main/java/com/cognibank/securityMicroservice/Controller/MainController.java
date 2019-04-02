package com.cognibank.securityMicroservice.Controller;

import com.cognibank.securityMicroservice.Model.User;
import com.cognibank.securityMicroservice.Model.UserDetails;
import com.cognibank.securityMicroservice.Repository.UserDetailsRepository;
import com.cognibank.securityMicroservice.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController("/")
public class MainController {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @GetMapping("helloWorld")
    public String HelloWorld() {
        System.out.print("sadsdisand ");
        return "hello";
    }

    //Can
    @PostMapping (path = "userManagement" , consumes = "application/json", produces = "application/json")
    public UserDetails sendDataToUserManagement(@RequestBody String user) {
//        System.out.println("adasdasd");
       System.out.print("sendDataToUserManagement " + user);
        UserDetails mailAndPhone = new UserDetails();
        mailAndPhone.setUserId(123456);
        mailAndPhone.setEmail("anilvarma@gmail.com");
        mailAndPhone.setPhone("+11234567890");
        return mailAndPhone;
    }

    //Amit
    @PostMapping(path = "notification" , consumes = "application/json", produces = "application/json")
    public void sendDataToNotification(@RequestBody String emailOrPhone) {
        System.out.print("sendDataToNotification " + emailOrPhone);
    }




    //Receive data from UI and forward it to UserManagement team and receive email address and phone number and forward email/phone to UI
    @PostMapping(path = "loginUser", consumes = "application/json", produces = "application/json")
    public UserDetails loginUser (@RequestBody String user) {

        System.out.println(user);
        final String uri = "http://localhost:8080/userManagement";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(user, headers);
        UserDetails userObjFromUserManagement =  restTemplate.postForObject(uri, request, UserDetails.class);
        System.out.println(("userObjFromUserManagement sending to UM " ) + userObjFromUserManagement);
        System.out.println("Data sent to user----> " + maskUserDetails(userObjFromUserManagement).toString());

        return maskUserDetails(userObjFromUserManagement);
    }

    public UserDetails maskUserDetails(UserDetails toMaskUserDetails){

        String emailID = toMaskUserDetails.getEmail();
        String emailIDFormatted = emailID.replace(emailID.substring(3,emailID.indexOf('@')), "XXX");
        toMaskUserDetails.setEmail(emailIDFormatted);

        String phone = toMaskUserDetails.getPhone();
        String phoneFormatted = phone.replace(phone.substring(4,9), "XXXXX");
        toMaskUserDetails.setPhone(phoneFormatted);

        return toMaskUserDetails;
    }



    //Recieved OTP from User and returning authID if authenticated
    @PostMapping(path = "validateUserWithOTP", consumes = "application/json", produces = "application/json")
    public String validateUser(@RequestBody User user, HttpServletResponse response){

        String message = "User not found";
        Optional<User> validateThisUser = userRepository.findById(user.getUserId());
        System.out.println(validateThisUser);
        if(validateThisUser.isPresent()) {
            if ((user.getOtpCode()).equalsIgnoreCase(validateThisUser.get().getOtpCode())) {
                String authCode = authCodeGenerator();
                response.addHeader("Authorization", authCode);
                validateThisUser.get().setAuthID(authCode);
                userRepository.save(validateThisUser.get());
                System.out.println("validateThisUser.toString() ----------------------------> " + validateThisUser.toString());
                message = "User found!!! Hurray!!";
            }
        }
        return message;

    }

    public String authCodeGenerator() {
        String credentials = UUID.randomUUID().toString();
        return credentials;
    }

    public String generateOTP() {
        int otpNumber = 100000 + new Random().nextInt(900000);
        String otp = Integer.toString(otpNumber);
        System.out.println(otp);
        return otp;
    }

}
