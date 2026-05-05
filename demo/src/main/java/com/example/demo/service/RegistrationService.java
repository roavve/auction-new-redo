package com.example.demo.service;

import com.example.demo.entity.RegisterRequest;
import com.example.demo.repository.RegisterRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class RegistrationService {

    private final RegisterRequestRepository registerRequestRepository;

    public RegistrationService(RegisterRequestRepository registerRequestRepository) {
        this.registerRequestRepository = registerRequestRepository;
    }

    @Transactional
    public void saveRequest(
            String companyName, String companyType, String category,
            String taxId, String businessDesc, String phisAddress, String legalAddress,
            boolean vatPayer, String bankCode1, String bankAccount1, String webSite,
            String contactName, String contactSurname, String contactPosition,
            String contactEmail, String contactPhone, String contactMobile,
            MultipartFile regFile, MultipartFile vatFile, MultipartFile otherFile
    ) throws IOException {

        RegisterRequest req = new RegisterRequest();
        req.setCompanyName(companyName);
        req.setCompanyType(companyType);
        req.setCategory(category);
        req.setTaxId(taxId);
        req.setBusinessDesc(businessDesc);
        req.setPhisAddress(phisAddress);
        req.setLegalAddress(legalAddress);
        req.setVatPayer(vatPayer);
        req.setBankCode1(bankCode1);
        req.setBankAccount1(bankAccount1);
        req.setWebSite(webSite);
        req.setContactName(contactName);
        req.setContactSurname(contactSurname);
        req.setContactPosition(contactPosition);
        req.setContactEmail(contactEmail);
        req.setContactPhone(contactPhone);
        req.setContactMobile(contactMobile);
        req.setStatus("PENDING");
        req.setRequestDate(LocalDateTime.now());

        if (regFile != null && !regFile.isEmpty()) {
            req.setRegFile(regFile.getBytes());
            req.setRegFileName(regFile.getOriginalFilename());
        }
        if (vatFile != null && !vatFile.isEmpty()) {
            req.setVatFile(vatFile.getBytes());
            req.setVatFileName(vatFile.getOriginalFilename());
        }
        if (otherFile != null && !otherFile.isEmpty()) {
            req.setOtherFile(otherFile.getBytes());
            req.setOtherFileName(otherFile.getOriginalFilename());
        }

        registerRequestRepository.save(req);
    }
}