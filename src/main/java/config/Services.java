package config;

import java.util.Scanner;

public class Services {

    static Scanner in = new Scanner(System.in);

    public static int getIntFromUser(String textToPrint) {
        System.out.println(textToPrint);
        return in.nextInt();
    }

    public static String getStringFromUser(String textToPrint) {
        System.out.println(textToPrint);
        return in.nextLine();
    }

    public static float getFloatFromUser(String textToPrint) {
        System.out.println(textToPrint);
        return in.nextFloat();
    }

    public static Scanner getIn () {
        return in;
    }


}
