/**
 * 
 */
package com.itsol.springmvc.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author huylv
 *
 */
@Controller
public class SignUpController {
	
	@RequestMapping(value="/signup", method=RequestMethod.GET)
	private String signup() {
		return "signup";
	}
}
