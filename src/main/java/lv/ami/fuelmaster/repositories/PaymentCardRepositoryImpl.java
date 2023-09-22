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

import lv.ami.fuelmaster.models.PaymentCard;
import lv.ami.fuelmaster.models.Vehicle;

@Repository
@Transactional
public class PaymentCardRepositoryImpl implements PaymentCardRepository {

   @Autowired
    private SessionFactory sessionFactory;

    @Override
	public Long count() {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);

		cq.select(cb.count(cq.from(PaymentCard.class)));

		return session.createQuery(cq).getSingleResult();
	}

    public void delete(PaymentCard entity) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(entity);
    }

    @Override
	public void deleteById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		PaymentCard entity = session.find(PaymentCard.class, id);
		session.delete(entity);
	}
    

	public List<PaymentCard> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from fuel", PaymentCard.class).getResultList();
    }
	
	

    @Override
    public Page<PaymentCard> findAll(Pageable pageable) {
        CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<PaymentCard> criteriaQuery = builder.createQuery(PaymentCard.class);
        Root<PaymentCard> root = criteriaQuery.from(PaymentCard.class);
        criteriaQuery.select(root);
        criteriaQuery.orderBy(builder.desc(root.get("id"))); 
        TypedQuery<PaymentCard> query = sessionFactory.getCurrentSession().createQuery(criteriaQuery);
        int total = query.getResultList().size();
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        List<PaymentCard> entities = query.getResultList();
        return new PageImpl<>(entities, pageable, total);
    }
    
    @Override
	public PaymentCard findById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(PaymentCard.class, id);
	}
    
    @Override
	public PaymentCard findByName(String name) {
    	CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<PaymentCard> query = builder.createQuery(PaymentCard.class);
        Root<PaymentCard> root = query.from(PaymentCard.class);

        Predicate predicate = builder.equal(root.get("name"), name);

        query.where(predicate);

        List<PaymentCard> entities = sessionFactory.getCurrentSession().createQuery(query).getResultList();
        if (entities.isEmpty())return null;
        else return entities.get(0);
        
	}

    @Override
	public PaymentCard save(PaymentCard entity) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(entity);
		return entity;
    }

}