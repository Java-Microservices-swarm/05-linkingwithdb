package hu.javalife.heroesofempires.species.service;

import hu.javalife.heroesofempires.species.servicemodel.BusinessException;
import hu.javalife.heroesofempires.species.daomodel.Species;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import hu.javalife.heroesofempires.species.daomodel.SpeciesDao;
import hu.javalife.heroesofempires.species.servicemodel.SpeciesService;

/**
 * @author krisztian
 */
@Stateless
public class SpeciesServiceImpl implements SpeciesService{
    
    @Inject
    SpeciesDao dao; 
    
    @Override
    public Species add(Species pValue) throws BusinessException{
        if(dao.isNameAvailable(pValue.getName()))
            return dao.add(pValue);
        else throw new BusinessException();
    }
    
    @Override
    public List<Species> getAll(){return dao.getAll();}
    
    public Species getById(long pID) throws BusinessException{
        Species hero = dao.getById(pID);
        if(hero==null)
            throw new BusinessException();
        else 
            return hero;
    }

    @Override
    public Species getByName(String pName) throws BusinessException{
        Species hero = dao.getByName(pName);
        if(hero==null)
            throw new BusinessException();
        else 
            return hero;
    }
    
    @Override
    public Species modify(long pID, Species pHero) throws BusinessException{
        Species hero = dao.getById(pID);
        if(hero == null)
            throw new BusinessException(1);
        if(!dao.isNameAvailable(pHero.getName()))
            throw new BusinessException(2);
        return dao.modify(pID, pHero);
    }
    
    @Override
    public void delete(long pID) throws BusinessException{
        Species hero = dao.getById(pID);
        if(hero==null)
            throw new BusinessException();
        else 
            dao.delete(pID);
    }
    
    @Override
    public List<Species> search(int pStart, int pCount, Species pPred, String pShortField, String pShortDirection){
        return dao.get(pStart, pCount, pPred, pShortField, pShortDirection);
    }

    @Override
    public long getNumberOfElements(){
        return dao.getItemCount();
    }
}
