/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ia.jpa.model;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Acer_E5
 */
@Entity
@Table(name = "CATEGORYS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Categorys.findAll", query = "SELECT c FROM Categorys c")
    , @NamedQuery(name = "Categorys.findByCategory", query = "SELECT c FROM Categorys c WHERE c.categorysPK.category = :category")
    , @NamedQuery(name = "Categorys.findByItemid", query = "SELECT c FROM Categorys c WHERE c.categorysPK.itemid = :itemid")
    , @NamedQuery(name = "Categorys.findByYearstock", query = "SELECT c FROM Categorys c WHERE c.categorysPK.yearstock = :yearstock")})
public class Categorys implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CategorysPK categorysPK;
    @JoinColumn(name = "ITEMID", referencedColumnName = "ITEMID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Items items;
    @JoinColumn(name = "YEARSTOCK", referencedColumnName = "YEARSTOCK", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Years years;

    public Categorys() {
    }

    public Categorys(CategorysPK categorysPK) {
        this.categorysPK = categorysPK;
    }

    public Categorys(String category, Items item, Years yearSession) {
        this.categorysPK = new CategorysPK(category, item.getItemid(), yearSession.getYearstock());
        this.years = yearSession;
        this.items = item;
    }

    public CategorysPK getCategorysPK() {
        return categorysPK;
    }

    public void setCategorysPK(CategorysPK categorysPK) {
        this.categorysPK = categorysPK;
    }

    public Items getItems() {
        return items;
    }

    public void setItems(Items items) {
        this.items = items;
    }

    public Years getYears() {
        return years;
    }

    public void setYears(Years years) {
        this.years = years;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (categorysPK != null ? categorysPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Categorys)) {
            return false;
        }
        Categorys other = (Categorys) object;
        if ((this.categorysPK == null && other.categorysPK != null) || (this.categorysPK != null && !this.categorysPK.equals(other.categorysPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ia.jpa.model.Categorys[ categorysPK=" + categorysPK + " ]";
    }

}
