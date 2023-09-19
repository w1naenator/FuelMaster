package lv.ami.fuelmaster.repositories;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import lv.ami.fuelmaster.models.Fuel;
import lv.ami.fuelmaster.models.Invoice;

@Repository
@Transactional
public class FuelRepositoryImpl implements FuelRepository {

   @Autowired
    private SessionFactory sessionFactory;

    @Override
	public Long count() {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);

		cq.select(cb.count(cq.from(Fuel.class)));

		return session.createQuery(cq).getSingleResult();
	}

    public void delete(Fuel entity) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(entity);
    }

    @Override
	public void deleteById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		Fuel entity = session.find(Fuel.class, id);
		session.delete(entity);
	}
    

	public List<Fuel> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Warehouse", Fuel.class).getResultList();
    }
	
	

    @Override
    public Page<Fuel> findAll(Pageable pageable) {
        CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Fuel> criteriaQuery = builder.createQuery(Fuel.class);
        Root<Fuel> root = criteriaQuery.from(Fuel.class);
        criteriaQuery.select(root);
        criteriaQuery.orderBy(builder.desc(root.get("id"))); 
        TypedQuery<Fuel> query = sessionFactory.getCurrentSession().createQuery(criteriaQuery);
        int total = query.getResultList().size();
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        List<Fuel> tests = query.getResultList();
        return new PageImpl<>(tests, pageable, total);
    }
    
    @Override
	public Fuel findById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(Fuel.class, id);
	}
    
    @Override
	public Fuel findByName(String name) {
    	CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Fuel> query = builder.createQuery(Fuel.class);
        Root<Fuel> root = query.from(Fuel.class);

        Predicate predicate = builder.equal(root.get("name"), name);

        query.where(predicate);

        
        try {
        Fuel fuel = sessionFactory.getCurrentSession().createQuery(query).uniqueResult();
        
        return fuel;
        }
        catch(Exception e) {
        	 e = new Exception("kluda ir te: " + e.getMessage());
        	e.printStackTrace();
        	return null;
        }
        //return sessionFactory.getCurrentSession().createQuery(query).uniqueResult();
	}

    @Override
	public Fuel save(Fuel entity) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(entity);
		return entity;
    }

}