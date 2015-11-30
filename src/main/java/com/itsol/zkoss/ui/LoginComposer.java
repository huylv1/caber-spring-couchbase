/**
 * 
 */
package com.itsol.zkoss.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.itsol.couchbase.service.PersonService;


/**
 * @author huylv
 *
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class LoginComposer extends SelectorComposer<Component> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4096327778514711908L;
	
	private static Logger logger = LoggerFactory.getLogger(LoginComposer.class.getName());
	
	@WireVariable
    private PersonService personService;
	
	@Wire
    private Button submitButton;    
    
    @Wire
    private Textbox nameBox;
    
    @Wire
    private Textbox passwordBox;
    
    @Listen("onClick = #submitButton")
    public void doLogin() {
    	String phoneNumber = nameBox.getText();
    	String password = passwordBox.getText();
        logger.debug("user " + phoneNumber + " login with password is " + password);
        if(personService.login(phoneNumber, password)) {
        	Executions.sendRedirect("/person/list");
        } else {
        	Messagebox.show("Invalid username or password", "Error", Messagebox.OK, Messagebox.ERROR);
        }
    }
}
