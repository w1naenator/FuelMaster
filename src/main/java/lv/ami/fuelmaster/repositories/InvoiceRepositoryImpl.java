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

import lv.ami.fuelmaster.models.Invoice;
import lv.ami.fuelmaster.models.Product;

@Repository
@Transactional
public class InvoiceRepositoryImpl implements InvoiceRepository {

   @Autowired
    private SessionFactory sessionFactory;

    @Override
	public Long count() {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);

		cq.select(cb.count(cq.from(Invoice.class)));

		return session.createQuery(cq).getSingleResult();
	}

    public void delete(Invoice entity) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(entity);
    }

    @Override
	public void deleteById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		Invoice entity = session.find(Invoice.class, id);
		session.delete(entity);
	}
    

	public List<Invoice> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Warehouse", Invoice.class).getResultList();
    }
	
	

    @Override
    public Page<Invoice> findAll(Pageable pageable) {
        CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Invoice> criteriaQuery = builder.createQuery(Invoice.class);
        Root<Invoice> root = criteriaQuery.from(Invoice.class);
        criteriaQuery.select(root);
        criteriaQuery.orderBy(builder.desc(root.get("id"))); 
        TypedQuery<Invoice> query = sessionFactory.getCurrentSession().createQuery(criteriaQuery);
        int total = query.getResultList().size();
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        List<Invoice> tests = query.getResultList();
        return new PageImpl<>(tests, pageable, total);
    }
    
    @Override
	public Invoice findById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(Invoice.class, id);
	}
    
    @Override
	public Invoice findByNumber(String number) {
   	
	        CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
	        CriteriaQuery<Invoice> query = builder.createQuery(Invoice.class);
	        Root<Invoice> root = query.from(Invoice.class);

	        Predicate predicate = builder.equal(root.get("number"), number);

	        query.where(predicate);

	        return sessionFactory.getCurrentSession().createQuery(query).uniqueResult();
	    
	}

    @Override
	public Invoice save(Invoice entity) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(entity);
		return entity;
    }

}