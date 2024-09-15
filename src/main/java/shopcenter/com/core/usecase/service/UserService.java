package shopcenter.com.core.usecase.service;

import shopcenter.com.core.entity.User;
import shopcenter.com.adapter.dto.request.create_user.CreateUserRequest;
import shopcenter.com.adapter.dto.request.update_user.UpdateUserRequest;
import shopcenter.com.adapter.dto.response.admin.list_user.UserResponse;
import shopcenter.com.adapter.dto.response.create_user.CreateUserResponse;
import shopcenter.com.adapter.dto.response.update_user.UpdateUserResponse;

import java.util.List;

public interface UserService {
	User getUserByEmail(String email);
	CreateUserResponse createUser(CreateUserRequest request);
	UpdateUserResponse updateUser(UpdateUserRequest request);
	List<UserResponse> getAllUser();
	UserResponse getInfoUser(Integer userId);
	UserResponse getMyInfo();

	CreateUserResponse createStaff(CreateUserRequest request);
}
