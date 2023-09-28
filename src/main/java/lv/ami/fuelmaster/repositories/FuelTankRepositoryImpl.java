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
import lv.ami.fuelmaster.models.FuelTank;
import lv.ami.fuelmaster.models.Invoice;

@Repository
@Transactional
public class FuelTankRepositoryImpl implements FuelTankRepository {

   @Autowired
    private SessionFactory sessionFactory;

    @Override
	public Long count() {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);

		cq.select(cb.count(cq.from(FuelTank.class)));

		return session.createQuery(cq).getSingleResult();
	}

    public void delete(FuelTank entity) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(entity);
    }

    
    @Override
    public List<FuelTank> findAll() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        
        // Create a CriteriaQuery for Fuel entities
        CriteriaQuery<FuelTank> criteriaQuery = criteriaBuilder.createQuery(FuelTank.class);
        Root<FuelTank> root = criteriaQuery.from(FuelTank.class);
        criteriaQuery.select(root);
        
        // Execute the query and return the result list
        return session.createQuery(criteriaQuery).getResultList();
    }
	
	

    @Override
    public Page<FuelTank> findAll(Pageable pageable) {
        CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<FuelTank> criteriaQuery = builder.createQuery(FuelTank.class);
        Root<FuelTank> root = criteriaQuery.from(FuelTank.class);
        criteriaQuery.select(root);
        criteriaQuery.orderBy(builder.desc(root.get("id"))); 
        TypedQuery<FuelTank> query = sessionFactory.getCurrentSession().createQuery(criteriaQuery);
        int total = query.getResultList().size();
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        List<FuelTank> tests = query.getResultList();
        return new PageImpl<>(tests, pageable, total);
    }
    
    @Override
	public FuelTank findById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(FuelTank.class, id);
	}
    

    @Override
	public FuelTank save(FuelTank entity) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(entity);
		return entity;
    }

}