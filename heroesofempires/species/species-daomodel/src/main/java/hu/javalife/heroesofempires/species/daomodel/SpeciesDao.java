package hu.javalife.heroesofempires.species.daomodel;

import java.util.List;

/**
 * @author krisztian
 */
public interface SpeciesDao {

    public Species getById(long pId);
    public boolean isNameAvailable(String pName);
    public Species getByName(String pName);
    public List<Species> getAll();
    public Species modify(long pId, Species pNewData);
    public void delete(long pId);
    public Species add(Species pNewData);
    public List<Species> get(int pStart, int pCount, Species pSearch, String pShortField, String pShortDirection);
    public long getItemCount();
}
