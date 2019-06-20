package soft.demo.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        System.out.println("方法前");
        //判断用户是否登录，如果没有登录 重定向到登录界面 不放行 如果登录 就放行
        //URI /login.action
        //URL http://localhost:8080/springmvc_mybatis_war_exploded/login.action
        String requestURI = request.getRequestURI();
        System.out.println(requestURI);
        //contains 包含
        if (!requestURI.contains("/login")){
            String username = (String) request.getSession().getAttribute("USER_SESSION");
            System.out.println("lo  "+username);
            if (username == null||username.equals("")){
                //重定向
                System.out.println("重定向回登录界面");
                response.sendRedirect(request.getContextPath()+"/login.action");
                return false; //不放行
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println("方法后");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.out.println("页面渲染后");
    }
}
