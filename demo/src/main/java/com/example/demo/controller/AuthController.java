package com.example.demo.controller;

import com.example.demo.service.RegistrationService;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AuthController {

    private final UserService userService;
    private final RegistrationService registrationService;

    public AuthController(UserService userService, RegistrationService registrationService) {
        this.userService = userService;
        this.registrationService = registrationService;
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String error, Model model) {
        if (error != null) model.addAttribute("loginError", true);
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerSubmit(
            @RequestParam String companyName,
            @RequestParam String companyType,
            @RequestParam(required = false) String category,
            @RequestParam String taxId,
            @RequestParam(required = false) String businessDesc,
            @RequestParam String phisAddress,
            @RequestParam String legalAddress,
            @RequestParam(required = false) String vatPayer,
            @RequestParam String bankCode1,
            @RequestParam String bankAccount1,
            @RequestParam(required = false) String webSite,
            @RequestParam String contactName,
            @RequestParam String contactSurname,
            @RequestParam String contactPosition,
            @RequestParam String contactEmail,
            @RequestParam String contactPhone,
            @RequestParam(required = false) String contactMobile,
            @RequestParam(required = false) MultipartFile regFile,
            @RequestParam(required = false) MultipartFile inputVat,
            @RequestParam(required = false) MultipartFile fileOther,
            Model model
    ) {
        try {
            registrationService.saveRequest(
                    companyName, companyType, category,
                    taxId, businessDesc, phisAddress, legalAddress,
                    "on".equals(vatPayer),
                    bankCode1, bankAccount1, webSite,
                    contactName, contactSurname, contactPosition,
                    contactEmail, contactPhone, contactMobile,
                    regFile, inputVat, fileOther
            );
            return "redirect:/register-success";
        } catch (Exception e) {
            model.addAttribute("error", "დაფიქსირდა შეცდომა. სცადეთ თავიდან.");
            return "register";
        }
    }

    @GetMapping("/register-success")
    public String registerSuccess() {
        return "register-success";
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String forgotPasswordSubmit(@RequestParam String email) {
        return "redirect:/forgot-password-success";
    }

    @GetMapping("/forgot-password-success")
    public String forgotPasswordSuccess() {
        return "forgot-password-success";
    }
}