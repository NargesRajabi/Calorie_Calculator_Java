import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public enum Validator {
    POSITIVE_NUMBER,
    POSITIVE_DOUBLE_NUMBER;
    public static boolean validPositiveNumber(String number){
        try {
            int n = Integer.parseInt(number);
            if(n<=0)
                return false;
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }

    public static boolean validYear(String year){
        if(validPositiveNumber(year)){
            int y = Integer.parseInt(year);
            if(y >= 1000){
                return true;
            }
        }
        return false;
    }
    public static boolean validMonth(String month){
        if(validPositiveNumber(month)){
            int m = Integer.parseInt(month);
            if(m>=1 && m<=12)
                return true;
        }
        return false;
    }
    public static boolean validDayOfMonth(String day){
        if(validPositiveNumber(day)){
            int d = Integer.parseInt(day);
            if(d>=1 && d<=31)
                return true;
        }
        return false;
    }

    //username is valid if just contains numbers and alphabtes
    public static boolean validUsername(String username){
        StringBuilder checkUsernameLength = new StringBuilder();
        for (int i = 0; i < username.length(); i++) {
            if(
                    Character.isDigit(username.charAt(i))||
                    Character.isAlphabetic(username.charAt(i))){
                checkUsernameLength.append(username.charAt(i));
            }
        }
        if(checkUsernameLength.length()==username.length())
            return true;
        return false;
    }
    public static boolean validPassword(String password) {
        char ch;
        boolean capitalFlag = false;
        boolean lowerCaseFlag = false;
        boolean numberFlag = false;
        boolean onlyChars_letters_numbers = true;
        if(password.length() < 8) {
            return false;
        }

        for(int i=0 ; i<password.length() ; i++) {
            ch = password.charAt(i);
            if( Character.isDigit(ch)) {
                numberFlag = true;
            }
            if (Character.isUpperCase(ch)) {
                capitalFlag = true;
            }
            if (Character.isLowerCase(ch)) {
                lowerCaseFlag = true;
            }
        }
        for (int i = 0; i < password.length(); i++) {
            if(password.charAt(i)<32 || password.charAt(i)>126){
                onlyChars_letters_numbers = false;
            }
        }
        if(numberFlag && capitalFlag && lowerCaseFlag && onlyChars_letters_numbers){
            return true;
        }
        return false;
    }

    public static boolean validDoublePositiveNumber(String number){
        try {
            double n = Double.parseDouble(number);
            if(n<=0)
                return false;
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }
}
