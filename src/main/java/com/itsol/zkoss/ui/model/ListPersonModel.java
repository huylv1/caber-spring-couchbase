package com.itsol.zkoss.ui.model;

import java.util.Arrays;
import java.util.List;

import org.springframework.util.CollectionUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;

import com.itsol.couchbase.service.PersonService;
import com.itsol.springmvc.model.Person;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ListPersonModel {
	@WireVariable
    private PersonService personService;
	
	private Person selectedItem;
	private List<Person> result;
	private List<String> gender = Arrays.asList(new String[]{"male", "female"});
	
	@Init
    public void init() {    // Initialize
		result = personService.findAllPersons();
		if (!CollectionUtils.isEmpty(result)){
			selectedItem = result.get(0); // Selected First One
		}
    }
	
	/**
	 * @return the selectedItem
	 */
	public Person getSelectedItem() {
		return selectedItem;
	}

	/**
	 * @param selectedItem the selectedItem to set
	 */
	public void setSelectedItem(Person selectedItem) {
		this.selectedItem = selectedItem;
	}

	@AfterCompose
    public void initSetup(@ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
    }
	
	/**
	 * @return the gender
	 */
	public List<String> getGender() {
		return gender;
	}

	public List<Person> getPersons() {
        return personService.findAllPersons();
    }
    
    public int getSize(){
        return result.size();
    }
    
    @Command
    @NotifyChange(value={"persons", "size"})
    public void deleteAllPerson() {
    	personService.deleteAllPersons();
    }
    
    @Command
    @NotifyChange(value={"persons", "size"})
    public void removePerson(@BindingParam("person") Person person) {
        personService.deletePersonById(person.getId());
    }
    
    @Command
    public void editPerson(@BindingParam("person") Person person) {
    	personService.updatePerson(person);
    	Messagebox.show("Person is updated!");
    }
}
