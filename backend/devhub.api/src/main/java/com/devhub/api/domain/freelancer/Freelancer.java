package com.devhub.api.domain.freelancer;

import com.devhub.api.domain.freelancer.CreateFreelancerData;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

@Table(name="freelancers")
@Entity(name="Freelancer")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Freelancer {
    public Freelancer(CreateFreelancerData data) {
    }
}
