package com.anz.AcctTrnInq.transform;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import com.anz.common.cache.impl.CacheHandlerFactory;
import com.anz.common.compute.ComputeInfo;
import com.anz.common.compute.OutputTarget;
import com.anz.common.compute.TransformType;
import com.anz.common.compute.impl.CommonJavaCompute;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;

public class FlowRedirection extends CommonJavaCompute {

	@Override
	public void prepareForTransformation(ComputeInfo metadata,
			MbMessageAssembly inAssembly, MbMessageAssembly outAssembly)
			throws Exception {
		MbElement httpHeader = null;

		MbMessage inMessage = inAssembly.getMessage();
		if (null != inMessage.getRootElement()
				&& null != inMessage.getRootElement().getFirstElementByPath(
						"HTTPInputHeader")) {
			httpHeader = inMessage.getRootElement().getFirstElementByPath(
					"HTTPInputHeader");
			String acctIdBef = null;
			String acctIdAfter = null;
			// Extract acctid
			if (null != httpHeader
					.getFirstElementByPath("X-Original-HTTP-Command")) {
				MbElement originalString = httpHeader
						.getFirstElementByPath("X-Original-HTTP-Command");
				String acctString = originalString.getValueAsString();
				acctIdBef = acctString.substring(
						acctString.indexOf("banking/") + 8,
						acctString.indexOf("-"));
				metadata.addUserDefinedProperty("acctidbefore", acctIdBef);

				acctIdAfter = acctString.substring(acctString.indexOf("-")+1,
						acctString.indexOf("/transctions"));
				metadata.addUserDefinedProperty("acctidafter", acctIdAfter);
			}

			// Extract Deviceid
			MbElement deviceIdObj = httpHeader
					.getFirstElementByPath("Deviceid");
			String deviceId = deviceIdObj.getValueAsString();
			metadata.addUserDefinedProperty("Deviceid", deviceId);

			// Extract Client-IP
			MbElement clientIpObj = httpHeader
					.getFirstElementByPath("Client-Ip");
			String clientIp = clientIpObj.getValueAsString();
			metadata.addUserDefinedProperty("Client-Ip", clientIp);

			// Extract Requestid
			MbElement requestIdObj = httpHeader
					.getFirstElementByPath("Requestid");
			String requestId = requestIdObj.getValueAsString();
			metadata.addUserDefinedProperty("Requestid", requestId);

			// Extract Userid
			MbElement userIdObj = httpHeader.getFirstElementByPath("Userid");
			String userId = userIdObj.getValueAsString();
			metadata.addUserDefinedProperty("Userid", userId);

			// Extract Requestid
			MbElement acceptObj = httpHeader.getFirstElementByPath("Accept");
			String accept = acceptObj.getValueAsString();
			metadata.addUserDefinedProperty("Accept", accept);

			MbElement userAgentObj = httpHeader
					.getFirstElementByPath("User-Agent");
			String userAgent = userAgentObj.getValueAsString();
			String uAgentStr = userAgent.substring(0, userAgent.indexOf("/"));

			MbElement queryStringObj = httpHeader
					.getFirstElementByPath("X-Query-String");
			String queryString = queryStringObj.getValueAsString();
			logger.info("queryString:" + queryString);
			String startDate = null, endDate = null, count = null, pagingToken = null;
			StringTokenizer st = new StringTokenizer(queryString, "&");
			while (st.hasMoreTokens()) {
				String tokenString = st.nextToken().toString();
				if (null != tokenString && tokenString.contains("count=")) {
					count = tokenString.substring(6);
				}
				if (null != tokenString && tokenString.contains("startDate=")) {
					startDate = tokenString.substring(10);
				}
				if (null != tokenString && tokenString.contains("endDate")) {
					endDate = tokenString.substring(8);
				}
				if (null != tokenString && tokenString.contains("pagingToken=")) {
					pagingToken = tokenString.substring(12);
				}
			}

			metadata.addUserDefinedProperty("startDate", startDate);
			metadata.addUserDefinedProperty("endDate", endDate);
			metadata.addUserDefinedProperty("count", count);
			metadata.addUserDefinedProperty("pagingToken", pagingToken);
			metadata.addUserDefinedProperty("userAgent", uAgentStr);

			/*
			 * SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			 * String dateStr=format.format(new Date());
			 * if(startDate.equalsIgnoreCase(dateStr)){ getTransformer(); }
			 */
			MbElement reqId = outAssembly
					.getLocalEnvironment()
					.getRootElement()
					.getFirstElementByPath(
							"/Destination/HTTP/RequestIdentifier");
			CacheHandlerFactory.getInstance().updateCache(
					CacheHandlerFactory.TransactionIdCache,
					reqId.getValueAsString().concat("qString"), queryString);
			CacheHandlerFactory.getInstance().updateCache(
					CacheHandlerFactory.TransactionIdCache,
					reqId.getValueAsString().concat("acctidbefore"), acctIdBef);
			CacheHandlerFactory.getInstance().updateCache(
					CacheHandlerFactory.TransactionIdCache,
					reqId.getValueAsString().concat("acctidafter"), acctIdBef);
			CacheHandlerFactory.getInstance().updateCache(
					CacheHandlerFactory.TransactionIdCache,
					reqId.getValueAsString().concat("deviceId"), deviceId);

			CacheHandlerFactory.getInstance().updateCache(
					CacheHandlerFactory.TransactionIdCache,
					reqId.getValueAsString().concat("clientIp"), clientIp);
			CacheHandlerFactory.getInstance().updateCache(
					CacheHandlerFactory.TransactionIdCache,
					reqId.getValueAsString().concat("requestId"), requestId);
			CacheHandlerFactory.getInstance().updateCache(
					CacheHandlerFactory.TransactionIdCache,
					reqId.getValueAsString().concat("userId"), userId);
			CacheHandlerFactory.getInstance().updateCache(
					CacheHandlerFactory.TransactionIdCache,
					reqId.getValueAsString().concat("accept"), accept);
			CacheHandlerFactory.getInstance().updateCache(
					CacheHandlerFactory.TransactionIdCache,
					reqId.getValueAsString().concat("uAgentStr"), uAgentStr);
		}

	}

