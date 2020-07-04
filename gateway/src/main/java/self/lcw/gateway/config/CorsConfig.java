package self.lcw.gateway.config;

import org.springframework.web.filter.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

//作为zuul的配置文件
@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter(){

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        //配置跨域的属性
        CorsConfiguration config = new CorsConfiguration();
        //是否支持cookies跨域
        config.setAllowCredentials(true);
        //设置原始域（接收list参数） *表示所有
        config.setAllowedOrigins(Arrays.asList("*"));
        //允许跨域的头
        config.setAllowedHeaders(Arrays.asList("*"));
        //允许跨域的方法类型
        config.setAllowedMethods(Arrays.asList("*"));
        //缓存跨域时间 ： 对于相同的跨域请求，一定时间内不再进行跨域权限检查
        config.setMaxAge(300l);

        //对所有域名下边配置该跨域设置
        source.registerCorsConfiguration("/**",config);

        return new CorsFilter(source);
    }
}
