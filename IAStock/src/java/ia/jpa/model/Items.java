/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ia.jpa.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Acer_E5
 */
@Entity
@Table(name = "ITEMS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Items.findAll", query = "SELECT i FROM Items i")
    , @NamedQuery(name = "Items.findByItemid", query = "SELECT i FROM Items i WHERE i.itemid = :itemid")
    , @NamedQuery(name = "Items.findByItemname", query = "SELECT i FROM Items i WHERE i.itemname = :itemname")
    , @NamedQuery(name = "Items.findByPrice", query = "SELECT i FROM Items i WHERE i.price = :price")})
public class Items implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "items")
    private List<Summarize> summarizeList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemid")
    private List<Categorys> categorysList;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "UNIT")
    private String unit;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ITEMTOTAL")
    private int itemtotal;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "ITEMID")
    private String itemid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ITEMNAME")
    private String itemname;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRICE")
    private double price;
    @JoinColumn(name = "YEARSTOCK", referencedColumnName = "YEARSTOCK")
    @ManyToOne(optional = false)
    private Years yearstock;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemid")
    private List<History> historyList;

    public Items() {
    }

    public Items(String itemid) {
        this.itemid = itemid;
    }

    public Items(String itemid, String itemname, double price) {
        this.itemid = itemid;
        this.itemname = itemname;
        this.price = price;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Years getYearstock() {
        return yearstock;
    }

    public void setYearstock(Years yearstock) {
        this.yearstock = yearstock;
    }

    @XmlTransient
    public List<History> getHistoryList() {
        return historyList;
    }

    public void setHistoryList(List<History> historyList) {
        this.historyList = historyList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itemid != null ? itemid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Items)) {
            return false;
        }
        Items other = (Items) object;
        if ((this.itemid == null && other.itemid != null) || (this.itemid != null && !this.itemid.equals(other.itemid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ia.jpa.model.Items[ itemid=" + itemid + " ]";
    }

    public int getItemtotal() {
        return itemtotal;
    }

    public void setItemtotal(int itemtotal) {
        this.itemtotal = itemtotal;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @XmlTransient
    public List<Summarize> getSummarizeList() {
        return summarizeList;
    }

    public void setSummarizeList(List<Summarize> summarizeList) {
        this.summarizeList = summarizeList;
    }

    @XmlTransient
    public List<Categorys> getCategorysList() {
        return categorysList;
    }

    public void setCategorysList(List<Categorys> categorysList) {
        this.categorysList = categorysList;
    }
    
}
