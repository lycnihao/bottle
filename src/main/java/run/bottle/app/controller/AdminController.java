package run.bottle.app.controller;

import cn.hutool.crypto.SecureUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import run.bottle.app.exception.BadRequestException;
import run.bottle.app.model.entity.Option;
import run.bottle.app.model.entity.User;
import run.bottle.app.model.params.InstallParam;
import run.bottle.app.model.params.LoginParam;
import run.bottle.app.model.support.BaseResponse;
import run.bottle.app.model.support.UserDetail;
import run.bottle.app.security.AuthToken;
import run.bottle.app.security.context.SecurityContextHolder;
import run.bottle.app.service.AdminService;

import javax.validation.Valid;
import run.bottle.app.service.OptionService;
import run.bottle.app.service.UserService;

@RestController
@RequestMapping("api/admin")
public class AdminController {

    private final AdminService adminService;

    private final OptionService optionService;

    private final UserService userService;

    public AdminController(AdminService adminService,
        OptionService optionService, UserService userService) {
        this.adminService = adminService;
        this.optionService = optionService;
        this.userService = userService;
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

    @GetMapping(value = "/is_installed")
    public boolean isInstall() {
        Option option = optionService.findByKey("is_installed");
        return option != null && !option.getValue().equals(Boolean.FALSE);
    }

    @PostMapping(value = "/installations")
    @ResponseBody
    public BaseResponse<String> installBlog(@RequestBody InstallParam installParam) {
        boolean isInstalled =  this.isInstall();
        if (isInstalled) {
            throw new BadRequestException("该博客已初始化，不能再次安装！");
        }
        // 初始化设置
        initSettings(installParam);
        // 创建默认的用户
        createUser(installParam);

        return BaseResponse.ok("安装完成！");
    }

    private void initSettings(InstallParam installParam) {
        Map<String, String> properties = new HashMap<>(11);
        properties.put("is_installed", "true");
        properties.put("title",installParam.getTitle());
        properties.put("url",installParam.getUrl());
        List<Option> optionList = new ArrayList<>();
        properties.forEach((k, v) -> optionList.add(new Option(k, v)));
        optionService.removeAll();
        optionService.createInBatch(optionList);
    }

    private User createUser(InstallParam installParam) {
        String gravatar = "//cn.gravatar.com/avatar/" + SecureUtil.md5(installParam.getEmail()) +
            "?s=256&d=mm";
        installParam.setNickname(StringUtils.isBlank(installParam.getNickname())? installParam.getUsername() : installParam.getNickname());
        installParam.setAvatar(gravatar);
        installParam.prePersist();
        return userService.createBy(installParam);
    }

}
