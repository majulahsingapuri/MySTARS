package MySTARS;

public final class LogoutView extends View {
    
    public LogoutView() {}
   
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

        clearScreen("Login");

    }


}
