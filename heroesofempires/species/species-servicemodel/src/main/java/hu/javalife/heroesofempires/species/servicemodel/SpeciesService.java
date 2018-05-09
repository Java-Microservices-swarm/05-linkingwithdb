package hu.javalife.heroesofempires.species.servicemodel;

import hu.javalife.heroesofempires.species.daomodel.Species;
import java.util.List;

/**
 * @author krisztian
 */
public interface SpeciesService {
    
    public long getNumberOfElements();
    public Species add(Species pValue) throws BusinessException;
    public List<Species> getAll();
    public Species getById(long pID) throws BusinessException;
    public Species getByName(String pName) throws BusinessException;    
    public Species modify(long pID, Species pHero) throws BusinessException;    
    public void delete(long pID) throws BusinessException;    
    public List<Species> search(int pStart, int pCount, Species pPred, String pShortField, String pShortDirection);
}
