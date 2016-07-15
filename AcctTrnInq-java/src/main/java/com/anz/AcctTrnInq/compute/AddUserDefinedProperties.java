
/**
 * 
 */
package com.anz.AcctTrnInq.compute;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.anz.common.cache.impl.CacheHandlerFactory;
import com.anz.common.compute.ComputeInfo;
import com.anz.common.compute.TransformType;
import com.anz.common.compute.impl.CommonJavaCompute;
import com.anz.common.compute.impl.ComputeUtils;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;

/**
 * @author sanketsw & psamon
 *
 */
public class AddUserDefinedProperties extends CommonJavaCompute {
	
	public static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character
					.digit(s.charAt(i + 1), 16));
		}
		return data;
	}
	
	private static final Logger logger = LogManager.getLogger();


	@Override
	public void execute(MbMessageAssembly inAssembly,
			MbMessageAssembly outAssembly) throws Exception {
		
		ComputeInfo compInfo= getMetaData();
		compInfo.setUserDefinedProperties(setGlobalEnvironment(inAssembly));
		
		// Get Transaction ID
		String transId = ComputeUtils.getTransactionId(outAssembly);
		logger.info("transId = {}", transId);
		
		// Convert Transaction ID to String
		byte[] msgIdByteArray = ComputeUtils.getMsgIdFromString(transId);
		
		if(msgIdByteArray != null){
		
			logger.info("msgByteArray = {}", msgIdByteArray);
			
			// Set msgId
			MbElement msgId = outAssembly.getMessage().getRootElement().getFirstElementByPath("/MQMD/MsgId");
			MbElement corrId = outAssembly.getMessage().getRootElement().getFirstElementByPath("/MQMD/CorrelId");
			
			msgId.setValue(msgIdByteArray);		
			corrId.setValue(msgIdByteArray);
			corrId.setValue(msgId.getValue());
			logger.info("msgId = {}", msgId.getValue());
			
		}
		
		if (null!=compInfo.getUserDefinedProperties().get("cisFlag") && compInfo.getUserDefinedProperties().get("cisFlag").equalsIgnoreCase("T")){
			// Set ReplyToQ MQMD field to the UDP REPLY_QUEUE
			MbElement replyToQ = ComputeUtils.setElementInTree(getUserDefinedAttribute("CISREPLY_QUEUE"), outAssembly.getMessage(), "MQMD","ReplyToQ");
			logger.info("CTMREPLY_QUEUE {} = {}", replyToQ.getName(), replyToQ.getValueAsString());
			
			// Set ReplyToQMgr MQMD field to the UDP REPLY_QUEUE_MGR
			MbElement replyToQMgr = ComputeUtils.setElementInTree(getUserDefinedAttribute("CIS_RQM"), outAssembly.getMessage(), "MQMD","ReplyToQMgr");
			logger.info("REPLY_QUEUE_MGR {} = {}", replyToQMgr.getName(), replyToQMgr.getValueAsString());
			
			// Set the Local Environment MQ output queue parameter to the UDP PROVIDER_QUEUE
			MbElement providerQ = ComputeUtils.setElementInTree(getUserDefinedAttribute("CIS_QUEUE"), outAssembly.getLocalEnvironment(),"Destination", "MQ","DestinationData","queueName");
			logger.info("{} = {}", providerQ.getName(), providerQ.getValueAsString());	
			
			// Set the Local Environment MQ output queue manager parameter to the UDP PROVIDER_QUEUE_MGR
			MbElement providerQMgr = ComputeUtils.setElementInTree(getUserDefinedAttribute("CIS_QM"), outAssembly.getLocalEnvironment(),"Destination", "MQ","DestinationData","queueManagerName");
			logger.info("{} = {}", providerQMgr.getName(), providerQMgr.getValueAsString());
		}
		else {
			// Set ReplyToQ MQMD field to the UDP REPLY_QUEUE
			MbElement replyToQ = ComputeUtils.setElementInTree(getUserDefinedAttribute("CTMREPLY_QUEUE"), outAssembly.getMessage(), "MQMD","ReplyToQ");
			logger.info("CTMREPLY_QUEUE {} = {}", replyToQ.getName(), replyToQ.getValueAsString());
			
			// Set ReplyToQMgr MQMD field to the UDP REPLY_QUEUE_MGR
			MbElement replyToQMgr = ComputeUtils.setElementInTree(getUserDefinedAttribute("CTM_RQM"), outAssembly.getMessage(), "MQMD","ReplyToQMgr");
			logger.info("REPLY_QUEUE_MGR {} = {}", replyToQMgr.getName(), replyToQMgr.getValueAsString());
			
			// Set the Local Environment MQ output queue parameter to the UDP PROVIDER_QUEUE
			MbElement providerQ = ComputeUtils.setElementInTree(getUserDefinedAttribute("CTM_QUEUE"), outAssembly.getLocalEnvironment(),"Destination", "MQ","DestinationData","queueName");
			logger.info("{} = {}", providerQ.getName(), providerQ.getValueAsString());	
			
			// Set the Local Environment MQ output queue manager parameter to the UDP PROVIDER_QUEUE_MGR
			MbElement providerQMgr = ComputeUtils.setElementInTree(getUserDefinedAttribute("CTM_QM"), outAssembly.getLocalEnvironment(),"Destination", "MQ","DestinationData","queueManagerName");
			logger.info("{} = {}", providerQMgr.getName(), providerQMgr.getValueAsString());
		}
		
			

		MbElement reqId = outAssembly.getLocalEnvironment().getRootElement().getFirstElementByPath("/Destination/HTTP/RequestIdentifier");
		MbElement corrId = outAssembly.getMessage().getRootElement().getFirstElementByPath("/MQMD/CorrelId");
		MbElement msgId = outAssembly.getMessage().getRootElement().getFirstElementByPath("/MQMD/MsgId");
		msgId.setValue(hexStringToByteArray(corrId.getValueAsString()));
				
		CacheHandlerFactory.getInstance().updateCache(CacheHandlerFactory.TransactionIdCache, corrId.getValueAsString(), reqId.getValueAsString());
		
		
		MbMessage lclEnvironment=null;
		lclEnvironment=inAssembly.getLocalEnvironment();
		//MbMessage mbmsg=new MbMessage(lclEnvironment);
		outAssembly=new MbMessageAssembly(inAssembly, lclEnvironment, inAssembly.getExceptionList(), inAssembly.getMessage());
	}
	 

	@Override
	public TransformType getTransformationType() {
		// TODO Auto-generated method stub
		return TransformType.HTTP_MQ;
	}
	
	public Map<String, String> setGlobalEnvironment(MbMessageAssembly inAssembly)
			throws MbException {

		MbElement element = inAssembly.getGlobalEnvironment().getRootElement()
				.getFirstChild();

		while (!(element.getName().equals("ComputeInfo"))) {
			element = element.getNextSibling();
		}

		MbElement properties = element.getFirstChild();
		Map<String, String> userDefinedProperties = new HashMap<String, String>();
		while (properties != null) {
			userDefinedProperties.put(properties.getName(),
					properties.getValueAsString());

			properties = properties.getNextSibling();
		}

		return userDefinedProperties;
	}
}
