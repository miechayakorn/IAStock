/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ia.jpa.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author User
 */
@Entity
@Table(name = "CATEGORYS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Categorys.findAll", query = "SELECT c FROM Categorys c")
    , @NamedQuery(name = "Categorys.findByCategory", query = "SELECT c FROM Categorys c WHERE c.category = :category")})
public class Categorys implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "CATEGORY")
    private String category;
    @JoinColumn(name = "ITEMID", referencedColumnName = "ITEMID")
    @ManyToOne(optional = false)
    private Items itemid;
    @JoinColumn(name = "YEARSTOCK", referencedColumnName = "YEARSTOCK")
    @ManyToOne(optional = false)
    private Years yearstock;

    public Categorys() {
    }

    public Categorys(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
        hash += (category != null ? category.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Categorys)) {
            return false;
        }
        Categorys other = (Categorys) object;
        if ((this.category == null && other.category != null) || (this.category != null && !this.category.equals(other.category))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ia.jpa.model.Categorys[ category=" + category + " ]";
    }
    
}
