/**
 * 
 */
package com.anz.AcctTrnInq.compute;

import java.util.Map;
import java.util.HashMap;


import com.anz.AcctTrnInq.transform.PreTransformBLSample;
import com.anz.common.compute.ComputeInfo;
import com.anz.common.compute.OutputTarget;
import com.anz.common.compute.impl.CommonBlobTransformCompute;
import com.anz.common.compute.impl.ComputeUtils;
import com.anz.common.transform.ITransformer;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;

/**
 * @author sanketsw
 * 
 */
public class RequestTransformCompute extends CommonBlobTransformCompute {

	/**
	 * Get the external transformer class instance
	 * 
	 * @return transform
	 */

	public ITransformer<String, String> getTransformer() {

		return new PreTransformBLSample();

	}

	public void execute(MbMessageAssembly inAssembly,
			MbMessageAssembly outAssembly) throws Exception {
		String outputString = null;
		ITransformer<String, String> stringTransformer = getTransformer();
		if (stringTransformer != null) {

			MbMessage inMessage = inAssembly.getMessage();
			MbMessage outMessage = outAssembly.getMessage();
			ComputeInfo info=getMetaData();
			info.setUserDefinedProperties(setGlobalEnvironment(inAssembly));
			String inputString = ComputeUtils.getStringFromBlob(inMessage);
			if (null != inputString) {

				outputString = stringTransformer.execute(inputString,
						appLogger, getMetaData());
			}

			if (outputString != null) {
				// Write this outputJson to outMessage
				ComputeUtils.replaceStringAsBlob(outMessage, outputString);
				getMetaData().setOutputTarget(OutputTarget.NONE);
				MbOutputTerminal outputTerminal = getOutputTerminal("out");
				outputTerminal.propagate(outAssembly);
			}
		}

	}
	
	public Map<String, String> setGlobalEnvironment( MbMessageAssembly inAssembly) throws MbException{
		
		MbElement element = inAssembly.getGlobalEnvironment().getRootElement().getFirstChild();
		
		while(!(element.getName().equals("ComputeInfo"))){
			element = element.getNextSibling();
		}
		
		MbElement properties = element.getFirstChild();
		Map<String, String> userDefinedProperties = new HashMap<String, String>();
		while(properties != null ) 
        { 
			userDefinedProperties.put(properties.getName(), properties.getValueAsString());	     
	          
	         properties = properties.getNextSibling(); 
         } 
		
		
		return userDefinedProperties;
	}

}
