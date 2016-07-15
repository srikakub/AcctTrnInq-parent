/**
 * 
 */
package com.anz.AcctTrnInq.transform;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.anz.cobolTransform.transform.pojo.AcctTrnInqCTMReq;
import com.anz.cobolTransform.transform.pojo.AcctTrnInqCTMReq.InputHeader;
import com.anz.cobolTransform.transform.pojo.AcctTrnInqCTMReq.SecondaryHeader;
import com.anz.cobolTransform.transform.pojo.AcctTrnInqCTMReq.ServiceRequestMsg;
import com.anz.cobolTransform.transform.pojo.InputHTTPHeaders;
import com.anz.common.compute.ComputeInfo;
import com.anz.common.domain.CIMREFDomain;
import com.anz.common.transform.ITransformer;

/**
 * @author sanketsw
 * 
 */
public class HttpToCobolTransformer implements
		ITransformer<InputHTTPHeaders, AcctTrnInqCTMReq> {
	private static final Logger logger = LogManager.getLogger();

	@Override
	public AcctTrnInqCTMReq execute(InputHTTPHeaders input, Logger appLogger,
			ComputeInfo metadata) throws Exception {

		AcctTrnInqCTMReq CTMReq = new AcctTrnInqCTMReq();
		CTMReq.setServiceRequest("PCTM-TDS-TXN-TODAY-REQ");

		InputHeader inputHdr = new InputHeader();
		inputHdr.setServiceRequest2("PCTM-TDS-TXN-TODAY-REQ");
		inputHdr.setVersionNumber(02);

		Date curDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String cDate = sdf.format(curDate);
		
		
		inputHdr.setEffectiveDate(Long.parseLong(cDate));
		inputHdr.setInitiatedDate(Long.parseLong(cDate));
		
		 Date date = new Date();
		    SimpleDateFormat timeSDF;
		    timeSDF = new SimpleDateFormat("hhmmssSS");		    
		    
		//inputHdr.setInitiatedTime(Long.parseLong(timeSDF.format(date)));
		    inputHdr.setInitiatedTime(Long.parseLong("121212"));
		// TBD
		inputHdr.setInitiatingCo(00010);
		inputHdr.setOperator("FINACLEU");
		inputHdr.setOperatorBranch(Long.parseLong((input.getAcctidBefore())));
		inputHdr.setWorkstationId(input.getUserAgent());
		
		if (null!= CIMREFDomain.getInstance().getCIMREFValue("IIB.OrigAppl", "goMoney").getValue()){
			
			String OrgApp = CIMREFDomain.getInstance().getCIMREFValue("IIB.OrigAppl", "goMoney").getValue();
		
			inputHdr.setOriginatingApplication(OrgApp);
		}
		
		//inputHdr.setOriginatingApplication("CIM");

		SecondaryHeader secHdr = new SecondaryHeader();
		secHdr.setProcessingApplication("TBC");
		secHdr.setAccountNumber(input.getAcctidAfter());
		secHdr.setLinkageNumber(0);

		ServiceRequestMsg servReqMsg = new ServiceRequestMsg();

		if (null != input.getCount()) {
			int count = Integer.parseInt(input.getCount());
			if (count > 50) {
				servReqMsg.setNoToRetrieve(50);
			} else {
				servReqMsg.setNoToRetrieve(count);
			}
		}
		servReqMsg.setNextTransactionStart(00);
		servReqMsg.setTransactionProcessDate(0);

		CTMReq.setServiceRequestMsg(servReqMsg);
		CTMReq.setSecondaryHeader(secHdr);
		CTMReq.setInputHeader(inputHdr);

		return CTMReq;
	}

}
