/**
 * 
 */
package com.itsol.couchbase.service;

import java.util.List;

import com.itsol.springmvc.model.Person;

/**
 * @author huylv
 *
 */
public interface PersonService {
	Person findById(String id);
    
    Person findByName(String name);
     
    void savePerson(Person person);
     
    void updatePerson(Person person);
     
    void deletePersonById(String id);
 
    List<Person> findAllPersons(); 
     
    void deleteAllPersons();
     
    public boolean isPersonExist(Person person);
    
    public boolean login(final String username, final String password);
}
