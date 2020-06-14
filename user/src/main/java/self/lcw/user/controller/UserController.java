package self.lcw.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import self.lcw.user.entity.User;
import self.lcw.user.result.Result;


@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/info")
    @ResponseBody
    public Result<User> userInfo(User user){
        return Result.success(user);
    }
}
