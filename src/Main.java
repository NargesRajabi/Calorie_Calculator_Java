import java.util.Scanner;

public class Main {
    private static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) {
        boolean quit = false;
        Users.getInstance().loadData();
        while (!quit){
            printOptions();
            System.out.print(">");
            String inputStr = scan.nextLine();
            int input;
            if(Validator.validPositiveNumber(inputStr)){
                input = Integer.parseInt(inputStr);
            }else{
                System.out.println("Invalid Input");
                continue;
            }
            switch (input) {
                case 1 -> Login.login();
                case 2 -> Register.register();
                case 3 -> quit = true;
                default -> System.out.println("Invalid input");
            }
        }
    }
    
    public static void printOptions(){
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Quit");
    }

    
}
