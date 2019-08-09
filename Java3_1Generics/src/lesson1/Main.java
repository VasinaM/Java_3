package lesson1;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    // метод, который меняет два элемента массива местами

    public static void swapTwoElementsArray(Object[] array, int firstElement, int secondElement) {

        Object temp = array[firstElement];
        array[firstElement] = array[secondElement];
        array[secondElement] = temp;
    }


    // метод, который преобразует массив в ArrayList

    public static <T> ArrayList toArrayList(T[] array){
        return new ArrayList<>(Arrays.asList(array));
    }

    public static void main(String[] args) {

        //для перестановки элементов используем swapTwoElementsArray
        Integer[] intArr = {1, 2, 3, 4, 5};
        swapTwoElementsArray(intArr, 0, 1);
        System.out.println(Arrays.toString(intArr));

        //преобразование массива в ArrayList
        ArrayList<Integer> arrayList = toArrayList(intArr);

        //задача с фруктами

        Apple apple1 = new Apple();
        Apple apple2 = new Apple();
        Apple apple3 = new Apple();
        Apple apple4 = new Apple();

        Orange orange1 = new Orange();
        Orange orange2 = new Orange();
        Orange orange3 = new Orange();

        Box<Apple> boxAppls = new Box<Apple>(apple1, apple2, apple3, apple4);
        Box<Orange> boxOranges = new Box<Orange>(orange1, orange2, orange3);

        System.out.println(boxAppls.compare(boxOranges));

        Box<Orange> boxOranges2 = new Box<Orange>();
        boxOranges.transfer(boxOranges2);

    }
}
