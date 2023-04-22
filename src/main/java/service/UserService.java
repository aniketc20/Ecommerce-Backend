package service;

import beans.UserBean;
import models.UserEntity;

import java.util.List;

public interface UserService {
    
	void save(UserBean user);

    List<UserBean> getAll();

    UserEntity findByUsername(String username);
    
    boolean authenticate(String username, String password);
}
