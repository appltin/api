package com.in28minutes.rest.webservices.restfulwebservice.user;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.in28minutes.rest.webservices.restfulwebservice.jpa.PostResponsitory;
import com.in28minutes.rest.webservices.restfulwebservice.jpa.UserResponsitory;

import jakarta.validation.Valid;

import java.net.URI;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.hibernate.dialect.MySQLDialect;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.ResponseEntity;

@RestController
public class UserJpaResource {
    
    private UserResponsitory userResponsitory;
    private PostResponsitory postResponsitory;
    

    public UserJpaResource(UserResponsitory userResponsitory, PostResponsitory postResponsitory) {
        this.userResponsitory = userResponsitory;
        this.postResponsitory = postResponsitory;
    }


    @GetMapping("/jpa/users")
    public List<User> retrieveAllUsers() {
        return userResponsitory.findAll();
    }

    // @GetMapping("/users/{id}")
    // public User retrieveCertain(@PathVariable int id) {
    //     User user = service.findOne(id);

    //     if(user == null)
    //         throw new UserNotFoundException("id" + id);
        
    //     return user;
    // }
    
    @GetMapping("/jpa/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id) {
        Optional<User> user = userResponsitory.findById(id);

        if(user.isEmpty())
            throw new UserNotFoundException("id" + id);
        
        EntityModel<User> entityModel = EntityModel.of(user.get());
        WebMvcLinkBuilder link =  linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(link.withRel("/all-users"));
        
        return entityModel;
    }

    @PostMapping("/jpa/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = userResponsitory.save(user);

        URI  location = ServletUriComponentsBuilder.fromCurrentRequest()
                                                .path("/{id}")
                                                .buildAndExpand(savedUser.getId())
                                                .toUri();

        return ResponseEntity.created(location).build();
    }   
    
    @DeleteMapping("/jpa/users/{id}")
    public void deleteById(@PathVariable int id) {
        userResponsitory.deleteById(id);
    }   

    @GetMapping("/jpa/users/{id}/posts")
    public List<Post> retrievePostsForUser(@PathVariable int id) {
        Optional<User> user = userResponsitory.findById(id);

        if(user.isEmpty())
            throw new UserNotFoundException("id" + id);

        return user.get().getPosts();
    }  

    @PostMapping("/jpa/users/{id}/posts")
    public ResponseEntity<Post> createPostsForUser(@PathVariable int id, @Valid @RequestBody Post post) {
        Optional<User> user = userResponsitory.findById(id);

        if(user.isEmpty())
            throw new UserNotFoundException("id" + id);

        post.setUser(user.get());
        Post savePost = postResponsitory.save(post);

        URI  location = ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(savePost.getId())
                            .toUri();
               
        return ResponseEntity.created(location).build();
    }   
    

}
