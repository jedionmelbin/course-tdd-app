package com.xprotec.app.service;

import com.xprotec.app.domain.Person;
import com.xprotec.app.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultPersonService implements PersonService {

    private final PersonRepository personRepository;

    @Override
    public Flux<Person> getPersons() {
        return personRepository.findAll();
    }
}
