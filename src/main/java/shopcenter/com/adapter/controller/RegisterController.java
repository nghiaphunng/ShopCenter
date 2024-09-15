package shopcenter.com.adapter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import shopcenter.com.adapter.dto.request.create_user.CreateUserRequest;
import shopcenter.com.adapter.dto.response.ResponseData;
import shopcenter.com.adapter.dto.response.create_user.CreateUserResponse;
import shopcenter.com.core.usecase.service.UserService;

@RestController
public class RegisterController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
//    @ResponseStatus(HttpStatus.CREATED)
//    @Operation(summary = "đăng ký tài khoản", description = "đăng ký tài khoản cho người dùng",
//            responses = {
//                @ApiResponse(responseCode = "201", description = "tạo tài khoản thành công",
//                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
//                        examples = @ExampleObject(name = "thêm tài khoản người mua", summary = "thêm tài khoản",
//                        value = """
//                                {
//                                    "status": 201,
//                                    "message": "register successfully",
//                                    "data": {
//                                        "userId": "16",
//                                        "userName": "user8",
//                                        "userAddress": "Hồ Chí Minh",
//                                        "userEmail": "user8@gmail.com",
//                                        "userFullName": "Người dùng 8"
//                                    }
//                                }
//                                """
//                        )))
//            })
    @Operation(summary = "đăng ký tài khoản", description = "đăng ký tài khoản cho người dùng")
    public ResponseData<CreateUserResponse> register(@Valid @RequestBody CreateUserRequest request){
        CreateUserResponse createUserResponse = userService.createUser(request);
//        return ApiResponse.builder()
//                .message("register successfully")
//                .result(createUserResponse)
//                .build();
        return new ResponseData<>(HttpStatus.CREATED, "register successfully", createUserResponse);
    }
}
