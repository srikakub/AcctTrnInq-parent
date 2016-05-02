/**
 * 
 */
package com.anz.HttpToMQ.transform;

import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;

import com.anz.HttpToMQ.transform.pojo.NumbersInput;

import com.anz.common.compute.ComputeInfo;
import com.anz.common.transform.ITransformer;
import com.anz.common.transform.TransformUtils;

import com.anz.common.cache.impl.CacheHandlerFactory;

import com.anz.common.dataaccess.models.iib.Operation;
import com.anz.common.domain.OperationDomain;
import com.anz.common.transform.ITransformer;
import com.anz.common.transform.TransformUtils;



/**
 * @author sanketsw
 * 
 */
public class PostTransformBLSample implements ITransformer<String, String> {

	private static final Logger logger = LogManager.getLogger();
	
	/* (non-Javadoc)
	 * @see com.anz.common.transform.IJsonJsonTransformer#execute(java.lang.String)
	 */
	public String execute(String inputJson, Logger logger, ComputeInfo metadata) throws Exception {
		NumbersInput json = (NumbersInput) TransformUtils.fromJSON(inputJson,
				NumbersInput.class);
		logger.info("Inside Java Compute");
		
		//Add left and right
		json.setRight(json.getRight() + 100);
		String out = TransformUtils.toJSON(json);
		return out;
	}


}