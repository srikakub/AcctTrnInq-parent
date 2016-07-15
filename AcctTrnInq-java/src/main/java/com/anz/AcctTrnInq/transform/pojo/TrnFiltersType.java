//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.06.21 at 08:58:39 AM IST 
//


package com.anz.AcctTrnInq.transform.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.anz.AcctTrnInq.transform.pojo.runtime.ZeroOneBooleanAdapter;


/**
 * <p>Java class for TrnFiltersType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TrnFiltersType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DebitsOnly" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}boolean">
 *               &lt;pattern value="0|1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="CreditsOnly" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}boolean">
 *               &lt;pattern value="0|1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="NoBookOnly" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}boolean">
 *               &lt;pattern value="0|1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="CycledOnly" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}boolean">
 *               &lt;pattern value="0|1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="NonCycledOnly" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}boolean">
 *               &lt;pattern value="0|1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ValueOnly" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}boolean">
 *               &lt;pattern value="0|1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="StmtOnly" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}boolean">
 *               &lt;pattern value="0|1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="MonetaryOnly" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}boolean">
 *               &lt;pattern value="0|1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="NonMonetaryOnly" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}boolean">
 *               &lt;pattern value="0|1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="OtherOnly" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}boolean">
 *               &lt;pattern value="0|1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Descending" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}boolean">
 *               &lt;pattern value="0|1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TrnFiltersType", propOrder = {
    "debitsOnly",
    "creditsOnly",
    "noBookOnly",
    "cycledOnly",
    "nonCycledOnly",
    "valueOnly",
    "stmtOnly",
    "monetaryOnly",
    "nonMonetaryOnly",
    "otherOnly",
    "descending"
})
public class TrnFiltersType {

    @XmlElement(name = "DebitsOnly", type = String.class)
    @XmlJavaTypeAdapter(ZeroOneBooleanAdapter.class)
    protected Boolean debitsOnly;
    @XmlElement(name = "CreditsOnly", type = String.class)
    @XmlJavaTypeAdapter(ZeroOneBooleanAdapter.class)
    protected Boolean creditsOnly;
    @XmlElement(name = "NoBookOnly", type = String.class)
    @XmlJavaTypeAdapter(ZeroOneBooleanAdapter.class)
    protected Boolean noBookOnly;
    @XmlElement(name = "CycledOnly", type = String.class)
    @XmlJavaTypeAdapter(ZeroOneBooleanAdapter.class)
    protected Boolean cycledOnly;
    @XmlElement(name = "NonCycledOnly", type = String.class)
    @XmlJavaTypeAdapter(ZeroOneBooleanAdapter.class)
    protected Boolean nonCycledOnly;
    @XmlElement(name = "ValueOnly", type = String.class)
    @XmlJavaTypeAdapter(ZeroOneBooleanAdapter.class)
    protected Boolean valueOnly;
    @XmlElement(name = "StmtOnly", type = String.class)
    @XmlJavaTypeAdapter(ZeroOneBooleanAdapter.class)
    protected Boolean stmtOnly;
    @XmlElement(name = "MonetaryOnly", type = String.class)
    @XmlJavaTypeAdapter(ZeroOneBooleanAdapter.class)
    protected Boolean monetaryOnly;
    @XmlElement(name = "NonMonetaryOnly", type = String.class)
    @XmlJavaTypeAdapter(ZeroOneBooleanAdapter.class)
    protected Boolean nonMonetaryOnly;
    @XmlElement(name = "OtherOnly", type = String.class)
    @XmlJavaTypeAdapter(ZeroOneBooleanAdapter.class)
    protected Boolean otherOnly;
    @XmlElement(name = "Descending", type = String.class)
    @XmlJavaTypeAdapter(ZeroOneBooleanAdapter.class)
    protected Boolean descending;

    /**
     * Gets the value of the debitsOnly property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Boolean isDebitsOnly() {
        return debitsOnly;
    }

    /**
     * Sets the value of the debitsOnly property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDebitsOnly(Boolean value) {
        this.debitsOnly = value;
    }

    /**
     * Gets the value of the creditsOnly property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Boolean isCreditsOnly() {
        return creditsOnly;
    }

    /**
     * Sets the value of the creditsOnly property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditsOnly(Boolean value) {
        this.creditsOnly = value;
    }

    /**
     * Gets the value of the noBookOnly property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Boolean isNoBookOnly() {
        return noBookOnly;
    }

    /**
     * Sets the value of the noBookOnly property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNoBookOnly(Boolean value) {
        this.noBookOnly = value;
    }

    /**
     * Gets the value of the cycledOnly property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Boolean isCycledOnly() {
        return cycledOnly;
    }

    /**
     * Sets the value of the cycledOnly property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCycledOnly(Boolean value) {
        this.cycledOnly = value;
    }

    /**
     * Gets the value of the nonCycledOnly property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Boolean isNonCycledOnly() {
        return nonCycledOnly;
    }

    /**
     * Sets the value of the nonCycledOnly property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNonCycledOnly(Boolean value) {
        this.nonCycledOnly = value;
    }

    /**
     * Gets the value of the valueOnly property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Boolean isValueOnly() {
        return valueOnly;
    }

    /**
     * Sets the value of the valueOnly property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValueOnly(Boolean value) {
        this.valueOnly = value;
    }

    /**
     * Gets the value of the stmtOnly property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Boolean isStmtOnly() {
        return stmtOnly;
    }

    /**
     * Sets the value of the stmtOnly property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStmtOnly(Boolean value) {
        this.stmtOnly = value;
    }

    /**
     * Gets the value of the monetaryOnly property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Boolean isMonetaryOnly() {
        return monetaryOnly;
    }

    /**
     * Sets the value of the monetaryOnly property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMonetaryOnly(Boolean value) {
        this.monetaryOnly = value;
    }

    /**
     * Gets the value of the nonMonetaryOnly property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Boolean isNonMonetaryOnly() {
        return nonMonetaryOnly;
    }

    /**
     * Sets the value of the nonMonetaryOnly property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNonMonetaryOnly(Boolean value) {
        this.nonMonetaryOnly = value;
    }

    /**
     * Gets the value of the otherOnly property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Boolean isOtherOnly() {
        return otherOnly;
    }

    /**
     * Sets the value of the otherOnly property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtherOnly(Boolean value) {
        this.otherOnly = value;
    }

    /**
     * Gets the value of the descending property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Boolean isDescending() {
        return descending;
    }

    /**
     * Sets the value of the descending property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescending(Boolean value) {
        this.descending = value;
    }

}