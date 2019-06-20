package soft.demo.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
    异常处理器的自定义的实现类
 */
public class CustomExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                         Object obj, Exception e)
    {
        //obj:发生异常的地方 例Service层 方法 包名+类名+方法名（形参）字符串
        ModelAndView mav = new ModelAndView();
        //判断异常类型
        if (e instanceof MessageException){
            //预期异常
            MessageException me = (MessageException)e;
            mav.addObject("error",me.getMessage());
        }else {
            mav.addObject("error","未知异常");
        }
        mav.setViewName("error");

        return mav;
    }
}
