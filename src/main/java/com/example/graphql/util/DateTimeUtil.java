package com.example.graphql.util;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author pcs
 * Date         2020/4/9
 * Description:
 */
@Component
public class DateTimeUtil {
    public String convertDate2Str(String format, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
}
