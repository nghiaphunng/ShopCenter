package shopcenter.com.core.usecase.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import shopcenter.com.core.entity.Role;
import shopcenter.com.core.entity.User;
import shopcenter.com.framework.exception.AppException;
import shopcenter.com.framework.exception.ErrorCode;
import shopcenter.com.repository.RoleRepository;
import shopcenter.com.repository.UserRepository;
import shopcenter.com.adapter.dto.request.create_user.CreateUserRequest;
import shopcenter.com.adapter.dto.request.update_user.UpdateUserRequest;
import shopcenter.com.adapter.dto.response.admin.list_user.UserResponse;
import shopcenter.com.adapter.dto.response.create_user.CreateUserResponse;
import shopcenter.com.adapter.dto.response.update_user.UpdateUserResponse;
import shopcenter.com.core.usecase.service.UserService;
import shopcenter.com.shared.utils.ProcessPassword;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public User getUserByEmail(String email) {
		return userRepository.findByUserEmail(email)
				.orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
	}

	@Override
	public CreateUserResponse createUser(CreateUserRequest request) {
		if(userRepository.findByUserName(request.getUserName()).isPresent())
			throw new AppException(ErrorCode.USER_NAME_EXISTED);
		if(userRepository.findByUserEmail(request.getUserEmail()).isPresent())
			throw new AppException(ErrorCode.USER_EMAIL_EXISTED);

		User user = modelMapper.map(request, User.class);
		user.setUserPassword(ProcessPassword.createUserPassword(request.getUserPassword()));

		Role userRole = roleRepository.findByRoleName("USER")
				.orElseGet(() -> {
					Role newRole = new Role();
					newRole.setRoleName("USER");
					return roleRepository.save(newRole);
				});
		user.getRoles().add(userRole);

		userRepository.save(user);

		return modelMapper.map(user, CreateUserResponse.class);
	}

	@Override
	public UpdateUserResponse updateUser(UpdateUserRequest request) {
		var context = SecurityContextHolder.getContext();
		String userName = context.getAuthentication().getName();

		//check
		log.info("requets_username : {}", request.getUserName());
		log.info("authentication_username : {}", userName);

		User user = userRepository.findByUserName(userName)
				.orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

		modelMapper.map(request, user);
		user.setUserPassword(ProcessPassword.createUserPassword(request.getUserPassword()));

		var roles = roleRepository.findAllById(request.getRoles());
		user.setRoles(new HashSet<>(roles));

		userRepository.save(user);

		return modelMapper.map(user, UpdateUserResponse.class);
	}

	@Override
	public List<UserResponse> getAllUser() {
		return userRepository.findAll().stream()
				.map(user -> modelMapper.map(user, UserResponse.class))
				.collect(Collectors.toList());
	}

	@Override
	@PostAuthorize("(returnObject.userName == authentication.name) or hasRole('ADMIN')")
	public UserResponse getInfoUser(Integer userId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

		UserResponse userResponse = modelMapper.map(user, UserResponse.class);
		log.info("getInfoUser: userId = {}", userId);
		log.info("getInfoUser: returnObject.userName = {}", userResponse.getUserName());
		log.info("getInfoUser: authentication.name = {}", authentication.getName());

		return userResponse;
	}

	@Override
	public UserResponse getMyInfo() {
		var context = SecurityContextHolder.getContext();
		String userName = context.getAuthentication().getName();

		User user = userRepository.findByUserName(userName)
				.orElseThrow(() -> new AppException(ErrorCode.USER_NAME_NOT_EXISTED));

		return modelMapper.map(user, UserResponse.class);
	}

	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public CreateUserResponse createStaff(CreateUserRequest request) {
		if(userRepository.findByUserName(request.getUserName()).isPresent())
			throw new AppException(ErrorCode.USER_NAME_EXISTED);
		if(userRepository.findByUserEmail(request.getUserEmail()).isPresent())
			throw new AppException(ErrorCode.USER_EMAIL_EXISTED);

		User user = modelMapper.map(request, User.class);
		user.setUserPassword(ProcessPassword.createUserPassword(request.getUserPassword()));

		Role userRole = roleRepository.findByRoleName("STAFF")
				.orElseGet(() -> {
					Role newRole = new Role();
					newRole.setRoleName("STAFF");
					return roleRepository.save(newRole);
				});
		user.getRoles().add(userRole);

		userRepository.save(user);

		return modelMapper.map(user, CreateUserResponse.class);
	}
}
