/**
 * 
 */
package com.itsol.zkoss.ui.constraint;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Constraint;

/**
 * @author huylv
 *
 */
public class EmailConstraint implements Constraint {

	/* (non-Javadoc)
	 * @see org.zkoss.zul.Constraint#validate(org.zkoss.zk.ui.Component, java.lang.Object)
	 */
	@Override
	public void validate(Component comp, Object value) throws WrongValueException {
		if(value == null || !value.toString().matches(".+@.+\\.[a-z]+")) {
            throw new WrongValueException(comp, "Please enter a valid email!");
        }
	}

}
