/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import entity.Author;
import entity.Book;
import entity.History;
import entity.Reader;
import interfaces.Keeping;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author pupil
 */
public class SaverToBase implements Keeping{
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("MyLibraryPU");
    private EntityManager em = emf.createEntityManager();
    private EntityTransaction tx = em.getTransaction();

    @Override
    public void saveBooks(List<Book> books) {
        tx.begin();
            for (int i = 0; i < books.size(); i++) {
                if(books.get(i).getId() == null){
                    for (int j = 0; j < books.get(i).getAuthor().size(); j++) {
                        em.persist(books.get(i).getAuthor().get(j));
                    }
                    em.persist(books.get(i));
                }
            }
        tx.commit();
    }

    @Override
    public List<Book> loadBooks() {
        List <Book> books = null;
        try {
            books = em.createQuery("SELECT book FROM Book book")
                .getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
        return books;
    }
    
     @Override
    public void saveAuthors(List<Author> authors) {
        tx.begin();
            for (int i = 0; i < authors.size(); i++) {
                if(authors.get(i).getId() == null){
                    em.persist(authors.get(i));
                }
            }
        tx.commit();
    }

    @Override
    public List<Author> loadAuthors() {
        List <Author> authors = null;
        try {
            authors = em.createQuery("SELECT author FROM Author author")
                .getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
        return authors;
        }

    @Override
    public void saveReaders(List<Reader> readers) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Reader> loadReaders() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveHistories(List<History> histories) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<History> loadHistories() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
