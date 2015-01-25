/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package matrices;

import java.awt.Dimension;
import java.util.Random;

/**
 *
 * @author galvez
 */
public class Matriz {
    private int[][]datos;
    private Random rnd = new Random();
    
    public Matriz(int filas, int columnas, boolean inicializarAleatorio){
        datos = new int[columnas][];
        for(int i=0; i<columnas; i++){
            datos[i] = new int[filas];
            if (inicializarAleatorio)
                for(int j=0; j<filas; j++)
                    datos[i][j] = rnd.nextInt(100);
        }
    }
    public Matriz(Dimension d, boolean inicializarAleatorio){
        this(d.height, d.width, inicializarAleatorio);
    }
    
    public Dimension getDimension(){
        return new Dimension(datos.length, datos[0].length);
    }
    
    public static Matriz sumarDosMatrices(Matriz a, Matriz b) throws DimensionesIncompatibles { 
        if(! a.getDimension().equals(b.getDimension())) throw new DimensionesIncompatibles("La suma de matrices requiere matrices de las mismas dimensiones");        
        int i, j, filasA, columnasA; 
        filasA = a.getDimension().height; 
        columnasA = a.getDimension().width; 
        Matriz matrizResultante = new Matriz(filasA, columnasA, false);
        for (j = 0; j < filasA; j++) { 
            for (i = 0; i < columnasA; i++) { 
                matrizResultante.datos[i][j] += a.datos[i][j] + b.datos[i][j]; 
            } 
        } 
        return matrizResultante; 
    } 
	
    // Multiplicar dos matrices
    public static Matriz producto(Matriz a, Matriz b) throws DimensionesIncompatibles
    {
        if (a.getDimension().width != b.getDimension().height)
            throw new DimensionesIncompatibles("La multiplicación de matrices requiere que columnas de A sea igual a filas de B.");
        
        // Tamaño de resultado será Filas de A y Columnas de B
        Matriz resultado = new Matriz(a.getDimension().height, b.getDimension().width, false);
        for (int i = 0; i < resultado.getDimension().height; i++)
        {
            for (int j = 0; j < resultado.getDimension().width; j++)
            {
                for (int k = 0; k < a.getDimension().width; k++)
                {
                    resultado.datos[i][j] += a.datos[i][k] * b.datos[k][j];
                }
            }
        }
        return resultado;
    }
    
    // Calcular la matriz inversa
    public static Matriz inversa(Matriz d) throws DimensionesIncompatibles, CloneNotSupportedException
    {
        if (d.getDimension().height != d.getDimension().width)
            throw new DimensionesIncompatibles("La matriz debe ser cuadrada");
        
        Matriz res = (Matriz)d.clone();
        
        int filas = d.getDimension().height;
        Matriz b = new Matriz(filas, filas, false);

        for (int i = 0; i < filas; i++)
        {
            b.datos[i][i] = 1;
        }

        for (int k = 0; k < filas-1; k++)
        {
            for (int i = k+1; i < filas; i++)
            {
                for (int s = 0; s < filas; s++)
                {
                    b.datos[i][s] -= res.datos[i][k] * b.datos[k][s] / res.datos[k][k];
                }
                for(int j = k+1; j < filas; j++)
                {
                    res.datos[i][j] -= res.datos[i][k] * res.datos[k][j] / res.datos[k][k];
                }
            }
        }
        return res;
    }
    
    @Override
    public String toString(){
        String ret = "";
        ret += "[\n";
        for (int i = 0; i < getDimension().width; i++) {
            ret += "(";
            for (int j = 0; j < getDimension().height; j++) {  
                ret += String.format("%3d", datos[i][j]); 
                if (j != getDimension().height - 1) ret += ", ";
            } 
            ret += ")";
            if (i != getDimension().width - 1) ret += ",";
            ret += "\n";
        } 
        ret += "]\n";
        return ret;
    }
}
