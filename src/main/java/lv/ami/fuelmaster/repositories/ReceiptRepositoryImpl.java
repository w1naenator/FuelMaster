package lv.ami.fuelmaster.repositories;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

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
    


    @Override
	public Receipt save(Receipt entity) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(entity);
		return entity;
    }

    public <T> long getCount(CriteriaQuery<T> criteriaQuery, Class<T> entityClass) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        Root<T> root = countQuery.from(entityClass);
        countQuery.select(builder.count(root)).where(criteriaQuery.getRestriction());
        return session.createQuery(countQuery).getSingleResult();
    }
    
    @Override
    public List<Receipt> findAll() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Receipt> criteriaQuery = builder.createQuery(Receipt.class);
        Root<Receipt> root = criteriaQuery.from(Receipt.class);
        criteriaQuery.select(root);

        return session.createQuery(criteriaQuery).getResultList();
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
        List<Receipt> entities = query.getResultList();
        return new PageImpl<>(entities, pageable, total);
    }
    
    @Override
	public Receipt findById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(Receipt.class, id);
	}
    
    @Override
	public List<Receipt> findByNumber(String number) {
    	
    	CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Receipt> criteriaQuery = builder.createQuery(Receipt.class);
        Root<Receipt> root = criteriaQuery.from(Receipt.class);


        criteriaQuery.where(builder.equal(root.get("number"), number));

        return sessionFactory.getCurrentSession().createQuery(criteriaQuery).getResultList();
    
	}
    
	@Override
	public Page<Receipt> findByNumber(String number, Pageable pageable) {
	    CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
	    CriteriaQuery<Receipt> criteriaQuery = builder.createQuery(Receipt.class);
	    Root<Receipt> root = criteriaQuery.from(Receipt.class);
	    
	    criteriaQuery.where(
	            builder.like(root.get("number"), number)
	        );
	    
	    // Count Query
	    CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
	    countQuery.select(builder.count(countQuery.from(Receipt.class))).where(criteriaQuery.getRestriction());
	    long total = sessionFactory.getCurrentSession().createQuery(countQuery).getSingleResult();
	    
	    // Result Query
	    TypedQuery<Receipt> resultQuery = sessionFactory.getCurrentSession().createQuery(criteriaQuery);
	    resultQuery.setFirstResult((int) pageable.getOffset());
	    resultQuery.setMaxResults(pageable.getPageSize());
	    List<Receipt> entities = resultQuery.getResultList();
	    
	    return new PageImpl<>(entities, pageable, total);
	}


	@Override
	public Receipt getByNumberAndDate(String number, LocalDateTime localDateTime)  throws Exception {
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

	@Override
	public List<Receipt> findByMonth(LocalDate localDate) throws Exception {
		Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Receipt> criteriaQuery = builder.createQuery(Receipt.class);
        Root<Receipt> root = criteriaQuery.from(Receipt.class);

        criteriaQuery.where(
                builder.between(
            		root.get("date_time"), 
            		localDate.with(TemporalAdjusters.firstDayOfMonth()),
            		localDate.with(TemporalAdjusters.lastDayOfMonth())
                )
         );

        Query<Receipt> query = session.createQuery(criteriaQuery);
        return query.getResultList();
	}

	@Override
	public Page<Receipt> findByMonth(LocalDate localDateTime, Pageable pageable) throws Exception {
        CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Receipt> criteriaQuery = builder.createQuery(Receipt.class);
        Root<Receipt> root = criteriaQuery.from(Receipt.class);
        criteriaQuery.where(
                builder.between(
            		root.get("date_time"), 
            		localDateTime.with(TemporalAdjusters.firstDayOfMonth()),
            		localDateTime.with(TemporalAdjusters.lastDayOfMonth())
                )
            );
        
     // Count Query
        CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        countQuery.select(builder.count(countQuery.from(Receipt.class))).where(criteriaQuery.getRestriction());
        long total = sessionFactory.getCurrentSession().createQuery(countQuery).getSingleResult();
        
        // Result Query
        TypedQuery<Receipt> resultQuery = sessionFactory.getCurrentSession().createQuery(criteriaQuery);
        resultQuery.setFirstResult((int) pageable.getOffset());
        resultQuery.setMaxResults(pageable.getPageSize());
        List<Receipt> entities = resultQuery.getResultList();
        
        return new PageImpl<>(entities, pageable, total);
	}

	@Override
	public List<Receipt> findByNumberAndMonth(String number, LocalDate localDate) throws Exception {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Receipt> criteriaQuery = builder.createQuery(Receipt.class);
        Root<Receipt> root = criteriaQuery.from(Receipt.class);

        criteriaQuery.where(
            builder.equal(root.get("number"), number),
            builder.between(
        		root.get("date_time"), 
        		localDate.with(TemporalAdjusters.firstDayOfMonth()),
        		localDate.with(TemporalAdjusters.lastDayOfMonth())
            )
        );

        Query<Receipt> query = session.createQuery(criteriaQuery);
        return query.getResultList();
    }
	
	@Override
    public Page<Receipt> findByNumberAndMonth(String number, LocalDate localDate, Pageable pageable) {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Receipt> criteriaQuery = criteriaBuilder.createQuery(Receipt.class);
        Root<Receipt> root = criteriaQuery.from(Receipt.class);

        Predicate numberPredicate = criteriaBuilder.like(root.get("number"), "%" + number + "%");
        Predicate monthPredicate = criteriaBuilder.equal(criteriaBuilder.function("month", Integer.class, root.get("receiptDateTime")), localDate.getMonthValue());

        criteriaQuery.where(numberPredicate, monthPredicate);

        List<Receipt> resultList = session.createQuery(criteriaQuery)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return new PageImpl<>(resultList, pageable, resultList.size());

    }





	


	

}