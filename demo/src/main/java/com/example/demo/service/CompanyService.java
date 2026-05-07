package com.example.demo.service;

import com.example.demo.entity.Company;
import com.example.demo.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company getById(Integer id) {
        return companyRepository.findById(id).orElse(null);
    }

    public Optional<Company> findByName(String name) {
        return companyRepository.findByCompanyName(name);
    }

    public Company save(Company company) {
        return companyRepository.save(company);
    }
}