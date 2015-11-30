/**
 * 
 */
package com.itsol.couchbase.service;

import com.itsol.springmvc.model.Fee;

/**
 * @author huylv
 *
 */
public interface FeeService {
	Fee getFee();
    void updateFee(Fee fee);
}
