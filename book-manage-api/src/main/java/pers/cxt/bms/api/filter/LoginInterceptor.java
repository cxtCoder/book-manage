package pers.cxt.bms.api.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import pers.cxt.bms.api.annotation.AuthIgnore;
import pers.cxt.bms.api.constants.UserConstant;
import pers.cxt.bms.api.enums.HttpStatusEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Author cxt
 * @Date 2020/6/25
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private final static Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        logger.debug(request.getRequestURI());
        HttpSession session = request.getSession();
        AuthIgnore authIgnore = null;

        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            authIgnore = ((HandlerMethod) handler)
                    .getMethodAnnotation(AuthIgnore.class);
        }

        if (authIgnore != null && authIgnore.validate()) {
            // 忽视权限
            response.addHeader("http_status", HttpStatusEnum.IGNORE_AUTH.toString());
            return true;
        } else {
            if (session.getAttribute(UserConstant.SESSION_USER) == null) {
                response.setHeader("http_status", HttpStatusEnum.INVALID_USER.toString());
                response.setStatus(401);
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub
        super.postHandle(request, response, handler, modelAndView);
    }
}
