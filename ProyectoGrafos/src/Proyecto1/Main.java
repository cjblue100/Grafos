/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proyecto1;

/**
 *
 * @author Gabriela Diaz R
 */
public class Main {
     public static Visual frame;
     public static Grafos grafos;
     public static void main(String[] args) {
        frame = new Visual();
        grafos= new Grafos();
        frame.setResizable(false);
        frame.setVisible(true);
        
    }
}
