package com.devhub.api.controller;

import com.devhub.api.domain.freelancer.Freelancer;
import com.devhub.api.domain.freelancer.CreateFreelancerData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/freelancer")
public class FreelancerController {

    @PostMapping
    @Transactional
    public ResponseEntity createFreelancer(@Valid @RequestBody CreateFreelancerData data) {
        var freelancer = new Freelancer(data);
//        return ResponseEntity.created().
        return null;
    }
}
