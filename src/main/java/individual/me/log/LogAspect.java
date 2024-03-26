package individual.me.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Slf4j
@Component
public class LogAspect {


    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

    ThreadLocal<Long> currentTime = new ThreadLocal<>();

    private final LogMapper logMapper;

    public LogAspect(LogMapper logMapper, ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.logMapper = logMapper;
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }

    @Pointcut("@annotation(individual.me.log.Log)")
    public void pointcut(){}


    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        currentTime.set(System.currentTimeMillis());
        Object result = joinPoint.proceed();

        long timeCost = System.currentTimeMillis() - currentTime.get();
        currentTime.remove();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        MyLog myLog = getLog(method, timeCost);

        log.info("start");
        threadPoolTaskExecutor.execute(new LogRecorder(myLog,logMapper));
        log.info("end");
        return result;
    }

    private  MyLog getLog(Method method, long timeCost) {
        String methodName = method.getName();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username  = null;
        if (authentication.getPrincipal() instanceof User user){
            username = user.getUsername();
        }

        Log log = method.getAnnotation(Log.class);

        return new MyLog(null,methodName, timeCost,username,null,log.note());
    }
}
