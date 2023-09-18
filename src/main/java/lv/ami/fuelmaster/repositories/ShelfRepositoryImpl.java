package lv.ami.fuelmaster.repositories;

import java.util.List;
import java.util.Optional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import lv.ami.fuelmaster.models.Shelf;

@Repository
@Transactional
public class ShelfRepositoryImpl implements ShelfRepository {

   @Autowired
    private SessionFactory sessionFactory;

    @Override
	public Long count() {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);

		cq.select(cb.count(cq.from(Shelf.class)));

		return session.createQuery(cq).getSingleResult();
	}

    public void delete(Shelf shelf) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(shelf);
    }

    @Override
	public void deleteById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		Shelf role = session.find(Shelf.class, id);
		session.delete(role);
	}
    

	public List<Shelf> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Warehouse", Shelf.class).getResultList();
    }
	
	

    @Override
    public Page<Shelf> findAll(Pageable pageable) {
        CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Shelf> criteriaQuery = builder.createQuery(Shelf.class);
        Root<Shelf> root = criteriaQuery.from(Shelf.class);
        criteriaQuery.select(root);
        criteriaQuery.orderBy(builder.desc(root.get("id"))); 
        TypedQuery<Shelf> query = sessionFactory.getCurrentSession().createQuery(criteriaQuery);
        int total = query.getResultList().size();
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        List<Shelf> tests = query.getResultList();
        return new PageImpl<>(tests, pageable, total);
    }
    
    @Override
	public Shelf findById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(Shelf.class, id);
	}
    
	public Optional<Shelf> findOptionalById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(Shelf.class, id));
    }

	public Shelf save(Shelf shelf) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(shelf);
		return shelf;
    }

}