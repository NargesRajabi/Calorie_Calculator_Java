import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Users {
    private Users() {
    }

    private static Users instance = new Users();

    public static Users getInstance() {
        return instance;
    }




    private ArrayList<User> users = new ArrayList<>();

    public void add(User newUser) {
        if (userExist(newUser.getUsername())==null) {
            users.add(newUser);
            saveUser(newUser);
        }
    }

    private void loadToList(User newUser) {
        if (userExist(newUser.getUsername())==null) {
            users.add(newUser);
        }
    }

    public User userExist(String username) {

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equalsIgnoreCase(username)) {
                return users.get(i);
            }
        }
        return null;
    }


    public User login(String username, String password) {
        if (userExist(username)==null) return null;

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equalsIgnoreCase(username) && users.get(i).getPassword().equalsIgnoreCase(password)) {
                return users.get(i);
            }
        }

        return null;
    }

    public void loadData() {
        try (Scanner sc = new Scanner(new File("usersInformation.txt"));
             Scanner sc2 = new Scanner(new File("Users Daily Consumed Cal Information.txt"))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] userInfo = line.split(",");
                String username = userInfo[0];
                String password = userInfo[1];
                int age = Integer.parseInt(userInfo[2]);
                double weight = Double.parseDouble(userInfo[3]);
                double height = Double.parseDouble(userInfo[4]);
                double goalWeight = Double.parseDouble(userInfo[5]);
                String specificDiet = userInfo[6];
                String gender = userInfo[7];
                String healthIssue = userInfo[8];
                String activity = userInfo[9];
                User u = new User(username, password, age, weight, height, goalWeight, specificDiet, gender, healthIssue, activity);
                this.loadToList(u);
            }
            while(sc2.hasNextLine()){
                String line = sc2.nextLine();
                if(line.startsWith("-")){
                    User u = userExist(line.substring(1).trim());
                    line = sc2.nextLine();
                    while(line.startsWith("+")){
                        String[] line_info = line.substring(1).split("\t");
                        u.getDailyConsumedCalories().put(LocalDate.parse(line_info[0]), Double.valueOf(line_info[1]));
                        line = sc2.nextLine();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("! Loading Data Failed: ");
            e.printStackTrace();
        }
    }
    private void saveUser(User u){
        try (FileWriter fw = new FileWriter("usersInformation.txt",true)) {
                String information = String.format("%s,%s,%d,%f,%f,%f,%s,%s,%s,%s",
                        u.getUsername(), u.getPassword(), u.getAge(), u.getWeight(),
                        u.getHeight(), u.getGoalWeight(), u.getSpecificDiet(),
                        u.getGender(), u.getHealthIssue(), u.getActivity()
                );
                fw.write(information + "\n");
        } catch (IOException e) {
            System.out.println("Saving user data Failed");
        }
    }
    public ArrayList<User> getUsers(){
        return this.users;
    }
    public void saveModifiedUsersData() {
        try (FileWriter fw = new FileWriter("usersInformation.txt")) {
            for (User u : users) {
                String information = String.format("%s,%s,%d,%f,%f,%f,%s,%s,%s,%s",
                        u.getUsername(), u.getPassword(), u.getAge(), u.getWeight(),
                        u.getHeight(), u.getGoalWeight(), u.getSpecificDiet(),
                        u.getGender(), u.getHealthIssue(), u.getActivity()
                );
                fw.write(information + "\n");
            }

        } catch (IOException e) {
            System.out.println("Saving Data Failed");
        }
    }

}

