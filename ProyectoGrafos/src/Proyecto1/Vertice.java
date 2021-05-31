/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proyecto1;

import java.util.ArrayList;

/**
 *
 * @author Gabriela Diaz R
 */
public class Vertice {
    String valor;
    ArrayList <String> conexiones;
    Object nodoVisual;
    public Vertice(String valor2){
        valor= valor2;
        conexiones =new ArrayList<String>();
    }
    
}

