package pl.javastart.library.app;

import pl.javastart.library.io.DataReader;
import pl.javastart.library.model.Book;
import pl.javastart.library.model.Library;

public class LibraryControl {

    private static final int EXIT = 0;
    private static final int ADD_BOOK = 1;
    private static final int PRINT_BOOKS = 2;
    private DataReader dataReader = new DataReader();
    private Library library = new Library();

    public void controlloop() {
        int option;
        do {
            printOptions();
            option = dataReader.getInt();
            switch (option) {
                case ADD_BOOK:
                    addBook();
                    break;
                case PRINT_BOOKS:
                    printBooks();
                    break;
                case EXIT:
                    exit();
                default:
                    System.out.println("Nie ma takiej opcji, wprowadz ponownie");
            }
        } while (option != EXIT);
    }

    private void exit() {
        System.out.println("Koniec proogramu.");
        dataReader.close();
    }

    private void printBooks() {
        library.printBooks();
    }

    private void addBook() {
        Book book = dataReader.readAndCreateBook();
        library.addBook(book);
    }

    private void printOptions() {
        System.out.println("Wybierz opcje");
        System.out.println(EXIT + " - wyjscie z programu");
        System.out.println(ADD_BOOK + " - dodanie nowej ksiazki");
        System.out.println(PRINT_BOOKS + " - wyswietl dostepne ksiazki");
    }
}
