package com.xprotec.app.service;

import com.xprotec.app.domain.Person;
import reactor.core.publisher.Flux;

public interface PersonService {
    Flux<Person> getPersons();
}
