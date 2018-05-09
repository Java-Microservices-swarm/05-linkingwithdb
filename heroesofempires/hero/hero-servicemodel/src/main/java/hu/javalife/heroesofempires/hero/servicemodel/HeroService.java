package hu.javalife.heroesofempires.hero.servicemodel;

import hu.javalife.heroesofempires.hero.datamodel.Hero;
import java.util.List;

/**
 * @author krisztian
 */
public interface HeroService {
    
    public long getNumberOfHeroes();
    public Hero add(Hero pValue) throws BusinessException;
    public List<Hero> getAll();
    public Hero getById(long pID) throws BusinessException;
    public Hero getByName(String pName) throws BusinessException;    
    public Hero modify(long pID, Hero pHero) throws BusinessException;    
    public void delete(long pID) throws BusinessException;    
    public List<Hero> search(int pStart, int pCount, Hero pPred, String pShortField, String pShortDirection);
}
