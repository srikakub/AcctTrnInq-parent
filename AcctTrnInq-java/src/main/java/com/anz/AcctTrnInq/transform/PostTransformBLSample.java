/**
 * 
 */
package com.anz.AcctTrnInq.transform;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.anz.AcctTrnInq.json.pojo.JsonToPojo;
import com.anz.AcctTrnInq.json.pojo.Pagination;
import com.anz.AcctTrnInq.json.pojo.Transaction;
import com.anz.AcctTrnInq.transform.pojo.AcctTrnInqRs;
import com.anz.AcctTrnInq.transform.pojo.BankAcctTrnRecType;
import com.anz.cobolTransform.transform.pojo.AcctTrnInqCTMRsp;
import com.anz.cobolTransform.transform.pojo.AcctTrnInqCTMRsp.ServiceResponseMsg.OutputDetails;
import com.anz.common.compute.ComputeInfo;
import com.anz.common.transform.ITransformer;
import com.anz.common.transform.TransformUtils;

/**
 * @author sanketsw
 * 
 */
public class PostTransformBLSample implements ITransformer<String, String> {

	private static final Logger logger = LogManager.getLogger();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.anz.common.transform.IJsonJsonTransformer#execute(java.lang.String)
	 */
	public String execute(String inputXml, Logger appLogger,
			ComputeInfo metadata) throws Exception {

		AcctTrnInqRs outXmlObj = null;
		List<Transaction> trnList = new ArrayList<Transaction>();
		int noonTrns = 0;
		List<OutputDetails> outputDetailsList = null;

		String cisMsg = metadata.getUserDefinedProperties().get("CISMsg");
		if (null != cisMsg) {
			List<BankAcctTrnRecType> trnRecList = null;

			

			outXmlObj = (AcctTrnInqRs) TransformUtils.convertFromXml(cisMsg,
					AcctTrnInqRs.class);
			
			if (null != outXmlObj.getBankAcctTrnRec()) {
				trnRecList = outXmlObj.getBankAcctTrnRec();
			}
			Transaction transactionJson = null;
			
			
			if (null != trnRecList && trnRecList.size() > 0) {
				for (int i = 0; i < trnRecList.size(); i++) {
					BankAcctTrnRecType bankRecType = (BankAcctTrnRecType) trnRecList
							.get(i);

					transactionJson = new Transaction();

					XMLGregorianCalendar calender = bankRecType.getTrnDt();

					// transaction.setTransactionDate(transactionDate)

					transactionJson.setType(bankRecType.getTrnType());
					transactionJson.setAmount(bankRecType.getAmt().doubleValue());
					transactionJson.setShortDescription(bankRecType.getDesc1());

					transactionJson.setDetailedDescription(bankRecType.getDesc1());
					if (bankRecType.getAmt().doubleValue() > 0) {
						transactionJson.setIsCredit(Boolean.TRUE);
					} else {
						transactionJson.setIsCredit(Boolean.FALSE);
					}
					if (null != bankRecType.getRunningBal()) {
						transactionJson.setRunningBalance(bankRecType
								.getRunningBal().intValue());
					}

					trnList.add(transactionJson);
				}
			}


		}

		String CTMMsg = metadata.getUserDefinedProperties().get("CTMMsg");
		AcctTrnInqCTMRsp ctmAcctTrnRsp = null;
		if (null != CTMMsg) {

			ctmAcctTrnRsp = (AcctTrnInqCTMRsp) TransformUtils.convertFromXml(
					CTMMsg, AcctTrnInqCTMRsp.class);
		}

		logger.info("Inside Post Transform");
		JsonToPojo jsonToPojo = new JsonToPojo();
		
		

		
		if (null != ctmAcctTrnRsp
				&& null != ctmAcctTrnRsp.getServiceResponseMsg()) {
			if (null != ctmAcctTrnRsp.getServiceResponseMsg()
					.getOutputDetails()
					&& null != ctmAcctTrnRsp.getServiceResponseMsg()) {
				Pagination pagination=new Pagination();
				pagination.setPagingToken("C"+String.valueOf(ctmAcctTrnRsp.getServiceResponseMsg().getNextTransactionStart()));
				
				jsonToPojo.setPagination(pagination);
				outputDetailsList = ctmAcctTrnRsp.getServiceResponseMsg()
						.getOutputDetails();
				noonTrns = ctmAcctTrnRsp.getServiceResponseMsg()
						.getOutputDetails().size();
			}
			Transaction transactionJson = null;
			for (int i = 0; i < noonTrns; i++) {
				transactionJson = new Transaction();
				OutputDetails outDetails = outputDetailsList.get(i);
				transactionJson.setTransactionDate(String.valueOf(outDetails
						.getTransactionDateV2()));
				transactionJson.setProcessedDate(String.valueOf(outDetails
						.getTransactionDateV2()));
				transactionJson.setType(String.valueOf(outDetails
						.getTransactionTypeCodeV2()));

				// transactionJson.setAmount(Double.valueOf(new
				// BigDecimal(outDetails.getTransactionAMTV2()));
				transactionJson.setShortDescription(outDetails
						.getTransactionTypeV2());
				transactionJson.setDetailedDescription(outDetails
						.getTransactionNarrativeV2());
				transactionJson.setIsCredit(Boolean.parseBoolean((outDetails
						.getTransactionNarrativeV2())));
				// transactionJson.setRunningBalance(Integer.parseInt(outDetails.getTransactionAMTV2()));
				trnList.add(transactionJson);
			}
		}
		
		
				jsonToPojo.setTransactions(trnList);

		// -----------------------------------------------------------------------------------------
		// User Code Below

		/*
		 * logger.info("Before transform: left = {}, right = {}",
		 * json.getLeft(), json.getRight());
		 * 
		 * //Example: Increase right by 100 json.setRight(json.getRight() +
		 * 100);
		 * 
		 * logger.info("After transform: left = {}, right = {}", json.getLeft(),
		 * json.getRight());
		 */

		String out = TransformUtils.toJSON(jsonToPojo);

		// End User Code
		// -----------------------------------------------------------------------------------------

		return out;
	}

}