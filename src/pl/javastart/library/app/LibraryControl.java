package pl.javastart.library.app;

import pl.javastart.library.exception.DataImportException;
import pl.javastart.library.exception.InvalidDataException;
import pl.javastart.library.exception.NoSuchOptionException;
import pl.javastart.library.io.ConsolePrinter;
import pl.javastart.library.io.DataReader;
import pl.javastart.library.io.file.FileManager;
import pl.javastart.library.io.file.FileManagerBuilder;
import pl.javastart.library.model.Book;
import pl.javastart.library.model.Library;
import pl.javastart.library.model.Magazine;
import pl.javastart.library.model.Publication;
import pl.javastart.library.model.comparator.AlphabeticalTitleComparator;

import java.util.Arrays;
import java.util.InputMismatchException;

public class LibraryControl {

    private ConsolePrinter printer = new ConsolePrinter();
    private DataReader dataReader = new DataReader(printer);
    private FileManager fileManager;
    private Library library;

    LibraryControl() {
        fileManager = new FileManagerBuilder(printer, dataReader).build();
        try {
            library = fileManager.importData();
            printer.printLine("Zaimportowano dane z pliku.");
        } catch (DataImportException | InvalidDataException e) {
            printer.printLine(e.getMessage());
            printer.printLine("Zainicjowano nowa baze.");
            library = new Library();
        }

    }

    void controlloop() {
        Option option;
        do {
            printOptions();
            option = getOption();
            switch (option) {
                case ADD_BOOK:
                    addBook();
                    break;
                case ADD_MAGAZINE:
                    addMagazine();
                    break;
                case PRINT_BOOKS:
                    printBooks();
                    break;
                case PRINT_MAGAZINES:
                    printMagazines();
                    break;
                case DELETE_BOOK:
                    deleteBoook();
                    break;
                case DELETE_MAGAZINE:
                    deleteMagazine();
                    break;
                case EXIT:
                    exit();
                    break;
                default:
                    printer.printLine("Nie ma takiej opcji, wprowadz ponownie");
            }
        } while (option != Option.EXIT);
    }


    private Option getOption() {
        boolean optionOK = false;
        Option option = null;
        while (!optionOK) {
            try {
                option = Option.createFromInt(dataReader.getInt());
                optionOK = true;
            } catch (NoSuchOptionException e) {
                printer.printLine(e.getMessage());
            } catch (InputMismatchException e) {
                printer.printLine("Wprowadzono wartosc, ktora nie jest liczba, podaj ponownie");
            }
        }
        return option;
    }

    private void printMagazines() {
        Publication[] publications = getSortedPublications();
        printer.printMagazines(publications);
    }

    private Publication[] getSortedPublications() {
        Publication[] publications = library.getPublications();
        Arrays.sort(publications, new AlphabeticalTitleComparator());
        return publications;
    }

    private void addMagazine() {
        try {
            Magazine magazine = dataReader.readAndCreateMagazine();
            library.addPublication(magazine);
        } catch (InputMismatchException e) {
            printer.printLine("Nie udalo sie utworzyc magazynu, niepoprawne dane.");
        } catch (ArrayIndexOutOfBoundsException e) {
            printer.printLine("Osiagnieto limit pojemnosci, nie mozna dodac kolejnego magazynu.");
        }
    }
    private void deleteMagazine() {
        try {
            Magazine magazine = dataReader.readAndCreateMagazine();
            if (library.removePublication(magazine)) {
                printer.printLine("Usunieto magazyn.");
            } else {
                printer.printLine("Brak wskazanego magazyn.");
            }
        }catch (InputMismatchException e){
            printer.printLine("Nie udalo sie utworzyc magazynu, niepoprawne dane.");
        }
    }


    private void exit() {
        try {
            fileManager.exportData(library);
            printer.printLine("Export danych do pliku zakonczony powodzeniem.");
        }catch (DataImportException e){
            printer.printLine(e.getMessage());
        }
        printer.printLine("Koniec proogramu.");
        dataReader.close();
    }

    private void printBooks() {
        Publication[] publications = getSortedPublications();
        printer.printBooks(publications);
    }

    private void addBook() {
        try {
            Book book = dataReader.readAndCreateBook();
            library.addPublication(book);
        } catch (InputMismatchException e) {
            printer.printLine("Nie udalo sie utworzyc ksiazki, niepoprawne dane.");
        } catch (ArrayIndexOutOfBoundsException e) {
            printer.printLine("Osiagnieto limit pojemnosci, nie mozna dodac kolejnej ksiazki.");
        }
    }
    private void deleteBoook() {
        try {
            Book book = dataReader.readAndCreateBook();
            if (library.removePublication(book)) {
                printer.printLine("Usunieto ksiazke.");
            } else {
                printer.printLine("Brak wskazanej ksiazki.");
            }
        }catch (InputMismatchException e){
            printer.printLine("Nie udalo sie utworzyc ksiazki, niepoprawne dane.");
        }
    }
    private void printOptions() {
        printer.printLine("Wybierz opcje");
        for (Option option : Option.values()) {
            printer.printLine(option.toString());
        }
    }

    private enum Option {
        EXIT(0, "wyjscie z programu"),
        ADD_BOOK(1, "dodanie nowej ksiazki"),
        ADD_MAGAZINE(2, "dodanie nowego magazynu"),
        PRINT_BOOKS(3, "wyswietl dostepne ksiazki"),
        PRINT_MAGAZINES(4, "wyswietl dostepne magazyny"),
        DELETE_BOOK(5, "usun, ksiazke"),
        DELETE_MAGAZINE(6, "usun, magazyn");
        private final int value;
        private final String description;

        Option(int value, String description) {
            this.value = value;
            this.description = description;
        }

        public int getValue() {
            return value;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return value + " - " + description;
        }

        static Option createFromInt(int option) throws NoSuchOptionException {
            try {
                return Option.values()[option];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new NoSuchOptionException("Brak opcji o id " + option);
            }
        }

    }
}
