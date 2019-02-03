/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ia.jpa.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Acer_E5
 */
@Entity
@Table(name = "SUMMARIZE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Summarize.findAll", query = "SELECT s FROM Summarize s")
    , @NamedQuery(name = "Summarize.findByItemid", query = "SELECT s FROM Summarize s WHERE s.summarizePK.itemid = :itemid")
    , @NamedQuery(name = "Summarize.findByYearstock", query = "SELECT s FROM Summarize s WHERE s.summarizePK.yearstock = :yearstock")
    , @NamedQuery(name = "Summarize.findByBringforward", query = "SELECT s FROM Summarize s WHERE s.bringforward = :bringforward")
    , @NamedQuery(name = "Summarize.findByAddtotal", query = "SELECT s FROM Summarize s WHERE s.addtotal = :addtotal")
    , @NamedQuery(name = "Summarize.findByDrawtotal", query = "SELECT s FROM Summarize s WHERE s.drawtotal = :drawtotal")})
public class Summarize implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SummarizePK summarizePK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "BRINGFORWARD")
    private int bringforward;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ADDTOTAL")
    private int addtotal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DRAWTOTAL")
    private int drawtotal;
    @JoinColumn(name = "ITEMID", referencedColumnName = "ITEMID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Items items;
    @JoinColumn(name = "YEARSTOCK", referencedColumnName = "YEARSTOCK", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Years years;

    public Summarize() {
    }

    public Summarize(SummarizePK summarizePK) {
        this.summarizePK = summarizePK;
    }

    public Summarize(SummarizePK summarizePK, int bringforward, int addtotal, int drawtotal) {
        this.summarizePK = summarizePK;
        this.bringforward = bringforward;
        this.addtotal = addtotal;
        this.drawtotal = drawtotal;
    }

    public Summarize(String itemid, String yearstock, int bringforward, int addtotal, int drawtotal) {
        this.summarizePK = new SummarizePK(itemid, yearstock);
        this.bringforward = bringforward;
        this.addtotal = addtotal;
        this.drawtotal = drawtotal;
    }

    public Summarize(String itemid, String yearstock) {
        this.summarizePK = new SummarizePK(itemid, yearstock);
    }

    public SummarizePK getSummarizePK() {
        return summarizePK;
    }

    public void setSummarizePK(SummarizePK summarizePK) {
        this.summarizePK = summarizePK;
    }

    public int getBringforward() {
        return bringforward;
    }

    public void setBringforward(int bringforward) {
        this.bringforward = bringforward;
    }

    public int getAddtotal() {
        return addtotal;
    }

    public void setAddtotal(int addtotal) {
        this.addtotal = addtotal;
    }

    public int getDrawtotal() {
        return drawtotal;
    }

    public void setDrawtotal(int drawtotal) {
        this.drawtotal = drawtotal;
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
        hash += (summarizePK != null ? summarizePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Summarize)) {
            return false;
        }
        Summarize other = (Summarize) object;
        if ((this.summarizePK == null && other.summarizePK != null) || (this.summarizePK != null && !this.summarizePK.equals(other.summarizePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ia.jpa.model.Summarize[ summarizePK=" + summarizePK + " ]";
    }

}
