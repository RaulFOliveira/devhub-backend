package com.devhub.api.controller;

import com.devhub.api.domain.freelancer.DetailFreelancerData;
import com.devhub.api.domain.freelancer.Freelancer;
import com.devhub.api.domain.freelancer.CreateFreelancerData;
import com.devhub.api.domain.freelancer.FreelancerRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/freelancers")
public class FreelancerController {
    @Autowired
    private FreelancerRepository repository;


    @PostMapping
    @Transactional
    public ResponseEntity createFreelancer(@Valid @RequestBody CreateFreelancerData data, UriComponentsBuilder uriBuilder) {

        var freelancer = new Freelancer(data);
//        return ResponseEntity.created().

        repository.save(freelancer);

        var uri = uriBuilder.path("/freelancers/{id}").buildAndExpand(freelancer.getId()).toUri();

        return ResponseEntity.created(uri).body(new DetailFreelancerData(freelancer));
    }
}
