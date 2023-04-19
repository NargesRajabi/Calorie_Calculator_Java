import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Stream;

public class User {
    private String username;
    private String password;
    private int age;
    private double weight;
    private double height;
    private double goalWeight;
    private String specificDiet;
    private String gender;
    private String healthIssue;
    private String activity;
    private HashMap<LocalDate, Double> dailyConsumedCalories = new HashMap<>();

    public User(String username, String password, int age, double weight, double height, double goalWeight,
                String specificDiet, String gender, String healthIssue, String activity) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.goalWeight = goalWeight;
        this.specificDiet = specificDiet;
        this.gender = gender;
        this.healthIssue = healthIssue;
        this.activity = activity;
    }

    public void addTodayConsumedCalories(double kCal) {
        LocalDate today = LocalDate.now();
        if (dailyConsumedCalories.containsKey(today)) {
            dailyConsumedCalories.replace(today, dailyConsumedCalories.get(today) + kCal);
        } else {
            dailyConsumedCalories.put(today, kCal);
        }
    }

    public double todayConsumedCalorie() {
        return dailyConsumedCalories.getOrDefault(LocalDate.now(), 0.0);
    }

    public void getSpecificDayInformation(LocalDate day) {
        if (dailyConsumedCalories.containsKey(day)) {
            System.out.println(day + " - " + day.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()) + " - Consumed Calorie: " + dailyConsumedCalories.get(day));
        } else {
            System.out.println(day + " - " + day.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()) + " - Consumed Calorie: 0");
        }
    }

    public void getTodayInformation() {
        double calorieIntake = 0.0;
        if(dailyConsumedCalories.get(LocalDate.now())!=null)
         calorieIntake = dailyConsumedCalories.get(LocalDate.now());
        else
            dailyConsumedCalories.put(LocalDate.now(),0.0);
        System.out.println("--------------------");
        System.out.println("Calorie Budget: " + BMI.Calorie(this));
        System.out.println("Calorie Intake: " + calorieIntake);
        double consumed = (BMI.Calorie(this) - dailyConsumedCalories.get(LocalDate.now()));
        if(consumed<0)
            System.out.println("Calorie Left: Calorie Over " + Math.abs(consumed));
        else
            System.out.println("Calorie Left: " + consumed );
        System.out.println("--------------------");
    }

    public HashMap<LocalDate, Double> getDailyConsumedCalories() {
        return dailyConsumedCalories;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getGoalWeight() {
        return goalWeight;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGoalWeight(double goalWeight) {
        this.goalWeight = goalWeight;
    }

    public String getSpecificDiet() {
        return specificDiet;
    }

    public void setSpecificDiet(String specificDiet) {
        this.specificDiet = specificDiet;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHealthIssue() {
        return healthIssue;
    }

    public void setHealthIssue(String healthIssue) {
        this.healthIssue = healthIssue;
    }

    public void showAllInformation() {
        System.out.println("Your Personal Information is: ");
        System.out.println();
        System.out.println("username: " + username);
        System.out.println("Age: " + age);
        System.out.println("Gender: " + gender);
        if (healthIssue != null) {
            System.out.println("Based on your health issue \"" + getHealthIssue() + "\" we recommend you to get " + BMI.Calorie(this) + " cal Daily");
        }else{
            System.out.println("Based on your health issue \"None\" we recommend you to get " + BMI.Calorie(this) + " cal Daily");

        }
        System.out.println("Activity: "+ activity);
        System.out.println("Current weight: " + weight + "kg");
        System.out.println("Goal Weight: " + goalWeight + "kg");
        System.out.println("Height: " + height + "m");
        System.out.println("specific Diet: " + specificDiet);
        System.out.println("------------------------------");
        System.out.println();

    }
}
