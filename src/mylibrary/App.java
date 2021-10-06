/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylibrary;

import java.util.Calendar;
import java.util.GregorianCalendar;
import myclasses.Book;
import myclasses.Reader;
import myclasses.Author;
import myclasses.History;

/**
 *
 * @author pupil
 */
public class App {
    
    public void run(){
        Reader reader1 = new Reader();
        reader1.setFirstname("Petr");
        reader1.setLastname("Petrov");
        reader1.setPhone("545654564");
        
        Reader reader2 = new Reader();
        reader2.setFirstname("Olga");
        reader2.setLastname("Tamme");
        reader2.setPhone("564346543");
        
        Book book1 = new Book();
        book1.setBookName("Voina i mir");
        book1.setPublishedYear(2021);
        Author author1 = new Author();
        author1.setFirstname("Lev");
        author1.setLastname("Tolstoy");
        author1.setBirthYear(1828);
        Author[] authors = new Author[1];
        authors[0] = author1;
        book1.setAuthor(authors);
        
        Book book2 = new Book();
        book2.setBookName("Otsi i deti");
        book2.setPublishedYear(2020);
        Author author2 = new Author();
        author2.setFirstname("Ivan");
        author2.setLastname("Turgenev");
        author2.setBirthYear(1818);
        Author[] authors2 = new Author[1];
        authors2[0] = author2;
        book2.setAuthor(authors2);
        
        History history1 = new History();
        history1.setBook(book1);
        history1.setReader(reader1);
        Calendar c = new GregorianCalendar();
        history1.setGivenDate(c.getTime());
        System.out.println("history1 - " + history1.toString());
       
        History history2 = new History();
        history2.setBook(book2);
        history2.setReader(reader2);
        c = new GregorianCalendar();
        history1.setGivenDate(c.getTime());  
        System.out.println("history2 - " + history2.toString());
        
        c = new GregorianCalendar();
        history1.setReturnedDate(c.getTime());
        System.out.println("---------------------------");
        System.out.println("history1 - " + history1.toString());
        
        c = new GregorianCalendar();
        history2.setReturnedDate(c.getTime());
        System.out.println("---------------------------");
        System.out.println("history2 - " + history2.toString());
    }
}
        

    
