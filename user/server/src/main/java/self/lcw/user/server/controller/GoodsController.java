package self.lcw.user.server.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import self.lcw.product.client.ProductClient;
import self.lcw.product.common.GoodsDetailDto;
import self.lcw.product.common.GoodsDto;
import self.lcw.user.server.entity.User;
import self.lcw.user.server.redis.GoodsKey;
import self.lcw.user.server.redis.RedisService;
import self.lcw.user.server.result.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {


    @Autowired
    RedisService redisService;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    ProductClient productClient;


    @RequestMapping(value = "/to_list",produces = "text/html")
    @ResponseBody
    public String list(Model model, User user, HttpServletResponse response, HttpServletRequest request){

        List<GoodsDto> goodsDtoList = productClient.showList();

        model.addAttribute("goodsList",goodsDtoList);
        //return "goods_list";

        //手工渲染页面
        //因为实现了接口IContext的SpringWebContext类已经过时，所以使用IWebContext
        IWebContext context = new WebContext(request,response,request.getServletContext(),
                //其中model就是要传入的数据
                request.getLocale(),model.asMap());
        //使用thymeleaf的引擎，将数据和页面模板作为参数传值
        String html = thymeleafViewResolver.getTemplateEngine().process("goods_list", context);
        if (!StringUtils.isEmpty(html)){
            redisService.set(GoodsKey.getGoodsList,"",html);
        }
        return html;
    }

    @RequestMapping(value = "/detail/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailDto> detail(@PathVariable("goodsId")long goodsId){

        GoodsDetailDto goodsDetailDto = productClient.detail(goodsId);
        if (goodsDetailDto != null){
            return Result.success(goodsDetailDto);
        }
        else {
            return Result.error(null);
        }
    }
}
