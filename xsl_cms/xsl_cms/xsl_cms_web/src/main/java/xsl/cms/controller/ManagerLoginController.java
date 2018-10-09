package xsl.cms.controller;

import Utils.CookieUtils;
import Utils.JedisClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 说明：
 *
 * @Auther: 11432_000
 * @Date: 2018/10/5 21:29
 * @Description:
 */
@Controller
public class ManagerLoginController {
    @Value("${TOKEN_KEY}")
    private String TOKEN_KEY;
    @Value("${SSO_URL}")
    private String SSO_URL;
    @Resource
    private JedisClient jedisClient;

    @RequestMapping("/manager/logout")
    public String managerLogout(HttpServletRequest request , HttpServletResponse response){
        String tokenKey = CookieUtils.getCookieValue(request, TOKEN_KEY);
        jedisClient.del(tokenKey);
        setCookie(response , TOKEN_KEY , "");
        StringBuffer requestURL = request.getRequestURL();
        int i = requestURL.indexOf("/manager/logout");
        int length = requestURL.length();
        requestURL.replace(i + 1,length,"").toString();
        return "redirect:" + SSO_URL + "?returnUrl=" + requestURL;
    }


    private void setCookie(HttpServletResponse response ,String name , String value){
        Cookie cookie = new Cookie(name , value);
        //47.93.230.61
        cookie.setDomain("47.93.230.61");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
