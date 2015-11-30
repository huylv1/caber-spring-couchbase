/**
 * 
 */
package com.itsol.springmvc.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author huylv
 *
 */
@Controller
public class PersonWebController {
	
	@RequestMapping(value="/person/list", method = RequestMethod.GET)
	public String list(ModelMap model) {
        return "person/list";
    }
}
