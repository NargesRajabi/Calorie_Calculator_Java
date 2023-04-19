import java.util.Scanner;

public class BMI {

    /*
               weight(kg)
    Formula = ------------
               [height(m)]^2
     */
    public static double BMICalculator(User u){
        double weight = u.getWeight();
        double height = u.getHeight();
        //multiply by 0.01 to convert cm to m
        return  Double.parseDouble(String.format("%.2f",weight / Math.pow(height * 0.01 ,2)));
    }
    public static double Calorie(User u){
        double bmi = BMICalculator(u);
        double multipleByActivity = 1;//normal activity is already 1 so we don't declare it in switch case
        switch (u.getActivity()){
            case "very low" -> multipleByActivity = 0.8;
            case "low"-> multipleByActivity=0.9;
            case "high" -> multipleByActivity=1.2;
        }
        if(u.getHealthIssue()!=null && (u.getHealthIssue().equalsIgnoreCase("pregnant") ||
                u.getHealthIssue().equalsIgnoreCase("breastfeeding")))
            return 2300 * multipleByActivity;
        if(bmi>=15 && bmi<25){
            return 1900 * multipleByActivity;
        }else if(bmi >= 25 && bmi<26){
            return 1800 * multipleByActivity;
        }else if(bmi >= 26 && bmi<27){
            return 1700 * multipleByActivity;
        }else if(bmi>=27 && bmi<28){
            return 1600 * multipleByActivity;
        }else if(bmi>= 29){
            return 1500 * multipleByActivity;

        }
        return 2300 * multipleByActivity;
    }
}
