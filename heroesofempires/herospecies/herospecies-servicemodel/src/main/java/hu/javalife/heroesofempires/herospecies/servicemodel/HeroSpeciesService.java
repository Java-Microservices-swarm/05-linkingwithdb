package hu.javalife.heroesofempires.herospecies.servicemodel;

import hu.javalife.heroesofempires.herospecies.daomodel.HeroSpecies;
import java.util.List;

/**
 * @author krisztian
 */
public interface HeroSpeciesService {
    public HeroSpecies getById(long pId) throws BusinessException;
    public List<HeroSpecies> getAllByHero(long pHeroID);
    public List<HeroSpecies> getAllBySpecies(long pSpeciesID);
    public HeroSpecies modify(long pId, byte pNewPercent) throws BusinessException;
    public void delete(long pId) throws BusinessException;
    public HeroSpecies add(long pHeroID, long pSpeciesID, byte pPercent) throws BusinessException;
}
