package hu.javalife.heroesofempires.species.dao;

import hu.javalife.heroesofempires.species.daomodel.Species;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import hu.javalife.heroesofempires.species.daomodel.SpeciesDao;

/**
 * @author krisztian
 */
@RequestScoped
public class SpeciesDaoImpl implements SpeciesDao{
    
    @PersistenceContext(name = "SpeciesPU")
    private EntityManager em;
    
    
    @Override
    public Species getById(long pId){
        return em.find(Species.class, pId);
    }
    
    @Override
    public boolean isNameAvailable(String pName){
        return em.createNamedQuery("Species.name")
                .setParameter("name", pName)
                .getResultList()
                .isEmpty();
    }

    @Override
    public Species getByName(String pName){
        return (Species) em.createNamedQuery("Species.name").setParameter("name", pName).getSingleResult();
    }
    
    
    @Override
    public List<Species> getAll(){
        return em.createQuery("SELECT h FROM Species h").getResultList();
    }
    
    
    @Override
    public Species modify(long pId, Species pNewData){
        Species hero = em.find(Species.class, pId);
        hero.setName(pNewData.getName());
        hero.setDescription(pNewData.getDescription());
        em.merge(hero);
        return hero;
    }

    @Override
    public void delete(long pId){
        Species hero = em.find(Species.class, pId);
        em.remove(hero);
    }


    @Override
    public Species add(Species pNewData){
        em.persist(pNewData);
        return pNewData;
    }

    @Override
    public List<Species> get(int pStart, int pCount, Species pSearch, String pShortField, String pShortDirection){
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Species> query = builder.createQuery(Species.class);
        
        Root root = query.from(Species.class);
        query.select(root);
        
        List<Predicate> predicates = searchPredicates(pSearch, builder, root);
        query.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));

        if(pShortField != null && pShortDirection != null){
            if("asc".equals(pShortDirection.toLowerCase()))
                query.orderBy(builder.asc(root.get(pShortField)));
            if("desc".equals(pShortDirection))
                query.orderBy(builder.desc(root.get(pShortField)));
        }
        return em.createQuery(query)
                .setFirstResult(pStart)
                .setMaxResults(pStart+pCount)
                .getResultList();
    }
    
    
    @Override
    public long getItemCount(){
        CriteriaBuilder qb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = qb.createQuery(Long.class);
        cq.select(qb.count(cq.from(Species.class)));
        return em.createQuery(cq).getSingleResult();
    }    
    
    protected List<Predicate> searchPredicates(Species pSearch, CriteriaBuilder builder, Root root){
        List<Predicate> predicates = new ArrayList<>();        
        if(pSearch!= null && pSearch.getName()!=null && !pSearch.getName().isEmpty()){
            predicates.add(
                builder.like(
                    builder.upper(
                        root.get("name")), 
                        "%".concat(pSearch.getName().toUpperCase()).concat("%")));
        }

        if(pSearch!= null && pSearch.getDescription()!=null && !pSearch.getName().isEmpty()){
            predicates.add(
                builder.like(
                    builder.upper(
                        root.get("name")), 
                        "%".concat(pSearch.getDescription().toUpperCase()).concat("%")));
        }
        return predicates;
    }    
}
