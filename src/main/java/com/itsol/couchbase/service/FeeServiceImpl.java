/**
 * 
 */
package com.itsol.couchbase.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.itsol.couchbase.service.exception.DataNotExistException;
import com.itsol.springmvc.model.Fee;

/**
 * @author huylv
 *
 */
@Service("feeService")
public class FeeServiceImpl implements FeeService {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(FeeServiceImpl.class);
	
	@Autowired
	private Bucket bucket;
	
	@Value("${couchbase.bucket}")
	private String bucketName;

	
	/* (non-Javadoc)
	 * @see com.itsol.couchbase.service.FeeService#updateFee(com.itsol.springmvc.model.Fee)
	 */
	@Override
	public void updateFee(Fee fee) {
		if (!bucket.exists(Fee.ID)) {
			throw new DataNotExistException("A Fee with id (" + Fee.ID + ") is not exist");
		}
		
		LOGGER.info("Updating document id: " + Fee.ID);
		
		JsonDocument doc = bucket.get(Fee.ID);
		doc.content()
			.put("monthly", fee.getMonthly())
			.put("perTransaction", fee.getFeePerTransaction());
		bucket.replace(doc);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.itsol.couchbase.service.FeeService#getFee()
	 */
	@Override
	public Fee getFee() {
		JsonDocument doc = bucket.get(Fee.ID);
		
		if (doc == null) {
			return null;
		}
		
		JsonObject object = doc.content();
		return new Fee(object.getDouble("monthly"), object.getDouble("perTransaction"));
	}

}
