package com.anz.AcctTrnInq.transform;

import static org.junit.Assert.*;

import org.junit.Test;

import com.anz.AcctTrnInq.transform.pojo.NumbersInput;
import com.anz.common.transform.ITransformer;
import com.anz.common.transform.TransformUtils;
import com.ibm.broker.plugin.MbMessageAssembly;

public class PreTransformBLSampleTest {

	@Test
	public void testExexcute() throws Exception {
		String inputJson = "{ \"left\": 5, \"right\": 6}";
		
		//public abstract ITransformer<MbMessageAssembly, String> getTransformer();
		
		MbMessageAssembly assembly=null;
		String out = new PreTransformBLSample().execute(inputJson, null, null);
		assertNotNull(out);
		NumbersInput n = TransformUtils.fromJSON(out, NumbersInput.class);
		assertNotNull(n);
		assertEquals(105, n.getLeft());
	}
	
	@Test
	public void testExexcuteFailure() {
		String inputJson = "";
		@SuppressWarnings("unused")
		String out;
		Exception exception = null;
		MbMessageAssembly assembly=null;
		try {
			out = new PreTransformBLSample().execute(inputJson, null, null);
		} catch (Exception e) {
			exception = e;
		}
		assertNotNull(exception);
	}

}
