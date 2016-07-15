
package com.anz.AcctTrnInq.json.pojo;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Transaction {

    /**
     * 
     * (Required)
     * 
     */
    private String transactionID;
    /**
     * 
     * (Required)
     * 
     */
    private String referenceNumber;
    /**
     * 
     * (Required)
     * 
     */
    private String transactionDate;
    /**
     * 
     * (Required)
     * 
     */
    private String processedDate;
    /**
     * 
     * (Required)
     * 
     */
    private String type;
    /**
     * 
     * (Required)
     * 
     */
    private Double amount;
    /**
     * 
     * (Required)
     * 
     */
    private String shortDescription;
    /**
     * 
     * (Required)
     * 
     */
    private String detailedDescription;
    /**
     * 
     * (Required)
     * 
     */
    private Boolean isCredit;
    /**
     * 
     * (Required)
     * 
     */
    private Integer runningBalance;

    /**
     * 
     * (Required)
     * 
     * @return
     *     The transactionID
     */
    public String getTransactionID() {
        return transactionID;
    }

    /**
     * 
     * (Required)
     * 
     * @param transactionID
     *     The transactionID
     */
    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The referenceNumber
     */
    public String getReferenceNumber() {
        return referenceNumber;
    }

    /**
     * 
     * (Required)
     * 
     * @param referenceNumber
     *     The referenceNumber
     */
    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The transactionDate
     */
    public String getTransactionDate() {
        return transactionDate;
    }

    /**
     * 
     * (Required)
     * 
     * @param transactionDate
     *     The transactionDate
     */
    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The processedDate
     */
    public String getProcessedDate() {
        return processedDate;
    }

    /**
     * 
     * (Required)
     * 
     * @param processedDate
     *     The processedDate
     */
    public void setProcessedDate(String processedDate) {
        this.processedDate = processedDate;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * (Required)
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The amount
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * 
     * (Required)
     * 
     * @param amount
     *     The amount
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The shortDescription
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * 
     * (Required)
     * 
     * @param shortDescription
     *     The shortDescription
     */
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The detailedDescription
     */
    public String getDetailedDescription() {
        return detailedDescription;
    }

    /**
     * 
     * (Required)
     * 
     * @param detailedDescription
     *     The detailedDescription
     */
    public void setDetailedDescription(String detailedDescription) {
        this.detailedDescription = detailedDescription;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The isCredit
     */
    public Boolean getIsCredit() {
        return isCredit;
    }

    /**
     * 
     * (Required)
     * 
     * @param isCredit
     *     The isCredit
     */
    public void setIsCredit(Boolean isCredit) {
        this.isCredit = isCredit;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The runningBalance
     */
    public Integer getRunningBalance() {
        return runningBalance;
    }

    /**
     * 
     * (Required)
     * 
     * @param runningBalance
     *     The runningBalance
     */
    public void setRunningBalance(Integer runningBalance) {
        this.runningBalance = runningBalance;
    }

}
