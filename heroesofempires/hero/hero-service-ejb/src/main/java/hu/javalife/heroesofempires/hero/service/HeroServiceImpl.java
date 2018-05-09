package hu.javalife.heroesofempires.hero.service;

import hu.javalife.heroesofempires.hero.servicemodel.BusinessException;
import hu.javalife.heroesofempires.hero.datamodel.Hero;
import hu.javalife.heroesofempires.hero.datamodel.HeroDao;
import hu.javalife.heroesofempires.hero.servicemodel.HeroService;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author krisztian
 */
@Stateless
public class HeroServiceImpl implements HeroService{
    
    @Inject
    HeroDao dao; 
    
    @Override
    public Hero add(Hero pValue) throws BusinessException{
        if(dao.isNameAvailable(pValue.getName()))
            return dao.add(pValue);
        else throw new BusinessException();
    }
    
    @Override
    public List<Hero> getAll(){return dao.getAll();}
    
    public Hero getById(long pID) throws BusinessException{
        Hero hero = dao.getById(pID);
        if(hero==null)
            throw new BusinessException();
        else 
            return hero;
    }

    @Override
    public Hero getByName(String pName) throws BusinessException{
        Hero hero = dao.getByName(pName);
        if(hero==null)
            throw new BusinessException();
        else 
            return hero;
    }
    
    @Override
    public Hero modify(long pID, Hero pHero) throws BusinessException{
        Hero hero = dao.getById(pID);
        if(hero == null)
            throw new BusinessException(1);
        if(!dao.isNameAvailable(pHero.getName()))
            throw new BusinessException(2);
        return dao.modify(pID, pHero);
    }
    
    @Override
    public void delete(long pID) throws BusinessException{
        Hero hero = dao.getById(pID);
        if(hero==null)
            throw new BusinessException();
        else 
            dao.delete(pID);
    }
    
    @Override
    public List<Hero> search(int pStart, int pCount, Hero pPred, String pShortField, String pShortDirection){
        return dao.get(pStart, pCount, pPred, pShortField, pShortDirection);
    }

    @Override
    public long getNumberOfHeroes(){
        return dao.getItemCount();
    }
}
