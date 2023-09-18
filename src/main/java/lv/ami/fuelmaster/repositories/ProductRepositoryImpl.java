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

import lv.ami.fuelmaster.models.Product;

@Repository
@Transactional
public class ProductRepositoryImpl implements ProductRepository {

   @Autowired
    private SessionFactory sessionFactory;

    public Product save(Product product) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(product);
		return product;
    }

    public void delete(Product product) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(product);
    }

    public Optional<Product> findOptionalById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(Product.class, id));
    }
    

	@Override
	public Product findById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(Product.class, id);
	}
	
	

    public List<Product> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Product", Product.class).getResultList();
    }
    
    @Override
    public Page<Product> findAll(Pageable pageable) {
        CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = builder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);
        criteriaQuery.select(root);
        criteriaQuery.orderBy(builder.desc(root.get("id"))); 
        TypedQuery<Product> query = sessionFactory.getCurrentSession().createQuery(criteriaQuery);
        int total = query.getResultList().size();
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        List<Product> products = query.getResultList();
        return new PageImpl<>(products, pageable, total);
    }
    
	@Override
	public void deleteById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		Product product = session.find(Product.class, id);
		session.delete(product);
	}

	@Override
	public Long count() {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		cq.select(cb.count(cq.from(Product.class)));
		return session.createQuery(cq).getSingleResult();
	}

	@Override
	public boolean notUsed(String manufacturer, String orderCode) {
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<Product> root = query.from(Product.class);

		Predicate manufacturerPredicate = builder.equal(root.get("manufacturer"), manufacturer);
		Predicate orderCodePredicate = builder.equal(root.get("orderCode"), orderCode);
		query.where(manufacturerPredicate, orderCodePredicate);

		// Select the count of matching products
		query.select(builder.count(root));

		Long count = sessionFactory.getCurrentSession().createQuery(query).getSingleResult();

		// Check if the product exists
		return (count == null || count == 0);
	}

	 public List<Product> search(String keyword) {
	        CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
	        CriteriaQuery<Product> query = builder.createQuery(Product.class);
	        Root<Product> root = query.from(Product.class);

	        Predicate predicate = builder.or(
	                builder.like(builder.lower(root.get("orderCode")), "%" + keyword.toLowerCase() + "%"),
	                builder.like(builder.lower(root.get("shortDescription")), "%" + keyword.toLowerCase() + "%"),
	                builder.like(builder.lower(root.get("longDescription")), "%" + keyword.toLowerCase() + "%")
	        );

	        query.where(predicate);

	        return sessionFactory.getCurrentSession().createQuery(query).getResultList();
	    }
	 public Page<Product> search(String keyword, Pageable pageable) {
	        CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
	        CriteriaQuery<Product> criteriaQuery = builder.createQuery(Product.class);
	        Root<Product> root = criteriaQuery.from(Product.class);

	        Predicate predicate = builder.or(
	                builder.like(builder.lower(root.get("orderCode")), "%" + keyword.toLowerCase() + "%"),
	                builder.like(builder.lower(root.get("shortDescription")), "%" + keyword.toLowerCase() + "%"),
	                builder.like(builder.lower(root.get("longDescription")), "%" + keyword.toLowerCase() + "%")
	        );
	        
	        criteriaQuery.where(predicate);
	        criteriaQuery.orderBy(builder.desc(root.get("orderCode")));
	        
	        TypedQuery<Product> query = sessionFactory.getCurrentSession().createQuery(criteriaQuery);
	        int total = query.getResultList().size();
	        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
	        query.setMaxResults(pageable.getPageSize());
	        
	        List<Product> products = query.getResultList();
	        return new PageImpl<>(products, pageable, total);
	    }
}