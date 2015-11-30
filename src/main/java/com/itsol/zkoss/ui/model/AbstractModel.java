/**
 * 
 */
package com.itsol.zkoss.ui.model;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

/**
 * @author huylv
 *
 */
public abstract class AbstractModel {
	//message for confirming the deletion.
	String deleteMessage;
	public String getDeleteMessage(){
		return deleteMessage;
	}
	
	@NotifyChange("deleteMessage")
	@Command
	public void confirmDelete(){
		//set the message to show to user
		deleteMessage = "Do you want to delete ?";
	}
	
	@NotifyChange("deleteMessage")
	@Command
	public void cancelDelete(){
		//clear the message
		deleteMessage = null;
	}
}
