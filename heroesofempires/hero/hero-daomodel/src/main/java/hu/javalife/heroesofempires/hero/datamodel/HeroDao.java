package hu.javalife.heroesofempires.hero.datamodel;

import java.util.List;

/**
 * @author krisztian
 */
public interface HeroDao {

    public Hero getById(long pId);
    public boolean isNameAvailable(String pName);
    public Hero getByName(String pName);
    public List<Hero> getAll();
    public Hero modify(long pId, Hero pNewData);
    public void delete(long pId);
    public Hero add(Hero pNewData);
    public List<Hero> get(int pStart, int pCount, Hero pSearch, String pShortField, String pShortDirection);
    public long getItemCount();
}
