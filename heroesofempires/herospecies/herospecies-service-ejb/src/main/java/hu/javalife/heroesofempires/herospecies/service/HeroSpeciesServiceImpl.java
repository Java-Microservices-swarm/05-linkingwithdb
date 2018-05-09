package hu.javalife.heroesofempires.herospecies.service;

import hu.javalife.heroesofempires.hero.datamodel.Hero;
import hu.javalife.heroesofempires.herospecies.daomodel.HeroSpecies;
import hu.javalife.heroesofempires.herospecies.daomodel.HeroSpeciesDao;
import hu.javalife.heroesofempires.herospecies.servicemodel.BusinessException;
import hu.javalife.heroesofempires.herospecies.servicemodel.HeroSpeciesService;
import hu.javalife.heroesofempires.species.daomodel.Species;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;

/**
 * @author krisztian
 */
@Stateless
public class HeroSpeciesServiceImpl implements HeroSpeciesService{

    @Inject
    HeroSpeciesDao dao;
    
    @Override
    public HeroSpecies getById(long pId) throws BusinessException {
        HeroSpecies hs = dao.getById(pId);
        if(hs==null) throw new BusinessException();
        return hs;                
    }

    @Override
    public List<HeroSpecies> getAllByHero(long pHeroID) {
        return dao.getAllForHero(pHeroID);
    }

    @Override
    public List<HeroSpecies> getAllBySpecies(long pSpeciesID) {
        return dao.getAllForSpecies(pSpeciesID);
    }

    @Override
    public HeroSpecies modify(long pId, byte pNewPercent) throws BusinessException {
        getById(pId);
        return dao.modify(pId, pNewPercent);
    }

    @Override
    public void delete(long pId) throws BusinessException {
        getById(pId);
        dao.delete(pId);
    }

    @Override
    public HeroSpecies add(long pHeroID, long pSpeciesID, byte pPercent) throws BusinessException {
        long sumPercent=getAllByHero(pHeroID).stream().mapToInt(s-> s.getPercent()).sum();
        if(sumPercent+pPercent > 100) throw new BusinessException(1);
        
        Client client = ClientBuilder.newClient();
        Invocation.Builder request = client.target("http://localhost:8080")
            .path("/hero/"+pHeroID)
            .request(MediaType.APPLICATION_JSON);        
        Hero hero = request.get(Hero.class);
        if(hero==null)
            throw new BusinessException(2);
                    
        request = client.target("http://localhost:8180")
            .path("/species/"+pSpeciesID)
            .request(MediaType.APPLICATION_JSON);        
        Species species = request.get(Species.class);
        if(species==null)
            throw new BusinessException(3);
        
        HeroSpecies hs = new HeroSpecies();
        hs.setPercent(pPercent);
        hs.setHero(hero);
        hs.setSpecies(species);
        dao.add(hero, species, pPercent);
        
        return hs;
    }
    
}
