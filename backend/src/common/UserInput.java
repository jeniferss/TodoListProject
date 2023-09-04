package common;

import java.util.Scanner;

public class UserInput {
    private final Scanner inputReader = new Scanner(System.in);

    public String read(String message) {
        System.out.println(message);
        return inputReader.nextLine().trim();
    }

}
