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
import facade.AuthorFacade;
import facade.BookFacade;
import facade.HistoryFacade;
import facade.ReaderFacade;
import interfaces.Keeping;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import tools.SaverToBase;
import tools.SaverToFile;

/**
 *
 * @author pupil
 */
public class App {

    private Scanner scanner = new Scanner(System.in);
    private AuthorFacade authorFacade;
    private BookFacade bookFacade;
    private ReaderFacade readerFacade;
    private HistoryFacade historyFacade;

    public App() {
        init();
    }

    private void init() {
        authorFacade = new AuthorFacade(Author.class);
        bookFacade = new BookFacade(Book.class);
        readerFacade = new ReaderFacade(Reader.class);
        historyFacade = new HistoryFacade(History.class);
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
            System.out.println("8: Добавить автора");
            System.out.println("9: Список авторов");
            System.out.println("10: Выборка книг по автору");
            System.out.println("11: Выборка книг по какому-то слову");
            System.out.println("12: Редактирование книги");
            System.out.println("13: Редактирование данных читателя");
            System.out.println("14: Редактирование данных автора");

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
                case 8:
                    System.out.println("---- Добавить автора -----");
                    addAuthor();
                    break;
                case 9:
                    System.out.println("---- Список авторов -----");
                    printListAuthors();
                    break;
                case 10:
                    System.out.println("----- Выборка книг по автору -----");
                    selectionOfBooksByAuthor();
                    break;
                case 11:
                    System.out.println("----- Выборка книг по какому-то слову -----");
                    selectionOfBooksByWord();
                    break;
                case 12:
                    System.out.println("----- Редактирование книги -----");
                    updateBook();
                    break;
                case 13:
                    System.out.println("----- Редактирование данных читателя -----");
                    updateReader();
                    break;
                case 14:
                    System.out.println("----- Редактирование данных автора -----");
                    updateAuthor();
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
    private void returnBook(){
        System.out.println("Вернуть книгу: ");
        if(quit()) return;
        Set<Integer> numbersGivenBooks = printGivenBooks();
        if(numbersGivenBooks.isEmpty()){
            return;
        }
        int historyNumber = insertNumber(numbersGivenBooks);
        History history = historyFacade.find((long)historyNumber);
        Calendar c = new GregorianCalendar();
        history.setReturnedDate(c.getTime());
//      Здесь объясняется что значит передача по ссылке в Java
//      https://coderoad.ru/40480/%D0%AD%D1%82%D0%BE-Java-pass-by-reference-%D0%B8%D0%BB%D0%B8-pass-by-value
//      Постарайтесь понять почему работает неправильно следующий код.
//         histories.get(historyNumber - 1).getBook().setCount(
//                histories.get(historyNumber-1).getBook().getCount() + 1
//         );
        
//      а следующий работает правильно.
        Book book = bookFacade.find(history.getBook().getId());
        book.setCount(book.getCount()+1);
        bookFacade.edit(book);
        historyFacade.edit(history);
    }
    
    private Set<Integer> printGivenBooks(){
        System.out.println("Список выданных книг: ");
        Set<Integer> setNumberGivenBooks = new HashSet<>();
        List<History> historyesWithGivenBooks = historyFacade.findWithGivenBooks();
        for (int i = 0; i < historyesWithGivenBooks.size(); i++) {
            System.out.printf("%d. Книгу: %s читает %s %s%n",
                    historyesWithGivenBooks.get(i).getId(),
                    historyesWithGivenBooks.get(i).getBook().getBookName(),
                    historyesWithGivenBooks.get(i).getReader().getFirstname(),
                    historyesWithGivenBooks.get(i).getReader().getLastname()
            );
            setNumberGivenBooks.add(historyesWithGivenBooks.get(i).getId().intValue());
        }
        if(setNumberGivenBooks.isEmpty()){
            System.out.println("Выданных книг нет");
        }
        return setNumberGivenBooks;
    }
    
    private void addBook(){
        Book book = new Book();
        Set<Integer> setNumbersAuthors = printListAuthors();
        if(setNumbersAuthors.isEmpty()){
            System.out.println("Добавьте автора.");
            return;
        }
        System.out.print("Если в списке есть авторы книги нажмите 1: ");
        if(getNumber() != 1){
            System.out.println("Введите автора.");
            return;
        }
        System.out.println();
        System.out.print("Введите количество авторов: ");
        int countAutors = getNumber();
        List<Author> authorsBook = new ArrayList<>();
        for (int i = 0; i < countAutors; i++) {
            System.out.println("Введите номер автора "+(i+1)+" из списка: ");
            int numberAuthor = insertNumber(setNumbersAuthors);
            authorsBook.add(authorFacade.find((long)numberAuthor));
        }
        book.setAuthor(authorsBook);
        System.out.print("Введите название книги: ");
        book.setBookName(scanner.nextLine());
        System.out.print("Введите год публикации книги: ");
        book.setPublishedYear(getNumber());
        System.out.print("Введите количество экземпляров книги: ");
        book.setQuantity(getNumber());
        book.setCount(book.getQuantity());
        bookFacade.create(book);
    }
    private void addReader(){
        System.out.println("---- Добавление читателя ----");
        Reader reader = new Reader();
        System.out.print("Введите имя читателя: ");
        reader.setFirstname(scanner.nextLine());
        System.out.print("Введите фамилию читателя: ");
        reader.setLastname(scanner.nextLine());
        System.out.print("Введите телефон читателя: ");
        reader.setPhone(scanner.nextLine());
        readerFacade.create(reader);
        System.out.println("-----------------------");
    }

    private void addHistory() {
        History history = new History();
        /**
         * 1. Вывести пронумерованный список книг библиотеки
         * 2. Попросить пользователя выбрать номер книги
         * 3. Вывести пронумерованный список читателей
         * 4. Попрость пользователя выбрать номер читателя
         * 5. Генерироват текущую дату
         * 6. инициировать (задать состояние) объект history
         */
        System.out.println("------------ Выдать книгу читателю ----------");
        System.out.println("Список книг: ");
        Set<Integer> setNumbersBooks = printListBooks();
        if(setNumbersBooks.isEmpty()){
            return;
        }
        System.out.print("Введите номер книги: ");
        int numberBook = insertNumber(setNumbersBooks);
        Book book = bookFacade.find((long)numberBook);
        System.out.println("Список читателей: ");
        Set<Integer> setNumbersReaders = printListReaders();
        if(setNumbersReaders.isEmpty()) {
            return;
        }
        System.out.print("Введите номер читателя: ");
        int numberReader = insertNumber(setNumbersReaders);
        Reader reader = readerFacade.find((long)numberReader);
        history.setBook(book);
        if(book.getCount() > 0){
            book.setCount(book.getCount()-1);
        }
        history.setReader(reader);
        Calendar c = new GregorianCalendar();
        history.setGivenDate(c.getTime());
        bookFacade.edit(book);
        historyFacade.edit(history);
        System.out.println("========================");
    }

    private Set<Integer> printListBooks() {
        System.out.println("Список книг: ");
        List<Book> books = bookFacade.findAll();
        Set<Integer> setNumbersBooks = new HashSet<>();
        for (int i = 0; i < books.size(); i++) {
            StringBuilder cbAutors = new StringBuilder();
            for (int j = 0; j < books.get(i).getAuthor().size(); j++) {
                cbAutors.append(books.get(i).getAuthor().get(j).getFirstname())
                        .append(" ")
                        .append(books.get(i).getAuthor().get(j).getLastname())
                        .append(". ");
            }
            if(books.get(i) != null && books.get(i).getCount() > 0){
                System.out.printf("%d. %s. %s В наличии экземпряров: %d%n"
                        ,books.get(i).getId()
                        ,books.get(i).getBookName()
                        ,cbAutors.toString()
                        ,books.get(i).getCount()
                );
                setNumbersBooks.add(books.get(i).getId().intValue());
            }else if(books.get(i) != null){
                System.out.printf("%d. %s. %s Нет в наличии. Будет возвращена: %s%n"
                        ,books.get(i).getId()
                        ,books.get(i).getBookName()
                        ,cbAutors.toString()
                        ,getReturnDate(books.get(i))
                );
            }
        }
        return setNumbersBooks;
    }
    
    private Set<Integer> printListAllBooks() {
        System.out.println("Список книг: ");
        List <Book> books = bookFacade.findAll();
        Set<Integer> setNumbersBooks = new HashSet<>();
        for (int i = 0; i < books.size(); i++) {
            StringBuilder cbAutors = new StringBuilder();
            for (int j = 0; j < books.get(i).getAuthor().size(); j++) {
                cbAutors.append(books.get(i).getAuthor().get(j).getFirstname())
                        .append(" ")
                        .append(books.get(i).getAuthor().get(j).getLastname())
                        .append(". ");
            }
            if(books.get(i) != null && books.get(i).getCount() >= 0){
                System.out.printf("%d. %s %s %d. В наличии экземпряров: %d%n"
                        ,books.get(i).getId()
                        ,books.get(i).getBookName()
                        ,cbAutors.toString()
                        ,books.get(i).getPublishedYear()
                        ,books.get(i).getCount()
                );
                setNumbersBooks.add(i+1);
            }else if(books.get(i) != null && books.get(i).getCount() > 0){
                System.out.printf("%d. %s %s %d. Нет в наличии. Будет возвращена: %s%n"
                        ,books.get(i).getId()
                        ,books.get(i).getBookName()
                        ,cbAutors.toString()
                        ,books.get(i).getPublishedYear()
                        ,getReturnDate(books.get(i))
                );
            }
        }
        return setNumbersBooks;
    }
    
    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
    } 
   
