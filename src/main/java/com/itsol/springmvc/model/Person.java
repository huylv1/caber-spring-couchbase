/**
 * 
 */
package com.itsol.springmvc.model;

import java.io.Serializable;
import java.util.Date;

import org.zkoss.bind.annotation.DependsOn;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author huylv
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Person implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -23822917851885022L;
	
	/**
	 * Phone number
	 */
	private String id;
	private String firstName;
	private String lastName;
	private String password;
	private String type = "B";
	private Double price;
	private Date birthday;
	private String gender = "male";
	private String email;
	private int rate;
	private String imageUrl;
	
	/**
	 * 
	 */
	public Person() {
	}
	
	/**
	 * @param id
	 * @param firstName
	 * @param lastName
	 * @param password
	 * @param type
	 * @param price
	 * @param birthday
	 * @param gender
	 * @param email
	 * @param rate
	 */
	public Person(String id, String firstName, String lastName, String password, String type, Double price,
			Date birthday, String gender, String email, int rate, String imageUrl) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.type = type;
		this.price = price;
		this.birthday = birthday;
		this.gender = gender;
		this.email = email;
		this.rate = rate;
		this.imageUrl = imageUrl;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}
	
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * @return the birthday
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * @return the rate
	 */
	public int getRate() {
		return rate;
	}

	/**
	 * @param rate the rate to set
	 */
	public void setRate(int rate) {
		this.rate = rate;
	}
	
	/**
	 * @return the imageUrl
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * @param imageUrl the imageUrl to set
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	@DependsOn({ "firstName", "lastName" })
    public String getFullName() {
        return getLastName() + " " + getFirstName();
    }

	@Override
	public String toString() {
		//use google guava ObjectUtils here
		return "Person {id=[" + id + "]}";
	}
}
