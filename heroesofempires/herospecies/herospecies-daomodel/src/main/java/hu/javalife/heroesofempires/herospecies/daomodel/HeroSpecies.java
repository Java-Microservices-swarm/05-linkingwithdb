package hu.javalife.heroesofempires.herospecies.daomodel;

import hu.javalife.heroesofempires.hero.datamodel.Hero;
import hu.javalife.heroesofempires.species.daomodel.Species;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author krisztian
 */
@Entity
@Table(name = "hero_species")
@NamedQueries({
    @NamedQuery(name = "HeroSpecies.hero", query = "SELECT h FROM HeroSpecies h WHERE h.hero.id=:heroid ORDER BY h.species.name"),
    @NamedQuery(name = "HeroSpecies.species", query = "SELECT h FROM HeroSpecies h WHERE h.species=:speciesid ORDER BY h.hero.name")
})
public class HeroSpecies implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="hero_ID")
    private Hero hero;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="species_ID")
    private Species species;
    
    private byte percent;

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    public byte getPercent() {
        return percent;
    }

    public void setPercent(byte percent) {
        this.percent = percent;
    }
    
    
    
    
    
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HeroSpecies)) {
            return false;
        }
        HeroSpecies other = (HeroSpecies) object;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hu.javalife.heroesofempires.herospecies.daomodel.HeroSpecies[ id=" + id + " ]";
    }
    
}
