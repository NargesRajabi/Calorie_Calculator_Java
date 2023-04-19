public class Food {
    private String category;
    private String name;
    private String quantity;
    private int calorieIn100;

    public Food(String category,String name, String quantity, int calorieIn100) {
        this.category = category;
        this.name = name;
        this.quantity = quantity;
        this.calorieIn100 = calorieIn100;
    }
    public double calorieCalc(double value){
        double perOneCal = this.calorieIn100/100.0;
        return value*perOneCal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public int getCalorieIn100() {
        return calorieIn100;
    }

    public String getCategory() {
        return category;
    }

    public void setCalorieIn100(int calorieIn100) {
        this.calorieIn100 = calorieIn100;
    }

    @Override
    public String toString(){
        return category;
    }
}
