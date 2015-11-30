package com.itsol.zkoss.ui;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

public class MyWindow extends Window {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8783499935444820034L;

	public void onCreate() { //to process the onCreate event
		Executions.sendRedirect("/login");
	}
}
