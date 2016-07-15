
package com.anz.AcctTrnInq.json.pojo;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class JsonToPojo {

    /**
     * 
     * (Required)
     * 
     */
    private List<Transaction> transactions = new ArrayList<Transaction>();
    /**
     * 
     * (Required)
     * 
     */
    private Pagination pagination;

    /**
     * 
     * (Required)
     * 
     * @return
     *     The transactions
     */
    public List<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * 
     * (Required)
     * 
     * @param transactions
     *     The Transactions
     */
    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The pagination
     */
    public Pagination getPagination() {
        return pagination;
    }

    /**
     * 
     * (Required)
     * 
     * @param pagination
     *     The Pagination
     */
    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

}
