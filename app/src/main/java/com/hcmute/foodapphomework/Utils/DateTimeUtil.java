package com.hcmute.foodapphomework.Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DateTimeUtil {
    public static List<String> get7DateTime() {
        LocalDate currentDate = null;
        List<String> list = new ArrayList<String>();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
            String formattedDate = currentDate.format(formatter);
            list.add(formattedDate);
            for (int i = 0; i < 6; i++) {
                currentDate = currentDate.plusDays(1);
                list.add(currentDate.format(formatter));
            }
        }
       return list;
    }
}