    private int getNumber() {
        do{
            try {
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
    private String getReturnDate(Book book){
        List<History> histories = historyFacade.findAll();
        for (int i = 0; i < histories.size(); i++) {
            if(book.getBookName().equals(histories.get(i).getBook().getBookName())){
                LocalDate localGivenDate = histories.get(i).getGivenDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                localGivenDate = localGivenDate.plusDays(14);
                return localGivenDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            }
        }
        return "";
    }
    private Set<Integer> printListReaders() {
        List<Reader> readers = readerFacade.findAll();
        Set<Integer> setNumbersReaders = new HashSet<>();
        System.out.println("Список читателей: ");
        for (int i = 0; i < readers.size(); i++) {
            if(readers.get(i) != null){
                System.out.printf("%d. %s %s. Телефон: %s%n"
                        ,readers.get(i).getId()
                        ,readers.get(i).getFirstname()
                        ,readers.get(i).getLastname()
                        ,readers.get(i).getPhone()
                );
                setNumbersReaders.add(readers.get(i).getId().intValue());
            }
        }
        if(setNumbersReaders.isEmpty()) {
            System.out.println("Добавьте читателей!");
        }
        return setNumbersReaders;
    }

    private Set<Integer> printListAuthors() {
        List<Author> authors = authorFacade.findAll();
        Set<Integer> setNumbersAuthors = new HashSet<>();
        System.out.println("Список книг: ");
        for (int i = 0; i < authors.size(); i++) {
            if(authors.get(i) != null){
                System.out.printf("%d. %s %s%n"
                        ,authors.get(i).getId()
                        ,authors.get(i).getFirstname()
                        ,authors.get(i).getLastname()
                );
                setNumbersAuthors.add(authors.get(i).getId().intValue());
            }
        }
        return setNumbersAuthors;
    }

    private void addAuthor() {
        System.out.println("---- Добавление автора ----");
        Author author = new Author();
        System.out.print("Введите имя автора: ");
        author.setFirstname(scanner.nextLine());
        System.out.print("Введите фамилию автора: ");
        author.setLastname(scanner.nextLine());
        System.out.print("Введите год рождения: ");
        author.setBirthYear(getNumber());
        authorFacade.create(author);
        System.out.println("-----------------------");
    }

    private void selectionOfBooksByAuthor() {
        List <Book> books = bookFacade.findAll();
        System.out.println("----- Выборка книг по автору -----");
        Set<Integer> setNumbersAuthors = printListAuthors();
        if(setNumbersAuthors.isEmpty()){
            System.out.println("Список авторов пуст. Добавьте автора!");
            return;
        }
        System.out.println("Выберите номер автора: ");
        Author author = authorFacade.find((long)insertNumber(setNumbersAuthors));
        //List<Book> listBooksByAuthor = bookFacade.findBooksByAuthors(author);
        for (int i = 0; i < books.size(); i++) {
            List<Author>authorsBook = books.get(i).getAuthor();
            if(authorsBook.contains(author))
                    System.out.printf("%d. %s %d%n"
                            ,books.get(i).getId()
                            ,books.get(i).getBookName()
                            ,books.get(i).getPublishedYear()
                    );
                }
        System.out.println("----------------------------");
    }

    private void selectionOfBooksByWord() {
        List <Book> books = bookFacade.findAll();
        System.out.println("Введите часть названия книги: ");
        String a = scanner.nextLine();
        int n = 0;
        
        for (int i = 0; i < books.size(); i++) {
            if(books.get(i).getBookName().contains(a)) {
                System.out.printf("Данные символы содержатся в названии книг/книги: %s %n",books.get(i).getBookName());
                n++;
            }
        }
        if(n == 0) {
            System.out.println("Книг, содержащих данные символы - нет!");
        }
    }

    private void updateReader() {
        Set<Integer> setNumbersReaders = printListReaders();
        if(setNumbersReaders.isEmpty()){
            System.out.println("Нет читателей в базе");
            return;
        }
        System.out.println("Выберите номер читателя: ");
        int numReader = insertNumber(setNumbersReaders);
        Set<Integer> setNum = new HashSet<>();
        setNum.add(1);
        setNum.add(2);
        Reader reader = readerFacade.find((long)numReader);
        System.out.println("имя читателя: "+reader.getFirstname());
        System.out.println("Хотите изменить нажмите 1, оставить без изменения 2");
        int change = insertNumber(setNum);
        if(1 == change){
            System.out.println("Введите новое имя читателя: ");
            reader.setFirstname(scanner.nextLine());
        }
        System.out.println("Фамилия читателя: "+reader.getLastname());
        System.out.println("Хотите изменить нажмите 1, оставить без изменения 2");
        change = insertNumber(setNum);
        if(1 == change){
            System.out.println("Введите новую фамилию читателя: ");
            reader.setLastname(scanner.nextLine());
        }
        System.out.println("Номер телефона читателя: "+reader.getPhone());
        System.out.println("Хотите изменить нажмите 1, оставить без изменения 2");
        change = insertNumber(setNum);
        if(1 == change){
            System.out.println("Введите новый номер телефона читателя: ");
            reader.setPhone(scanner.nextLine());
        }
        readerFacade.edit(reader);
    }

    private void updateBook() {
        Set<Integer> setNumbersBooks = printListAllBooks();
        if(setNumbersBooks.isEmpty()){
            System.out.println("Нет книг в базе");
            return;
        }
        System.out.println("Выберите номер книги: ");
        int numBook = insertNumber(setNumbersBooks);
        Set<Integer> setNum = new HashSet<>();
        setNum.add(1);
        setNum.add(2);
        Book book = bookFacade.find((long)numBook);
        System.out.println("Название книги: "+book.getBookName());
        System.out.println("Хотите изменить нажмите 1, оставить без изменения 2");
        int change = insertNumber(setNum);
        if(1 == change){
            System.out.println("Введите новое название книги: ");
            book.setBookName(scanner.nextLine());
        }
        System.out.println("Год издания книги: "+book.getPublishedYear());
        System.out.println("Хотите изменить нажмите 1, оставить без изменения 2");
        change = insertNumber(setNum);
        if(1 == change){
            System.out.println("Введите новый год издания: ");
            book.setPublishedYear(getNumber());
        }
        System.out.println("Количество экземпляров книги: "+book.getQuantity());
        System.out.println("Хотите изменить нажмите 1, оставить без изменения 2");
        change = insertNumber(setNum);
        if(1 == change){
            System.out.println("Введите новое количество книг: ");
            int oldCount = book.getCount();
            int oldQuantity = book.getQuantity();
            int newQuantity;
            do {                
                newQuantity = getNumber();
                if(newQuantity >= 0 && newQuantity >= oldQuantity - oldCount){
                    break;
                }
                System.out.println("Попробуй еще (>"+(oldQuantity - oldCount)+"): ");
            } while (true);
            int newCount = oldCount + (newQuantity - oldQuantity);
            book.setQuantity(newQuantity);
            book.setCount(newCount);
        }
        System.out.println("Авторы книги: ");
        for (int i = 0; i < book.getAuthor().size(); i++) {
            System.out.printf("%d. %s %s. %d%n"
                    ,book.getAuthor().get(i).getId()
                    ,book.getAuthor().get(i).getFirstname()
                    ,book.getAuthor().get(i).getLastname()
                    ,book.getAuthor().get(i).getBirthYear()
            );
            
        }
        System.out.println("Хотите изменить нажмите 1, оставить без изменения 2");
        change = insertNumber(setNum);
        if(1 == change){
            book.getAuthor().clear();
            System.out.println("Введите количество авторов: ");
            int countAuthors = getNumber();
            Set<Integer> setNumbersAuthors = printListAuthors();
            if(!setNumbersAuthors.isEmpty()){
                for (int i = 0; i < countAuthors; i++) {
                    System.out.println("Введите номер автора "+(i+1)+": ");
                    book.getAuthor().add(authorFacade.find((long)insertNumber(setNumbersAuthors)));
                }
            }else{
                System.out.println("Список авторов пуст");
            }
        }
        bookFacade.edit(book);
    }
    
    private void updateAuthor() {
        Set<Integer> setNumbersAuthors = printListAuthors();
        if(setNumbersAuthors.isEmpty()){
            System.out.println("Нет авторов в базе");
            return;
        }
        System.out.println("Выберите номер автора: ");
        int numAuthor = insertNumber(setNumbersAuthors);
        Set<Integer> setNum = new HashSet<>();
        setNum.add(1);
        setNum.add(2);
        Author author = authorFacade.find((long)numAuthor);
        System.out.println("Имя автора: "+author.getFirstname());
        System.out.println("Хотите изменить нажмите 1, оставить без изменения 2");
        int change = insertNumber(setNum);
        if(1 == change){
            System.out.println("Введите новое имя автора: ");
            author.setFirstname(scanner.nextLine());
        }
        System.out.println("Фамилия автора: "+author.getLastname());
        System.out.println("Хотите изменить нажмите 1, оставить без изменения 2");
        change = insertNumber(setNum);
        if(1 == change){
            System.out.println("Введите новую фамилию автору: ");
            author.setLastname(scanner.nextLine());
        }
        System.out.println("Год рождения автора: "+author.getBirthYear());
        System.out.println("Хотите изменить нажмите 1, оставить без изменения 2");
        change = insertNumber(setNum);
        if(1 == change){
            System.out.println("Введите новый год дня рождения автора: ");
            author.setBirthYear(getNumber());
        }
        authorFacade.edit(author);
    }
}
