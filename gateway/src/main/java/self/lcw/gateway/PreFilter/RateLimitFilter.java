package self.lcw.gateway.PreFilter;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.*;

@Component
public class RateLimitFilter extends ZuulFilter {

    //使用了google的RateLimiter组件 其中create的参数是每秒生成多少个令牌
    private static final RateLimiter rateLimiter = RateLimiter.create(100);

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        //因为限流一般是优先级最高的过滤器，所以选了个值最小的Zuul常量然后减1
        return SERVLET_DETECTION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //使用tryAcquire方法尝试获取令牌
        if (!rateLimiter.tryAcquire()){
            //获取不到报错
            throw new RuntimeException("获取不到令牌");
        }
        return null;
    }
}
