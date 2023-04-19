import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.*;

public class Login {
    private static User loggedIn;
  public static void login(){
    Scanner scan = new Scanner(System.in);
    System.out.print("Enter Username: ");
    String username = scan.nextLine();
    System.out.print("Enter Password: ");
    String password = scan.nextLine();
    loggedIn = Users.getInstance().login(username,password);
    if(loggedIn!=null){
        System.out.println("---------Welcome!---------");
        boolean quit = false;
        while(!quit){
            userOptions();
            System.out.print(">");
            String inputStr = scan.nextLine();
            int input;
            if(Validator.validPositiveNumber(inputStr)){
                input = Integer.parseInt(inputStr);
            }else{
                System.out.println("Try Again");
                continue;
            }
            switch (input){
                case 1:
                    loggedIn.showAllInformation();
                    break;
                case 2:
                    loggedIn.getTodayInformation();
                    break;
                case 3:
                    showFoods();
                    break;
                case 4:
                    System.out.println("---------- Today Consumed Calories -----------");
                    System.out.println("Today is "+ LocalDate.now()+" - "+LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()));
                    System.out.println(loggedIn.todayConsumedCalorie()+"/ "+BMI.Calorie(loggedIn)+" cal ");
                    System.out.println("----------------------------------------------");
                    break;
                case 5:
                    editInformation();
                    break;
                case 6:
                    dayConsumedCalorie();
                    break;
                case 7:
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid Input. Try Again");
            }
        }
    }else{
        System.out.println("Invalid Username or password");
    }
    System.out.println("------------------------");
  }

  public static void showFoods(){
      List<String> warningCategories = new ArrayList<>();
        if(loggedIn.getSpecificDiet().equalsIgnoreCase("Diabetic")){
            warningCategories = List.of("(Fruit) Juices Calories", "Canned Fruit","Cakes & Pies","Soda & Soft Drinks",
                    "Ice cream","Fruits", "Candy sweets", "Beer");
        }else if(loggedIn.getSpecificDiet().equalsIgnoreCase("Vegan")){
            warningCategories = List.of("Beef Veal","Cakes & Pies","Cheese","Cream Cheese","Dishes & Meals",
                    "Fast Food","Fish & Seafood","Ice cream","Meat","Milk & Dairy Products(ml)","Milk & Dairy Products(g)",
                    "Pizza","Pork","Poultry & Fowl","Sausage","Soups","Spreads","Yogurts");
        }else if(loggedIn.getSpecificDiet().equalsIgnoreCase("Lactose free")){

            warningCategories = List.of("Cakes & Pies", "Candy Sweets", "Cheese", "Cream Cheese",
                    "Ice cream", "Milk & Dairy Products(ml)", "Milk & Diary Products(g)",
                    "Sauces & Dressings", "Sauces & Dressings(ml)", "Spreads", "Yogurt"
                    );
        }
      Scanner scan = new Scanner(System.in);
      File f = new File("src/consumedCaloriesData");
      File[] files = f.listFiles();
      ArrayList<ArrayList<Food>> foods = new ArrayList<>();


      Arrays.stream(files).forEach(e -> {
          foods.add(Data.foodsReader(e));
      });

      while(true) {
          System.out.println("---------- Total Consumed Calories -----------");
          System.out.println(loggedIn.todayConsumedCalorie()+"/ "+BMI.Calorie(loggedIn)+" cal ");
          System.out.println("----------------------------------------------");
          int i;
          for (i = 0; i < foods.size(); i++) {
              System.out.println((i + 1) + ". " + foods.get(i).get(0).getCategory());//printing foods list with Showing index from i+1
          }
          System.out.println((i+1)+". Back");
          if(loggedIn.todayConsumedCalorie()>BMI.Calorie(loggedIn)){
              System.err.print("Warning: You Have Already Consumed your Daily calorie.  If you want to back, please select "+(i+1)+".Back Option or you can continue): \"");
          }
          System.out.print("\nSelect > ");
          String inputStr = scan.nextLine();
          while(!Validator.validPositiveNumber(inputStr)){
                  System.out.print("Sorry Input Must be a Number: ");
                  inputStr = scan.nextLine();
          }
          int input = Integer.parseInt(inputStr);

          if(input==(i+1)){
              System.out.println("---------- Today Consumed Calories -----------");
              System.out.println(loggedIn.todayConsumedCalorie()+"/ "+BMI.Calorie(loggedIn)+" cal ");
              System.out.println("----------------------------------------------");
              return;
          }else{
              if(input>i || input<0){
                  System.out.println("Invalid Try Again");
                  continue;
              }
              ArrayList<Food> selectedFoodList = foods.get(input-1);//i-1 because index starts from zero
              int j;
              for (j = 0; j < selectedFoodList.size(); j++) {
                  System.out.println((j+1)+". "+selectedFoodList.get(j).getName());
              }
              System.out.println((j+1)+". Back");
              //check if category is listed specificDiet
              String selectedFoodCategry = selectedFoodList.get(0).getCategory().toLowerCase();
              for (int k = 0; k < warningCategories.size(); k++) {
                  if(selectedFoodCategry.contains(warningCategories.get(k).toLowerCase())){
                      System.err.print("\n(Warning: As you are "+ loggedIn.getSpecificDiet()+" You Better not to choose this category. If you want to back, please select "+(j+1)+".Back Option however you can continue): ");
                    break;
                  }
              }
              System.out.print("Select Food from List <Per 100 "+selectedFoodList.get(0).getQuantity()+">: ");
              String selectedStr = scan.nextLine();
              int selected;
              while(!Validator.validPositiveNumber(selectedStr)){
                  System.out.println("Try Again. Select Food From List<per 100 "+selectedFoodList.get(0).getQuantity()+">:");
                  selectedStr = scan.nextLine();
              }
              selected = Integer.parseInt(selectedStr);
              if(selected==j+1){
                  continue;
              }
              if(selected>j+1 || selected<=0){
                  System.out.println("Invalid. Try Again");
                  continue;
              }
              System.out.println("How many "+selectedFoodList.get(selected-1).getQuantity()+" Of "+ selectedFoodList.get(selected-1).getName()+" Have you Consumed? ");
              String valueStr = scan.nextLine();
              double value;
              if(Validator.validDoublePositiveNumber(valueStr)){
                  value = Double.parseDouble(valueStr);
              }else{
                  System.out.println("Try Again. Value Must be Positive Number");
                  continue;
              }
              loggedIn.addTodayConsumedCalories(selectedFoodList.get(selected-1).calorieCalc(value));
              System.out.println("---Select Next Food---");
              saveUserDailyInfo();
          }
      }
  }
  private static void saveUserDailyInfo(){
      try (FileWriter fw2 = new FileWriter("Users Daily Consumed Cal Information.txt")) {
          for (User u : Users.getInstance().getUsers()) {
              fw2.write("-" + u.getUsername() + "\n");
              for (LocalDate day : u.getDailyConsumedCalories().keySet()) {
                  fw2.write("+"+day+"\t"+u.getDailyConsumedCalories().get(day)+"\n");
              }
              fw2.write("\n");
          }

      } catch (IOException e) {
          System.out.println("Saving Data Failed");
      }
  }
  public static void editInformation(){
      Scanner scan = new Scanner(System.in);
      boolean quit = false;
      while(!quit){
          System.out.println("1. Edit Password ");
          System.out.println("2. Edit age ");
          System.out.println("3. Edit current weight ");
          System.out.println("4. Edit Goal Weight ");
          System.out.println("5. Edit Height ");
          System.out.println("6. Edit specific Diet ");
          System.out.println("7. Edit gender ");
          System.out.println("8. Edit health Issue ");
          System.out.println("9. Edit Activity ");
          System.out.println("10. Back ");
          System.out.print("Select> ");
          String input = scan.nextLine();
          if(Validator.validPositiveNumber(input)){
              int inp = Integer.parseInt(input);
              switch (inp){
                  case 1:
                      System.out.print("Enter password(8 characters, at least 1 uppercase, 1 lowercase and 1 digit): ");
                      String password = scan.nextLine();
                      while(!Validator.validPassword(password)) {
                          System.out.print("! Password is too weak, please choose strong password: ");
                          password = scan.nextLine();
                      }
                      System.out.println("Password Changed");
                      loggedIn.setPassword(password);
                      break;
                  case 2:
                      System.out.print("Enter age: ");
                      String ageStr;
                      int age;
                      while(true){
                          ageStr = Register.getInput(scan.nextLine(),Validator.POSITIVE_NUMBER,
                                  "! Age must be positive number. Please Try Again");
                          age = Integer.parseInt(ageStr);
                          if(age>115 || age<13){
                              System.out.print("! Age must be < 115 and > 13. Please Try Again:");
                          }else{
                              break;
                          }
                      }
                      if(loggedIn.getHealthIssue()!=null && age<18) {
                          System.out.println("Sorry You Cannot Change Your Age. because of having healthIssue");
                      }else {
                          loggedIn.setAge(age);
                          System.out.println("Age Changed");
                      }break;
                  case 3:
                      System.out.print("Enter Current Weight(kg): ");
                      double weight;
                      while(true){
                          String weightStr = Register.getInput(scan.nextLine(),Validator.POSITIVE_DOUBLE_NUMBER,
                                  "! weight must be positive number.Please Try Again");
                          weight = Double.parseDouble(weightStr);
                          if(weight>=30 && weight<=300){
                              break;
                          }else{
                              System.out.print("Weight should be between 30 kg and 300 kg. Try again: ");
                          }
                      }
                      System.out.println("Weight Changed");
                      loggedIn.setWeight(weight);
                      break;
                  case 4:
                      System.out.print("Enter goal weight(kg): ");
                      double goalWeight;
                      while(true){
                          String goalWeightStr = Register.getInput(scan.nextLine(),Validator.POSITIVE_DOUBLE_NUMBER,
                                  "! Goal weight must be positive number.Please Try Again");
                          goalWeight = Double.parseDouble(goalWeightStr);
                          if(goalWeight>=30 && goalWeight<=300){
                              break;
                          }else{
                              System.out.print("Goal Weight should be between 30 kg and 300 kg. Try again: ");
                          }
                      }
                      System.out.println("Goal Weight Changed");
                      loggedIn.setGoalWeight(goalWeight);
                      break;
                  case 5:
                      System.out.print("Enter Current Height(cm): ");
                      double height;
                      while(true){
                          String heightStr = Register.getInput(scan.nextLine(),Validator.POSITIVE_DOUBLE_NUMBER,
                                  "! Height Must be positive number");
                          height = Double.parseDouble(heightStr);
                          if(height>=50 && height<=250){
                              break;
                          }else{
                              System.out.print("! Height should be between 50cm and 250cm.Try again: ");
                          }
                      }
                      System.out.println("Height Changed");
                      loggedIn.setHeight(height);
                      break;
                  case 6: {
                      System.out.println("Select Specific Diet: ");
                      System.out.println("1. None");
                      System.out.println("2. Diabetic");
                      System.out.println("3. Vegan");
                      System.out.println("4. Lactose free");
                      System.out.print("> ");
                      String specificDiet;
                      whileLoop:
                      while(true){
                           input = scan.nextLine();
                          if(Validator.validPositiveNumber(input)){
                               inp = Integer.parseInt(input);
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
                      System.out.println("Specific Diet Changed");
                      loggedIn.setSpecificDiet(specificDiet);
                      break;
                  }
                  case 7:
                      String genderStr;
                      int g;
                      String Gender;
                      while(true){
                          System.out.print("Select Gender(1.Male / 2.Female): ");
                          genderStr = Register.getInput(scan.nextLine(),Validator.POSITIVE_DOUBLE_NUMBER,
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

                      System.out.println("Gender Changed");
                      loggedIn.setGender(Gender);
                      break;
                  case 8:
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
                          if(loggedIn.getAge()<18){
                              System.out.println("! Sorry, Because Of Age < 18 you cannot Add Health Issues");
                              return;
                          }
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
                      System.out.println("Health Issue Changed");
                      loggedIn.setHealthIssue(healthIssue);
                      break;
                  case 9:
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
                              System.out.print("!Please Enter On of options number: ");
                          }
                      }
                      System.out.println("Activity Changed");
                      loggedIn.setActivity(activity);
                      break;
                  case 10:
                      quit = true;
                      break;
                  default:
                      System.out.println("! Input Out Of Range. try again:");

              }
          }else{
              System.out.println("! Please Select");
          }
      }
      Users.getInstance().saveModifiedUsersData();
  }
  public static void dayConsumedCalorie(){
      Scanner scan = new Scanner(System.in);
      System.out.println("Enter Year(Please Enter Year >= 1000. for example:2022):");
      String year = scan.nextLine();
      while(!Validator.validYear(year)){
          System.out.print("Try Again. Please Enter Year >= 1000 : ");
          year = scan.nextLine();
      }

      System.out.println("Enter month(Month Range: 1- 12):");
      String month = scan.nextLine();
      while(!Validator.validMonth(month)){
          System.out.print("Try Again. Please Enter in Range of 1 to 12: ");
          month = scan.nextLine();
      }

      System.out.println("Enter Day(Day Range: 1- 31):");
      String day = scan.nextLine();
      while(!Validator.validDayOfMonth(day)){
          System.out.print("Try Again. Please Enter in Range of 1 to 31: ");
          day = scan.nextLine();
      }
      if(Integer.parseInt(month)<10){
          month = "0"+Integer.parseInt(month);
      }
      if(Integer.parseInt(day)<10){
          day = "0"+Integer.parseInt(day);
      }
      try{
          LocalDate date = LocalDate.parse(year+"-"+month+"-"+day);
          loggedIn.getSpecificDayInformation(date);
      }catch (DateTimeParseException e){
          System.out.println("! Please Enter Information Correctly:");
      }
  }
  public static void userOptions(){
    System.out.println("1. Show Information");
    System.out.println("2. Calculate Calorie");
    System.out.println("3. Calculate Consumed Calories");
    System.out.println("4. Today Consumed Calories");
    System.out.println("5. Edit Information");
    System.out.println("6. Day Consumed Calorie Information");
    System.out.println("7. Quit");
  }
}
