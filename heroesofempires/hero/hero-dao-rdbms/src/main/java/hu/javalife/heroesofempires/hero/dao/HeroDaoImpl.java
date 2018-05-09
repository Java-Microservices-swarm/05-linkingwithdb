package hu.javalife.heroesofempires.hero.dao;

import hu.javalife.heroesofempires.hero.datamodel.Hero;
import hu.javalife.heroesofempires.hero.datamodel.HeroDao;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @author krisztian
 */
@RequestScoped
public class HeroDaoImpl implements HeroDao{
    
    @PersistenceContext(name = "HeroPU")
    private EntityManager em;
    
    
    @Override
    public Hero getById(long pId){
        return em.find(Hero.class, pId);
    }
    
    @Override
    public boolean isNameAvailable(String pName){
        return em.createNamedQuery("Hero.name")
                .setParameter("name", pName)
                .getResultList()
                .isEmpty();
    }

    @Override
    public Hero getByName(String pName){
        return (Hero) em.createNamedQuery("Hero.name").setParameter("name", pName).getSingleResult();
    }
    
    
    @Override
    public List<Hero> getAll(){
        return em.createQuery("SELECT h FROM Hero h").getResultList();
    }
    
    
    @Override
    public Hero modify(long pId, Hero pNewData){
        Hero hero = em.find(Hero.class, pId);
        hero.setName(pNewData.getName());
        hero.setDescription(pNewData.getDescription());
        em.merge(hero);
        return hero;
    }

    @Override
    public void delete(long pId){
        Hero hero = em.find(Hero.class, pId);
        em.remove(hero);
    }


    @Override
    public Hero add(Hero pNewData){
        System.out.println("DAO+"+pNewData.getName());
        em.persist(pNewData);
        return pNewData;
    }

    @Override
    public List<Hero> get(int pStart, int pCount, Hero pSearch, String pShortField, String pShortDirection){
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Hero> query = builder.createQuery(Hero.class);
        
        Root root = query.from(Hero.class);
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
        cq.select(qb.count(cq.from(Hero.class)));
        return em.createQuery(cq).getSingleResult();
    }    
    
    protected List<Predicate> searchPredicates(Hero pSearch, CriteriaBuilder builder, Root root){
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
