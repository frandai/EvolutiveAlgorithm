/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmosgeneticos;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author fran
 */
public class Individuo extends IndividuoAbstracto<Representacion> {

    public Individuo(Representacion rep, boolean aleatorio, Random r) {
        super(rep, aleatorio, r);
        if(!aleatorio){
            reemplazarSolucion(rep.solucion);
        }
    }

    @Override
    protected double calculaFitness() {
        return calculaFitness(representacion.solucion);
    }

    private double calculaFitness(ArrayList<Integer> sol) {
        double fitness = 0;
        for (int i = 0; i < representacion.distancias.size(); i++) {
            for (int j = 0; j < representacion.distancias.get(i).size(); j++) {
                fitness += representacion.pesos_flujos.get(i).get(j) * representacion.distancias.get(sol.get(i)).get(sol.get(j));
            }

        }
        return fitness;
    }

    @Override
    public IndividuoAbstracto<Representacion> cruzar(IndividuoAbstracto<Representacion> individuo, boolean solucion_primitiva) {
        ArrayList<Integer> miSolucion = solucion_primitiva && representacion.solucion_primitiva != null ? representacion.solucion_primitiva : representacion.solucion;
        ArrayList<Integer> otraSolucion = solucion_primitiva && individuo.representacion.solucion_primitiva != null ? individuo.representacion.solucion_primitiva : individuo.representacion.solucion;

        int corta_cruce = r.nextInt(miSolucion.size());
        Individuo n = new Individuo(this.representacion, false, r);
        ArrayList<Integer> solucion_cruce = new ArrayList<>(miSolucion.subList(0, corta_cruce));
        for (Integer s : otraSolucion) {
            if (!solucion_cruce.contains(s)) {
                solucion_cruce.add(s);
            }
        }
        n.reemplazarSolucion((ArrayList<Integer>) solucion_cruce);
        return n;
    }

    @Override
    public void mutar() {
        ArrayList<Integer> solucion = (ArrayList<Integer>) representacion.solucion.clone();
        int pos1 = r.nextInt(solucion.size()), pos2 = pos1;
        while (pos2 == pos1) {
            pos2 = r.nextInt(solucion.size());
        }
        int x = solucion.get(pos1);
        solucion.set(pos1, solucion.get(pos2));
        solucion.set(pos2, x);
        reemplazarSolucion(solucion);
    }

    @Override
    protected void funcionAprendizaje() {
        double fitness_sol = getFitness();
        this.representacion.solucion_primitiva = (ArrayList<Integer>) this.representacion.solucion.clone();
        double mejor;
        int limite = 5;
        
        do {

            mejor = fitness_sol;

            for (int i = 0; i < representacion.solucion.size(); i++) {
                for (int j = i + 1; j < representacion.solucion.size(); j++) {
                    ArrayList<Integer> T = (ArrayList<Integer>) representacion.solucion.clone();
                    int x = T.get(i);
                    T.set(i, T.get(j));
                    T.set(j, x);
                    double sT = calculaFitness(T);
                    if (sT < fitness_sol) {
                        reemplazarSolucion(T);
                        fitness_sol = sT;
                        limite--;
                    }
                    if (limite < 0) {
                        break;
                    }
                }
                if (limite < 0) {
                    break;
                }

            }

        } while (fitness_sol != mejor && limite > 0);
    }

    private void reemplazarSolucion(ArrayList<Integer> nuevaSol) {
        ArrayList<ArrayList<Double>> distancias = representacion.distancias;
        ArrayList<ArrayList<Double>> pesos = representacion.pesos_flujos;
        ArrayList<Integer> sol_prim = representacion.solucion_primitiva;
        representacion = new Representacion();
        representacion.distancias = distancias;
        representacion.pesos_flujos = pesos;
        representacion.solucion = nuevaSol;
        representacion.solucion_primitiva = sol_prim;
    }

    @Override
    protected void generarAleatorio() {
        reemplazarSolucion(new ArrayList<Integer>());
        for (int i = 0; i < representacion.distancias.size(); i++) {
            representacion.solucion.add(i);

        }
        Collections.shuffle(representacion.solucion);
    }

    @Override
    public String toString() {
        String sol = " ";
        for (Integer s : representacion.solucion) {
            sol += (s + 1) + " ";
        }
        return "Fitness: " + new DecimalFormat("#").format(this.getFitness()) + "; (" + sol + ")";
    }

}
