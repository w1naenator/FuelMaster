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

import lv.ami.fuelmaster.models.Manufacturer;

@Repository
@Transactional
public class ManufacturerRepositoryImpl implements ManufacturerRepository {

	@Autowired
	private SessionFactory sessionFactory;

	public Manufacturer save(Manufacturer manufacturer) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(manufacturer);
		return manufacturer;
	}

	public void delete(Manufacturer manufacturer) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(manufacturer);
	}

	public Optional<Manufacturer> findOptionalById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		return Optional.ofNullable(session.get(Manufacturer.class, id));
	}

	@Override
	public Manufacturer findById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(Manufacturer.class, id);
	}

	public List<Manufacturer> findAll() {
		/*
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("from Manufacturer", Manufacturer.class).getResultList();
		*/
	    CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
	    CriteriaQuery<Manufacturer> query = builder.createQuery(Manufacturer.class);
	    Root<Manufacturer> root = query.from(Manufacturer.class);

	    query.select(root);

	    TypedQuery<Manufacturer> typedQuery = sessionFactory.getCurrentSession().createQuery(query);
	    return typedQuery.getResultList();
	}

	@Override
	public Page<Manufacturer> findAll(Pageable pageable) {
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Manufacturer> criteriaQuery = builder.createQuery(Manufacturer.class);
		Root<Manufacturer> root = criteriaQuery.from(Manufacturer.class);
		criteriaQuery.select(root);
		criteriaQuery.orderBy(builder.desc(root.get("id")));
		TypedQuery<Manufacturer> query = sessionFactory.getCurrentSession().createQuery(criteriaQuery);
		int total = query.getResultList().size();
		query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
		query.setMaxResults(pageable.getPageSize());
		List<Manufacturer> entitues = query.getResultList();
		return new PageImpl<>(entitues, pageable, total);
	}

	@Override
	public void deleteById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		Manufacturer entity = session.find(Manufacturer.class, id);
		session.delete(entity);
	}

	@Override
	public Long count() {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		cq.select(cb.count(cq.from(Manufacturer.class)));
		return session.createQuery(cq).getSingleResult();
	}

	@Override
	public boolean notUsed(String name) {
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<Manufacturer> root = query.from(Manufacturer.class);

		Predicate manufacturerPredicate = builder.equal(root.get("name"), name);
		query.where(manufacturerPredicate);

		// Select the count of matching manufacturers
		query.select(builder.count(root));

		Long count = sessionFactory.getCurrentSession().createQuery(query).getSingleResult();

		// Check if the manufacturer exists
		return (count == null || count == 0);
	}

	@Override
	public List<Manufacturer> search(String keyword) {
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Manufacturer> query = builder.createQuery(Manufacturer.class);
		Root<Manufacturer> root = query.from(Manufacturer.class);

		Predicate predicate = builder.like(builder.lower(root.get("name")), "%" + keyword.toLowerCase() + "%");

		query.where(predicate);

		return sessionFactory.getCurrentSession().createQuery(query).getResultList();
	}

	@Override
	public Page<Manufacturer> search(String keyword, Pageable pageable) {
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Manufacturer> criteriaQuery = builder.createQuery(Manufacturer.class);
		Root<Manufacturer> root = criteriaQuery.from(Manufacturer.class);

		Predicate predicate = builder.like(builder.lower(root.get("name")), "%" + keyword.toLowerCase() + "%");

		criteriaQuery.where(predicate);
		criteriaQuery.orderBy(builder.desc(root.get("name")));

		TypedQuery<Manufacturer> query = sessionFactory.getCurrentSession().createQuery(criteriaQuery);
		int total = query.getResultList().size();
		query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
		query.setMaxResults(pageable.getPageSize());

		List<Manufacturer> manufacturers = query.getResultList();
		return new PageImpl<>(manufacturers, pageable, total);
	}


}