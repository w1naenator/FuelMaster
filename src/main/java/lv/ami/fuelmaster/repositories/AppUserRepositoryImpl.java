package lv.ami.fuelmaster.repositories;

import java.util.List;

import javax.persistence.NoResultException;
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

import lv.ami.fuelmaster.models.AppUser;

//AppUserRepositoryImpl.java

@Repository
@Transactional
public class AppUserRepositoryImpl implements AppUserRepository {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public AppUser findByUsername(String username) {
		Session session = sessionFactory.getCurrentSession();
		String query = "SELECT u FROM AppUser u WHERE u.username = :username";
		TypedQuery<AppUser> typedQuery = session.createQuery(query, AppUser.class);
		typedQuery.setParameter("username", username);
		try {
		    return typedQuery.getSingleResult();
		} catch (NoResultException e) {
		    return null;
		}
	}

	@Override
	public AppUser findById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		return session.find(AppUser.class, id);
	}

	@Override
	public void save(AppUser user) {
		Session session = sessionFactory.getCurrentSession();
		if (user.getId() == null) {
			session.persist(user);
		} else {
			session.merge(user);
		}
	}

	@Override
	public void deleteById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		AppUser user = findById(id);
		if (user != null) {
			session.remove(user);
		}
	}

	@Override
	public List<AppUser> findAll() {
		Session session = sessionFactory.getCurrentSession();
		TypedQuery<AppUser> query = session.createQuery("SELECT u FROM AppUser u", AppUser.class);
		return query.getResultList();

	}

	@Override
	public Page<AppUser> findAll(Pageable pageable) {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = sessionFactory.getCriteriaBuilder();
		CriteriaQuery<AppUser> cq = cb.createQuery(AppUser.class);

		Root<AppUser> root = cq.from(AppUser.class);
		cq.select(root);

		TypedQuery<AppUser> query = session.createQuery(cq);
		query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
		query.setMaxResults(pageable.getPageSize());

		List<AppUser> resultList = query.getResultList();
		long totalRows = countUsers();

		return new PageImpl<>(resultList, pageable, totalRows);
	}

	@Override
	public Long countUsers() {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);

		cq.select(cb.count(cq.from(AppUser.class)));

		return session.createQuery(cq).getSingleResult();
	}

}
