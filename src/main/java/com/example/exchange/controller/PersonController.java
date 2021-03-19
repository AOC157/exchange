package com.example.exchange.controller;

import com.example.exchange.model.Person;
import com.example.exchange.service.PersonService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/person")
@Validated
public class PersonController {

    @Autowired
    public PersonService personService;

    @PostMapping(value = "/insert")
    public Person insertPerson(@Valid @RequestBody Person person) {
        return personService.save(person);
    }

    @GetMapping(value = "/getAll")
    public List<Person> getAllPeople() {
        return personService.getAll();
    }

    @GetMapping(value = "/get/{id}")
    public String getOnePerson(@PathVariable("id") int id) throws NotFoundException {
        return personService.get(id).toString();
    }
}
