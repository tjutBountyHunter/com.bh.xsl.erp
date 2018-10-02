package xsl.cms.Interceptor;

import Utils.JedisClient;
import Utils.JwtUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null && cookies.length > 0){
            for (Cookie cookie : cookies){
                if (COOKIE_KEY.equals(cookie.getName())){
                    tokenId = cookie.getValue();
                    break;
                }
            }
        }
        if (tokenId != null && !"".equals(tokenId)){
            String token = jedisClient.get(TOKEN_KEY_PREFIX + tokenId);
            if (token != null && !"".equals(token)){
                if (JwtUtils.checkJWTSign(token)){
                    return true;
                }
            }
        }
        StringBuffer returnURL = httpServletRequest.getRequestURL();
        httpServletResponse.sendRedirect(SSO_URL + "?returnUrl=" + returnURL);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
