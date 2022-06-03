package com.sdau.hotelsystem.handler;

import com.sdau.hotelsystem.enums.ErrorCode;
import com.sdau.hotelsystem.util.Response;
import com.sdau.hotelsystem.util.ResponseUtil;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * controller统一异常处理
 *
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    /**
     * 未认证异常处理
     *
     * @return 未认证文案
     */
    @ResponseBody
    @ExceptionHandler(UnauthenticatedException.class)
    public Response authenticationException() {
        return ResponseUtil.makeSuccess(ErrorCode.UNAUTHENTIC.getCode(), ErrorCode.UNAUTHENTIC.getMsg());
    }

    /**
     * 未授权异常处理
     *
     * @return 未授权文案
     */
    @ResponseBody
    @ExceptionHandler(value = UnauthorizedException.class)
    public Response authorizationException() {
        return ResponseUtil.makeSuccess(ErrorCode.UNAUTHORIZED.getCode(), ErrorCode.UNAUTHORIZED.getMsg());
    }
}
