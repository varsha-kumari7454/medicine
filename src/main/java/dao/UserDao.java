package dao;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import models.User;
import ninja.jpa.UnitOfWork;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

public class UserDao implements IBaseDao{
	Logger log=Logger.getLogger(UserDao.class.getName());
	
	@Inject
	Provider<EntityManager> entityManagerProvider;
	
	@Override
	@UnitOfWork
	@SuppressWarnings("unchecked")
	public <T> List<T> findAll() {
		EntityManager em=entityManagerProvider.get();
		List<User> userList=(List<User>) em.createQuery("SELECT x FROM User x").getResultList();
		return (List<T>) userList;
	}

	@Override
	@Transactional
	public <T> boolean delete(T object) {
		EntityManager em=entityManagerProvider.get();
		User user=(User)object;
		user=em.getReference(User.class, user.getId());
		em.remove(user);
		return true;
	}
	@Override
	@Transactional
	public <T> int save(T object) {
		EntityManager em=entityManagerProvider.get();
		em.persist(object);
		return 0;
	}

	@Override
	@Transactional
	public <T> boolean saveOrUpdate(T object) {
		EntityManager em=entityManagerProvider.get();
		em.merge(object);
		return true;
	}

	@UnitOfWork
	public User findUserById(int userId) {
		EntityManager em=entityManagerProvider.get();
		Query q=em.createQuery("SELECT x FROM User x WHERE x.id = :idParam");
		User user=null;
		try{
			user=(User) q.setParameter("idParam", userId).getSingleResult();
		}catch(Exception e){
			log.warning("User doesn't exists for id : "+userId);
		}
		return user;
	}
	@UnitOfWork
    public boolean isUserAndPasswordValid(String username, String password) {
        
        if (username != null && password != null) {
            
            EntityManager entityManager = entityManagerProvider.get();
            
            TypedQuery<User> q = entityManager.createQuery("SELECT x FROM User x WHERE username = :usernameParam", User.class);
            User user = getSingleResult(q.setParameter("usernameParam", username));

            if (user != null) {
                
                if (user.password.equals(password)) {

                    return true;
                }
            }
        }
        
        return false;
 
    }

    /**
     * Get single result without throwing NoResultException, you can of course just catch the
     * exception and return null, it's up to you.
     */
    public static <T> T getSingleResult(TypedQuery<T> query) {
        query.setMaxResults(1);
        List<T> list = query.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }
	
	
	
	@Transactional
	public void deleteUser(String id) {
        
		EntityManager entityManager = entityManagerProvider.get();
		   entityManager.createQuery("DELETE FROM User x WHERE x.name = :idParam").setParameter("idParam", id).executeUpdate();
        
        
    }
	
	
	   @Transactional
	   public void updateitem(Integer userid1, String username) {
		   EntityManager entityManager1 = entityManagerProvider.get();
	        
//	        TypedQuery<stock> q = entityManager.createQuery("SELECT x FROM stock x WHERE itemid = :itemid", stock.class);
//	        stock updatestock = q.setParameter("itemid", itemid).getSingleResult();
//	        
//	        if (updatestock == null) {
//	            flash.alert = "User not found.";
//	        }
//	        entityManager.createQuery("UPDATE User x SET x.name=:username WHERE x.id =:userid1").setParameter("username", username).executeUpdate();
	  User user=(User)entityManager1.find(User.class,userid1);
	  user.setEmail(username);
	   }
}


