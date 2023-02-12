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
import java.util.ArrayList;
public class CalculadoraFuncionalidad {
    //metodo para convertir string(input) en arraylist con propiedades de los numeros
    public static ArrayList<String> convertirCadena(String cadena){
        ArrayList<String> array = new ArrayList<String>();
        int i = 0;
        String stringAux = "";
        while(i<cadena.length()){
            if(Character.isDigit(cadena.charAt(i)) || cadena.charAt(i)=='-'){ //si encontramos un digito o un -
                if(cadena.charAt(i)=='-'){ //añadimos el menos a un stringAux (puede ser un número negativo)
                    stringAux+=cadena.charAt(i);
                    i++;
                }  
                //si el número tiene más dígitos, va a entrar un loop
                while(i<cadena.length() && (Character.isDigit(cadena.charAt(i)) || cadena.charAt(i)=='.')){
                    stringAux+=cadena.charAt(i); //va a seguir guardando todo en un string
                    i++;
                }
                array.add(stringAux); //guarda el string del numero ya completo
                stringAux=""; //vaciamos el auxiliar
            }
            //si encuentra un operador o un parentesis
            if(i<cadena.length() && (cadena.charAt(i)=='+' || cadena.charAt(i)=='-' || cadena.charAt(i)=='*' || cadena.charAt(i)=='/' || cadena.charAt(i)=='^' || cadena.charAt(i)==')' || cadena.charAt(i)=='(')){
                array.add(String.valueOf(cadena.charAt(i))); //lo añade como un
            }
            i++;
        }    
        return array;   
    }
    //metodo para checar si el input es válido
    public static boolean verificarInput(String expresion){
      boolean resp = true; //empieza como verdadera y cuando hay una excepción se hace falsa
      ArrayList<String> array = new ArrayList<String>();
      array = convertirCadena(expresion); //convertimos la cadena a string para todos los métodos
      //debe tener elementos
      if (expresion.isEmpty()){
            return false; //no va a poder checar nada más
      }
      //debe haber balance de parentesis
      if(!balanceParentesis(array)) 
          resp = false; 
      //no puede empezar con operador al menos de que sea (-)
      if(checarOperador(array.get(0)) && !array.get(0).equals("-")) 
          resp = false;
      //se recorrer todo el arreglo para verificar que no hayan dos numeros ni dos operadores seguidos
      int i=0, j=1, m=0; 
      while(resp && j>array.size()){
          if(checarOperador(array.get(i)) && checarOperador(array.get(j)))
              resp = false; //encontró dos operadores juntos
          if(checarNumero(array.get(i)) && checarNumero(array.get(j)))
              resp = false; //encontró dos números juntos
          i++;
          j++;
      }  
      //finalmente, para evitar input con dos puntos decimales
      while(m<array.size() && resp ){
          //checamos que todos los elementos sean operadores o números o parentesis
          if(checarNumero(array.get(m)) || checarOperador(array.get(m)) || array.get(m).equals("(") || array.get(m).equals(")"))
              resp=true;
          else
              resp=false;
        m++;   
      }
      //checamos que todos en el array sean o un numero o un operador
      return resp;
    }
    //metodo para verificar balance de parentesis
    public static boolean balanceParentesis(ArrayList array){
        int i = 0;
        boolean resp = true;
        PilaA<String> pila = new PilaA();
        while(i<array.size() && resp){
           if(array.get(i).equals("(")) //si encontramos el parentesis izquierdo
               pila.push("("); //lo agrgamos a la pila
           else
               if(array.get(i).equals(")")) //si encontramos el parentesis derecho
                   if(pila.isEmpty()) // la pila está vacía (no hay parenteis izquiero)
                       resp = false; //la respuesta es falsa y sale del loop
                   else
                       pila.pop(); //quitamos el parentesis izquierdo, porque si está balanceado 
           i++;
       }
       if(!pila.isEmpty()) //si la pila no está vacía, no hay balance (falto el derecho)
           resp = false; //regresa el false
       return resp;
    }
    //metodo para checar si es un operador
    public static boolean checarOperador(String dato){
        boolean resp = false;
        if(dato.equals("+") || dato.equals("-") || dato.equals("*") || dato.equals("/") || dato.equals("^"))
            resp = true; //si es un operador regresa true
        return resp;
    } 
    //metodo para checar si es un número
    public static boolean checarNumero(String dato){
        boolean resp = true;
        try {
            Double.parseDouble(dato); //intenta convertirlo a double 
        }catch(NumberFormatException e){ //no puede convertir de string a double
            resp=false; 
        }
        return resp;
    }
    //metodo para checar jerarquía de operadores
   public static int jerarquia(String dato){
       int resp = 0; //resultado default
       if(dato.equals("+") || dato.equals("-")) //menor jerarquía
           resp = 1; 
       else if(dato.equals("*") || dato.equals("/"))
           resp = 2; 
       else if(dato.equals("^")) //mayor jerarquía
           resp = 3; 
       return resp;
   }
    //metodo para convertir infijo a postfijo
    public static ArrayList<String> convertirPostfijo(ArrayList<String> arrayInfijo){
        PilaA<String> pila = new PilaA<>(); //pila para trabajar con operadores y numeros
        ArrayList<String> arrayPostfijo = new ArrayList<>(); //arreglo para guardar resultado en forma postfija
        String stringAux; //string auxiliar para revisar elementos
        for (int i = 0; i < arrayInfijo.size(); i++) { //recorremos todo el arreglo en infijo
          stringAux = arrayInfijo.get(i); //elemento que vamos a revisar
          if (checarNumero(stringAux))  //si hay un número lo agregamos al arreglo postfijo
            arrayPostfijo.add(stringAux); 
           else if (stringAux.equals("("))  //si se abre un parentesis, lo agregamos a la pila
            pila.push(stringAux);
           else if (stringAux.equals(")")) { //si se cierra un parentesis
            while (!pila.isEmpty() && !pila.peek().equals("(")) { //buscamos hasta que encontremos el parentesis abierto
              arrayPostfijo.add(pila.pop()); //todo lo que está en el parentesis lo agregamos al arreglo postfijo y lo quitamos de la pila 
            }
            pila.pop(); //quitamos el parentesis abierto de la pila
          } else {  
            //si el string es un operador, los quitamos del infijo y lo ponemos en el postfijo 
            while (!pila.isEmpty() && jerarquia(stringAux) <= jerarquia(pila.peek())) 
              arrayPostfijo.add(pila.pop());
            //AL MENOS que los operadores de la pila tengan una jerarquia menor
            pila.push(stringAux); //y en ese caso, empujamos el string a la pila
          }
        }
        while (!pila.isEmpty()) 
          arrayPostfijo.add(pila.pop());
        return arrayPostfijo;
      }
    //metodo para evaluar operaciones
    public static double operacion(Double dato1, Double dato2, char operador){
        double resp=0;
        switch (operador){ //realiza la operación que corresponde al operador
            case '+':
                resp = dato1 + dato2;
                break;
            case '-':
                resp = dato1 - dato2;
                break;
            case '*':
                resp = dato1 * dato2;
                break;
            case '/':
                resp = dato1 / dato2;
                break;
            case '^':
                resp = Math.pow(dato1, dato2);
                break;
        }
        return resp;
    }
    //metodo para evaluar expreciones postfijo
    public static double evaluaPostfijo(ArrayList<String> arrayPostfijo){
        double dato1, dato2, resp=0;
        char operador;
        String stringAux;
        PilaA<Double> pila = new PilaA<>();
        for(int i = 0; i<arrayPostfijo.size(); i++){
            stringAux = arrayPostfijo.get(i);
            if(checarNumero(stringAux))
                pila.push(Double.parseDouble(stringAux));
            else {
                dato2 = pila.pop(); //el dato2 es el push más reciente
                dato1 = pila.pop(); //el dato1 es el push menos reciente 
                operador = stringAux.charAt(0); 
                resp = operacion(dato1, dato2, operador);
                pila.push(resp); 
            }   
        }
        return resp;
    }
    //metodo para imprimir y checar procesos
    public static String imprime(ArrayList array){
        StringBuilder sb = new StringBuilder();
        sb.append("Arreglo: [ ");
        for(int i=0; i<array.size(); i++)
            sb.append(array.get(i)+", ");
        sb.append("]");
        return sb.toString();  
    }
    //main para checar proceso
    public static void main(String[] args) {
        ArrayList<String> array1 = new ArrayList<String>();
        array1 = convertirCadena("(-56/8)+2.1");
        ArrayList<String> array2 = new ArrayList<String>();
        array2 = convertirCadena("(-56/8)+2.1)");
        ArrayList<String> array3 = new ArrayList<String>();
        array3 = convertirCadena("(-56/8)+2.1.1)");
        
        System.out.println("balanceParentesis");
        System.out.println(balanceParentesis(array1)); //true: -56/8)+2.1
        System.out.println(balanceParentesis(array2)+"\n"); //false: (-56/8)+2.1)
        
        System.out.println("checarOperador");
        System.out.println(checarOperador(array1.get(2))); //true (/)
        System.out.println(checarOperador(array1.get(1))+"\n"); //false (-56)
        
        System.out.println("checarNumero");
        System.out.println(checarNumero(array1.get(6))); //true (2.1)
        System.out.println(checarNumero(array3.get(6))+"\n"); //false (2.1.1)
        
        System.out.println("verificarInput");
        System.out.println(verificarInput("(-56/8)+2.1")); //true 
        System.out.println(verificarInput("")); //false, no puede estar vacia, debe tenr al menos 3 elementos
        System.out.println(verificarInput("(-56/8)+2.1)")); //false (parentesis no balanceados)
        System.out.println(verificarInput("(-56/8)+2.1.1")); //false (número inváido 2.1.1)
        System.out.println(verificarInput("*2(-56/8)+2.1.1)")); //false (comienza con operador)
        System.out.println(verificarInput("(-56/*8)+2.1.1)")+"\n"); //false (dos operadores juntos)
        
        System.out.println("jerarquiaOperadores");
        System.out.println(jerarquia("0")); //0
        System.out.println(jerarquia("+")); //1
        System.out.println(jerarquia("-")); //1
        System.out.println(jerarquia("*")); //2
        System.out.println(jerarquia("/")); //2
        System.out.println(jerarquia("^")+"\n"); //3
        
        ArrayList<String> array4 = new ArrayList<String>();
        array4 = convertirCadena("4*2-(6+3)+9");
        System.out.println("convertirPostfijo");
        System.out.println(imprime(array4));
        System.out.println(imprime(convertirPostfijo(array4))+"\n");
        
        System.out.println("operacion");
        System.out.println(operacion(2.0,3.0,'+')); //5
        System.out.println(operacion(2.0,3.0,'-')); //-1
        System.out.println(operacion(2.0,3.0,'/')); //0.66..
        System.out.println(operacion(2.0,3.0,'*')); //6
        System.out.println(operacion(2.0,3.0,'^')+"\n"); //8
        
        System.out.println("evaluaPostfijo");
        System.out.println(evaluaPostfijo(convertirPostfijo(array4)));
        System.out.println(evaluaPostfijo(convertirPostfijo(array1)));
    }
}
