package com.xprotec.app.controller;

import com.xprotec.app.domain.Person;
import com.xprotec.app.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/persons")
public class PersonController {
    private final PersonService personService;

    @GetMapping
    public Flux<Person> getPersons() {
        return personService.getPersons();
    }
}
