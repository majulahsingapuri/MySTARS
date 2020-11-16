package MySTARS;

/**
 * The log out page before being redirected to the {@link LoginView}.
 * @author Nicolette
 * @version 1.0
 * @since 2020-11-1
 */
public final class LogoutView extends View {

    /**
     * Constructor method
     */
    public LogoutView() {}

    /**
     * Required method from View. Logs out the {@link Database.CURRENT_USER} and redirects user back to the log in page.
     */
    protected void print() {
    clearScreen("Logout");

        Database.CURRENT_USER = null;
        Database.CURRENT_ACCESS_LEVEL = AccessLevel.NONE;
           
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║                                                  ║");
        System.out.println("║           You have been logged out of            ║");
        System.out.println("║       __  ___     _____________   ___  ____      ║");
        System.out.println("║      /  |/  /_ __/ __/_  __/ _ | / _ \\/ __/      ║");
        System.out.println("║     / /|_/ / // /\\ \\  / / / __ |/ , _/\\ \\        ║");
        System.out.println("║    /_/  /_/\\_, /___/ /_/ /_/ |_/_/|_/___/        ║");
        System.out.println("║           /___/                                  ║");
        System.out.println("║                                                  ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        System.out.println("\n\nTaking you back to the log in screen");
        for (int i = 0; i < 6; i++) {
            Helper.load();
        }

        System.out.println("\n");

        clearScreen("Login");
    }
}
