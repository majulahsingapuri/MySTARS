package MySTARS;

import java.util.Scanner;

public final class Helper {
    
    protected static Scanner sc = new Scanner(System.in);

    protected void load() {

        //TODO Change to our loading bars
        System.out.printf("║║║║║║║║║║║║");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }
    }
}
