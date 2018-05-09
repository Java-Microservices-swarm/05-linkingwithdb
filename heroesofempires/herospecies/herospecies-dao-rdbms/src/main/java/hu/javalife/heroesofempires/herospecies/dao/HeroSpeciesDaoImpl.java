package hu.javalife.heroesofempires.herospecies.dao;

import hu.javalife.heroesofempires.hero.datamodel.Hero;
import hu.javalife.heroesofempires.herospecies.daomodel.HeroSpecies;
import hu.javalife.heroesofempires.herospecies.daomodel.HeroSpeciesDao;
import hu.javalife.heroesofempires.species.daomodel.Species;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author krisztian
 */
@RequestScoped
public class HeroSpeciesDaoImpl implements HeroSpeciesDao{

    @PersistenceContext(name = "HeroSpeciesPU")
    EntityManager em;
    
    @Override
    public HeroSpecies getById(long pId) {
        return em.find(HeroSpecies.class, pId);
    }

    @Override
    public List<HeroSpecies> getAllForHero(long pHeroID) {
        return em.createQuery("HeroSpecies.hero").setParameter("heroid", pHeroID).getResultList();
    }

    @Override
    public List<HeroSpecies> getAllForSpecies(long pSpeciesID) {
        return em.createQuery("HeroSpecies.species").setParameter("speciesid", pSpeciesID).getResultList();
    }

    @Override
    public HeroSpecies modify(long pId, byte pNewPercent){
        HeroSpecies hs = getById(pId);
        hs.setPercent(pNewPercent);
        em.merge(hs);
        return hs;
    }

    @Override
    public void delete(long pId) {
        HeroSpecies hs = getById(pId);
        em.remove(pId);
    }

    @Override
    public HeroSpecies add(Hero pHero, Species pSpecies, byte pPercent){
        HeroSpecies hs = new HeroSpecies();
        hs.setHero(pHero);
        hs.setSpecies(pSpecies);
        hs.setPercent(pPercent);
        em.persist(hs);
        return hs;
    }
    
   
}
