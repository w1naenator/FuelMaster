package lv.ami.fuelmaster.repositories;

import java.util.List;
import java.util.Optional;

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

import lv.ami.fuelmaster.models.ProductExemplar;

@Repository
@Transactional
public class ProductExemplarRepositoryImpl implements ProductExemplarRepository {

   @Autowired
    private SessionFactory sessionFactory;

    public ProductExemplar save(ProductExemplar productExemplar) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(productExemplar);
		return productExemplar;
    }

    public void delete(ProductExemplar productExemplar) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(productExemplar);
    }

    public Optional<ProductExemplar> findOptionalById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(ProductExemplar.class, id));
    }
    

	@Override
	public ProductExemplar findById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(ProductExemplar.class, id);
	}
	
	

    public List<ProductExemplar> findAll() {
        //Session session = sessionFactory.getCurrentSession();
        //return session.createQuery("from ProductExemplar", ProductExemplar.class).getResultList();
    	CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<ProductExemplar> criteriaQuery = builder.createQuery(ProductExemplar.class);
        Root<ProductExemplar> root = criteriaQuery.from(ProductExemplar.class);
        criteriaQuery.select(root);
        criteriaQuery.orderBy(builder.desc(root.get("id"))); 
        TypedQuery<ProductExemplar> query = sessionFactory.getCurrentSession().createQuery(criteriaQuery);
        return query.getResultList();
    }
    
    @Override
    public Page<ProductExemplar> findAll(Pageable pageable) {
        CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<ProductExemplar> criteriaQuery = builder.createQuery(ProductExemplar.class);
        Root<ProductExemplar> root = criteriaQuery.from(ProductExemplar.class);
        criteriaQuery.select(root);
        criteriaQuery.orderBy(builder.desc(root.get("id"))); 
        TypedQuery<ProductExemplar> query = sessionFactory.getCurrentSession().createQuery(criteriaQuery);
        int total = query.getResultList().size();
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        List<ProductExemplar> products = query.getResultList();
        return new PageImpl<>(products, pageable, total);
    }
    
	@Override
	public void deleteById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		ProductExemplar product = session.find(ProductExemplar.class, id);
		session.delete(product);
	}
	
	public Long count() {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);

		cq.select(cb.count(cq.from(ProductExemplar.class)));

		return session.createQuery(cq).getSingleResult();
	}

	 public List<ProductExemplar> search(String keyword) {
	        CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
	        CriteriaQuery<ProductExemplar> query = builder.createQuery(ProductExemplar.class);
	        Root<ProductExemplar> root = query.from(ProductExemplar.class);

	        Predicate predicate = builder.or(
	                builder.like(builder.lower(root.get("orderCode")), "%" + keyword.toLowerCase() + "%"),
	                builder.like(builder.lower(root.get("shortDescription")), "%" + keyword.toLowerCase() + "%"),
	                builder.like(builder.lower(root.get("longDescription")), "%" + keyword.toLowerCase() + "%")
	        );

	        query.where(predicate);

	        return sessionFactory.getCurrentSession().createQuery(query).getResultList();
	    }
	 public Page<ProductExemplar> search(String keyword, Pageable pageable) {
	        CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
	        CriteriaQuery<ProductExemplar> criteriaQuery = builder.createQuery(ProductExemplar.class);
	        Root<ProductExemplar> root = criteriaQuery.from(ProductExemplar.class);

	        Predicate predicate = builder.or(
	                builder.like(builder.lower(root.get("orderCode")), "%" + keyword.toLowerCase() + "%"),
	                builder.like(builder.lower(root.get("shortDescription")), "%" + keyword.toLowerCase() + "%"),
	                builder.like(builder.lower(root.get("longDescription")), "%" + keyword.toLowerCase() + "%")
	        );
	        
	        criteriaQuery.where(predicate);
	        criteriaQuery.orderBy(builder.desc(root.get("orderCode")));
	        
	        TypedQuery<ProductExemplar> query = sessionFactory.getCurrentSession().createQuery(criteriaQuery);
	        int total = query.getResultList().size();
	        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
	        query.setMaxResults(pageable.getPageSize());
	        
	        List<ProductExemplar> productExemplars = query.getResultList();
	        return new PageImpl<>(productExemplars, pageable, total);
	    }
}