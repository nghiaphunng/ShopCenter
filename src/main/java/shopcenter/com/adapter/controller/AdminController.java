package shopcenter.com.adapter.controller;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import shopcenter.com.adapter.dto.request.create_user.CreateUserRequest;
import shopcenter.com.adapter.dto.request.crud_category.CategoryRequest;
import shopcenter.com.adapter.dto.request.permission.PermissionRequest;
import shopcenter.com.adapter.dto.request.role.RoleRequest;
import shopcenter.com.adapter.dto.response.ApiResponse;
import shopcenter.com.adapter.dto.response.admin.crud_category.CategoryResponse;
import shopcenter.com.adapter.dto.response.admin.list_user.UserResponse;
import shopcenter.com.adapter.dto.response.admin.permission.PermissionResponse;
import shopcenter.com.adapter.dto.response.admin.role.RoleResponse;
import shopcenter.com.adapter.dto.response.create_user.CreateUserResponse;
import shopcenter.com.core.usecase.service.CategoryService;
import shopcenter.com.core.usecase.service.PermissionService;
import shopcenter.com.core.usecase.service.RoleService;
import shopcenter.com.core.usecase.service.UserService;

@Slf4j
@RestController
@RequestMapping(value ={"/admin"})
public class AdminController {
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private UserService userService;

	@Autowired
	private PermissionService permissionService;

	@Autowired
	private RoleService roleService;

	//user
	@GetMapping("/users")
	@PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
	public ApiResponse<?> getAllUser(){
		//lấy thông tin người đang đăng nhập
		var authentication = SecurityContextHolder.getContext().getAuthentication();

		log.info("username: {}", authentication.getName());
		authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

		List<UserResponse> users = userService.getAllUser();
		return ApiResponse.builder()
				.message("get info all users successfully")
				.result(users)
				.build();
	}

	//tạo tài khoản cho nhân viên
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping("/staff/create")
	public ApiResponse<?> createStaff(@RequestBody CreateUserRequest request){
		CreateUserResponse result = userService.createStaff(request);
		return ApiResponse.builder()
				.message("create acc staff successfully")
				.result(result)
				.build();
	}

	//crud permission
	@PostMapping("/permission/create")
	public ApiResponse<?> createPermission(@RequestBody PermissionRequest request){
		PermissionResponse result = permissionService.createPermission(request);
		return ApiResponse.builder()
				.message("create permission successfully")
				.result(result)
				.build();
	}

	@GetMapping("/permission")
	public ApiResponse<?> getAllPermission(){
		List<PermissionResponse> result = permissionService.getALlPermission();
		return ApiResponse.builder()
				.message("get all permission successfully")
				.result(result)
				.build();
	}

	@DeleteMapping("/permission/{id}")
	public ApiResponse<?> deletePermission(@PathVariable(name = "id") String permissionName){
		permissionService.deletePermission(permissionName);
		return ApiResponse.builder()
				.message("delete permission successfully")
				.build();
	}

	//crud Role
	@PostMapping("/role/create")
	public ApiResponse<?> createRole(@RequestBody RoleRequest request){
		RoleResponse result = roleService.createRole(request);
		return ApiResponse.builder()
				.message("create role successfully")
				.result(result)
				.build();
	}

	@GetMapping("/role")
	public ApiResponse<?> getAllRole(){
		List<RoleResponse> result = roleService.getAllRole();
		return ApiResponse.builder()
				.message("get all role successfully")
				.result(result)
				.build();
	}

	@DeleteMapping("/role/{id}")
	public ApiResponse<?> deleteRole(@PathVariable(name = "id") String roleName){
		roleService.deleteRole(roleName);
		return ApiResponse.builder()
				.message("delete role successfully")
				.build();
	}

	//crud category 
	@GetMapping("/category")
	public ApiResponse<?> getAllCategories(){
		List<CategoryResponse> categoryResponses = categoryService.getAllCategories();
		return ApiResponse.builder()
				.message("get info all category successfully")
				.result(categoryResponses)
				.build();
	}
	
	@PostMapping("/category/create")
	public ApiResponse<?> createCategory(@RequestBody CategoryRequest categoryRequest){
		CategoryResponse result = categoryService.createCategory(categoryRequest);
		return ApiResponse.builder()
				.message("create category successfully")
				.result(result)
				.build();
	}
	
	@PutMapping("/category/update")
	public ApiResponse<?> updateCategory(@RequestBody CategoryRequest categoryRequest){
		CategoryResponse result = categoryService.updateCategory(categoryRequest);
		return ApiResponse.builder()
				.message("delete category successfully")
				.result(result)
				.build();
	}
	
	@DeleteMapping("/category/delete/{id}")
	public ApiResponse<?> deleteCategory(@PathVariable(name = "id") Integer categoryId){
		categoryService.deleteCategory(categoryId);
		return ApiResponse.builder()
				.message("delete category successfully")
				.build();
	}
}
