package run.bottle.app.controller;

import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import run.bottle.app.model.params.LoginParam;
import run.bottle.app.model.support.BaseResponse;
import run.bottle.app.model.support.UserDetail;
import run.bottle.app.security.AuthToken;
import run.bottle.app.security.context.SecurityContextHolder;
import run.bottle.app.service.AdminService;

import javax.validation.Valid;

@RestController
@RequestMapping("api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @PostMapping("login")
    public AuthToken auth(@RequestBody @Valid LoginParam loginParam) {
        return adminService.authenticate(loginParam.getUsername(), loginParam.getPassword());
    }

    @PostMapping("logout")
    public void logout() {
        adminService.clearToken();
    }

    @GetMapping("info")
    public BaseResponse getUserInfo() {
        UserDetail userDetail = SecurityContextHolder.getContext();
        if (!ObjectUtils.isEmpty(userDetail) && !ObjectUtils.isEmpty(userDetail.getUser())) {
            return BaseResponse.ok(userDetail.getUser());
        }
        return BaseResponse.ok(null);
    }

}
