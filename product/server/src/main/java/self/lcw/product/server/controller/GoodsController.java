package self.lcw.product.server.controller;


import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import self.lcw.product.common.GoodsDetailDto;
import self.lcw.product.common.GoodsDto;
import self.lcw.product.server.redis.GoodsKey;
import self.lcw.product.server.redis.RedisService;
import self.lcw.product.server.result.Result;
import self.lcw.product.server.service.GoodsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/product")
public class GoodsController {

    @Autowired
    GoodsService goodsService;

    @Autowired
    RedisService redisService;

    //渲染thymeleaf页面的框架
    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    AmqpTemplate amqpTemplate;


    // produces 指定返回的内容是html
    @RequestMapping(value = "/to_list",produces = "text/html")
    @ResponseBody
    public String list(Model model, HttpServletResponse response, HttpServletRequest request){


        //取缓存中的页面
        String html = redisService.get(GoodsKey.getGoodsList,"",String.class);
        if (!StringUtils.isEmpty(html)){
            return html;
        }
        
        List<GoodsDto> goodsDtoList = goodsService.listGoodsDto();
        model.addAttribute("goodsList",goodsDtoList);
        //return "goods_list";

        //手工渲染页面
        //因为实现了接口IContext的SpringWebContext类已经过时，所以使用IWebContext
        IWebContext context = new WebContext(request,response,request.getServletContext(),
                //其中model就是要传入的数据
                request.getLocale(),model.asMap());
        //使用thymeleaf的引擎，将数据和页面模板作为参数传值
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", context);
        if (!StringUtils.isEmpty(html)){
            redisService.set(GoodsKey.getGoodsList,"",html);
        }
        return html;
    }


    @RequestMapping(value = "/detail/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailDto> detail(Model model,
                                         @PathVariable("goodsId")long goodsId,
                                         HttpServletRequest request, HttpServletResponse response){

        GoodsDto goods = goodsService.getGoodsDtoByGoodsId(goodsId);

        //对秒杀时间进行判断
        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        //秒杀状态
        int miaoshaStatus = 0;
        //秒杀倒计时
        int remainSeconds = 0;
        if (now<startAt){
            miaoshaStatus = 0;
            remainSeconds = (int)(startAt - now)/1000;
        }else if (now>endAt){
            miaoshaStatus = 2;
            remainSeconds = -1;
        }else{
            miaoshaStatus = 1;
            remainSeconds = 0;
        }
        GoodsDetailDto goodsDetailDto = new GoodsDetailDto();
        goodsDetailDto.setGoodsDto(goods);
        goodsDetailDto.setRemainSeconds(remainSeconds);
        goodsDetailDto.setMiaoshaStatus(miaoshaStatus);
        return Result.success(goodsDetailDto);
    }



    @PostMapping(value = "/reduce")
    @ResponseBody
    public Boolean reduce(@RequestParam("goodsId") long goodsId){
        GoodsDto goodsDto = new GoodsDto();
        goodsDto.setId(goodsId);
        boolean success = goodsService.reduceStock(goodsDto);
        //发送消息 减库存成功
        if (success) {
            goodsDto = goodsService.getGoodsDtoByGoodsId(goodsId);
            //对秒杀时间进行判断
            long startAt = goodsDto.getStartDate().getTime();
            long endAt = goodsDto.getEndDate().getTime();
            long now = System.currentTimeMillis();

            //秒杀状态
            int miaoshaStatus = 0;
            //秒杀倒计时
            int remainSeconds = 0;
            if (now<startAt){
                miaoshaStatus = 0;
                remainSeconds = (int)(startAt - now)/1000;
            }else if (now>endAt){
                miaoshaStatus = 2;
                remainSeconds = -1;
            }else{
                miaoshaStatus = 1;
                remainSeconds = 0;
            }
            GoodsDetailDto goodsDetailDto = new GoodsDetailDto();
            goodsDetailDto.setGoodsDto(goodsDto);
            goodsDetailDto.setRemainSeconds(remainSeconds);
            goodsDetailDto.setMiaoshaStatus(miaoshaStatus);
            amqpTemplate.convertAndSend("productInfo", JSON.toJSONString(goodsDetailDto));
        }else{
            amqpTemplate.convertAndSend("productInfo", "null");
        }
        return success;
    }


    @GetMapping(value = "/detail")
    @ResponseBody
    public GoodsDetailDto detail(@RequestParam("goodsId")long goodsId){

        GoodsDto goods = goodsService.getGoodsDtoByGoodsId(goodsId);

        //对秒杀时间进行判断
        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        //秒杀状态
        int miaoshaStatus = 0;
        //秒杀倒计时
        int remainSeconds = 0;
        if (now<startAt){
            miaoshaStatus = 0;
            remainSeconds = (int)(startAt - now)/1000;
        }else if (now>endAt){
            miaoshaStatus = 2;
            remainSeconds = -1;
        }else{
            miaoshaStatus = 1;
            remainSeconds = 0;
        }
        GoodsDetailDto goodsDetailDto = new GoodsDetailDto();
        goodsDetailDto.setGoodsDto(goods);
        goodsDetailDto.setRemainSeconds(remainSeconds);
        goodsDetailDto.setMiaoshaStatus(miaoshaStatus);
        return goodsDetailDto;
    }


    @GetMapping(value = "/showList")
    @ResponseBody
    public List<GoodsDto> showList(){
        try{
            //Thread.sleep(2000);
        }catch (Exception e){
            e.printStackTrace();
        }
        List<GoodsDto> goodsDtoList = goodsService.listGoodsDto();

        return goodsDtoList;
    }

    @GetMapping(value = "/basic")
    @ResponseBody
    public GoodsDto basic(@RequestParam("goodsId") long goodsId){

        GoodsDto goodsDto = goodsService.getGoodsDtoByGoodsId(goodsId);

        return goodsDto;
    }
}
