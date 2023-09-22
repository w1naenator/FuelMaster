package lv.ami.fuelmaster.repositories;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lv.ami.fuelmaster.models.AppRole;

@Repository
@Transactional
public class AppRoleRepositoryImpl implements AppRoleRepository {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public AppRole findById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		return session.find(AppRole.class, id);
	}

	@Override
	public AppRole findByIdInitialized(Long id) {
		AppRole appRole = findById(id);
		if (appRole != null) Hibernate.initialize(appRole.getAppUsers());
		return appRole;
	}

	@Override
		/*
	public List<AppRole> findAll() {
		Session session = sessionFactory.getCurrentSession();
		TypedQuery<AppRole> query = session.createQuery("SELECT r FROM AppRole r", AppRole.class);
		return query.getResultList();
	}
	*/
	public List<AppRole> findAll() {
	    CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
	    CriteriaQuery<AppRole> query = builder.createQuery(AppRole.class);
	    Root<AppRole> root = query.from(AppRole.class);
	    
	    query.select(root);

	    TypedQuery<AppRole> typedQuery = sessionFactory.getCurrentSession().createQuery(query);
	    
	    return typedQuery.getResultList();
	}


	
	

	@Override
	public List<AppRole> findAllInitialized() {
		List<AppRole> appRoles = findAll();
		if (appRoles != null) {
			for (AppRole appRole : appRoles) {
				Hibernate.initialize(appRole.getAppUsers());
			}
		}
		return appRoles;
	}

	@Override
		/*public AppRole findByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		TypedQuery<AppRole> query = session.createQuery("SELECT r FROM AppRole r WHERE r.roleName = :name", AppRole.class);
		query.setParameter("name", name);
		return query.getSingleResult();
	}*/
	public AppRole findByName(String name) {
	    CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
	    CriteriaQuery<AppRole> query = builder.createQuery(AppRole.class);
	    Root<AppRole> root = query.from(AppRole.class);

	    Predicate predicate = builder.equal(root.get("roleName"), name);
	    query.where(predicate);

	    TypedQuery<AppRole> typedQuery = sessionFactory.getCurrentSession().createQuery(query);

	    List<AppRole> resultList = typedQuery.getResultList();

	    if (!resultList.isEmpty()) {
	        return resultList.get(0); 
	    } else {
	        return null; 
	    }
	}


	@Override
	public void save(AppRole role) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(role);
	}

	@Override
	public void deleteById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		AppRole role = session.find(AppRole.class, id);
		session.delete(role);
	}
}
