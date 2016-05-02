
/**
 * 
 */
package com.anz.HttpToMQ.compute;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.anz.HttpToMQ.transform.PreTransformBLSample;


import com.anz.common.compute.TransformType;
import com.anz.common.compute.impl.CommonJavaCompute;
import com.anz.common.transform.ITransformer;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbMessageAssembly;

/**
 * @author sanketsw & psamon
 *
 */
public class AddUserDefinedProperties extends CommonJavaCompute {

	/* (non-Javadoc)
	 * @see com.anz.common.compute.impl.CommonJsonJsonTransformCompute#getTransformer()
	 */
	
	private static final Logger logger = LogManager.getLogger();

	@Override
	public void execute(MbMessageAssembly inAssembly,
			MbMessageAssembly outAssembly) throws Exception {
		
		// Set ReplyToQ MQMD field to the UDP replyQueue
		MbElement replyToQ = outAssembly.getMessage().getRootElement().getFirstElementByPath("/MQMD/ReplyToQ");
		replyToQ.setValue((String) getUserDefinedAttribute("replyQueue"));
		logger.info("{} = {}", replyToQ.getName(), replyToQ.getValueAsString());
		
		// Set the Local Environment MQ output queue parameter to the UDP providerQueue
		MbElement providerQ = outAssembly.getLocalEnvironment().getRootElement()
				.createElementAsFirstChild(MbElement.TYPE_NAME_VALUE, "Destination", "")
				.createElementAsFirstChild(MbElement.TYPE_NAME_VALUE, "MQ", "")
				.createElementAsFirstChild(MbElement.TYPE_NAME_VALUE, "DestinationData", "")
				.createElementAsFirstChild(MbElement.TYPE_NAME_VALUE, "queueName", getUserDefinedAttribute("providerQueue"));
		logger.info("{} = {}", providerQ.getName(), providerQ.getValueAsString());	
	}

	@Override
	public TransformType getTransformationType() {
		// TODO Auto-generated method stub
		return TransformType.HTTP_MQ;
	}
}
