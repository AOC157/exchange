package com.example.exchange.service;

import com.example.exchange.model.Person;
import com.example.exchange.repository.PersonRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Person save(Person person){
        return personRepository.save(person);
    }

    public Person get(int id) {
        if (personRepository.existsById(id)){
            return personRepository.getOne(id);
        }
        throw new ObjectNotFoundException(id,"the person not found");
    }

    public List<Person> getAll(){
        return personRepository.findAll();
    }
}
