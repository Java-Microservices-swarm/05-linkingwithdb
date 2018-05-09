package hu.javalife.heroesofempires.herospecies.daomodel;

import hu.javalife.heroesofempires.hero.datamodel.Hero;
import hu.javalife.heroesofempires.species.daomodel.Species;
import java.util.List;

/**
 * @author krisztian
 */
public interface HeroSpeciesDao {
    public HeroSpecies getById(long pId);
    public List<HeroSpecies> getAllForHero(long pHeroID);
    public List<HeroSpecies> getAllForSpecies(long pSpeciesID);
    public HeroSpecies modify(long pId, byte pNewPercent);
    public void delete(long pId);
    public HeroSpecies add(Hero pHero, Species pSpecies, byte pPercent);
}
