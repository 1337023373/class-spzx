package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.SysMenuService;
import com.atguigu.spzx.manager.service.SysUserService;
import com.atguigu.spzx.manager.service.ValidateCodeVoService;
import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.vo.system.LoginVo;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.system.SysMenuVo;
import com.atguigu.spzx.model.vo.system.ValidateCodeVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/system/index")
@CrossOrigin(allowCredentials = "true", originPatterns = "*", allowedHeaders = "*")
@Tag(name = "登录接口")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private ValidateCodeVoService validateCodeVoService;

    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public Result login(@RequestBody LoginDto loginDto) {
        LoginVo loginVo = sysUserService.login(loginDto);
        return Result.build(loginVo, ResultCodeEnum.SUCCESS);
    }


    @GetMapping("/getCode")
    @Operation(summary = "获取验证码")
    public Result<ValidateCodeVo> generateValidateCode() {
        ValidateCodeVo validateCodeVo = validateCodeVoService.generateValidateCode();
        return Result.build(validateCodeVo, ResultCodeEnum.SUCCESS);
    }

    //    通过携带token，让后端识别，前端的token放在了请求头中
    @GetMapping("/getInfo")
    @Operation(summary = "获取用户信息")
    public Result<SysUser> getUserInfo(@RequestHeader(name = "token") String token) {
        SysUser sysUser = sysUserService.getUserInfo(token);
        return Result.build(sysUser, ResultCodeEnum.SUCCESS);
    }

    @DeleteMapping("/logout")
    @Operation(summary = "用户退出")
    public Result logout(@RequestHeader(name = "token") String token) {
        sysUserService.logout(token);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "角色菜单展示")
    @GetMapping("/getSysRoleMenu")
    public Result<SysMenuVo> getSysRoleMenu() {
        List<SysMenuVo> sysMenuVoList = sysMenuService.getSysRoleMenu();
        return Result.build(sysMenuVoList, ResultCodeEnum.SUCCESS);
    }
}
