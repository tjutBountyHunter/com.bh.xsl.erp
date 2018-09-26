package xsl.cms.Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import xsl.cms.annotation.SystemControllerLog;
import xsl.cms.annotation.SystemServiceLog;
import xsl.cms.mapper.XslLogMapper;
import xsl.cms.mapper.XslThrowinglogMapper;
import xsl.cms.pojo.XslLog;
import xsl.cms.pojo.XslThrowinglog;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 关于日志的切面
 * @author 王坤
 * @since 2018-08-07 Pm 10:40
 */
//定义切面
@Aspect
//便于IOC
@Component
public class LogAspects {
    @Autowired
    private XslLogMapper xslLogMapper;
    @Autowired
    private XslThrowinglogMapper xslThrowinglogMapper;

    //本地异常日志记录对象
    private  static  final Logger logger = LoggerFactory.getLogger(LogAspects.class);

    //切入点，Controller的切入点
    @Pointcut("@annotation(xsl.cms.annotation.SystemControllerLog)")
    private void ControllerAspect(){};

    //切入点，Service的切入点
    @Pointcut("@annotation(xsl.cms.annotation.SystemServiceLog)")
    public void ServiceAspect(){};

    /**
     * 前置通知，用于拦截Controller的操作
     * @param jp 方法对象
     */
    @Before("ControllerAspect()")
    public void BeforeControllerLog(JoinPoint jp){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        //得到ip地址
        String ip = request.getRemoteAddr();
        //得到操作人的信息
        String operationer = null;
        if( session == null ){
            operationer = "非工作人员";
        }else{
            //得到登录人员的信息进行operationer的设置
        }
        try{
            //控制台操作
            System.out.println("====Before Controller前置通知开始====");
            System.out.println("ip地址：" + ip);
            System.out.println("====Before Controller后置通知结束====");
            //Log操作
            XslLog log = new XslLog();
            //设置操作的ip地址
            log.setIp(ip);
            //操作
            log.setOperation(getControllerInfo(jp));
            log.setOperationer(operationer);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            log.setOperationtime(sdf.format(new Date()));
            //Controller日志进行数据库中的存储
            xslLogMapper.insertSelective(log);
        }catch (Throwable e){
            System.out.println("Controller日志操作出现异常！");
            System.out.println(e.getMessage());
        }
    }

    /**
     * 异常通知，用于拦截错误的Service操作
     * @param jp 方法对象
     * @param e 异常对象
     */
    @AfterThrowing(pointcut="ServiceAspect()",throwing = "e")
    public void ThrowingServiceLog(JoinPoint jp,Throwable e){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String operationer = null;
        if( session == null ){
            operationer = "非工作人员";
        }else{
           //得到登录人员的信息进行operationer的设置
        }
        //得到ip
        String ip = request.getRemoteAddr();
            //控制台操作
            System.out.println("====Throwing Service异常通知开始====");
            System.out.println("ip地址：" + ip);
            System.out.println("====Throwing Service异常通知结束====");
            //Log操作
            XslThrowinglog log = new XslThrowinglog();
            //设置操作的ip地址
            log.setIp(ip);
            //操作
            log.setThrowing(e.getClass().getName());
            log.setOperationer(operationer);
            //获取方法的名称
            jp.getSignature().getName();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            log.setThrowingtime(sdf.format(new Date()));
            this.xslThrowinglogMapper.insertSelective(log);
            System.out.println("Service日志操作出现异常！");
            System.out.println(e.getClass().getName());
    }

    /**
     * 获取Controller中方法的对象中的信息
     * @param joinPoint
     * @return 信息列举的字符串
     */
    public String getControllerInfo(JoinPoint joinPoint){
        //返回的描述的信息
        String information = "";
        try{
            //得到类名
            String ClassName = joinPoint.getTarget().getClass().getName();
            //得到方法名
            String MethodName = joinPoint.getSignature().getName();
            //得到传入的参数
            Object[] args = joinPoint.getArgs();
            //通过反射回去对象的类类型
            Class targetClass = Class.forName(ClassName);
            //通过类类型获取他的每一个方法
            Method[] methods = targetClass.getMethods();
            //遍历方法，找到那个被通知的方法
            for(Method method:methods){
                if(method.getName().equals(MethodName)){
                    Class[] clazzs = method.getParameterTypes();
                    if(clazzs.length == args.length){
                        information = method.getAnnotation(SystemControllerLog.class).description();
                        break;
                    }
                }
            }
        }catch (Throwable throwable){
            System.out.println("Controller的日志异常通知！");
        }
        return information;
    }

    /**
     * Service异常通知的方法的信息
     * @param joinPoint
     * @return 方法中的信息
     */
    public String getServiceInfo(JoinPoint joinPoint){
        String information = "";
        try {
            //获取方法的类
            String ClassName = joinPoint.getTarget().getClass().getName();
            //获取方法的名称
            String MethodName = joinPoint.getSignature().getName();
            //获取方法的传入参数
            Object[] args = joinPoint.getArgs();
            //获取方法的类类型
            Class clazzs = Class.forName(ClassName);
            //使用方法的类类型得到方法
            Method[] methods = clazzs.getMethods();
            //进行方法判断，找到与拦截的方法一样的方法对象
            for(Method method:methods){
                if(MethodName == method.getName()){
                    if(args.length == method.getParameterTypes().length){
                        //方法反射到他的注解的内容
                        information = method.getAnnotation(SystemServiceLog.class).description();
                        break;
                    }
                }
            }
        }catch (Throwable e){
            System.out.println("Service异常通知中的异常！");
        }
        return information;
    }
}
