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

import lv.ami.fuelmaster.models.Warehouse;

@Repository
@Transactional
public class WarehouseRepositoryImpl implements WarehouseRepository {

   @Autowired
    private SessionFactory sessionFactory;

    public Warehouse save(Warehouse warehouse) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(warehouse);
		return warehouse;
    }

    public void delete(Warehouse warehouse) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(warehouse);
    }

    public Optional<Warehouse> findOptionalById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(Warehouse.class, id));
    }
    

	@Override
	public Warehouse findById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(Warehouse.class, id);
	}
	
	

    public List<Warehouse> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Warehouse", Warehouse.class).getResultList();
    }
    
    @Override
    public Page<Warehouse> findAll(Pageable pageable) {
        CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Warehouse> criteriaQuery = builder.createQuery(Warehouse.class);
        Root<Warehouse> root = criteriaQuery.from(Warehouse.class);
        criteriaQuery.select(root);
        criteriaQuery.orderBy(builder.desc(root.get("id"))); 
        TypedQuery<Warehouse> query = sessionFactory.getCurrentSession().createQuery(criteriaQuery);
        int total = query.getResultList().size();
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        List<Warehouse> tests = query.getResultList();
        return new PageImpl<>(tests, pageable, total);
    }
    
	@Override
	public void deleteById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		Warehouse role = session.find(Warehouse.class, id);
		session.delete(role);
	}

	@Override
	public Long countUsers() {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);

		cq.select(cb.count(cq.from(Warehouse.class)));

		return session.createQuery(cq).getSingleResult();
	}

}