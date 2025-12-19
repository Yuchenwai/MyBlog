package com.zhoufeng.myblog.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.zhoufeng.myblog.enums.ErrorEnum;
import com.zhoufeng.myblog.utils.Result;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public Result accessDeniedException() {
        logger.error(ErrorEnum.UNAUTHORIZED.getMessage());
        return new Result(0,ErrorEnum.UNAUTHORIZED.getMessage(), null);
    }

    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception e, Model model) {
        ErrorEnum error;
        if (e instanceof CustomException) {
            CustomException ce = (CustomException) e;
            error = ErrorEnum.getByCode(ce.getCode());
        } else {
            error = ErrorEnum.UNKNOWN_ERROR;
        }
        logger.error("Unhandled exception", e);
        Result result = new Result(error.getCode(), error.getMessage(), null);
        model.addAttribute("result", result);
        return "error/error";
    }
}
