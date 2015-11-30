/**
 * 
 */
package com.itsol.zkoss.ui.renderer;

import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;

import com.itsol.springmvc.model.Person;

/**
 * @author huylv
 *
 */
public class PersonRenderer implements RowRenderer<Person> {

	@Override
	public void render(Row row, Person data, int index) throws Exception {
		/// the data append to each row with simple label
        row.appendChild(new Label(Integer.toString(index + 1)));
        row.appendChild(new Label(data.getFullName()));
        row.appendChild(new Label(data.getGender()));
	}

}
