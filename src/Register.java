import java.util.Scanner;


public class Register {
  private static Scanner scan = new Scanner(System.in);

  public static void register(){
    System.out.print("Enter username: ");
    String username;
    while(true){
      username = scan.nextLine();
      if(username.trim().isEmpty()){
        System.out.print("! Please Enter A Username: ");
      }else if(Users.getInstance().userExist(username)!=null){
        System.out.print("! User Already Exists. Please Try Again: ");
      }else if(!Validator.validUsername(username)){
        System.out.print("! Username Should not include symbols:");
      }else{
        break;
      }
    }

    System.out.print("Enter password(8 characters, at least 1 uppercase, 1 lowercase and 1 digit): ");
    String password = scan.nextLine();
    while(!Validator.validPassword(password)) {
      System.out.print("! Password is too weak, please choose strong password: ");
      password = scan.nextLine();
    }

    System.out.print("Enter age: ");
    String ageStr;
    int age;
    while(true){
       ageStr = getInput(scan.nextLine(),Validator.POSITIVE_NUMBER,
              "! Age must be positive number. Please Try Again");
      age = Integer.parseInt(ageStr);
      if(age>115 || age<13){
        System.out.print("! Age must be < 115 and > 13. Please Try Again:");
      }else{
        break;
      }
    }

    System.out.print("Enter Current Weight(kg): ");
    double weight;
    while(true){
      String weightStr = getInput(scan.nextLine(),Validator.POSITIVE_DOUBLE_NUMBER,
              "! weight must be positive number.Please Try Again");
      weight = Double.parseDouble(weightStr);
      if(weight>=30 && weight<=300){
        break;
      }else{
        System.out.print("Weight should be between 30 kg and 300 kg. Try again: ");
      }
    }


    System.out.print("Enter goal weight(kg): ");
    double goalWeight;
    while(true){
      String goalWeightStr = getInput(scan.nextLine(),Validator.POSITIVE_DOUBLE_NUMBER,
              "! Goal weight must be positive number.Please Try Again");
      goalWeight = Double.parseDouble(goalWeightStr);
      if(goalWeight>=30 && goalWeight<=300){
        break;
      }else{
        System.out.print("Goal Weight should be between 30 kg and 300 kg. Try again: ");
      }
    }

    System.out.print("Enter Current Height(cm): ");
    double height;
    while(true){
      String heightStr = getInput(scan.nextLine(),Validator.POSITIVE_DOUBLE_NUMBER,
              "! Height Must be positive number");
      height = Double.parseDouble(heightStr);
      if(height>=50 && height<=250){
        break;
      }else{
        System.out.print("! Height should be between 50cm and 250cm.Try again: ");
      }
    }

    System.out.println("Please Select Activity: ");
    System.out.println("1. Very Low");
    System.out.println("2. Low");
    System.out.println("3. Normal");
    System.out.println("4. High");
    System.out.print("> ");
    String activity;
    whileloop:
    while(true){
      String activityNumber = scan.nextLine();
      if(Validator.validPositiveNumber(activityNumber)){
        int selectedNumber = Integer.parseInt(activityNumber);
        switch (selectedNumber){
          case 1:
            activity = "very low";
            break whileloop;
          case 2:
            activity = "low";
            break whileloop;
          case 3:
            activity = "normal";
            break whileloop;
          case 4:
            activity = "high";
            break whileloop;
          default:
            System.out.print("!input out of range. Please select again: ");
        }
      }else{
        System.out.print("!Please Enter One of options number: ");
      }
    }

    String genderStr;
    int g;
    String Gender;
    while(true){
      System.out.print("Select Gender(1.Male / 2.Female): ");
       genderStr = getInput(scan.nextLine(),Validator.POSITIVE_DOUBLE_NUMBER,
              "! Please Enter A value(1. Male / 2.Female)");
       g = Integer.parseInt(genderStr);
      if(g==1){
        Gender = "Male";
        break;
      }else if(g==2){
        Gender = "Female";
        break;
      }else{
        System.out.print("! Please Select 1. Male or 2.Female: ");
      }
    }

    System.out.println("Select Specific Diet: ");
    System.out.println("1. None");
    System.out.println("2. Diabetic");
    System.out.println("3. Vegan");
    System.out.println("4. Lactose free");
    System.out.print("> ");
    String specificDiet;
    whileLoop:
    while(true){
      String input = scan.nextLine();
      if(Validator.validPositiveNumber(input)){
        int inp = Integer.parseInt(input);
        switch (inp){
          case 1:
            specificDiet = "None";
            break whileLoop;
          case 2:
            specificDiet = "Diabetic";
            break whileLoop;
          case 3:
            specificDiet = "Vegan";
            break whileLoop;
          case 4:
            specificDiet = "Lactose free";
            break whileLoop;
          default:
            System.out.print("!input out of range. Please select again:");
        }
        }else{
        System.out.print("! Please Enter Option Number: ");

      }
    }

    System.out.print("Health Issues? (true / false)");
    String booleanStr = scan.nextLine();
    boolean isHealthIssue;
    while(!booleanStr.equalsIgnoreCase("true") && !booleanStr.equalsIgnoreCase("false")){
      System.out.print("Please Enter true or false: ");
      booleanStr = scan.nextLine();
    }
    isHealthIssue = Boolean.parseBoolean(booleanStr);
    String healthIssue= null;
    if(isHealthIssue){
      System.out.print("Enter Health Issue: ");
       healthIssue = scan.nextLine();
       while(healthIssue.isEmpty()){
         System.out.print("! Please Enter HealthIssue: ");
         healthIssue = scan.nextLine();
         if(!healthIssue.equalsIgnoreCase("null")){
             break;
         }
       }
    }
    if(healthIssue!=null && age<18){
      System.out.println("Sorry You Cannot Register. because of age<18 and having healthIssue");
    }else {
      User registeredUser = new User(username, password, age, weight,height, goalWeight, specificDiet, Gender, healthIssue,activity);
      Users.getInstance().add(registeredUser);
      System.out.println("-------User Registered Successfully---------");
      registeredUser.showAllInformation();
    }
  }
  public static String getInput(String input, Validator validator, String errorMsg){
    if(validator==Validator.POSITIVE_NUMBER) {
      while (!Validator.validPositiveNumber(input) || input.trim().isEmpty()) {
        System.out.print(errorMsg + ":");
        input = scan.nextLine();
      }
    }else{
      while (!Validator.validDoublePositiveNumber(input) || input.trim().isEmpty()) {
        System.out.print(errorMsg + ":");
        input = scan.nextLine();
      }
    }
    return input;
  }

  
}
