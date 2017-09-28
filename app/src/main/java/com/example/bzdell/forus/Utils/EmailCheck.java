package com.example.bzdell.forus.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jack on 2017/8/26.
 */

public class EmailCheck {

   public static boolean isValidEmail(String mail) {

    Pattern pattern = Pattern
    .compile("^[A-Za-z0-9][\\w\\._]*[a-zA-Z0-9]+@[A-Za-z0-9-_]+\\.([A-Za-z]{2,4})");
    Matcher mc = pattern.matcher(mail);

        return mc.matches();
}

}
