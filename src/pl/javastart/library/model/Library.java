package pl.javastart.library.model;

public class Library {

    private static final int MAX_BOOKS = 100;
    private Book[] books = new Book[MAX_BOOKS];
    private int booksNumber = 0;

    public void addBook(Book book) {
        if (booksNumber < MAX_BOOKS) {
            books[booksNumber]=book;
            booksNumber++;
        } else{
            System.out.println("Maksymalna liczba ksiazek zostala osiagnieta");
        }
    }
    public void printBooks(){
        if(booksNumber == 0){
            System.out.println("Brak ksiazek w bibliotece");
        }
        for (int i = 0; i < booksNumber; i++) {
            books[i].printInfo();
        }
    }
}