/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proyecto1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.PriorityQueue;

/**
 *
 * @author Gabriela Diaz R
 */
public class Grafos {

    ArrayList<Vertice> Nodos;
    int r = 0;

    public Grafos() {
        Nodos = new ArrayList<Vertice>();
    }

    public void AgregarNodo(String nuevoValor) {
        if (Buscar(nuevoValor) == false) {
            Vertice nuevo = new Vertice(nuevoValor);
            Nodos.add(nuevo);

        } else {
            System.out.println("YA EXISTE");
        }

    }

    public int gradoGrafo() {
        int grado = 0;
        for (Vertice vertice : Nodos) {
            if (grado < vertice.conexiones.size()) {
                grado = vertice.conexiones.size();
            }
        }
        return grado;

    }

    public int sumaGrafo() {
        int sumagrado = 0;
        for (Vertice vertice : Nodos) {

            sumagrado += vertice.conexiones.size();

        }
        return sumagrado;

    }

    public int menorVertice() {
        int grado = 0;
        if (Nodos.size() != 0) {
            grado = Nodos.get(0).conexiones.size();
            for (Vertice vertice : Nodos) {
                if (vertice.conexiones.size() <= grado) {
                    grado = vertice.conexiones.size();
                }
            }
        }
        return grado;

    }

    public Boolean caminoValido(ArrayList<String> camino) {

        if (Buscar(camino.get(0)) == true && camino.size() != 1) {
            for (int x = 0; x < camino.size() - 1; x++) {
                Vertice tmp = Buscar2(camino.get(x));
                if (tmp.conexiones.contains(camino.get(x + 1)) == false) {
                    return false;
                }
            }
        }
        return true;
    }

    public Boolean verificacionCiclos(ArrayList<String> visitados, String parent, String valor) {
        Vertice tmp = Buscar2(valor);
        visitados.add(valor);
        for (int x = 0; x < tmp.conexiones.size(); x++) {

            if (visitados.contains(tmp.conexiones.get(x)) != true) {

                if (verificacionCiclos(visitados, valor, tmp.conexiones.get(x))) {
                    return true;
                }
            } else if (!parent.equals(tmp.conexiones.get(x))) {
                return true;
            }
        }

        return false;
    }

    public Boolean verificacion() {
        r = 0;
        ArrayList<String> visitados = new ArrayList<String>();
        String parent = "-1";
        visitados.add(Nodos.get(0).valor);
        for (Vertice v : Nodos) {
            if (verificacionCiclos(visitados, parent, v.valor) == true) {
                return true;
            }
            visitados.clear();
            parent = "-1";
        }
        return false;

    }

    public void AgregarArista(String origen, String destino, int valor) {
        if (Buscar(origen) == true && Buscar(destino) == true) {
            Vertice actual = Buscar2(origen);
            Vertice actual2 = Buscar2(destino);
            ArrayList<Integer> tmp1 = new ArrayList<Integer>();
            ArrayList<Integer> tmp2 = new ArrayList<Integer>();
            ArrayList<String> tmp3 = new ArrayList<String>();
            ArrayList<String> tmp4 = new ArrayList<String>();

            actual.conexiones.add(destino);

            actual2.conexiones.add(origen);
            for (String cp : actual.conexiones) {
                tmp1.add(Integer.parseInt(cp));
            }
            for (String cp : actual2.conexiones) {
                tmp2.add(Integer.parseInt(cp));
            }
            Collections.sort(tmp1);
            Collections.sort(tmp2);
            for (Integer cp : tmp1) {
               tmp3.add(String.valueOf(cp));
            }
            for (Integer cp : tmp2) {
                tmp4.add(String.valueOf(cp));
            }
              actual.conexiones=tmp3;
              actual2.conexiones=tmp4;
            
        } else {
            System.out.println("NO EXISTE");
        }

    }

    public boolean confirmacionActualizado(String valor, ArrayList<String> nodosRecorrido) {
        for (String buscar : nodosRecorrido) {
            if (buscar.equals(valor)) {
                return true;
            }
        }
        return false;
    }

    public boolean Buscar(String valor) {
        for (Vertice busco : Nodos) {
            if (valor.equals(busco.valor)) {
                return true;
            }

        }

        return false;
    }

    public Vertice Buscar2(String valor) {
        for (Vertice busco : Nodos) {
            if (valor.equals(busco.valor)) {
                return busco;
            }

        }

        return null;
    }

}
