/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylibrary;

import java.awt.BorderLayout;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
import entity.Author;
import entity.Book;
import entity.History;
import entity.Reader;
import interfaces.Keeping;
import tools.SaverToFile;

/**
 *
 * @author pupil
 */
public class App {

    private Scanner scanner = new Scanner(System.in);
    private Book[] books = new Book[10];
    private Reader[] readers = new Reader[10];
    private History[] histories = new History[10];
    private Keeping keeper = new SaverToFile();

    public App() {
        books = keeper.loadBooks();
        readers = keeper.loadReaders();
        histories = keeper.loadHistories();
    }
    

    public void run() {
        String repeat = "r";
        do {

            System.out.println("Выберите номер задачи: ");
            System.out.println("0: Выход из программы");
            System.out.println("1: Добавить книгу");
            System.out.println("2: Список книг");
            System.out.println("3: Добавить читателя");
            System.out.println("4: Список читателей");
            System.out.println("5: Выдать книгу");
            System.out.println("6: Список выданных книг");
            System.out.println("7: Возврат книги");

            int task = scanner.nextInt();
            scanner.nextLine();
            switch (task) {
                case 0:
                    repeat = "q";
                    System.out.println("Пока! :)");
                    break;
                case 1:
                    System.out.println("---- Добавление книги ----");
                    for (int i = 0; i < books.length; i++) {
                        if (books[i] == null) {
                            books[i] = addBook();
                            keeper.saveBooks(books);
                            break;
                        }
                    }
                    break;
                case 2:
                    System.out.println("----- Список книг -----");
                    for (int i = 0; i < books.length; i++) {
                        if (books[i] != null) {
                            System.out.println(books[i].toString());
                        }
                    }
                    break;
                case 3:
                    System.out.println("----- Добавление читателей -----");
                    for (int i = 0; i < readers.length; i++) {
                        if (readers[i] == null) {
                            readers[i] = addReader();
                            keeper.saveReaders(readers);
                            break;
                        }
                    }
                    break;
                case 4:
                    System.out.println("----- Список читателей -----");
                    for (int i = 0; i < readers.length; i++) {
                        if (readers[i] != null) {
                            System.out.println(readers[i].toString());
                        }
                    }
                    break;
                case 5:
                    System.out.println("----- Выдать книгу -----");
                    for (int i = 0; i < histories.length; i++) {
                        if (histories[i] == null) {
                            histories[i] = addHistory();
                            keeper.saveHistories(histories);
                            break;
                        }
                    }
                    System.out.println("------------------------");
                    break;
                case 6:
                    System.out.println("----- Список выданных книг -----");
                    printGivenBooks();
                    break;
                case 7:
                    System.out.println("----- Возврат книги -----");
                    System.out.println("Список выданных книг: ");
                    printGivenBooks();
                    System.out.println("Введите номер возвращаемой книги: ");
                    int numberHistory = scanner.nextInt();
                    scanner.nextLine();
                    Calendar c = new GregorianCalendar();
                    histories[numberHistory - 1].setReturnedDate(c.getTime());
                    System.out.printf("Книга \"%s\" возвращена%n", histories[numberHistory - 1].getBook().getBookName());
                    break;
                default:
                    System.out.println("Введите номер из списка!");
            }

        } while ("r".equals(repeat));
    }

    private void printGivenBooks() {
        int n = 0;
        for (int i = 0; i < histories.length; i++) {
            if (histories[i] != null && histories[i].getReturnedDate() == null) {
                System.out.printf("%d. Книгу %s читает %s %s. Выдана книга: %s%n",
                        i + 1,
                        histories[i].getBook().getBookName(),
                        histories[i].getReader().getFirstname(),
                        histories[i].getReader().getLastname(),
                        histories[i].getGivenDate().toString());
                n++;
            }
        }
        if (n < 1) {
            System.out.println("Нет выданных книг");
        }
    }

    private Book addBook() {
        Book book = new Book();
        System.out.print("Введите название книги: ");
        book.setBookName(scanner.nextLine());
        System.out.print("Введите год публикации книги: ");
        book.setPublishedYear(scanner.nextInt());
        scanner.nextLine();
        System.out.println("Автор книги: ");
        System.out.print("Количество авторов: ");
        int countAuthors = scanner.nextInt();
        scanner.nextLine();
        Author[] authors = new Author[countAuthors];
        for (int i = 0; i < authors.length; i++) {
            Author author = new Author();
            System.out.print("Введите имя автора" + (i + 1) + ": ");
            author.setFirstname(scanner.nextLine());
            System.out.print("Введите фамилию автора: ");
            author.setLastname(scanner.nextLine());
            System.out.print("Введите год рождения автора: ");
            author.setBirthYear(scanner.nextInt());
            scanner.nextLine();
            authors[i] = author;

        }
        book.setAuthor(authors);
        return book;
    }

    private Reader addReader() {
        Reader reader = new Reader();
        System.out.println("Введите имя читателя: ");
        reader.setFirstname(scanner.nextLine());
        System.out.println("Введите фамилию читателя: ");
        reader.setLastname(scanner.nextLine());
        System.out.println("Введите номер телефона читателя: ");
        reader.setPhone(scanner.nextLine());
        return reader;
    }

    private History addHistory() {
        History history = new History();
        /**
         * 1. Вывести пронумерованный список книг библиотеки
         * 2. Попросить пользователя выбрать номер книги 
         * 3. Вывести пронумерованный список читателей
         * 4. Попросить пользователя выбрать номер читателя
         * 5. Сгенерировать текущую дату выдачи 6. Инициировать объект History (задать состояние)
         */
        System.out.println("Список книг");
        for (int i = 0; i < books.length; i++) {
            if (books[i] != null) {
                System.out.println(i + 1 + ". " + books[i].toString());
            }
        }
        System.out.print("Введите номер книги: ");
        int numberBook = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Список читателей");
        for (int i = 0; i < readers.length; i++) {
            if (readers[i] != null) {
                System.out.println(i + 1 + ". " + readers[i].toString());
            }
        }
        System.out.print("Введите номер читателя: ");
        int numberReader = scanner.nextInt();
        scanner.nextLine();
        history.setBook(books[numberBook - 1]);
        history.setReader(readers[numberReader - 1]);
        Calendar c = new GregorianCalendar();
        history.setGivenDate(c.getTime());
        return history;
    }
}
