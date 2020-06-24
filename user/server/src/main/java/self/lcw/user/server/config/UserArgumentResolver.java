package self.lcw.user.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import self.lcw.user.server.ThreadLocalContext.UserContext;
import self.lcw.user.server.entity.User;
import self.lcw.user.server.service.UserService;

@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    UserService userService;

    //定义该类支持的参数类型，如果该方法返回true，则会执行下边的处理参数方法
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        //判断前端获取的参数的类型
        Class<?> clazz = methodParameter.getParameterType();
        return clazz == User.class;
    }

    @Override
    public User resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {

        /*

        V2.0 在拦截器中使用ThreadLocal保存了参数，在后执行的这里可以直接获得并返回给controller

         */
        return UserContext.getUserThreadLocal();



        /*

        V1.0版本 直接在参数处理器HandlerMethodArgumentResolver中处理参数

        //从nativeWebRequest中取得request和response并转换为http类型
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        String paramToken = request.getParameter(UserService.COOKIE_NAME_TOKEN);
        String cookieToken = getCookieValue(request,UserService.COOKIE_NAME_TOKEN);
        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)){
            return null;
        }
        //优先取参数中的token
        String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
        return userService.getByToken(response,token);

         */
    }

    /*

    private String getCookieValue(HttpServletRequest request,String cookieName){
        //取出所有的cookie做循环
        Cookie[] cookies = request.getCookies();
        if(cookies == null || cookies.length<=0){
            return null;
        }
        for (Cookie cookie:cookies){
            if (cookie.getName().equals(cookieName)){
                return cookie.getValue();
            }
        }
        return null;
    }

     */
}
