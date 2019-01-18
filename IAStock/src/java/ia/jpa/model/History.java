/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ia.jpa.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Acer_E5
 */
@Entity
@Table(name = "HISTORY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "History.findAll", query = "SELECT h FROM History h")
    , @NamedQuery(name = "History.findByHistoryid", query = "SELECT h FROM History h WHERE h.historyid = :historyid")
    , @NamedQuery(name = "History.findByQuantity", query = "SELECT h FROM History h WHERE h.quantity = :quantity")
    , @NamedQuery(name = "History.findByType", query = "SELECT h FROM History h WHERE h.type = :type")
    , @NamedQuery(name = "History.findByTimestamp", query = "SELECT h FROM History h WHERE h.timestamp = :timestamp")
    , @NamedQuery(name = "History.findByAnnotation", query = "SELECT h FROM History h WHERE h.annotation = :annotation")})
public class History implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "PRICE")
    private double price;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "HISTORYID")
    private Integer historyid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "QUANTITY")
    private int quantity;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "TYPE")
    private String type;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    @Size(max = 100)
    @Column(name = "ANNOTATION")
    private String annotation;
    @JoinColumn(name = "ITEMID", referencedColumnName = "ITEMID")
    @ManyToOne(optional = false)
    private Items itemid;
    @JoinColumn(name = "YEARSTOCK", referencedColumnName = "YEARSTOCK")
    @ManyToOne(optional = false)
    private Years yearstock;

    public History() {
    }

    public History(Integer historyid) {
        this.historyid = historyid;
    }

    public History(Integer historyid, int quantity, String type, Date timestamp) {
        this.historyid = historyid;
        this.quantity = quantity;
        this.type = type;
        this.timestamp = timestamp;
    }

    public History(Items itemid, double price, int quantity, String type, Date timestamp, Years yearstock) {
        this.itemid = itemid;
        this.price = price;
        this.quantity = quantity;
        this.type = type;
        this.timestamp = timestamp;
        this.yearstock = yearstock;
    }

    public Integer getHistoryid() {
        return historyid;
    }

    public void setHistoryid(Integer historyid) {
        this.historyid = historyid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public Items getItemid() {
        return itemid;
    }

    public void setItemid(Items itemid) {
        this.itemid = itemid;
    }

    public Years getYearstock() {
        return yearstock;
    }

    public void setYearstock(Years yearstock) {
        this.yearstock = yearstock;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (historyid != null ? historyid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof History)) {
            return false;
        }
        History other = (History) object;
        if ((this.historyid == null && other.historyid != null) || (this.historyid != null && !this.historyid.equals(other.historyid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ia.jpa.model.History[ historyid=" + historyid + " ]";
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
