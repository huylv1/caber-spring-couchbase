/**
 * 
 */
package com.itsol.springmvc.model;

import java.io.Serializable;

/**
 * @author huylv
 *
 */
public class Fee implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6248215538802886519L;
	
	public static String ID = "global::fee"; 
	
	private double monthly;
	private double feePerTransaction;
	
	/**
	 * @param monthlyFee
	 * @param feePerTransaction
	 */
	public Fee(double monthly, double feePerTransaction) {
		this.monthly = monthly;
		this.feePerTransaction = feePerTransaction;
	}

	/**
	 * @return the monthly
	 */
	public double getMonthly() {
		return monthly;
	}

	/**
	 * @param monthly the monthly to set
	 */
	public void setMonthly(double monthly) {
		this.monthly = monthly;
	}

	/**
	 * @return the feePerTransaction
	 */
	public double getFeePerTransaction() {
		return feePerTransaction;
	}

	/**
	 * @param feePerTransaction
	 *            the feePerTransaction to set
	 */
	public void setFeePerTransaction(double feePerTransaction) {
		this.feePerTransaction = feePerTransaction;
	}

}
