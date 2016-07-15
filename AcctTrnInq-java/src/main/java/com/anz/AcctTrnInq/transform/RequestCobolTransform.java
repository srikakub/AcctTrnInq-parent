package com.anz.AcctTrnInq.transform;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;

import com.anz.AcctTrnInq.compute.AcctTrnInqUtility;
import com.anz.cobolTransform.transform.pojo.AcctTrnInqCTMReq;
import com.anz.cobolTransform.transform.pojo.InputHTTPHeaders;
import com.anz.common.compute.ComputeInfo;
import com.anz.common.compute.IParser;
import com.anz.common.compute.OutputTarget;
import com.anz.common.compute.TransformType;
import com.anz.common.compute.impl.CustomParserTransformCompute;
import com.anz.common.compute.impl.JaxbDFDLParser;
import com.anz.common.compute.impl.JsonBlobParser;
import com.anz.common.transform.ITransformer;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;

public class RequestCobolTransform extends
		CustomParserTransformCompute<InputHTTPHeaders, AcctTrnInqCTMReq> {

	@Override
	public TransformType getTransformationType() {
		return TransformType.HTTP_MQ;
	}

	@Override
	public IParser<InputHTTPHeaders> getInputParser() {
		return new JsonBlobParser<InputHTTPHeaders>(InputHTTPHeaders.class);
	}

	@Override
	public IParser<AcctTrnInqCTMReq> getOutputParser() {
		return new JaxbDFDLParser<AcctTrnInqCTMReq>(AcctTrnInqCTMReq.class);
	}

	@Override
	public ITransformer<InputHTTPHeaders, AcctTrnInqCTMReq> getTransformer() {
		return new HttpToCobolTransformer();
	}

	@Override
	public void execute(MbMessageAssembly inAssembly,
			MbMessageAssembly outAssembly) throws Exception {

		MbMessage inMessage = inAssembly.getMessage();
		MbMessage outMessage = outAssembly.getMessage();
		ComputeInfo info = getMetaData();
		info.setUserDefinedProperties(setGlobalEnvironment(inAssembly));
		/*
		 * MbMessage inMessage = inAssembly.getMessage(); MbMessage outMessage =
		 * outAssembly.getMessage();
		 */
		InputHTTPHeaders inputHdr = new InputHTTPHeaders();

		inputHdr.setAcctidBefore(info.getUserDefinedProperties().get(
				"acctidbefore"));
		inputHdr.setAcctidAfter(info.getUserDefinedProperties().get(
				"acctidafter"));
		inputHdr.setDeviceId(info.getUserDefinedProperties().get("Deviceid"));
		inputHdr.setClientIp(info.getUserDefinedProperties().get("Client-Ip"));
		inputHdr.setRequestId(info.getUserDefinedProperties().get("Requestid"));
		inputHdr.setUserId(info.getUserDefinedProperties().get("Userid"));
		inputHdr.setAccept(info.getUserDefinedProperties().get("Accept"));
		inputHdr.setUserAgent(info.getUserDefinedProperties().get("userAgent"));

		inputHdr.setStartDate(info.getUserDefinedProperties().get("startDate"));
		inputHdr.setEndDate(info.getUserDefinedProperties().get("endDate"));
		inputHdr.setCount(info.getUserDefinedProperties().get("count"));
		inputHdr.setPagingToken(info.getUserDefinedProperties().get(
				"pagingToken"));

		IParser<InputHTTPHeaders> inputParser = getInputParser();

		InputHTTPHeaders inputObject = inputParser.readInputMessage(
				jaxbContext, inputHdr);
		inputParser.removeMessageBody(outMessage);

		ITransformer<InputHTTPHeaders, AcctTrnInqCTMReq> transformer = getTransformer();
		AcctTrnInqCTMReq ouputObject = transformer.execute(inputObject,
				appLogger, getMetaData());

		IParser<AcctTrnInqCTMReq> outputParser = getOutputParser();
		outputParser.setOutputMessage(jaxbContext, outMessage, ouputObject);
		//getMetaData().setOutputTarget(OutputTarget.NONE);
		//MbOutputTerminal outputTerminal = getOutputTerminal("out");
		//outputTerminal.propagate(outAssembly);

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
