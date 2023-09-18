package lv.ami.fuelmaster.service;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lv.ami.fuelmaster.models.AppUser;

@Service
@Transactional
public class AppUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<AppUser> criteria = builder.createQuery(AppUser.class);
        Root<AppUser> root = criteria.from(AppUser.class);
        criteria.select(root).where(builder.equal(root.get("username"), username));
        TypedQuery<AppUser> query = session.createQuery(criteria);
        AppUser user = query.getSingleResult();
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : user.getRolesAsArray()) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return new User(user.getUsername(), user.getPassword(), authorities);
    }


}

