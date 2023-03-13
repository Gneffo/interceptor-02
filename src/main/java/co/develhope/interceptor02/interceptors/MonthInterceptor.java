package co.develhope.interceptor02.interceptors;

import co.develhope.interceptor02.entities.Month;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
public class MonthInterceptor implements HandlerInterceptor {

    public static List<Month> monthList = new ArrayList<>(Arrays.asList(
    new Month(1,"July","Luglio","Juli"),
    new Month(2,"June","Giugno","Juni"),
    new Month(3,"August","Agosto","August"),
    new Month(4,"September","Settembre","September"),
    new Month(5,"October","Ottobre","Oktober"),
    new Month(6,"November","Novembre","November")
    ));

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String monthIdString = request.getHeader("monthNumber");
        if(monthIdString == null || monthIdString.isEmpty()){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return false;
        }else {
            int monthId = Integer.parseInt(monthIdString);
            Month month = monthList.stream().filter(singleMonth -> {
                return singleMonth.getMonthNumber() == monthId;
            }).findFirst().orElseGet(() -> new Month(monthId, "nope",
                    "nope",
                    "nope"));
            request.setAttribute("monthNumber",month);
        }
        response.setStatus(HttpServletResponse.SC_OK);
        return true;
    }
}
