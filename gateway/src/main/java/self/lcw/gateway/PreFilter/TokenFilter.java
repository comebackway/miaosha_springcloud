package self.lcw.gateway.PreFilter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Component
public class TokenFilter extends ZuulFilter {

    @Override
    //定义filter的类型（pre post ...)
    public String filterType() {
        return PRE_TYPE;
    }

    //定义filter的执行顺序，数字越小越先执行
    @Override
    public int filterOrder() {
        //官方推荐写法，当然直接写一个数字上去也行
        return PRE_DECORATION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    //过滤器实现的逻辑
    public Object run() throws ZuulException {
        //Zuul自带的取得上下文内容的方法
        RequestContext requestContext = RequestContext.getCurrentContext();
        //从上下文中取得request
        HttpServletRequest request = requestContext.getRequest();
        String token = request.getParameter("token");
        if (StringUtils.isEmpty(token)){
            //不通过该过滤器的校验  false
            requestContext.setSendZuulResponse(false);
            //设置一下返回的状态码（当然不设置也可以）
            requestContext.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
        }
        return null;
    }
}
