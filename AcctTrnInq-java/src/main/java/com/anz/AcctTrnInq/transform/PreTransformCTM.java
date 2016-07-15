package com.anz.AcctTrnInq.transform;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import com.anz.common.compute.ComputeInfo;
import com.anz.common.compute.impl.CommonBlobTransformCompute;
import com.anz.common.transform.ITransformer;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;

public class PreTransformCTM extends CommonBlobTransformCompute {

	/**
	 * Get the external transformer class instance
	 * 
	 * @return transform
	 */
	public ITransformer<String, String> getTransformer() {
		return  null;
	}

	@Override
	public void prepareForTransformation(ComputeInfo metadata,
			MbMessageAssembly inAssembly, MbMessageAssembly outAssembly)
			throws Exception {
		
		String startDate=metadata.getUserDefinedProperties().get("startDate");
		String endDate=metadata.getUserDefinedProperties().get("endDate");
		String pagingToken=metadata.getUserDefinedProperties().get("pagingToken");
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr=format.format(new Date());
		if(startDate.equalsIgnoreCase(dateStr)){
			getTransformer();
		}
		
		
	}

	

}