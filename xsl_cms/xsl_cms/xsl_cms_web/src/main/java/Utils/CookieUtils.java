package Utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 说明：
 *
 * @Auther: 11432_000
 * @Date: 2018/10/2 21:21
 * @Description:
 */
public class CookieUtils {

    public static Cookie getCookieByName(HttpServletRequest request , String cookieName){
        /**
         *
         * 功能描述: 根据name获取cookie，出错返回null
         *
         * @param: [request, cookieName]
         * @return: javax.servlet.http.Cookie
         * @auther: 11432_000
         * @date: 2018/10/2 21:27
         */
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0){
            for (Cookie cookie : cookies){
                if (cookieName.equals(cookie.getName())){
                    return cookie;
                }
            }
        }
        return null;
    }
}
