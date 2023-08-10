package com.example.appshopping;

import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

public class Function_class
{
    public static String FormatMoney(long money)
    {
        Locale locale = new Locale("vi","VN");
        NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
        return  numberFormat.format(money)+"đ";
    }
   public static int  createID()
   {
    return  (int) new Date().getTime();
   }

}
