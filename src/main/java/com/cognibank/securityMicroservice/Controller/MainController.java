package com.cognibank.securityMicroservice.Controller;

import com.cognibank.securityMicroservice.Model.User;
import com.cognibank.securityMicroservice.Repository.SecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController("/")
public class MainController {


    @Autowired
    private SecurityRepository securityRepository;

    @GetMapping("helloWorld")
    public String HelloWorld() {
        System.out.print("sadsdisand ");
        return "hello";
    }

    //Can
    @PostMapping (path = "userManagement" , consumes = "application/json", produces = "application/json")
    public User sendDataToUserManagement(@RequestBody User user) {
        System.out.print("sendDataToUserManagement " + user.getUserName());
        User mailAndPhone = new User();
        mailAndPhone.setUserId("123456");
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
    public Map<String, String> loginUser (@RequestBody User user) {

        //Get Email and phone from User management team
        System.out.println(user.getUserName());
        final String uri = "http://localhost:8080/userManagement";
        // final String uri = "http://10.61.142.247:8090/checkUserNamePassword";
        RestTemplate restTemplate = new RestTemplate();
        User userObjFromUserManagement =  restTemplate.postForObject(uri,user,User.class);
        System.out.println(("LoginUser sending to UM " ) + userObjFromUserManagement);

        // store in redis so that we can compare once we receive from UI team to generate otp
        securityRepository.save(userObjFromUserManagement);

       // storeDataToRedis(userObjFromUserManagement);

        //format email/phone before sending to the UI
        String emailID = userObjFromUserManagement.getEmail();
        String emailIDFormatted = emailID.replace(emailID.substring(3,emailID.indexOf('@')), "XXX");
        // System.out.println("emailIDFormatted " + emailIDFormatted);

        String phone = userObjFromUserManagement.getPhone();
        String phoneFormatted = phone.replace(phone.substring(4,9), "XXXXX");
        // System.out.println("phoneFormatted " + phoneFormatted);

        //Map only the required data that is to be sent to the user
        Map<String,String> dataToUI = new HashMap<String,String>();
        dataToUI.put("userID", userObjFromUserManagement.getUserId());
        dataToUI.put("email", emailIDFormatted);
        dataToUI.put("phone", phoneFormatted);

        System.out.println("Data sent to user----> " + dataToUI.toString());

        return dataToUI;
    }



    //Recieved OTP from User and returning authID if authenticated
    @PostMapping(path = "validateUserWithOTP", consumes = "application/json", produces = "application/json")
    public String validateUser(@RequestBody User user, HttpServletResponse response){

        String message = "User not found";
        Optional<User> validateThisUser = securityRepository.findById(user.getUserId());
        System.out.println(validateThisUser);
        if(validateThisUser.isPresent()) {
            if ((user.getOtpCode()).equalsIgnoreCase(validateThisUser.get().getOtpCode())) {
                String authCode = authCodeGenerator();
                response.addHeader("Authorization", authCode);
                validateThisUser.get().setAuthID(authCode);
                securityRepository.save(validateThisUser.get());
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
