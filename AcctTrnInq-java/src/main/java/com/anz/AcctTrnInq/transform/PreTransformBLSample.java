/**
 * 
 */
package com.anz.AcctTrnInq.transform;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.anz.AcctTrnInq.transform.pojo.ANZAcctIdType;
import com.anz.AcctTrnInq.transform.pojo.ANZTraceInfoType;
import com.anz.AcctTrnInq.transform.pojo.AcctTrnInqRq;
import com.anz.AcctTrnInq.transform.pojo.CustIdType;
import com.anz.AcctTrnInq.transform.pojo.RecCtrlInType;
import com.anz.AcctTrnInq.transform.pojo.SelRangeDtType;
import com.anz.common.cache.impl.CacheHandlerFactory;
import com.anz.common.compute.ComputeInfo;
import com.anz.common.domain.CIMREFDomain;
import com.anz.common.transform.ITransformer;
import com.anz.common.transform.TransformUtils;


/**
 * @author sanketsw
 * 
 */
public class PreTransformBLSample implements ITransformer<String, String> {

	private static final Logger logger = LogManager.getLogger();
		
	/* (non-Javadoc)
	 * @see com.anz.common.transform.IJsonJsonTransformer#execute(java.lang.String)
	 * 
	 */
	public String execute(String inputJson,Logger appLogger,ComputeInfo metadata) throws Exception {
		
		AcctTrnInqRq request=new AcctTrnInqRq();
		
		ANZTraceInfoType traceInfoType=new ANZTraceInfoType();
		
		Date effDate=new Date();
		
		XMLGregorianCalendar calendar = getDateFormat(effDate);
		
		traceInfoType.setEffDt(calendar);
		
		//Mapping TBD
		//traceInfoType.setInitCompany(arg0);
		
		//Mapping TBD
		//traceInfoType.setOperatorId(arg0);
		
		//CacheHandlerFactory.getInstance().lookupCache(CacheHandlerFactory.MessageHeaderCache, objectKey)
		Map map=metadata.getUserDefinedProperties();
		
		traceInfoType.setBranchId(Integer.parseInt(map.get("acctidbefore").toString()));
		
		traceInfoType.setTerminalId(map.get("Deviceid").toString());
		
		if (null!= CIMREFDomain.getInstance().getCIMREFValue("IIB.OrigAppl", "goMoney").getValue()){
		
			String OrgApp = CIMREFDomain.getInstance().getCIMREFValue("IIB.OrigAppl", "goMoney").getValue();
		
			traceInfoType.setOrigApp(OrgApp);
		}
		//traceInfoType.setOrigApp("CIM");
		traceInfoType.setIPAddr(map.get("Client-Ip").toString());
		
		
		request.setANZTraceInfo(traceInfoType);
		request.setRqUID(map.get("Requestid").toString());
		
		ANZAcctIdType acctIdType=new ANZAcctIdType();
		acctIdType.setAcctId(map.get("acctidafter").toString());
		acctIdType.setAcctType("Type");
		
		CustIdType custIdType=new CustIdType();
		custIdType.setCustPermId(map.get("Userid").toString());
		
		SelRangeDtType rangeDtType= new SelRangeDtType();
		
		//Set startDate
		String strDate = map.get("startDate").toString();
		Date startDate =convertStringToDate(strDate);
		rangeDtType.setStartDt(getDateFormat(startDate));
		
		//Set endDate
		String eDate = map.get("endDate").toString();
		Date endDate =convertStringToDate(eDate);
		rangeDtType.setEndDt(getDateFormat(endDate));
		
		request.setIncDetail(new Boolean("true"));
		
		RecCtrlInType ctrlInType=new RecCtrlInType();
		ctrlInType.setMaxRec(Integer.parseInt(map.get("count").toString()));
		
		request.setSelRangeDt(rangeDtType);
		request.setCustId(custIdType);
		request.setANZAcctId(acctIdType);
		request.setRecCtrlIn(ctrlInType);
		
		String out = TransformUtils.convertToXml(request, AcctTrnInqRq.class);        
		return out;
	}

	/**
	 * @return
	 * @throws DatatypeConfigurationException
	 */
	private XMLGregorianCalendar getDateFormat(Date date)
			throws DatatypeConfigurationException {
		GregorianCalendar gregory = new GregorianCalendar();
		gregory.setTime(date);
		
		XMLGregorianCalendar calendar = DatatypeFactory.newInstance()
		        .newXMLGregorianCalendar(
		            gregory);
		return calendar;
	}
	
	/**
	 * @param dateString
	 * @return
	 */
	public Date convertStringToDate(String dateString)
	{
	    Date date = null;
	    DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
	    try{
	        date = df.parse(dateString);
	    }
	    catch ( Exception ex ){
	    }
	    return date;
	}
	
}
	

	
