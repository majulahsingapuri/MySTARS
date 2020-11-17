package MySTARS;

/**
 * The abstract superclass of all views in the application.
 * @author Bhargav, Nicolette
 * @version 1.0
 * @since 2020-11-1
 */
public abstract class View {
    
    /**
     * Necessary print functions that will be used as the start point of every view.
     */
    public abstract void print();

    /**
     * ClearScreen function that prints a header as well as the current view that the User is in.
     * @param directory The path of the current view that the user is in.
     */
    protected void clearScreen(String directory) {
        Helper.printLargeSpace();
               
        String spaces = String.format("%" + (71 - directory.length()) + "s", "");

        System.out.println("╔════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                                        ║");
        System.out.println("║                              Welcome to                                ║");
        System.out.println("║                                                                        ║");
        System.out.println("║      ███╗   ███╗██╗   ██╗███████╗████████╗ █████╗ ██████╗ ███████╗     ║");
        System.out.println("║      ████╗ ████║╚██╗ ██╔╝██╔════╝╚══██╔══╝██╔══██╗██╔══██╗██╔════╝     ║");
        System.out.println("║      ██╔████╔██║ ╚████╔╝ ███████╗   ██║   ███████║██████╔╝███████╗     ║");
        System.out.println("║      ██║╚██╔╝██║  ╚██╔╝  ╚════██║   ██║   ██╔══██║██╔══██╗╚════██║     ║");
        System.out.println("║      ██║ ╚═╝ ██║   ██║   ███████║   ██║   ██║  ██║██║  ██║███████║     ║");
        System.out.println("║      ╚═╝     ╚═╝   ╚═╝   ╚══════╝   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝     ║");
        System.out.println("║                                                                        ║");
        System.out.println("║                    Nanyang Technological University                    ║");            
        System.out.println("║                                                                        ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════╝");
        
        System.out.println("╔════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║ " + directory + spaces + "║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════╝");
        System.out.println();
    }
}
