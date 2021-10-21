/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entity.Book;
import entity.History;
import entity.Reader;

/**
 *
 * @author pupil
 */
public interface Keeping {
    public void saveBooks(Book[] books);
    public Book[] loadBooks();
    public void saveReaders(Reader[] readers);
    public Reader[] loadReaders();
    public void saveHistories(History[] histories);
    public History[] loadHistories();
}
