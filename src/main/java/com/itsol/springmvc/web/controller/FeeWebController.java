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
public class FeeWebController {
	@RequestMapping(value="/fee/list", method=RequestMethod.GET)
	public String list() {
		return "fee/list";
	}
}
