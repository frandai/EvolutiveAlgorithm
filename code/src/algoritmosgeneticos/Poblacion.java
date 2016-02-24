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
public class Poblacion extends PoblacionAbstracta<Individuo> {

    public Poblacion(ArrayList<Individuo> generacion0, int tipo, double prob_cruce, double prob_mutacion, int franccion_poblacion_seleccion, Random r) {
        super(generacion0, tipo, prob_cruce, prob_mutacion, franccion_poblacion_seleccion, r);
    }

    @Override
    protected ArrayList<Individuo> seleccionar(ArrayList<Individuo> ultima_generacion) {
        Collections.sort(ultima_generacion);
        return new ArrayList<>(ultima_generacion.subList(0, ultima_generacion.size() / franccion_poblacion_seleccion));
    }

    @Override
    protected ArrayList<Individuo> cruzarYmutar(ArrayList<Individuo> padres, int tamanio_poblacion) {
        ArrayList<Individuo> hijos = (ArrayList<Individuo>) padres.clone();
        while (hijos.size() < tamanio_poblacion) {
            Individuo padre1 = padres.get(r.nextInt(padres.size()));
            Individuo padre2 = padres.get(r.nextInt(padres.size()));
            Individuo hijo;
            if (r.nextDouble() < prob_cruce) {
                hijo = (Individuo) padre1.cruzar(padre2, tipo == 2);
            } else {
                hijo = new Individuo(padre2.representacion, false, r);
            }
            if (r.nextDouble() < prob_mutacion) {
                hijo.mutar();
            }
            hijos.add(hijo);
        }
        return hijos;
    }

}
