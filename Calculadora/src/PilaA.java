/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * 11/febrero/2023
 * @author 
 * Tania Mendoza
 * Carlos Lugo
 * Mauricia Peña
 * Evelyn Resendiz
 * Emilio Gonzalez
 */
public class PilaA <T> implements PilaADT<T>{
    private T[] pila;
    private int tope;
    private final int MAXIMO = 100;
    
    public PilaA(){
        pila = (T[]) new Object[MAXIMO];
        tope = -1;
    }
    
    public PilaA(int maximo){
         pila = (T[]) new Object[maximo];
        tope = -1;
    }
    
    public boolean isEmpty(){
        return tope == -1;
    }
    
    public void push(T dato){
        if(tope == pila.length - 1) // si el tope se excede, expandimos la pila
            expande();
        tope++;
        pila[tope] = dato;
    }
    
    private void expande(){
        T[] masGrande = (T[]) new Object[pila.length*2];
        for(int i=0; i <= tope; i++){
            masGrande[i]= pila[i];
        }
        pila = masGrande; //la nueva pila
    }
    
    public T pop(){
        if(isEmpty())
            throw new ExcepcionColeccionVacia("La pila no tiene elementos"); //ya no ejecuta lo demás
        T eliminado = pila[tope]; //guarda el eliminado
        pila[tope] = null; //hace nulo el eliminado
        tope--; //le quitamos 1 al tope
        return eliminado; //regresa el eliminado
    }
    
    public T peek(){
        if(isEmpty())
            throw new ExcepcionColeccionVacia("La pila no tiene elementos"); //ya no ejecuta lo demás
        return pila[tope]; //regresa el ultimo elemento
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<= tope; i++) //incluimos al tope
            sb.append(pila[i]).append(" ");          
        return sb.toString();
    }
}

}
