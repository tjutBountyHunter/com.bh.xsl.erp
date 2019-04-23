package xsl.erp.Interceptor;

import Utils.CookieUtils;
import xsl.erp.commons.JedisClient;
import Utils.JsonUtils;
import Utils.JwtUtils;
import com.xsl.JWTpojo;
import com.xsl.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import xsl.erp.pojo.XslManager;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 说明：
 *
 * @Auther: 11432_000
 * @Date: 2018/10/1 16:32
 * @Description:
 */

public class SsoInterceptor implements HandlerInterceptor {

    @Value("${SSO_URL}")
    private String SSO_URL;
    @Value("${TOKEN_KEY}")
    private String TOKEN_KEY;
    @Value("${TOKEN_KEY_PREFIX}")
    private String TOKEN_KEY_PREFIX;
    @Value("${COOKIE_LIFE}")
    private int COOKIE_LIFE;
    @Value("${ID_COOKIE_KEY}")
    private String ID_COOKIE_KEY;
    @Value("${XSL_MANAGER_INFO_KEY}")
    private String XSL_MANAGER_INFO_KEY;

    @Resource
    private JedisClient jedisClient;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String tokenKey = "";
        tokenKey = CookieUtils.getCookieValue(httpServletRequest, TOKEN_KEY);
        if (tokenKey != null && !"".equals(tokenKey)){
            if (checkToken(tokenKey)){
                String token = jedisClient.get(TOKEN_KEY_PREFIX + tokenKey);
                setIdInCookie(httpServletRequest,httpServletResponse,token);
                return true;
            }
        }
        String managerId = CookieUtils.getCookieValue(httpServletRequest, ID_COOKIE_KEY);
        StringBuffer returnURL = httpServletRequest.getRequestURL();
        if (managerId != null){
            httpServletResponse.sendRedirect(SSO_URL + "?returnUrl=" + returnURL + "&id=" + managerId);
            return false;
        }
        httpServletResponse.sendRedirect(SSO_URL + "?returnUrl=" + returnURL);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    private void setIdInCookie(HttpServletRequest request ,HttpServletResponse response , String token){
        /**
         *
         * 功能描述: 将已登录的用户id存入cookie。
         *
         * @param: [request, response, token]
         * @return: void
         * @auther: 11432_000
         * @date: 2018/10/4 19:53
         */
        Result payload = JwtUtils.getPayloadByToken(token);
        JWTpojo data = (JWTpojo) payload.getData();
        Map<String, Object> extend = data.getExtend();
        String managerInfoKey = extend.get("managerInfoKey").toString();
        String managerInfo = jedisClient.get(XSL_MANAGER_INFO_KEY + managerInfoKey);
        XslManager xslManager = JsonUtils.jsonToPojo(managerInfo, XslManager.class);
        setCookie(response ,ID_COOKIE_KEY,xslManager.getId().toString());
    }


    private boolean checkToken(String tokenId){
        /**
         *
         * 功能描述: 检查tokenKey是否合法。
         *
         * @param:
         * @return: boolean
         * @auther: 11432_000
         * @date: 2018/10/4 19:53
         */
        boolean flag = false;
        String token = jedisClient.get(TOKEN_KEY_PREFIX + tokenId);
        if (token != null && !"".equals(token)){
            if (JwtUtils.checkJWTSign(token)){
                flag = true;
            }
        }
        return flag;
    }

    private void setCookie(HttpServletResponse response ,String name , String value){
        Cookie cookie = new Cookie(name , value);
        //47.93.230.61
//        cookie.setDomain("47.93.230.61");
        cookie.setDomain("localhost");
        cookie.setPath("/");
        cookie.setMaxAge(COOKIE_LIFE);
        response.addCookie(cookie);
    }

}
