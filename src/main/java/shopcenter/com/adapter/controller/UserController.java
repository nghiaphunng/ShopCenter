package shopcenter.com.adapter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import shopcenter.com.adapter.dto.request.update_user.UpdateUserRequest;
import shopcenter.com.adapter.dto.response.ApiResponse;
import shopcenter.com.core.usecase.service.UserService;

@RestController
@RequestMapping("/profile")
public class UserController {
    @Autowired
    private UserService userService;

    //crud thông tin người dùng
    //thông tin cá nhân
    @GetMapping("/{id}")
    public ApiResponse<?> getInfoUser(@PathVariable(name = "id") Integer userId){
        var userInfo = userService.getInfoUser(userId);

        return ApiResponse.builder()
                .message("get info user successfully")
                .result(userInfo)
                .build();
    }

    //lấy thông tin trực tiếp từ việc đăng nhập
    @GetMapping("/myInfo")
    public ApiResponse<?> getIMyInfo(){
        var userInfo = userService.getMyInfo();

        return ApiResponse.builder()
                .message("get info user successfully")
                .result(userInfo)
                .build();
    }

    //cập nhật thông tin cá nhân từ việc đăng nhập
    @PutMapping("/update")
    public ApiResponse<?> updateInfo(@RequestBody UpdateUserRequest request){
        var updateUserResponse = userService.updateUser(request);

        return ApiResponse.builder()
                .message("update info successfully")
                .result(updateUserResponse)
                .build();
    }
}
