/**
 * 
 */
package com.itsol.springmvc.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.couchbase.client.java.document.json.JsonObject;
import com.itsol.couchbase.service.PersonService;
import com.itsol.springmvc.model.Person;

/**
 * @author huylv
 *
 */
@RestController
public class PersonRestController {
	
	@Autowired
	private PersonService personService;
	
	private static final Logger logger = LoggerFactory.getLogger(PersonRestController.class); 

	//-------------------Retrieve Single Person--------------------------------------------------------
	
	@RequestMapping(value="/person/{id}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Person> findPerson(@PathVariable String id) {
		logger.info("Fetching person with id " + id);
		Person p = personService.findById(id);
		if (p == null) {
			return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Person>(p, HttpStatus.OK);
	}
	
	//-------------------Retrieve All persons--------------------------------------------------------
	@RequestMapping(value="/persons",method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Person>> getAllPerson() {
		List<Person> persons = personService.findAllPersons();
        if(persons.isEmpty()){
            return new ResponseEntity<List<Person>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Person>>(persons, HttpStatus.OK);
	}
	
	//-------------------Create a person--------------------------------------------------------
	
	@RequestMapping(value = "/person/", method = RequestMethod.POST)
    public ResponseEntity<?> createPerson(@RequestBody Person person, UriComponentsBuilder ucBuilder) {
		logger.info("Creating person " + person.getId());
 
        if (personService.isPersonExist(person)) {
        	String msg = "A person with id " + person.getId() + " already exist";
            return new ResponseEntity<String>(msg, HttpStatus.CONFLICT);
        }
        try {
        	personService.savePerson(person);
        	 
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/person/{id}").buildAndExpand(person.getId()).toUri());
            return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
		} catch (Exception e) {
			JsonObject responseData = JsonObject.empty()
			        .put("success", false)
			        .put("failure", "There was an error creating account")
			        .put("exception", e.getMessage());
			    return new ResponseEntity<String>(responseData.toString(), HttpStatus.OK);
		}
    }
	
	//------------------- Update a Person --------------------------------------------------------
    
    @RequestMapping(value = "/person/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Person> updatePerson(@PathVariable("id") String id, @RequestBody Person person) {
    	logger.info("Updating Person " + id);
         
        Person currentPerson = personService.findById(id);
         
        if (currentPerson==null) {
            System.out.println("Person with id " + id + " not found");
            return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
        }
 
        currentPerson.setId(person.getId());
        currentPerson.setFirstName(person.getFirstName());
        currentPerson.setLastName(person.getLastName());
        currentPerson.setPassword(person.getPassword());
        currentPerson.setPrice(person.getPrice());
        currentPerson.setType(person.getType());
         
        personService.updatePerson(currentPerson);
        return new ResponseEntity<Person>(currentPerson, HttpStatus.OK);
    }
 
    //------------------- Delete a Person --------------------------------------------------------
     
    @RequestMapping(value = "/person/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Person> deletePerson(@PathVariable("id") String id) {
    	logger.info("Fetching & Deleting Person with id " + id);
 
        Person person = personService.findById(id);
        if (person == null) {
            System.out.println("Unable to delete. Person with id " + id + " not found");
            return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
        }
 
        personService.deletePersonById(id);
        return new ResponseEntity<Person>(HttpStatus.NO_CONTENT);
    }
 
     
    //------------------- Delete All Persons --------------------------------------------------------
     
    @RequestMapping(value = "/person/", method = RequestMethod.DELETE)
    public ResponseEntity<Person> deleteAllPersons() {
    	logger.info("Deleting All Persons");
 
        personService.deleteAllPersons();
        return new ResponseEntity<Person>(HttpStatus.NO_CONTENT);
    }
}
