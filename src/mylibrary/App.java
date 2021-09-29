/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylibrary;

import java.util.Objects;

/**
 *
 * @author pupil
 */
public class App {
    private String hello = "Привет из экземпляра класса App!";
    public void run(){
        System.out.println(hello);
    }


    
    @Override
    public String toString() {
        return "App{" + "hello = " + "Привет из экземпляра класса App!" + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final App other = (App) obj;
        if (!Objects.equals(this.hello, other.hello)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.hello);
        return hash;
    }

    public String getHello() {
        return hello;
    }

    public void setHello(String hello) {
        this.hello = hello;
    }

    
    
    
}
