package com.in28minutes.rest.webservices.restfulwebservice.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.in28minutes.rest.webservices.restfulwebservice.user.User;

public interface UserResponsitory extends JpaRepository<User, Integer>{
    
}
