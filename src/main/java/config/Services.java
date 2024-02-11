package config;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Services {

    static Scanner in = new Scanner(System.in);

    public static int getIntFromUser(String textToPrint) {
        System.out.println(textToPrint);
        int inputInt;
        try {
            inputInt = in.nextInt();
            if (inputInt < 0) throw new IllegalArgumentException();
        } catch (InputMismatchException | IllegalArgumentException e) {
            System.err.println("\nNiepoprawne dane (" + e + ").\n");
            inputInt = 0;
        } finally {
            in.nextLine();
        }
        return inputInt;
    }

    public static String getStringFromUser(String textToPrint) {
        System.out.println(textToPrint);
        return in.nextLine();
    }

    public static float getFloatFromUser(String textToPrint) {
        System.out.println(textToPrint);
        return in.nextFloat();
    }

    public static Scanner getIn() {
        return in;
    }


}
