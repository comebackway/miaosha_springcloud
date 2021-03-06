package self.lcw.user.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import self.lcw.product.client.ProductClient;
import self.lcw.user.server.result.Result;
import self.lcw.user.server.service.UserService;
import self.lcw.user.server.vo.LoginVo;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductClient productClient;

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping("/to_login")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    //@Valid注解作用：该参数需要符合jsr303参数校验规则
    public Result<String> doLogin(HttpServletResponse response, @Valid LoginVo loginVo){

        String token = userService.login(response,loginVo);

        return Result.success(token);
    }
}
