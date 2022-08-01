package pl.javastart.library.app;

import pl.javastart.library.io.DataReader;
import pl.javastart.library.model.Book;

import java.util.Scanner;

class Library {
    public static void main(String[] args) {
        final String appName = "Biblioteka v0.7";
        Scanner scanner = new Scanner(System.in);

        Book[] books =  new Book[1000];

        DataReader dataReader = new DataReader();
        books[0]=dataReader.readAndCreateBook();
        books[1]=dataReader.readAndCreateBook();

        System.out.println(appName);
        System.out.println("Ksiazki dostepne w bibliotece:");
        books[0].printInfo();
        books[1].printInfo();
        System.out.println("System moze przechowywac do " + books.length + " ksiazek");
    }
}