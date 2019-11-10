/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion;

import java.awt.Color;
import java.io.*;


/**
 *
 * @author juaco
 */



public class Replicate implements Serializable {
    
    private int dimension ;
    private boolean[][] matriz ;
    private int replicas;
    private Color color; 
    public Replicate(){
        dimension = 10;
        replicas = 0;
        color=Color.BLACK;
        matriz= new boolean [dimension][dimension];
        llenarMatriz();
        
    }
    
    public void llenarMatriz(){
        matriz= new boolean [dimension][dimension];
        for (int i = 0; i<dimension;i++){
            for (int j = 0; j<dimension;j++){
                matriz[i][j]= false;
            }
        }
        replicas=0;
    }

    public void seleccionarCasilla(int i, int j,boolean estado){
        matriz[i][j] = estado;
    }   
        
    public void replicar(){
        int cantiCel;
        boolean [][]aux =new boolean [dimension][dimension];
        for (int i=0 ; i < dimension ; i++){
                for(int j=0 ; j < dimension ; j++){
                    aux[i][j]=matriz[i][j];
                }
        }
        
        for (int i=0 ; i < dimension ; i++){
            cantiCel = 0;
                for(int j=0 ; j < dimension ; j++){
                    
                    cantiCel=contarCelAlrededores(aux,i, j);
                    if (cantiCel % 2 == 0){ //Si es par 
                        matriz[i][j] = false; //Cambia su estado a "."
                    }else{ // Si es impar
                        matriz[i][j] = true; //Cambia su estado a "#"
                    }
                }
            }
       replicas+=1;
    }
    
    public boolean[][] getMatriz(){
        return matriz;
    }

    public void resize (int dim){
        dimension=dim;
        llenarMatriz();
        
    }
    
    public void salvar(File doc)throws IOException{
	ObjectOutputStream guardado = new ObjectOutputStream(
	new FileOutputStream(doc+".dat"));
	guardado.writeObject(this);
	guardado.close();
    }
    
    public void abrir(File doc) throws IOException, ClassNotFoundException{
	ObjectInputStream abriendo = new ObjectInputStream(
	new FileInputStream(doc));
	Replicate nuevo = (Replicate) abriendo.readObject();
	this.matriz = nuevo.getMatriz();
        this.dimension = nuevo.getDimension();
        this.replicas=nuevo.getReplicas();
        this.color=nuevo.getColor();
	abriendo.close();
    }
    
    public void setColor(Color c){
        color=c;
    }
	
    
   
    public boolean getValue(int i, int j){
        return matriz[i][j];
    }
    
    public Color getColor(){
        return color;
    }
    public int getReplicas(){
        return replicas;
    }
    
    public int getDimension(){
        return dimension;
    }
    
    private int contarCelAlrededores (boolean[][] mat,int i, int j){
        int[][] delta = { {-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1},{0,0}};// Posiciónes a evaluar
        int rtmp = 0;
        int ctmp = 0;
        int cont=0;
        for (int x=0; x<delta.length ; x++){ //Recorre cada posición
            rtmp = i + delta[x][0]; //Sacó la posición i a evaluar
            ctmp = j + delta[x][1]; //Sacó la posición j a evaluar
            if ((0<=rtmp && rtmp<dimension)&&(0 <= ctmp && ctmp< dimension)&& (mat[rtmp][ctmp]==true)){ // No salirse del rango de la matriz , significa que esa posición existe.
                cont+=1; //Va sumando las encendidas.
            }
        }        
        return cont;  
    
    }
}
    