	@Override
	public TransformType getTransformationType() {
		// TODO Auto-generated method stub
		return TransformType.HTTP_MQ;
	}

	/**
	 * Get the external transformer class instance
	 * 
	 * @return
	 */
	@Override
	public void execute(MbMessageAssembly inAssembly,
			MbMessageAssembly outAssembly)
			throws Exception {
		MbOutputTerminal out = getOutputTerminal("out");
		MbOutputTerminal alt = getOutputTerminal("alternate");
		
		ComputeInfo compInfo = getMetaData();
		String startDate = compInfo.getUserDefinedProperties().get("startDate");
		String endDate = compInfo.getUserDefinedProperties().get("endDate");
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate;
		currentDate = format.format(new Date());
		//setGlobalEnvironment(outAssembly, compInfo);
		if (!startDate.equals(currentDate)) {
			compInfo.addUserDefinedProperty("cisFlag", "T");
			setGlobalEnvironment(outAssembly, compInfo);
			compInfo.setOutputTarget(OutputTarget.DEFAULT);
		}
		if ((startDate.equals(currentDate) && endDate.equals(currentDate))) {
			compInfo.addUserDefinedProperty("cisFlag", "F");
			setGlobalEnvironment(outAssembly, compInfo);
			compInfo.setOutputTarget(OutputTarget.ALTERNATE);
			
		}

	}
	
	public void setGlobalEnvironment(MbMessageAssembly outAssembly, ComputeInfo metadata) throws MbException{
		 Iterator<Entry<String, String>> it = metadata.getUserDefinedProperties().entrySet().iterator();
		 MbElement parent = outAssembly.getGlobalEnvironment().getRootElement().createElementAsLastChild(MbElement.TYPE_NAME, "ComputeInfo", null);   
		 while (it.hasNext()) {
		        Map.Entry<String, String> pair = (Map.Entry<String,String>)it.next();
		        parent.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, pair.getKey(),pair.getValue());
		    }
	}
}
