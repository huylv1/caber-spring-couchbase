package com.itsol.zkoss.ui.model;

import java.io.File;
import java.io.IOException;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.image.Image;
import org.zkoss.io.Files;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;

import com.itsol.couchbase.service.PersonService;
import com.itsol.springmvc.model.Person;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class PersonModel {
	private Person person = new Person();
	private String retypedPassword;

	@WireVariable
	private PersonService personService;
	
	@Wire("#pics")
	private org.zkoss.zul.Image pics;
	
	private String imageUrl = "";

	/**
	 * @return the person
	 */
	public Person getPerson() {
		return person;
	}

	/**
	 * @param person
	 *            the person to set
	 */
	public void setPerson(Person person) {
		this.person = person;
	}

	/**
	 * @return the retypedPassword
	 */
	public String getRetypedPassword() {
		return retypedPassword;
	}

	/**
	 * @param retypedPassword
	 *            the retypedPassword to set
	 */
	public void setRetypedPassword(String retypedPassword) {
		this.retypedPassword = retypedPassword;
	}
	
	@AfterCompose
    public void afterCompose(@ContextParam(ContextType.VIEW) Component view){
        Selectors.wireComponents(view, this, false);
    }

	@Command
	public void onUploadImage(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx) throws IOException {
		UploadEvent upEvent = null;
		Object objUploadEvent = ctx.getTriggerEvent();
		if (objUploadEvent != null && (objUploadEvent instanceof UploadEvent)) {
			upEvent = (UploadEvent) objUploadEvent;
		}
		if (upEvent != null) {
			Media media = upEvent.getMedia();
			if (media instanceof Image) {
				
				//Getting directory on the server
				String filePath = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/");
				filePath = filePath + "resources\\upload\\";
				File baseDir = new File(filePath);
				if (!baseDir.exists()) {
					baseDir.mkdirs();
				}
				
				File fileToCopy = new File(filePath + media.getName()); 
				if (fileToCopy.exists()) {
					Messagebox.show("File " + media.getName() + " is exist", "Error", Messagebox.OK, Messagebox.ERROR);
				} else {
					//Copy to /resources/upload/ directory
					Files.copy(fileToCopy, media.getStreamData());
					filePath = filePath + media.getName();
					Messagebox.show("File Sucessfully uploaded");// in the path [ ." + filePath + " ]

					//Display the image in the browser
					Image img = (Image) media;
					pics.setContent(img);
					imageUrl = "/resources/upload/" + img.getName();
				}
			} else {
				Messagebox.show("Not an image: "+media, "Error", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}

	@Command
	@NotifyChange("person")
	public void submit() {
		try {
			person.setImageUrl(imageUrl);
			personService.savePerson(person);
			Messagebox.show("Person saved!");
		} catch (Exception e) {
			Clients.alert(e.getMessage());
		}
	}
}
