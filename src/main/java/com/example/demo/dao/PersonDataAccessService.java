package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("TestDao")
public class PersonDataAccessService implements PersonDao{

    private static List<Person> Data = new ArrayList<>();

    @Override
    public int insertPerson(UUID id, Person person) {
        Data.add(new Person(id,person.getName()));
        return 1;
    }

    @Override
    public List<Person> peopleList() {
        return Data;
    }

    @Override
    public Optional<Person> selectPersonByID(UUID id) {
        return Data.stream().filter(person -> person.getId().equals(id))
                .findFirst();
    }

    @Override
    public int deletePersonById(UUID id) {
        Optional<Person> personMaybe = selectPersonByID(id);
        if(personMaybe.isEmpty()){
            return 0;
        }
        Data.remove(personMaybe.get());
        return 1;
    }

    @Override
    public int updatePersonById(UUID id, Person update) {
        return selectPersonByID(id)
                .map(person ->{
                    int indexOfPersonToUpdate = Data.indexOf(person);
                    if(indexOfPersonToUpdate >=0){
                        Data.set(indexOfPersonToUpdate, new Person(id, update.getName()));
                            return 1;
                    }
                    return 0;
                })
                .orElse(0);
    }
}
