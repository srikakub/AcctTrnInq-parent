package com.anz.AcctTrnInq.compute;

import java.util.StringTokenizer;

import com.anz.common.compute.ComputeInfo;

public class AcctTrnInqUtility {

	public static ComputeInfo getQueryStrDetails(String queryString) {
		ComputeInfo metaData = new ComputeInfo();
		StringTokenizer st = new StringTokenizer(queryString, "&");
		while (st.hasMoreTokens()) {
			String tokenString = st.nextToken().toString();
			if (null != tokenString && tokenString.contains("count=")) {
				metaData.addUserDefinedProperty("count",
						tokenString.substring(6));
			}
			if (null != tokenString && tokenString.contains("startDate=")) {
				metaData.addUserDefinedProperty("startDate",
						tokenString.substring(10));
			}
			if (null != tokenString && tokenString.contains("endDate")) {
				metaData.addUserDefinedProperty("endDate",
						tokenString.substring(8));
			}
			if (null != tokenString && tokenString.contains("pagingToken=")) {
				metaData.addUserDefinedProperty("pagingToken",
						tokenString.substring(12));
			}
		}
		return metaData;
	}

}
