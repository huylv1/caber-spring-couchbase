/**
 * 
 */
package com.itsol.zkoss.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;

import com.itsol.couchbase.service.PersonService;


/**
 * @author huylv
 *
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class RegistrationComposer extends SelectorComposer<Component> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4096327778514711908L;
	
	private static Logger logger = LoggerFactory.getLogger(RegistrationComposer.class.getName());
	
	@WireVariable
    private PersonService personService;
	
	@Wire
    private Button submitButton;    
 
    @Wire
    private Checkbox acceptTermBox;
    
    @Wire
    private Textbox nameBox;
    
    @Wire
    private Datebox birthdayBox;
    
    @Wire
    private Radiogroup genderRadio;
    
    @Listen("onCheck = #acceptTermBox")
    public void changeSubmitStatus() {
        if (acceptTermBox.isChecked()){
            submitButton.setDisabled(false);
            submitButton.setImage("/resources/images/submit.png");
        }else{
            submitButton.setDisabled(true);
            submitButton.setImage("");
        }
    }
    
    @Listen("onClick = #submitButton")
    public void register() {
    	
        //save user input into newUser object
    	//personService.savePerson(p);
        logger.debug("a user " + nameBox.getText() + " with gender is " + genderRadio.getSelectedItem().getValue() + " with birthday " + birthdayBox.getValue() + " was added." );
    }
    
}
