package self.lcw.user.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import self.lcw.user.server.Interceptor.AccessLimitInterceptor;


import java.util.List;

//该注解的作用：将该类作为一个配置注入
@Configuration
//extends WebMvcConfigurerAdapter  在adapter层面对数据和逻辑进行自定义改写
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    UserArgumentResolver userArgumentResolver;

    @Autowired
    AccessLimitInterceptor accessLimitInterceptor;

    //该方法的作用是给controller的参数赋值
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(userArgumentResolver);
    }

    //将拦截器注册进来
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessLimitInterceptor);
    }
}
