package lv.ami.fuelmaster.repositories;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import lv.ami.fuelmaster.models.Fuel;
import lv.ami.fuelmaster.models.Invoice;
import lv.ami.fuelmaster.models.Trip;

@Repository
@Transactional
public class TripRepositoryImpl implements TripRepository {

   @Autowired
    private SessionFactory sessionFactory;

    @Override
	public Long count() {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);

		cq.select(cb.count(cq.from(Trip.class)));

		return session.createQuery(cq).getSingleResult();
	}

    public void delete(Trip entity) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(entity);
    }

    @Override
	public void deleteById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		Trip entity = session.find(Trip.class, id);
		session.delete(entity);
	}
    

	public List<Trip> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Warehouse", Trip.class).getResultList();
    }
	
	

    @Override
    public Page<Trip> findAll(Pageable pageable) {
        CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Trip> criteriaQuery = builder.createQuery(Trip.class);
        Root<Trip> root = criteriaQuery.from(Trip.class);
        criteriaQuery.select(root);
        criteriaQuery.orderBy(builder.desc(root.get("id"))); 
        TypedQuery<Trip> query = sessionFactory.getCurrentSession().createQuery(criteriaQuery);
        int total = query.getResultList().size();
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        List<Trip> tests = query.getResultList();
        return new PageImpl<>(tests, pageable, total);
    }
    
    @Override
	public Trip findById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(Trip.class, id);
	}
    
    @Override
	public List<Trip> findByNumber(String number) {
    	
    		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Trip> query = builder.createQuery(Trip.class);
        Root<Trip> root = query.from(Trip.class);

        Predicate predicate = builder.equal(root.get("number"), number);

        query.where(predicate);

        return sessionFactory.getCurrentSession().createQuery(query).getResultList();
    
	}
    
    

    @Override
	public Trip save(Trip entity) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(entity);
		return entity;
    }

	@Override
	public Trip findByNumberAndDate(String number, LocalDateTime localDateTime)  throws Exception {
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Trip> query = builder.createQuery(Trip.class);
        Root<Trip> root = query.from(Trip.class);

        Predicate predicate = builder.and(
                builder.equal(root.get("number"), number),
                builder.equal(root.get("tripDateTime"), localDateTime)
        );

        query.where(predicate);
        List<Trip> trips = sessionFactory.getCurrentSession().createQuery(query).getResultList();
        if (trips.isEmpty()) return null;
        else return trips.get(0);
	}

	@Override
	public boolean existsByDateAndNumber(String number, LocalDateTime localDateTime) throws Exception {
		 Session session = sessionFactory.getCurrentSession();
	        CriteriaBuilder builder = session.getCriteriaBuilder();
	        CriteriaQuery<Long> query = builder.createQuery(Long.class);
	        Root<Trip> root = query.from(Trip.class);

	        // Define the criteria for the query
	        query.select(builder.count(root));
	        query.where(
	            builder.equal(root.get("tripDateTime"), localDateTime),
	            builder.equal(root.get("number"), number)
	        );

	        // Execute the query
	        Query<Long> typedQuery = session.createQuery(query);
	        Long count = typedQuery.getSingleResult();

	        // Check if a trip with the specified date and number exists
	        return count > 0;
	}
	
	

}