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
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import tools.SaverToFile;

/**
 *
 * @author pupil
 */
public class App {

    private Scanner scanner = new Scanner(System.in);
    private List<Book> books = new ArrayList<>();
    private List<Reader> readers = new ArrayList<>();
    private List<History> histories = new ArrayList<>();
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
                    addBook();
                    System.out.println("-----------------------");
                    break;
                case 2:
                    System.out.println("---- Список книг -----");
                    printListBooks();
                    System.out.println("-----------------------");
                    break;
                case 3:
                    addReader();
                    break;
                case 4:
                    printListReaders();
                    break;
                case 5:
                    addHistory();
                    System.out.println("-----------------------");
                    break;
                case 6:
                    System.out.println("----- Список выданных книг -----");
                    printGivenBooks();
                    System.out.println("-----------------------");
                    break;
                case 7:
                    System.out.println("---- Возврат книги -----");
                    returnBook();
                    break;
                default:
                    System.out.println("Введите номер из списка!");
            }

        } while ("r".equals(repeat));
    }
    private boolean quit(){
        System.out.println("Чтобы закончить операцию нажмите \"q\", для продолжения любой другой символ");
        String quit = scanner.nextLine();
        if("q".equals(quit)) return true;
        return false;
    }
    
    private void returnBook() {
        System.out.println("Вернуть книгу: ");
        if(quit()) return;
        Set<Integer> numbersGivenBooks = printGivenBooks();
        if(numbersGivenBooks.isEmpty()){
            return;
        }
        int historyNumber = insertNumber(numbersGivenBooks);
        Calendar c = new GregorianCalendar();
        histories.get(historyNumber - 1).setReturnedDate(c.getTime());
        for (int i = 0; i < books.size(); i++) {
          if(books.get(i).getBookName().equals(histories.get(historyNumber-1).getBook().getBookName())){
            books.get(i).setCount(books.get(i).getCount()+1);
          }
        }
        keeper.saveBooks(books);
        keeper.saveHistories(histories);
    }

  private Set<Integer> printGivenBooks(){
        System.out.println("Список выданных книг: ");
        Set<Integer> setNumberGivenBooks = new HashSet<>();
        for (int i = 0; i < histories.size(); i++) {
            //если history не null и книга не возварщена и книг в наличии меньше
            // чем записано в quantity -
            // печатаем книгу
            if(histories.get(i) != null 
                    && histories.get(i).getReturnedDate() == null
                    && histories.get(i).getBook().getCount()
                        <histories.get(i).getBook().getQuantity()
                    ){
                System.out.printf("%d. Книгу: %s читает %s %s%n",
                        i+1,
                        histories.get(i).getBook().getBookName(),
                        histories.get(i).getReader().getFirstname(),
                        histories.get(i).getReader().getLastname()
                );
                setNumberGivenBooks.add(i+1);
            }
        }
        if(setNumberGivenBooks.isEmpty()){
            System.out.println("Выданных книг нет");
        }
        return setNumberGivenBooks;
    }

    private void addBook() {
        Book book = new Book();
        System.out.print("Введите название книги: ");
        book.setBookName(scanner.nextLine());
        System.out.print("Введите год публикации книги: ");
        book.setPublishedYear(getNumber());
        System.out.print("Введите количество экзамепляров книги: ");
        book.setQuantity(getNumber());
        book.setCount(book.getQuantity());
        
        System.out.println("Автор книги: ");
        System.out.print("Количество авторов: ");
        int countAuthors = getNumber();
        scanner.nextLine();
        Author[] authors = new Author[countAuthors];
        for (int i = 0; i < authors.length; i++) {
            Author author = new Author();
            System.out.print("Введите имя автора" + (i + 1) + ": ");
            author.setFirstname(scanner.nextLine());
            System.out.print("Введите фамилию автора: ");
            author.setLastname(scanner.nextLine());
            System.out.print("Введите год рождения автора: ");
            author.setBirthYear(getNumber());
            scanner.nextLine();
            authors[i] = author;

        }
        book.setAuthor(authors);
        books.add(book);
        keeper.saveBooks(books);
    }

    private void addReader() {
        Reader reader = new Reader();
        System.out.println("Введите имя читателя: ");
        reader.setFirstname(scanner.nextLine());
        System.out.println("Введите фамилию читателя: ");
        reader.setLastname(scanner.nextLine());
        System.out.println("Введите номер телефона читателя: ");
        reader.setPhone(scanner.nextLine());
        readers.add(reader);
        keeper.saveReaders(readers);
    }

    private void addHistory() {
        History history = new History();
        Book book = new Book();
        /**
         * 1. Вывести пронумерованный список книг библиотеки
         * 2. Попросить пользователя выбрать номер книги 
         * 3. Вывести пронумерованный список читателей
         * 4. Попросить пользователя выбрать номер читателя
         * 5. Сгенерировать текущую дату выдачи 6. Инициировать объект History (задать состояние)
         */
        
        System.out.println("-------- Выдать книгу читателю --------");
        
        System.out.println("Список книг: ");
        Set<Integer> setNumbersBooks = printListBooks();
        if(setNumbersBooks.isEmpty()){
            return;
        }
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i) != null && books.get(i).getCount() > 0) {
                System.out.println(i + 1 + ". " + books.get(i).toString());
                
                System.out.print("Введите номер книги: ");
                int numberBook = insertNumber(setNumbersBooks);

                System.out.println("Список читателей");
                Set<Integer> setNumbersReaders = printListReaders();
                System.out.print("Введите номер читателя: ");
                int numberReader = insertNumber(setNumbersReaders);
                history.setBook(books.get(numberBook - 1));
                if(books.get(numberBook - 1).getCount() > 0){
                    books.get(numberBook - 1).setCount(books.get(numberBook - 1).getCount() - 1);
                }
                history.setReader(readers.get(numberReader - 1));
                Calendar c = new GregorianCalendar();
                history.setGivenDate(c.getTime());
                LocalDate localdate = LocalDate.now();
                localdate = localdate.plusWeeks(2);
                history.setLocalReturnedDate(localdate);
            }else{
                System.out.print("пуст");
                break;
            }
        }
        keeper.saveBooks(books);
        histories.add(history);
        keeper.saveHistories(histories);
    }
    
    private Set<Integer> printListBooks() {
        System.out.println("Список книг: ");
        books = keeper.loadBooks();
        Set<Integer> setNumbersBooks = new HashSet<>();
        for (int i = 0; i < books.size(); i++) {
            StringBuilder cbAuthors = new StringBuilder();
            for (int j = 0; j < books.get(i).getAuthor().length; j++) {
                cbAuthors.append(books.get(i).getAuthor()[j].getFirstname()).append(" ").append(books.get(i).getAuthor()[j].getLastname()).append(". ");
            }
            if (books.get(i) != null && books.get(i).getCount() > 0) {
                System.out.printf("%d. %s. %s В наличии экземпляров: %d%n",i+1,books.get(i).getBookName(),cbAuthors.toString(),books.get(i).getCount());
                setNumbersBooks.add(i+1);
            }else if(books.get(i) != null){
                System.out.printf("%d. %s. %s Нет в наличии.",i+1,books.get(i).getBookName(),cbAuthors.toString());
                System.out.println("Предполагаемая дата возврата книги: "+histories.get(i).getLocalReturnedDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            }
        }
        return setNumbersBooks;
    }

    private int getNumber() {
        do{
            try{
                String strNumber = scanner.nextLine();
                return Integer.parseInt(strNumber);
            } catch (Exception e) {
                System.out.println("Попробуй еще раз: ");
            }
        }while(true);
    }
    
    private int insertNumber(Set<Integer> setNumbers) {
        do{
            int historyNumber = getNumber();
            if(setNumbers.contains(historyNumber)){
                return historyNumber;
            }
            System.out.println("Попробуй еще раз: ");
        }while(true);
    }
    
    private Set<Integer> printListReaders() {
        Set<Integer> setNumbersReaders = new HashSet<>();
        System.out.println("Список читателей: ");
        for (int i = 0; i < readers.size(); i++) {
            if(readers.get(i) != null){
                System.out.printf("%d. %s%n"
                        ,i+1
                        ,readers.get(i).toString()
                );
                setNumbersReaders.add(i+1);
            }
        }
        return setNumbersReaders;
    }
}
