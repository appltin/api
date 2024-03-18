package com.in28minutes.rest.webservices.restfulwebservice.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.in28minutes.rest.webservices.restfulwebservice.user.Post;
import com.in28minutes.rest.webservices.restfulwebservice.user.User;

public interface PostResponsitory extends JpaRepository<Post, Integer>{
    
}
