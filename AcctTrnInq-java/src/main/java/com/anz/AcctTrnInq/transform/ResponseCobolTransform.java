package com.anz.AcctTrnInq.transform;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.anz.cobolTransform.transform.pojo.AcctTrnInqCTMRsp;
import com.anz.cobolTransform.transform.pojo.AcctTrnInqCTMRsp.OutputHeader;
import com.anz.cobolTransform.transform.pojo.AcctTrnInqCTMRsp.SecondaryHdr;
import com.anz.cobolTransform.transform.pojo.AcctTrnInqCTMRsp.ServiceResponseMsg;
import com.anz.cobolTransform.transform.pojo.AcctTrnInqCTMRsp.ServiceResponseMsg.OutputDetails;
import com.anz.common.cache.impl.CacheHandlerFactory;
import com.anz.common.compute.ComputeInfo;
import com.anz.common.compute.TransformType;
import com.anz.common.compute.impl.CommonBlobTransformCompute;
import com.anz.common.compute.impl.ComputeUtils;
import com.anz.common.transform.ITransformer;
import com.anz.common.transform.TransformUtils;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;

public class ResponseCobolTransform extends CommonBlobTransformCompute {

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

		String cisStr = null;
		if (null != CacheHandlerFactory.getInstance().lookupCache(
				"TransactionIdCache", reqId + "cisStr")) {
			cisStr = CacheHandlerFactory.getInstance().lookupCache(
					"TransactionIdCache", reqId + "cisStr");
		}
		if (null != cisStr) {
			getMetaData().addUserDefinedProperty("CISMsg", cisStr);
		}

		MbElement ctmDFDLObj = inMessage.getRootElement()
				.getFirstElementByPath("/DFDL");

		String outXmlObj = null;

