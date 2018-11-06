package kz.edu.nu.cs.Utility;

import java.util.regex.Pattern;

public class CheckRegex {
    public CheckRegex(){}

    public boolean checkEmailRegex(String email) {
        return Pattern.compile("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$").matcher(email).matches();
    }
    public boolean checkPasswordRegex(String password) {
        return Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$").matcher(password).matches();
    }
    public boolean checkImgRegex(String img) {
        return Pattern.compile("(http(s?):)([/|.|\\w|\\s|-])*\\.(?:jpg|gif|png|jpeg)").matcher(img).matches();
    }
}
