/**
 * 
 */
package com.anz.AcctTrnInq.compute;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.anz.AcctTrnInq.transform.PostTransformBLSample;
import com.anz.AcctTrnInq.transform.pojo.AcctTrnInqRs;
import com.anz.common.cache.impl.CacheHandlerFactory;
import com.anz.common.compute.ComputeInfo;
import com.anz.common.compute.OutputTarget;
import com.anz.common.compute.impl.CommonBlobTransformCompute;
import com.anz.common.compute.impl.ComputeUtils;
import com.anz.common.transform.ITransformer;
import com.anz.common.transform.TransformUtils;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;

/**
 * @author sanketsw
 * 
 */
public class ResponseTransformCompute extends CommonBlobTransformCompute {

	public static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character
					.digit(s.charAt(i + 1), 16));
		}
		return data;
	}

	// private static final Logger logger = LogManager.getLogger();
	@Override
	public void execute(MbMessageAssembly inAssembly,
			MbMessageAssembly outAssembly) throws Exception {

		MbMessage inMessage = inAssembly.getMessage();
		MbMessage outMessage = outAssembly.getMessage();
		
		MbElement correlId = outAssembly.getMessage().getRootElement().getFirstElementByPath("/MQMD/CorrelId");
		String reqId = CacheHandlerFactory.getInstance().lookupCache("TransactionIdCache", correlId.getValueAsString());
		//String reqId = CacheHandlerFactory.getInstance().lookupCache("TransactionIdCache", "1234");
		ComputeUtils.setElementInTree(hexStringToByteArray(reqId),
				outAssembly.getLocalEnvironment(), "Destination", "HTTP",
				"RequestIdentifier");

		String queryString = CacheHandlerFactory.getInstance().lookupCache(
				"TransactionIdCache", reqId + "qString");
		

		String acctidbefore = CacheHandlerFactory.getInstance().lookupCache(
				"TransactionIdCache", reqId + "acctidbefore");
		String acctidafter = CacheHandlerFactory.getInstance().lookupCache(
				"TransactionIdCache", reqId + "acctidafter");
		String clientIp = CacheHandlerFactory.getInstance().lookupCache(
				"TransactionIdCache", reqId + "clientIp");
		String requestId = CacheHandlerFactory.getInstance().lookupCache(
				"TransactionIdCache", reqId + "requestId");
		String userId = CacheHandlerFactory.getInstance().lookupCache(
				"TransactionIdCache", reqId + "userId");
		String accept = CacheHandlerFactory.getInstance().lookupCache(
				"TransactionIdCache", reqId + "accept");

		String uAgentStr = CacheHandlerFactory.getInstance().lookupCache(
				"TransactionIdCache", reqId + "uAgentStr");
		String deviceId = CacheHandlerFactory.getInstance().lookupCache(
				"TransactionIdCache", reqId + "deviceId");
		
			
		ComputeInfo metaData=AcctTrnInqUtility.getQueryStrDetails(queryString);
		metaData.addUserDefinedProperty("startDate", metaData.getUserDefinedProperties().get("startDate"));
		metaData.addUserDefinedProperty("endDate", metaData.getUserDefinedProperties().get("endDate"));
		metaData.addUserDefinedProperty("count", metaData.getUserDefinedProperties().get("count"));
		metaData.addUserDefinedProperty("userAgent", metaData.getUserDefinedProperties().get("userAgent"));
		
	
		metaData.addUserDefinedProperty("acctidbefore", acctidbefore);
		metaData.addUserDefinedProperty("acctidafter", acctidafter);
		metaData.addUserDefinedProperty("Deviceid", deviceId);
		metaData.addUserDefinedProperty("Client-Ip", clientIp);
		metaData.addUserDefinedProperty("Requestid", requestId);

		metaData.addUserDefinedProperty("Userid", userId);
		metaData.addUserDefinedProperty("Accept", accept);

		metaData.addUserDefinedProperty("User-Agent", uAgentStr);
		

		// CacheHandlerFactory.getInstance().removeFromCache("TransactionIdCache",
		// "1234");
		MbElement cisBlobObj = inMessage.getRootElement()
				.getFirstElementByPath("/BLOB");

		byte[] cisByte;
		MbElement cisBlob;
		String cisStr = null;

		if (null != cisBlobObj) {
			cisBlob = cisBlobObj.getLastChild();
			// String inputString =ComputeUtils.getStringFromBlob(inMessage);
			cisByte = (byte[]) cisBlob.getValue();
			cisStr = new String(cisByte);
			metaData.addUserDefinedProperty("CISMsg", cisStr);

		}
		AcctTrnInqRs outXmlObj = null;

		outXmlObj = (AcctTrnInqRs) TransformUtils.convertFromXml(cisStr,
				AcctTrnInqRs.class);

		int count = outXmlObj.getBankAcctTrnRec().size();
		
		if (count < 50) {
			//Cache CIS Response and retrieve in CTM Response
			CacheHandlerFactory.getInstance().updateCache(
					CacheHandlerFactory.TransactionIdCache,
					reqId+"cisStr", cisStr);
			
			setGlobalEnvironment(outAssembly, metaData);
			// call CTM
			//metaData.setOutputTarget(OutputTarget.ALTERNATE);
			getMetaData().setOutputTarget(OutputTarget.NONE);
			MbOutputTerminal outputTerminal = getOutputTerminal("alternate");
			outputTerminal.propagate(outAssembly);

		}else{

		ITransformer<String, String> stringTransformer = getTransformer();
		String outputString = null;
		if (null != cisStr) {

			outputString = stringTransformer.execute(null, appLogger, metaData);
		}

		if (outputString != null) {
			// Write this outputJson to outMessage
			ComputeUtils.replaceStringAsBlob(outMessage, outputString);
		}
		}
		// Cleaning the cache after creating transformed output message
		// getMetaData().getUserDefinedProperties().remove("CTMMsg");
		//metaData.getUserDefinedProperties().remove("CISMsg");
		
	}

	public void setGlobalEnvironment(MbMessageAssembly outAssembly,
			ComputeInfo metadata) throws MbException {
		Iterator<Entry<String, String>> it = metadata
				.getUserDefinedProperties().entrySet().iterator();
		MbElement parent = outAssembly
				.getGlobalEnvironment()
				.getRootElement()
				.createElementAsLastChild(MbElement.TYPE_NAME, "ComputeInfo",
						null);
		while (it.hasNext()) {
			Map.Entry<String, String> pair = (Map.Entry<String, String>) it
					.next();
			parent.createElementAsLastChild(MbElement.TYPE_NAME_VALUE,
					pair.getKey(), pair.getValue());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.anz.common.compute.impl.CommonJsonJsonTransformCompute#getTransformer
	 * ()
	 */
	@Override
	public ITransformer<String, String> getTransformer() {
		return new PostTransformBLSample();
	}

	@Override
	public void executeAfterTransformation(ComputeInfo metadata,
			MbMessageAssembly inAssembly, MbMessageAssembly outAssembly)
			throws Exception {
		// TODO Auto-generated method stub
		super.executeAfterTransformation(metadata, inAssembly, outAssembly);
		MbElement reqId = outAssembly
				.getLocalEnvironment()
				.getRootElement()
				.getFirstElementByPath(
						"/Destination/HTTP/RequestIdentifier");
		ComputeUtils.setElementInTree(
				CacheHandlerFactory.getInstance().lookupCache("TransactionIdCache", reqId.getValueAsString().concat("accept")),
				outAssembly.getMessage(), "Properties", "ContentType");
		ComputeUtils.setElementInTree(
				CacheHandlerFactory.getInstance().lookupCache("TransactionIdCache",reqId.getValueAsString().concat("requestId")),
				outAssembly.getMessage(), "Properties", "ReplyIdentifier");
		
		// ComputeUtils.setElementInTree("d63a8ffd-bdac-498c-b861-a53e11989cef",
		// outAssembly.getMessage(), "Properties", "RequestID");

	}

}
