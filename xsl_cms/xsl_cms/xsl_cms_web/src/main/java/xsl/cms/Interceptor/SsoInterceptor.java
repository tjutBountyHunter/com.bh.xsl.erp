package xsl.cms.Interceptor;

import Utils.CookieUtils;
import Utils.JedisClient;
import Utils.JsonUtils;
import Utils.JwtUtils;
import com.xsl.JWTpojo;
import com.xsl.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import xsl.cms.pojo.XslManager;

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
    @Value("${COOKIE_KEY}")
    private String COOKIE_KEY;
    @Value("${TOKEN_KEY_PREFIX}")
    private String TOKEN_KEY_PREFIX;
    @Value("${COOKIE_LIFE}")
    private int COOKIE_LIFE;
    @Value("${ID_COOKIE_KEY}")
    private String ID_COOKIE_KEY;

    @Resource
    private JedisClient jedisClient;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String tokenId = "";
        String tokenKey = httpServletRequest.getParameter("tokenKey");
        if (tokenKey != null && !"".equals(tokenKey)){
            Cookie cookie = new Cookie(COOKIE_KEY , tokenKey);
            cookie.setPath("/");
            cookie.setMaxAge(COOKIE_LIFE);
            httpServletResponse.addCookie(cookie);
        }
        Cookie cookie = CookieUtils.getCookieByName(httpServletRequest, COOKIE_KEY);
        if (cookie != null){
            tokenId = cookie.getValue();
        }
        if (tokenId != null && !"".equals(tokenId)){
            String token = jedisClient.get(TOKEN_KEY_PREFIX + tokenId);
            if (token != null && !"".equals(token)){
                if (JwtUtils.checkJWTSign(token)){
                    setIdInCookie(httpServletResponse , token);
                    return true;
                }
            }
        }
        Cookie cookieWithId = CookieUtils.getCookieByName(httpServletRequest, ID_COOKIE_KEY);
        StringBuffer returnURL = httpServletRequest.getRequestURL();
        if (cookieWithId != null){
            httpServletResponse.sendRedirect(SSO_URL + "?returnUrl=" + returnURL + "&id=" + cookieWithId.getValue());
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

    private void setIdInCookie(HttpServletResponse response ,String token){
        Result payload = JwtUtils.getPayloadByToken(token);
        JWTpojo data = (JWTpojo) payload.getData();
        Map<String, Object> extend = data.getExtend();
        String managerInfoKey = extend.get("managerInfoKey").toString();
        String managerInfo = jedisClient.get(managerInfoKey);
        XslManager xslManager = JsonUtils.jsonToPojo(managerInfo, XslManager.class);
        Cookie cookie = new Cookie(ID_COOKIE_KEY,xslManager.getId().toString());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
