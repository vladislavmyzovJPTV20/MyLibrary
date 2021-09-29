/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylibrary;

/**
 *
 * @author pupil
 */
public class MyLibrary {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        App app = new App();
        String str = "строка";
        System.out.println(app.getClass().toString());
        System.out.println(app.equals(str));
        app.run();
        System.out.println(app.toString());
        app.setHello("Это поле было изменено с помощью метода setHello()");
        app.run();
        System.out.println(app.toString());
    }
    
}
