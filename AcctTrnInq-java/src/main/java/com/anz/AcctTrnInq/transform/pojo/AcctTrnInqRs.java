//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.06.21 at 08:58:39 AM IST 
//


package com.anz.AcctTrnInq.transform.pojo;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.anz.AcctTrnInq.transform.pojo.runtime.ZeroOneBooleanAdapter;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Status" type="{}StatusType" minOccurs="0"/>
 *         &lt;element name="RqUID" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="36"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ANZAcctId" type="{}ANZAcctIdType"/>
 *         &lt;element name="CustId" type="{}CustIdType" minOccurs="0"/>
 *         &lt;element name="SelRangeDt" type="{}SelRangeDtType" minOccurs="0"/>
 *         &lt;element name="SelRangeChqNum" type="{}SelRangeChqNumType" minOccurs="0"/>
 *         &lt;element name="SelRangeCurAmt" type="{}SelRangeCurAmtType" minOccurs="0"/>
 *         &lt;element name="IncDetail" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}boolean">
 *               &lt;pattern value="0|1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="TrnFilters" type="{}TrnFiltersType" minOccurs="0"/>
 *         &lt;element name="RecCtrlOut" type="{}RecCtrlOutType" minOccurs="0"/>
 *         &lt;element name="BankAcctTrnRec" type="{}BankAcctTrnRecType" maxOccurs="150" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "status",
    "rqUID",
    "anzAcctId",
    "custId",
    "selRangeDt",
    "selRangeChqNum",
    "selRangeCurAmt",
    "incDetail",
    "trnFilters",
    "recCtrlOut",
    "bankAcctTrnRec"
})
@XmlRootElement(name = "AcctTrnInqRs")
public class AcctTrnInqRs {

    @XmlElement(name = "Status")
    protected StatusType status;
    @XmlElement(name = "RqUID")
    protected String rqUID;
    @XmlElement(name = "ANZAcctId", required = true)
    protected ANZAcctIdType anzAcctId;
    @XmlElement(name = "CustId")
    protected CustIdType custId;
    @XmlElement(name = "SelRangeDt")
    protected SelRangeDtType selRangeDt;
    @XmlElement(name = "SelRangeChqNum")
    protected SelRangeChqNumType selRangeChqNum;
    @XmlElement(name = "SelRangeCurAmt")
    protected SelRangeCurAmtType selRangeCurAmt;
    @XmlElement(name = "IncDetail", type = String.class)
    @XmlJavaTypeAdapter(ZeroOneBooleanAdapter.class)
    protected Boolean incDetail;
    @XmlElement(name = "TrnFilters")
    protected TrnFiltersType trnFilters;
    @XmlElement(name = "RecCtrlOut")
    protected RecCtrlOutType recCtrlOut;
    @XmlElement(name = "BankAcctTrnRec")
    protected List<BankAcctTrnRecType> bankAcctTrnRec;

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link StatusType }
     *     
     */
    public StatusType getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatusType }
     *     
     */
    public void setStatus(StatusType value) {
        this.status = value;
    }

    /**
     * Gets the value of the rqUID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRqUID() {
        return rqUID;
    }

    /**
     * Sets the value of the rqUID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRqUID(String value) {
        this.rqUID = value;
    }

    /**
     * Gets the value of the anzAcctId property.
     * 
     * @return
     *     possible object is
     *     {@link ANZAcctIdType }
     *     
     */
    public ANZAcctIdType getANZAcctId() {
        return anzAcctId;
    }

    /**
     * Sets the value of the anzAcctId property.
     * 
     * @param value
     *     allowed object is
     *     {@link ANZAcctIdType }
     *     
     */
    public void setANZAcctId(ANZAcctIdType value) {
        this.anzAcctId = value;
    }

    /**
     * Gets the value of the custId property.
     * 
     * @return
     *     possible object is
     *     {@link CustIdType }
     *     
     */
    public CustIdType getCustId() {
        return custId;
    }

    /**
     * Sets the value of the custId property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustIdType }
     *     
     */
    public void setCustId(CustIdType value) {
        this.custId = value;
    }

    /**
     * Gets the value of the selRangeDt property.
     * 
     * @return
     *     possible object is
     *     {@link SelRangeDtType }
     *     
     */
    public SelRangeDtType getSelRangeDt() {
        return selRangeDt;
    }

    /**
     * Sets the value of the selRangeDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link SelRangeDtType }
     *     
     */
    public void setSelRangeDt(SelRangeDtType value) {
        this.selRangeDt = value;
    }

    /**
     * Gets the value of the selRangeChqNum property.
     * 
     * @return
     *     possible object is
     *     {@link SelRangeChqNumType }
     *     
     */
    public SelRangeChqNumType getSelRangeChqNum() {
        return selRangeChqNum;
    }

    /**
     * Sets the value of the selRangeChqNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link SelRangeChqNumType }
     *     
     */
    public void setSelRangeChqNum(SelRangeChqNumType value) {
        this.selRangeChqNum = value;
    }

    /**
     * Gets the value of the selRangeCurAmt property.
     * 
     * @return
     *     possible object is
     *     {@link SelRangeCurAmtType }
     *     
     */
    public SelRangeCurAmtType getSelRangeCurAmt() {
        return selRangeCurAmt;
    }

    /**
     * Sets the value of the selRangeCurAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link SelRangeCurAmtType }
     *     
     */
    public void setSelRangeCurAmt(SelRangeCurAmtType value) {
        this.selRangeCurAmt = value;
    }

    /**
     * Gets the value of the incDetail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Boolean isIncDetail() {
        return incDetail;
    }

    /**
     * Sets the value of the incDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIncDetail(Boolean value) {
        this.incDetail = value;
    }

    /**
     * Gets the value of the trnFilters property.
     * 
     * @return
     *     possible object is
     *     {@link TrnFiltersType }
     *     
     */
    public TrnFiltersType getTrnFilters() {
        return trnFilters;
    }

    /**
     * Sets the value of the trnFilters property.
     * 
     * @param value
     *     allowed object is
     *     {@link TrnFiltersType }
     *     
     */
    public void setTrnFilters(TrnFiltersType value) {
        this.trnFilters = value;
    }

    /**
     * Gets the value of the recCtrlOut property.
     * 
     * @return
     *     possible object is
     *     {@link RecCtrlOutType }
     *     
     */
    public RecCtrlOutType getRecCtrlOut() {
        return recCtrlOut;
    }

    /**
     * Sets the value of the recCtrlOut property.
     * 
     * @param value
     *     allowed object is
     *     {@link RecCtrlOutType }
     *     
     */
    public void setRecCtrlOut(RecCtrlOutType value) {
        this.recCtrlOut = value;
    }

    /**
     * Gets the value of the bankAcctTrnRec property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bankAcctTrnRec property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBankAcctTrnRec().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BankAcctTrnRecType }
     * 
     * 
     */
    public List<BankAcctTrnRecType> getBankAcctTrnRec() {
        if (bankAcctTrnRec == null) {
            bankAcctTrnRec = new ArrayList<BankAcctTrnRecType>();
        }
        return this.bankAcctTrnRec;
    }

}
