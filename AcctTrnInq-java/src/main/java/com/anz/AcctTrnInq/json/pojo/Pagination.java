
package com.anz.AcctTrnInq.json.pojo;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Pagination {

    /**
     * 
     * (Required)
     * 
     */
    private String pagingToken;
    /**
     * 
     * (Required)
     * 
     */
    private Integer matchedRecords;

    /**
     * 
     * (Required)
     * 
     * @return
     *     The pagingToken
     */
    public String getPagingToken() {
        return pagingToken;
    }

    /**
     * 
     * (Required)
     * 
     * @param pagingToken
     *     The pagingToken
     */
    public void setPagingToken(String pagingToken) {
        this.pagingToken = pagingToken;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The matchedRecords
     */
    public Integer getMatchedRecords() {
        return matchedRecords;
    }

    /**
     * 
     * (Required)
     * 
     * @param matchedRecords
     *     The matchedRecords
     */
    public void setMatchedRecords(Integer matchedRecords) {
        this.matchedRecords = matchedRecords;
    }

}