		if (null != ctmDFDLObj
				&& !("".equalsIgnoreCase(ctmDFDLObj.getValueAsString()))) {

			AcctTrnInqCTMRsp ctmResponse = new AcctTrnInqCTMRsp();
			MbElement serviceNameObj = ctmDFDLObj
					.getFirstElementByPath("AcctTrnInqCTMRsp/ServiceName");
			String serviceName = serviceNameObj.getValueAsString();

			ctmResponse.setServiceName(serviceName);
			OutputHeader outputHdr = new OutputHeader();

			MbElement outputHeader = ctmDFDLObj
					.getFirstElementByPath("AcctTrnInqCTMRsp/OutputHeader");

			MbElement serviceResponseObj = outputHeader
					.getFirstElementByPath("ServiceResponse");
			String serviceResponse = serviceResponseObj.getValueAsString();
			outputHdr.setServiceResponse(serviceResponse);

			MbElement versionNumberObj = outputHeader
					.getFirstElementByPath("VersionNumber");
			String versionNumber = versionNumberObj.getValueAsString();
			outputHdr.setVersionNumber(Integer.parseInt(versionNumber));

			MbElement dataLengthObj = outputHeader
					.getFirstElementByPath("DataLength");
			String dataLength = dataLengthObj.getValueAsString();
			outputHdr.setDataLength(Long.parseLong(dataLength));

			MbElement serviceResultCodeObj = outputHeader
					.getFirstElementByPath("ServiceResultCode");
			String serviceResultCode = serviceResultCodeObj.getValueAsString();
			outputHdr.setServiceResultCode(Integer.parseInt(serviceResultCode));

			MbElement erroMessageObj = outputHeader
					.getFirstElementByPath("ErroMessage");
			String erroMessage = erroMessageObj.getValueAsString();
			outputHdr.setErroMessage(erroMessage);

			MbElement conditionCodeObj = outputHeader
					.getFirstElementByPath("ConditionCode");
			String conditionCode = conditionCodeObj.getValueAsString();
			outputHdr.setConditionCode(Long.parseLong(conditionCode));
			ctmResponse.setOutputHeader(outputHdr);

			MbElement secondaryHdr = ctmDFDLObj
					.getFirstElementByPath("AcctTrnInqCTMRsp/SecondaryHdr");

			SecondaryHdr secHdr = new SecondaryHdr();

			MbElement processingApplicationObj = secondaryHdr
					.getFirstElementByPath("ProcessingApplication");
			String processingApplication = processingApplicationObj
					.getValueAsString();
			secHdr.setProcessingApplication(processingApplication);

			MbElement accountNumberObj = secondaryHdr
					.getFirstElementByPath("AccountNumber");
			String accountNumber = accountNumberObj.getValueAsString();
			secHdr.setAccountNumber(accountNumber);

			MbElement registrationNumberObj = secondaryHdr
					.getFirstElementByPath("RegistrationNumber");
			String registrationNumber = registrationNumberObj
					.getValueAsString();
			secHdr.setRegistrationNumber(registrationNumber);

			MbElement linkageNumberObj = secondaryHdr
					.getFirstElementByPath("LinkageNumber");
			String linkageNumber = linkageNumberObj.getValueAsString();
			secHdr.setLinkageNumber(Integer.parseInt(linkageNumber));

			MbElement serviceResponseMsg = ctmDFDLObj
					.getFirstElementByPath("AcctTrnInqCTMRsp/ServiceResponseMsg");

			ServiceResponseMsg serviceRspMsg = new ServiceResponseMsg();

			MbElement nextTransactionStartObj = serviceResponseMsg
					.getFirstElementByPath("NextTransactionStart");
			String nextTransactionStart = nextTransactionStartObj
					.getValueAsString();
			serviceRspMsg.setNextTransactionStart(Integer
					.parseInt(nextTransactionStart));

			MbElement numberOfTransactionsObj = serviceResponseMsg
					.getFirstElementByPath("NumberOfTransactions");
			String numberOfTransactions = numberOfTransactionsObj
					.getValueAsString();
			serviceRspMsg.setNumberOfTransactions(Integer
					.parseInt(numberOfTransactions));

			List<OutputDetails> outputDetails = new ArrayList<OutputDetails>();

			MbElement[] outputDetailsXPath = ctmDFDLObj
					.getAllElementsByPath("AcctTrnInqCTMRsp/ServiceResponseMsg/OutputDetails");
			// outputDetailsXPath.get

			OutputDetails outputDetail = null;
			for (int i = 0; i < serviceRspMsg.getNumberOfTransactions(); i++) {
				outputDetail = new OutputDetails();

				MbElement transactionDateV2Obj = outputDetailsXPath[i]
						.getFirstElementByPath("TransactionDateV2");
				String transactionDateV2 = transactionDateV2Obj
						.getValueAsString();
				outputDetail.setTransactionDateV2(Long
						.parseLong(transactionDateV2));

				MbElement transactionTimeV2Obj = outputDetailsXPath[i]
						.getFirstElementByPath("TransactionTimeV2");
				String transactionTimeV2 = transactionTimeV2Obj
						.getValueAsString();
				outputDetail.setTransactionTimeV2(Long
						.parseLong(transactionTimeV2));

				MbElement TransactionSRCECODEV2Obj = outputDetailsXPath[i]
						.getFirstElementByPath("TransactionSRCECODEV2");
				String transactionSRCECODEV2 = TransactionSRCECODEV2Obj
						.getValueAsString();
				outputDetail.setTransactionSRCECODEV2(transactionSRCECODEV2);

				MbElement transactionSRCEV2Obj = outputDetailsXPath[i]
						.getFirstElementByPath("TransactionSRCEV2");
				String transactionSRCEV2 = transactionSRCEV2Obj
						.getValueAsString();
				outputDetail.setTransactionSRCEV2(transactionSRCEV2);

				MbElement transactionAMTV2Obj = outputDetailsXPath[i]
						.getFirstElementByPath("TransactionAMTV2");
				String transactionAMTV2 = transactionAMTV2Obj
						.getValueAsString();
				outputDetail.setTransactionAMTV2(new BigDecimal(
						transactionAMTV2));
				// outputDetail.setTransactionAMTV2(new
				// BigDecimal().valueOf(Long.parseLong(transactionAMTV2)));

				MbElement transactionCRINDV2Obj = outputDetailsXPath[i]
						.getFirstElementByPath("TransactionCRINDV2");
				String transactionCRINDV2 = transactionCRINDV2Obj
						.getValueAsString();
				outputDetail.setTransactionCRINDV2(transactionCRINDV2);

				MbElement transactionTypeCodeV2Obj = outputDetailsXPath[i]
						.getFirstElementByPath("TransactionTypeCodeV2");
				String transactionTypeCodeV2 = transactionTypeCodeV2Obj
						.getValueAsString();
				outputDetail.setTransactionTypeCodeV2(Integer
						.parseInt(transactionTypeCodeV2));

				MbElement transactionTypeV2Obj = outputDetailsXPath[i]
						.getFirstElementByPath("TransactionTypeV2");
				String transactionTypeV2 = transactionTypeV2Obj
						.getValueAsString();
				outputDetail.setTransactionTypeV2(transactionTypeV2);

				MbElement transactionBranchV2Obj = outputDetailsXPath[i]
						.getFirstElementByPath("TransactionBranchV2");
				String transactionBranchV2 = transactionBranchV2Obj
						.getValueAsString();
				outputDetail.setTransactionBranchV2(Integer
						.parseInt(transactionBranchV2));

				MbElement transactionNarrativeV2Obj = outputDetailsXPath[i]
						.getFirstElementByPath("TransactionNarrativeV2");
				String transactionNarrativeV2 = transactionNarrativeV2Obj
						.getValueAsString();
				outputDetail.setTransactionNarrativeV2(transactionNarrativeV2);

				MbElement transactionSerialNoV2Obj = outputDetailsXPath[i]
						.getFirstElementByPath("TransactionSerialNoV2");
				String transactionSerialNoV2 = transactionSerialNoV2Obj
						.getValueAsString();
				outputDetail.setTransactionSerialNoV2(Long
						.parseLong(transactionSerialNoV2));

				MbElement transactionSourceV2Obj = outputDetailsXPath[i]
						.getFirstElementByPath("TransactionSourceV2");
				String transactionSourceV2 = transactionSourceV2Obj
						.getValueAsString();
				outputDetail.setTransactionSourceV2(transactionSourceV2);

				MbElement transactionEffDateV2Obj = outputDetailsXPath[i]
						.getFirstElementByPath("TransactionEffDateV2");
				String transactionEffDateV2 = transactionEffDateV2Obj
						.getValueAsString();
				outputDetail.setTransactionEffDateV2(Long
						.parseLong(transactionEffDateV2));

				serviceRspMsg.getOutputDetails().add(outputDetail);
			}

			ctmResponse.setSecondaryHdr(secHdr);

			ctmResponse.setServiceResponseMsg(serviceRspMsg);

			outXmlObj = TransformUtils.convertToXml(ctmResponse,
					AcctTrnInqCTMRsp.class);
			getMetaData().addUserDefinedProperty("CTMMsg", outXmlObj);
		}

		ITransformer<String, String> stringTransformer = getTransformer();
		String outputString = null;
		if (null != outXmlObj) {

			outputString = stringTransformer.execute(null, appLogger,
					getMetaData());
		}

		if (outputString != null) {
			// Write this outputJson to outMessage
			ComputeUtils.replaceStringAsBlob(outMessage, outputString);
		}

		// Cleaning the cache after creating transformed output message
		getMetaData().getUserDefinedProperties().remove("CTMMsg");
		getMetaData().getUserDefinedProperties().remove("CISMsg");
	
	}

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
		ComputeUtils.removeElementFromTree(outAssembly.getMessage(), "DFDL");
		
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

	}

	public TransformType getTransformationType() {
		return TransformType.UNSPECIFIED;
	}

}
