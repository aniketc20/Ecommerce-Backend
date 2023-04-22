package service;

import beans.UserBean;
import models.UserEntity;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void save(UserBean user) {
		// TODO Auto-generated method stub
		if (userRepository.findByUsername(user.getUsername()) != null) {
			return;
		}
		String hashedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(hashedPassword);
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);
		userRepository.save(userEntity);
	}

	@Override
	public List<UserBean> getAll() {
		// TODO Auto-generated method stub
		List<UserEntity> users = userRepository.findAll();
		List<UserBean> userBeans = new ArrayList<>();
		for (int i = 0; i < users.size(); i++) {
			UserBean userBean = new UserBean();
			BeanUtils.copyProperties(users.get(i), userBean);
			userBeans.add(userBean);
		}
		return userBeans;
	}

	@Override
	public UserEntity findByUsername(String username) {
		// TODO Auto-generated method stub
		return userRepository.findByUsername(username);
	}

	@Override
	public boolean authenticate(String username, String password) {
		// TODO Auto-generated method stub
		UserEntity userEntity = findByUsername(username);
		if (userEntity != null) {
			return passwordEncoder.matches(password, userEntity.getPassword());
		}
		return false;
	}
}
