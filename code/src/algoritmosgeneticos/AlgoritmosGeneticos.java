/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmosgeneticos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fran
 */
public class AlgoritmosGeneticos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //VARIABLES
        int semilla = 1;
        String archivo = "/home/fran/tai256c.dat";
        int tamanio_poblacion = 30;
        int num_iteraciones = 20;
        double prob_mutacion = 0.1;
        double prob_cruce = 0.6;
        int factor_poblacion_seleccion = 3;
        int tipo = 3;
        //FIN VARIABLES

        //Medir tiempo
        long startTime = System.currentTimeMillis();

        Random r = new Random(semilla);
        Representacion rep = leeRepresentacion(archivo);
        ArrayList<Individuo> generacion0 = new ArrayList<>(tamanio_poblacion);
        for (int i = 0;
                i < tamanio_poblacion;
                i++) {
            generacion0.add(new Individuo(rep, true, r));
        }
        Poblacion p = new Poblacion(generacion0, tipo, prob_cruce, prob_mutacion, factor_poblacion_seleccion, r);
        for (int i = 0;
                i < num_iteraciones;
                i++) {
            IndividuoAbstracto<Individuo> ind = p.seleccionarMejorUltimaIteracion();
            System.out.println("Iteración " + i + ", Mejor Individuo: " + ind.toString());
            if (i < num_iteraciones - 1) {
                p.iterar();
            }
        }
        //Mostrar tiempo tardado
        long endTime = System.currentTimeMillis();
        System.out.println("Tiempo de Ejecución: " + (endTime - startTime) + " ms");

    }

    private static Representacion leeRepresentacion(String file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String s = sb.toString();

            int numFil = Integer.parseInt(s.split("\n")[0].trim());

            ArrayList<String> texto = new ArrayList<String>();
            ArrayList<String> text = new ArrayList<String>();
            Collections.addAll(texto, s.split("\n"));
            for (String text1 : texto) {
                if (!text1.trim().isEmpty()) {
                    text.add(text1);
                }
            }
            ArrayList<String> longit = new ArrayList<>(text.subList(1, numFil + 1));
            ArrayList<String> pes = new ArrayList<>(text.subList(numFil + 1, numFil + 1 + numFil));

            ArrayList<ArrayList<Double>> longitudes = new ArrayList<>(numFil);
            ArrayList<ArrayList<Double>> pesos = new ArrayList<>(numFil);
            for (int i = 0; i < numFil; i++) {
                ArrayList<Double> long_i = new ArrayList<>();
                ArrayList<Double> pes_i = new ArrayList<>();
                for (String i1 : longit.get(i).split(" ")) {
                    if (!i1.trim().isEmpty()) {
                        long_i.add(Double.parseDouble(i1.trim()));
                    }
                }
                for (String i1 : pes.get(i).split(" ")) {
                    if (!i1.trim().isEmpty()) {
                        pes_i.add(Double.parseDouble(i1.trim()));
                    }
                }
                longitudes.add(long_i);
                pesos.add(pes_i);
            }
            Representacion r = new Representacion();
            r.distancias = longitudes;
            r.pesos_flujos = pesos;

            return r;

        } catch (Exception ex) {
            Logger.getLogger(AlgoritmosGeneticos.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
