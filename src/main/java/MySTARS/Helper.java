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

    protected static String getPasswordInput(){ //TODO remove from login
		Console console = System.console();
		String password = null;
		try {
			char[] input = console.readPassword();
			password = String.copyValueOf(input);
		} catch (Exception e){
			Scanner sc = new Scanner(System.in);
			password = sc.nextLine();
		}
		return password;
	}
}
