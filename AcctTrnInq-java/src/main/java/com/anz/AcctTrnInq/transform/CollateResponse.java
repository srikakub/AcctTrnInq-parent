package com.anz.AcctTrnInq.transform;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

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

public class CollateResponse extends CommonBlobTransformCompute {

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
		
		//MbElement corrId = outAssembly.getMessage().getRootElement().getFirstElementByPath("/MQMD/CorrelId");
		//String reqId = CacheHandlerFactory.getInstance().lookupCache("MessageHeaderCache", corrId.getValueAsString());
		String reqId = CacheHandlerFactory.getInstance().lookupCache("MessageHeaderCache", "1234");
		ComputeUtils.setElementInTree(hexStringToByteArray(reqId),outAssembly.getLocalEnvironment(), "Destination", "HTTP","RequestIdentifier");

		
		MbElement cisBlobObj = inMessage.getRootElement()
				.getFirstElementByPath("/BLOB");
		
		
		
		byte[] cisByte;
		MbElement cisBlob;
		String cisStr=null;
		
		if (null != cisBlobObj) {
			cisBlob = cisBlobObj.getLastChild();
			// String inputString =ComputeUtils.getStringFromBlob(inMessage);
			cisByte = (byte[]) cisBlob.getValue();

			cisStr = new String(cisByte);

			getMetaData().addUserDefinedProperty("CISMsg", cisStr);

		}
		
		// Document outDocument =
		// inMessage.createDOMDocument(MbXMLNSC.PARSER_NAME);

	
		String outXmlObj = null;
		
		
		

		// String inputString = ComputeUtils.getStringFromBlob(inMessage);
	

		ITransformer<String, String> stringTransformer = getTransformer();
		String outputString = null;
		if (null != cisStr ) {

			outputString = stringTransformer.execute(null, appLogger,
					getMetaData());
		}

		if (outputString != null) {
			// Write this outputJson to outMessage
			ComputeUtils.replaceStringAsBlob(outMessage, outputString);
		}
		
		
		// Cleaning the cache after creating transformed output message
		//getMetaData().getUserDefinedProperties().remove("CTMMsg");
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
		// ComputeUtils.setElementInTree("d63a8ffd-bdac-498c-b861-a53e11989cef",outAssembly.getLocalEnvironment(),"Destination",
		// "HTTP","RequestIdentifier");
		ComputeUtils.removeElementFromTree(outAssembly.getMessage(),
				"ComIbmAggregateReplyBody");
		ComputeUtils.setElementInTree(
				"application/vnd.mcp-anz.com+json;version=1.0",
				outAssembly.getMessage(), "Properties", "ContentType");
		ComputeUtils.setElementInTree("d63a8ffd-bdac-498c-b861-a53e11989cef",
				outAssembly.getMessage(), "Properties", "ReplyIdentifier");
		// ComputeUtils.setElementInTree("d63a8ffd-bdac-498c-b861-a53e11989cef",
		// outAssembly.getMessage(), "Properties", "RequestID");

	}

	public TransformType getTransformationType() {
		return TransformType.UNSPECIFIED;
	}

}
