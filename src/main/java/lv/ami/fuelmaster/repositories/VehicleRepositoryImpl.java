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
import lv.ami.fuelmaster.models.Vehicle;

@Repository
@Transactional
public class VehicleRepositoryImpl implements VehicleRepository {

   @Autowired
    private SessionFactory sessionFactory;

    @Override
	public Long count() {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);

		cq.select(cb.count(cq.from(Vehicle.class)));

		return session.createQuery(cq).getSingleResult();
	}

    public void delete(Vehicle entity) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(entity);
    }

    @Override
	public void deleteById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		Vehicle entity = session.find(Vehicle.class, id);
		session.delete(entity);
	}
    
    @Override
	/*public List<Vehicle> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from fuel", Vehicle.class).getResultList();
    }*/
    public List<Vehicle> findAll() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        
        // Create a CriteriaQuery for Vehicle entities
        CriteriaQuery<Vehicle> criteriaQuery = criteriaBuilder.createQuery(Vehicle.class);
        Root<Vehicle> root = criteriaQuery.from(Vehicle.class);
        criteriaQuery.select(root);
        
        // Execute the query and return the result list
        return session.createQuery(criteriaQuery).getResultList();
    }
	
	

    @Override
    public Page<Vehicle> findAll(Pageable pageable) {
        CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Vehicle> criteriaQuery = builder.createQuery(Vehicle.class);
        Root<Vehicle> root = criteriaQuery.from(Vehicle.class);
        criteriaQuery.select(root);
        criteriaQuery.orderBy(builder.desc(root.get("id"))); 
        TypedQuery<Vehicle> query = sessionFactory.getCurrentSession().createQuery(criteriaQuery);
        int total = query.getResultList().size();
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        List<Vehicle> entities = query.getResultList();
        return new PageImpl<>(entities, pageable, total);
    }
    
    @Override
	public Vehicle findById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(Vehicle.class, id);
	}
    
    @Override
	public Vehicle findByNumber(String number) {
    	CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Vehicle> query = builder.createQuery(Vehicle.class);
        Root<Vehicle> root = query.from(Vehicle.class);

        Predicate predicate = builder.equal(root.get("number"), number);

        query.where(predicate);

        List<Vehicle> entities = sessionFactory.getCurrentSession().createQuery(query).getResultList();
        if (entities.isEmpty())return null;
        else return entities.get(0);
        
	}

    @Override
	public Vehicle save(Vehicle entity) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(entity);
		return entity;
    }

}