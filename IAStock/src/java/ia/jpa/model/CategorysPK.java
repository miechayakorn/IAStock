/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ia.jpa.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Acer_E5
 */
@Embeddable
public class CategorysPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "CATEGORY")
    private String category;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "ITEMID")
    private String itemid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "YEARSTOCK")
    private String yearstock;

    public CategorysPK() {
    }

    public CategorysPK(String category, String itemid, String yearstock) {
        this.category = category;
        this.itemid = itemid;
        this.yearstock = yearstock;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getYearstock() {
        return yearstock;
    }

    public void setYearstock(String yearstock) {
        this.yearstock = yearstock;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (category != null ? category.hashCode() : 0);
        hash += (itemid != null ? itemid.hashCode() : 0);
        hash += (yearstock != null ? yearstock.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CategorysPK)) {
            return false;
        }
        CategorysPK other = (CategorysPK) object;
        if ((this.category == null && other.category != null) || (this.category != null && !this.category.equals(other.category))) {
            return false;
        }
        if ((this.itemid == null && other.itemid != null) || (this.itemid != null && !this.itemid.equals(other.itemid))) {
            return false;
        }
        if ((this.yearstock == null && other.yearstock != null) || (this.yearstock != null && !this.yearstock.equals(other.yearstock))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ia.jpa.model.CategorysPK[ category=" + category + ", itemid=" + itemid + ", yearstock=" + yearstock + " ]";
    }
    
}
