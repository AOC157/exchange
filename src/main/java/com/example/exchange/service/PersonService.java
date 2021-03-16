package com.example.exchange.service;

import com.example.exchange.model.Person;
import com.example.exchange.model.Product;
import com.example.exchange.repository.PersonRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public void save(Person person){
        personRepository.save(person);
    }

    public Person get(int id) throws NotFoundException {
        if (personRepository.existsById(id)){
            return personRepository.getOne(id);
        }
        throw new NotFoundException("");
    }

    public List<Person> getAll(){
        return personRepository.findAll();
    }
}
