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
import lv.ami.fuelmaster.models.Receipt;

@Repository
@Transactional
public class ReceiptRepositoryImpl implements ReceiptRepository {

   @Autowired
    private SessionFactory sessionFactory;

    @Override
	public Long count() {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);

		cq.select(cb.count(cq.from(Receipt.class)));

		return session.createQuery(cq).getSingleResult();
	}

    public void delete(Receipt entity) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(entity);
    }

    @Override
	public void deleteById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		Receipt entity = session.find(Receipt.class, id);
		session.delete(entity);
	}
    

	public List<Receipt> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Warehouse", Receipt.class).getResultList();
    }
	
	

    @Override
    public Page<Receipt> findAll(Pageable pageable) {
        CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Receipt> criteriaQuery = builder.createQuery(Receipt.class);
        Root<Receipt> root = criteriaQuery.from(Receipt.class);
        criteriaQuery.select(root);
        criteriaQuery.orderBy(builder.desc(root.get("id"))); 
        TypedQuery<Receipt> query = sessionFactory.getCurrentSession().createQuery(criteriaQuery);
        int total = query.getResultList().size();
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        List<Receipt> tests = query.getResultList();
        return new PageImpl<>(tests, pageable, total);
    }
    
    @Override
	public Receipt findById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(Receipt.class, id);
	}
    
    @Override
	public List<Receipt> findByNumber(String number) {
    	
    		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Receipt> query = builder.createQuery(Receipt.class);
        Root<Receipt> root = query.from(Receipt.class);

        Predicate predicate = builder.equal(root.get("number"), number);

        query.where(predicate);

        return sessionFactory.getCurrentSession().createQuery(query).getResultList();
    
	}
    
    

    @Override
	public Receipt save(Receipt entity) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(entity);
		return entity;
    }

	@Override
	public Receipt findByNumberAndDate(String number, LocalDateTime localDateTime)  throws Exception {
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Receipt> query = builder.createQuery(Receipt.class);
        Root<Receipt> root = query.from(Receipt.class);

        Predicate predicate = builder.and(
                builder.equal(root.get("number"), number),
                builder.equal(root.get("receiptDateTime"), localDateTime)
        );

        query.where(predicate);
        List<Receipt> receipts = sessionFactory.getCurrentSession().createQuery(query).getResultList();
        if (receipts.isEmpty()) return null;
        else return receipts.get(0);
	}

	@Override
	public boolean existsByDateAndNumber(String number, LocalDateTime localDateTime) throws Exception {
		 Session session = sessionFactory.getCurrentSession();
	        CriteriaBuilder builder = session.getCriteriaBuilder();
	        CriteriaQuery<Long> query = builder.createQuery(Long.class);
	        Root<Receipt> root = query.from(Receipt.class);

	        // Define the criteria for the query
	        query.select(builder.count(root));
	        query.where(
	            builder.equal(root.get("receiptDateTime"), localDateTime),
	            builder.equal(root.get("number"), number)
	        );

	        // Execute the query
	        Query<Long> typedQuery = session.createQuery(query);
	        Long count = typedQuery.getSingleResult();

	        // Check if a receipt with the specified date and number exists
	        return count > 0;
	}
	
	

}