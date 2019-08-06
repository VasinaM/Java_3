package lesson1;

import java.util.ArrayList;
import java.util.Arrays;

public class Box <T extends Fruit> {
    private ArrayList<T> fruits;

    public Box(T... fruits){
        this.fruits = new ArrayList<T>(Arrays.asList(fruits));
    }

    public ArrayList<T> getFruits(){
        return new ArrayList<T>(fruits);
    }

    public void add(T fruits){
        this.fruits.addAll(Arrays.asList(fruits));
    }

    public float getWeight(){
        if (fruits.size() == 0){
            return 0;
        }
        float weight = 0.0f;
        for (T fruit : fruits){
            weight += fruit.getWeight();
        }
        return weight;
    }

    public boolean compare(Box box) {
        return this.getWeight() == box.getWeight();
    }

    public void  transfer(Box<? super T> box) {
        box.fruits.addAll(this.fruits);
        clear();
    }

    private void clear() {
        fruits.clear();
    }
}
