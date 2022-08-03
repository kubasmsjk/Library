package pl.javastart.library.app;

public class LibraryApp {
    public static void main(String[] args) {
        final String appName = "Biblioteka v0.8";
        System.out.println(appName);

        LibraryControl libraryControl = new LibraryControl();
        libraryControl.controlloop();
    }
}
