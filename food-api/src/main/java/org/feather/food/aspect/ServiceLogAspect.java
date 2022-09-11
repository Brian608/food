package org.feather.food.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @projectName: food
 * @package: org.feather.food.aspect
 * @className: ServiceLogAspect
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/9/11 16:32
 * @version: 1.0
 */
@Aspect
@Component
public class ServiceLogAspect {

    public  static  final Logger log= LoggerFactory.getLogger(ServiceLogAspect.class);
    /**
     * 前置通知  在方法调用之前
     * 后置通知  在方法正常调用之后执行
     * 环绕通知  在方法调用之前和之后，都分别可以执行的通知
     * 异常通知 如果在方法调用过程中发生异常，则通知
     * 最终通知  在方法调用之后执行
     */
    /**
     * execution 代表所要执行的表达式主体
     * 第一处* 代表方法返回类型，* 代表所有类型
     * 第二处包名 代表aop监控的类所在的包
     * 第三处 ..代表该包以及子包下的所有类的方法
     * 第四处*代表雷米高，*代表所有类
     * 第五处 *(..) * 代表类中的方法名，（..）表示方法中的任何参数
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* org.feather.food.service.impl..*.*(..))")
    public  Object recordTimeLog(ProceedingJoinPoint  joinPoint) throws Throwable {
        log.info("=========开始执行{}.{}=======",joinPoint.getTarget().getClass(),joinPoint.getSignature().getName());
        long begin =System.currentTimeMillis();
        //执行目标service
        Object proceed = joinPoint.proceed();

        long end =System.currentTimeMillis();

         long  takeTime=end-begin;
         if (takeTime>3000){
             log.error("==========执行结束，耗时：{}毫秒=======",takeTime);
         }else if (takeTime>2000){
             log.warn("==========执行结束，耗时：{}毫秒=======",takeTime);
         }else {
             log.info("==========执行结束，耗时：{}毫秒=======",takeTime);
         }
         return  proceed;

    }

}
