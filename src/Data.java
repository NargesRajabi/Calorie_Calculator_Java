import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Data {
    public static ArrayList<Food> foodsReader(File f){
        ArrayList<Food> foods = new ArrayList<>();
        try(Scanner sc = new Scanner(f)){
            String category = sc.nextLine();
            String quantity = sc.nextLine().split("/")[1];//Skipping First Line -> name/ml/cal
            while(sc.hasNextLine()){
                String[] info = sc.nextLine().split(",");
                String name = info[0];
                int cal = Integer.parseInt(info[2]);
                Food food = new Food(category,name,quantity,cal);
                foods.add(food);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return foods;
    }
}
