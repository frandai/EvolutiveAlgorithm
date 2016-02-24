/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmosgeneticos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author fran
 */
public abstract class PoblacionAbstracta<T extends IndividuoAbstracto> {

    private ArrayList<ArrayList<T>> generacion_poblacion;
    protected int tipo; //1 = normal; 2 = baldwiniana; 3 = lamarkiana.
    protected double prob_mutacion; //entre 0 y 1
    protected double prob_cruce; // entre 0 y 1
    protected int franccion_poblacion_seleccion;
    Random r;

    public PoblacionAbstracta(ArrayList<T> generacion0, int tipo, double prob_cruce, double prob_mutacion, int franccion_poblacion_seleccion, Random r) {
        generacion_poblacion = new ArrayList<ArrayList<T>>();
        generacion_poblacion.add(generacion0);
        this.tipo = tipo;
        this.prob_mutacion = prob_mutacion;
        this.prob_cruce = prob_cruce;
        this.franccion_poblacion_seleccion = franccion_poblacion_seleccion;
        this.r = r;
    }

    public IndividuoAbstracto<T> seleccionarMejorUltimaIteracion() {
        return Collections.min(generacion_poblacion.get(generacion_poblacion.size() - 1));
    }

    public void iterar() {
        ArrayList<T> ultima_generacion = (ArrayList<T>) generacion_poblacion.get(generacion_poblacion.size() - 1).clone();
        if (tipo != 1) {
            for (T individuo : ultima_generacion) {
                individuo.aprender();
            }
        }
        ArrayList<T> nueva_generacion = seleccionar(ultima_generacion);
        nueva_generacion = cruzarYmutar(nueva_generacion, ultima_generacion.size());
        generacion_poblacion.add(nueva_generacion);
    }

    protected abstract ArrayList<T> seleccionar(ArrayList<T> ultima_generacion);

    protected abstract ArrayList<T> cruzarYmutar(ArrayList<T> ultima_generacion, int tamanio_poblacion);

}
