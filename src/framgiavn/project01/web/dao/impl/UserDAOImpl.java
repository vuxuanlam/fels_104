package framgiavn.project01.web.dao.impl;

import java.util.List;

import org.hibernate.Query;

import framgiavn.project01.web.dao.UserDAO;
import framgiavn.project01.web.model.User;
import framgiavn.project01.web.ulti.Logit2;

public class UserDAOImpl extends GenericDAOImpl<User, Integer> implements
    UserDAO {

  public UserDAOImpl() {

    super(User.class);
  }

  private static final Logit2 log  = Logit2.getInstance(UserDAOImpl.class);
  public static final String  NAME = "customerName";

  protected void initDAO() {

    // Do nothing
  }

  public User checkLogin(User user) throws Exception {

    try {
      Query query = getSession().getNamedQuery("User.CheckLogin");
      query.setParameter("username", user.getUsername());
      query.setParameter("password", user.getPassword());
      return (User) query.uniqueResult();
    } catch (RuntimeException re) {
      log.error("Get failed login", re);
      throw re;
    }
  }

  @Override
  public User checkAccountAvalible(User user) throws Exception {

    try {
      Query query = getSession().getNamedQuery("User.CheckAccountAvalible");
      query.setParameter("username", user.getUsername());
      query.setParameter("email", user.getEmail());
      return (User) query.uniqueResult();
    } catch (RuntimeException re) {
      log.error("Account is avalible");
      throw re;
    }
  }

  @Override
  public List<User> searchByUsername(String key) throws Exception {

    try {
      Query query = getSession().getNamedQuery("User.searchByUsername");
      query.setParameter("username", "%" + key + "%");
      return query.list();
    } catch (RuntimeException re) {
      throw re;
    }
  }

}
