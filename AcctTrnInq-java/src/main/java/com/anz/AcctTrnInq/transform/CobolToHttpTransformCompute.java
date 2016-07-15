package com.anz.AcctTrnInq.transform;
import com.anz.cobolTransform.transform.pojo.AcctTrnInqCTMRsp;
import com.anz.common.compute.IParser;
import com.anz.common.compute.TransformType;
import com.anz.common.compute.impl.CustomParserTransformCompute;
import com.anz.common.compute.impl.JaxbDFDLParser;
import com.anz.common.compute.impl.JsonBlobParser;
import com.anz.common.transform.ITransformer;


public class CobolToHttpTransformCompute extends CustomParserTransformCompute<AcctTrnInqCTMRsp, AcctTrnInqCTMRsp> {

	@Override
	public TransformType getTransformationType() {
		return TransformType.HTTP_MQ;
	}

	@Override
	public IParser<AcctTrnInqCTMRsp> getInputParser() {
		return new JaxbDFDLParser<AcctTrnInqCTMRsp>(AcctTrnInqCTMRsp.class);
	}

	@Override
	public IParser<AcctTrnInqCTMRsp> getOutputParser() {
		return new JsonBlobParser<AcctTrnInqCTMRsp>(AcctTrnInqCTMRsp.class);
	}

	@Override
	public ITransformer<AcctTrnInqCTMRsp, AcctTrnInqCTMRsp> getTransformer() {
		return new CobolToHttpTransformer();
	}

	
	

}
