package pl.javastart.library.app;

import pl.javastart.library.io.DataReader;
import pl.javastart.library.model.Book;
import pl.javastart.library.model.Library;

public class LibraryControl {

    private final int exit =0;
    private final int addBook =1;
    private final int printBooks =2;
    private DataReader dataReader = new DataReader();
    private Library library = new Library();

    public void controlloop(){
        int option;
        do{
            printOptions();
            option= dataReader.getInt();
            switch (option){
                case addBook:
                    addBook();
                    break;
                case printBooks:
                    printBooks();
                    break;
                case exit:
                    exit();
                default:
                    System.out.println("Nie ma takiej opcji, wprowadz ponownie");
            }
        }while(option!=exit);
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
        System.out.println(exit + " - wyjscie z programu");
        System.out.println(addBook + " - dodanie nowej ksiazki");
        System.out.println(printBooks + " - wyswietl dostepne ksiazki");
    }
}
