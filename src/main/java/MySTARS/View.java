package MySTARS;

public abstract class View {
    
    protected abstract void print();

    protected void clearScreen(String directory) {
        try {
            final String os = System.getProperty("os.name");
        
            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
                }
            else {
                Runtime.getRuntime().exec("clear");
            }
        }

        catch (final Exception e) {
            System.err.println(e);
        }
               
        String spaces = String.format("%" + (74 - directory.length()) + "s", "");

        System.out.println("╔════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                                        ║");
        System.out.println("║                              Welcome to                                ║");
        System.out.println("║                                                                        ║");
        System.out.println("║      ███╗   ███╗██╗   ██╗███████╗████████╗ █████╗ ██████╗ ███████╗     ║");
        System.out.println("║      ████╗ ████║╚██╗ ██╔╝██╔════╝╚══██╔══╝██╔══██╗██╔══██╗██╔════╝     ║");
        System.out.println("║      ██╔████╔██║ ╚████╔╝ ███████╗   ██║   ███████║██████╔╝███████╗     ║");
        System.out.println("║      ██║╚██╔╝██║  ╚██╔╝  ╚════██║   ██║   ██╔══██║██╔══██╗╚════██║     ║");
        System.out.println("║      ██║ ╚═╝ ██║   ██║   ███████║   ██║   ██║  ██║██║  ██║███████║     ║");
        System.out.println("║      ╚═╝     ╚═╝   ╚═╝   ╚══════╝   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝     ║");                                ║                                                                        ║
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
