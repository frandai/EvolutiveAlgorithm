/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmosgeneticos;

import java.util.Random;

/**
 *
 * @author fran
 */
public abstract class IndividuoAbstracto<T> implements Comparable<IndividuoAbstracto>{

    private Double fitness = null;
    private double fitness_original;
    protected T representacion;
    public Random r;
    
    public IndividuoAbstracto(T rep, boolean aleatorio, Random r){
        this.r = r;
        this.representacion = rep;
        if(aleatorio){
             generarAleatorio();
        }
    }
    
    public double getFitness(){
        if(fitness==null){
            fitness = calculaFitness();
            fitness_original = fitness;
        } else {
            fitness = calculaFitness();
        }
        return fitness;
    }
    
    public double getFitnessOriginal(){
        return fitness_original;
    }
    
    protected abstract double calculaFitness();

    public void aprender(){
        funcionAprendizaje();
        fitness = calculaFitness();
    }
    
    public abstract IndividuoAbstracto<T> cruzar(IndividuoAbstracto<T> individuo, boolean solucion_primitiva);
    
    public abstract void mutar();
    
    protected abstract void funcionAprendizaje();
    
    protected abstract void generarAleatorio();
    
    @Override
    public abstract String toString();
    
    @Override
    public int compareTo(IndividuoAbstracto o){
        return ((Double) this.getFitness()).compareTo(o.getFitness());
    }
}
