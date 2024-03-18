package com.in28minutes.rest.webservices.restfulwebservice.versioning;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class VersioningPersonController {

    @GetMapping("/v1/person")
    public PersonV1 getFirstVersionOfPerson() {
        return new PersonV1("Bob");
    }
    
    @GetMapping("/v2/person")
    public PersonV2 getSecondVersionOfPerson() {
        return new PersonV2(new Name("Bob", "charlie"));
    }

    @GetMapping(path = "/person", params = "version=1")
    public PersonV1 getFirstVersionOfRequest() {
        return new PersonV1("Bob");
    }

    @GetMapping(path = "/person", params = "version=2")
    public PersonV2 getSecondVersionOfRequest() {
        return new PersonV2(new Name("Bob", "charlie"));
    }

    @GetMapping(path = "/person/header", headers = "X-API-VERSION=1")
    public PersonV1 getFirstVersionOfHeader() {
        return new PersonV1("Bob");
    }

    @GetMapping(path = "/person/header", headers = "X-API-VERSION=2")
    public PersonV2 getSecondVersionOfHeader() {
        return new PersonV2(new Name("Bob", "charlie"));
    }

    @GetMapping(path = "/person/accept", produces = "application/vnd.company.app-v1+json")
    public PersonV1 getFirstVersionOfAccept() {
        return new PersonV1("Bob");
    }

    
}
