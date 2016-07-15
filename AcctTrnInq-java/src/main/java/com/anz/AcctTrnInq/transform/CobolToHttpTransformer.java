/**
 * 
 */
package com.anz.AcctTrnInq.transform;

import org.apache.logging.log4j.Logger;

import com.anz.cobolTransform.transform.pojo.AcctTrnInqCTMRsp;
import com.anz.common.compute.ComputeInfo;
import com.anz.common.transform.ITransformer;

/**
 * @author root
 *
 */
public class CobolToHttpTransformer implements ITransformer<AcctTrnInqCTMRsp, AcctTrnInqCTMRsp> {

	@Override
	public AcctTrnInqCTMRsp execute(AcctTrnInqCTMRsp input, Logger appLogger,
			ComputeInfo metadata) throws Exception {
	
		// perform any transformation here on input object 
		
		AcctTrnInqCTMRsp output = input;
		
		return output;
	}
	
}
