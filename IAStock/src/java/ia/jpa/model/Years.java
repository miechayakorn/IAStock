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
@Table(name = "YEARS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Years.findAll", query = "SELECT y FROM Years y")
    , @NamedQuery(name = "Years.findByYearstock", query = "SELECT y FROM Years y WHERE y.yearstock = :yearstock")
    , @NamedQuery(name = "Years.findByStatus", query = "SELECT y FROM Years y WHERE y.status = :status")})
public class Years implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "years")
    private List<Summarize> summarizeList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "yearstock")
    private List<Categorys> categorysList;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "YEARSTOCK")
    private String yearstock;
    @Basic(optional = false)
    @NotNull
    @Column(name = "STATUS")
    private int status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "yearstock")
    private List<Items> itemsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "yearstock")
    private List<History> historyList;

    public Years() {
    }

    public Years(String yearstock) {
        this.yearstock = yearstock;
    }

    public Years(String yearstock, int status) {
        this.yearstock = yearstock;
        this.status = status;
    }

    public String getYearstock() {
        return yearstock;
    }

    public void setYearstock(String yearstock) {
        this.yearstock = yearstock;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @XmlTransient
    public List<Items> getItemsList() {
        return itemsList;
    }

    public void setItemsList(List<Items> itemsList) {
        this.itemsList = itemsList;
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
        hash += (yearstock != null ? yearstock.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Years)) {
            return false;
        }
        Years other = (Years) object;
        if ((this.yearstock == null && other.yearstock != null) || (this.yearstock != null && !this.yearstock.equals(other.yearstock))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ia.jpa.model.Years[ yearstock=" + yearstock + " ]";
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
