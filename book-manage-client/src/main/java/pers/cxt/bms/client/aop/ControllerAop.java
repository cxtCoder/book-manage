package pers.cxt.bms.client.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import pers.cxt.bms.api.entity.ResponseEntity;
import pers.cxt.bms.api.enums.ErrorCodes;

import java.util.LinkedHashMap;

/**
 * @Author cxt
 * @Date 2020/6/25
 */
//@Aspect
@Component
public class ControllerAop {

    @Pointcut("execution(* pers.cxt.bms.client.controller.*Controller.*(..))")
    void controller() {
    }

    /**
     * 环绕通知
     *
     * @param joinPoint 可用于执行切点的类
     * @return responseData
     * @throws Throwable
     */
    @Around("controller()")
    public Object around(ProceedingJoinPoint joinPoint) {
        ResponseEntity responseData = new ResponseEntity();
        try {
            Object result = joinPoint.proceed();
            if (result instanceof LinkedHashMap && ((LinkedHashMap) result).containsKey("message")) {
                responseData.setSuc(false);
                responseData.setValue(null);
                responseData.setErrCode("-1");
                responseData.setMsg(((LinkedHashMap) result).get("message").toString());
            } else {
                responseData.setSuc(true);
                responseData.setValue(result);
                responseData.setErrCode(ErrorCodes.SUCCESS.getCode());
                responseData.setMsg(ErrorCodes.SUCCESS.getErrMsg());
            }
        } catch (Throwable throwable) {
            responseData.setSuc(false);
            responseData.setValue(null);
            responseData.setErrCode("-1");
            responseData.setMsg("未知异常");
        }
        return responseData;
    }
}
