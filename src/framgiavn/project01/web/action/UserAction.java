package framgiavn.project01.web.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import framgiavn.project01.web.business.FollowBusiness;
import framgiavn.project01.web.business.UserBusiness;
import framgiavn.project01.web.model.Follow;
import framgiavn.project01.web.model.Password;
import framgiavn.project01.web.model.User;
import framgiavn.project01.web.ulti.Constant;
import framgiavn.project01.web.ulti.Helpers;
import framgiavn.project01.web.ulti.User.UserHelpers;

public class UserAction extends ActionSupport implements SessionAware {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  // private Logit2 log = Logit2.getInstance(UserAction.class);

  private String     searchKey    = null;
  private List<User> resultSearch = null;

  public void setSearchKey(String searchKey) {

    this.searchKey = searchKey;
  }

  public List<User> getResultSearch() {

    return this.resultSearch;
  }

  private UserBusiness               userBusiness      = null;
  private FollowBusiness             followBusiness    = null;
  private List<User>                 listUserFollower  = null;
  private List<User>                 listUserFollowing = null;
  private User                       user              = null;
  private SessionMap<String, Object> session;
  private Follow                     follow            = null;
  private Integer                    userId            = null;
  private long                       joinedDay;
  private boolean                    isFollowing       = false;
  private Password                   password          = null;

  public long getJoinedDay() {

    return joinedDay;
  }

  public void setUserId(Integer userId) {

    this.userId = userId;
  }

  public void setUserBusiness(UserBusiness userBusiness) {

    this.userBusiness = userBusiness;
  }

  public void setFollowBusiness(FollowBusiness followBusiness) {

    this.followBusiness = followBusiness;
  }

  public void setFollow(Follow follow) {

    this.follow = follow;
  }

  public List<User> getListUserFollower() {

    return this.listUserFollower;
  }

  public List<User> getListUserFollowing() {

    return this.listUserFollowing;
  }

  public boolean getIsFollowing() {

    return this.isFollowing;
  }

  public User getUser() {

    return user;
  }

  public void setUser(User user) {

    this.user = user;
  }

  public Password getPassword() {

    return password;
  }

  public void setPassword(Password password) {

    this.password = password;
  }

  public String changePassword() {

    if (!session.isEmpty()) {
      user = UserHelpers.getUserFromSession();
      if (user != null) {
        if (password != null) {
          if (UserHelpers.checkOldPassword(user, password)) {
            if (UserHelpers.checkConfirmPassword(password)) {
              user.setPassword(password.getNewPassword());
              user.setUpdatedAt(new Date());
              try {
                userBusiness.update(user);
                addActionMessage("Password changed");
                return SUCCESS;
              } catch (Exception e) {
                e.printStackTrace();
              }
              addActionError("Have errors while update !");
              return ERROR;
            } else {
              addActionError("Comfirm password was wrong");
              return ERROR;
            }
          } else {
            addActionError("Old password was wrong");
            return ERROR;
          }
        } else {
          return ERROR;
        }
      }
    }
    return NONE;
  }

  public String findByUserId() {

    try {
      user = userBusiness.findByUserId(user.getUserId());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return SUCCESS;
  }

  public String findByUsername() {

    try {
      user = userBusiness.findByUsername(user.getUsername());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return SUCCESS;
  }

  public String findByEmail() {

    try {
      user = userBusiness.findByEmail(user.getEmail());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return SUCCESS;
  }

  public String checkLogin() {

    if (session.isEmpty()) {
      try {
        user = userBusiness.checkLogin(user);
      } catch (Exception e) {
        e.printStackTrace();
      }
      if (user != null) {
        session.put(Constant.CURRENT_USER, user);
        return SUCCESS;
      } else {
        addActionError("Username or Password was wrong!");
        return ERROR;

      }
    } else
      return NONE;
  }

  public String signup() {

    if (session.isEmpty()) {
      if (userBusiness.checkAccountAvalible(user) == null) {
        user.setCreatedAt(new Date());
        user.setAvatar(Constant.NOT_FOUND_IMAGE);
        try {
          userBusiness.signup(user);
          addActionMessage("Signup successfull! Please login");
          return SUCCESS;
        } catch (Exception e) {
          e.printStackTrace();
        }

      } else {
        addActionError("Username Or Email was avalible!");
        return ERROR;
      }
    }
    return NONE;
  }

  public String logout() {

    if (session != null) {
      session.invalidate();
    }
    return SUCCESS;
  }

  public String searchUser() {

    if (session.isEmpty())
      return NONE;
    if (searchKey != null) {
      resultSearch = userBusiness.searchByUsername(searchKey);
      if (!Helpers.isEmpty(resultSearch)) {
        addActionMessage("Result for " + searchKey + ":");
        return SUCCESS;
      } else {
        addActionError("Dont have result: " + searchKey);
        return ERROR;
      }
    } else {
      addActionError("Enter your key search");
      return ERROR;
    }
  }

  public String showProfile() {

    List<Follow> listFollowing = null;
    List<Follow> listFollower = null;

    User currentUser = UserHelpers.getUserFromSession();
    if (!session.isEmpty()) {
      if (userId == null) {
        user = currentUser;
      } else {
        user = userBusiness.findByUserId(userId);
      }
      if (user != null) {
        follow = UserHelpers.getCurrentFollow(currentUser, user);
        listFollowing = followBusiness.getFollowing(user);
        listFollower = followBusiness.getFollower(user);
        listUserFollower = getListUserFollow(listFollowing, false);
        listUserFollowing = getListUserFollow(listFollower, true);
        isFollowing = followBusiness.isFollowing(follow);
        joinedDay = UserHelpers.getJoinedDay(user.getCreatedAt());
        return SUCCESS;
      } else {
        return ERROR;
      }
    } else {
      return ERROR;
    }
  }

  public String unfollow() {

    if (!session.isEmpty()) {
      User currentUser = UserHelpers.getUserFromSession();
      User user = userBusiness.findByUserId(userId);
      if (user != null) {
        follow = UserHelpers.getCurrentFollow(currentUser, user);
        if (followBusiness.isFollowing(follow)) {
          follow = followBusiness.getDataFollow(follow);
          if (follow != null) {
            followBusiness.deleteFollow(followBusiness.getDataFollow(follow));
          }
        }
      }
    }
    return showProfile();
  }

  public String follow() {

    if (!session.isEmpty()) {
      User currentUser = UserHelpers.getUserFromSession();
      User user = userBusiness.findByUserId(userId);

      if (user != null && user.getUserId() != currentUser.getUserId()) {
        follow = UserHelpers.getCurrentFollow(currentUser, user);
        if (!followBusiness.isFollowing(follow)) {
          followBusiness.addFollower(follow);
        }
      }
    }
    return showProfile();
  }

  public List<User> getListUserFollow(List<Follow> listFollow, boolean b) {

    Iterator<Follow> listFollowIterator = listFollow.iterator();
    User user;
    List<User> listUser = new ArrayList<User>();
    while (listFollowIterator.hasNext()) {
      Follow f = listFollowIterator.next();
      if (!b) {
        user = userBusiness.findByUserId(f.getFollowerId());
      } else {
        user = userBusiness.findByUserId(f.getFollowingId());
      }
      listUser.add(user);
    }
    return listUser;
  }

  public String homePage() {

    System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
    return SUCCESS;
  }

  @Override
  public void setSession(Map<String, Object> session) {

    this.session = (SessionMap) session;
  }

}
