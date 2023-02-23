/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * 
 * 
 * 
 * Clase CalculadoraFuncionalidad,
 * 
 * incluye todos los métodos para: <ul>
 * 
 * <li>Verificar que lo que ingrese el usuario sea valido.</li>
 * <li>Convertirlo a notacion postfija.</li>
 * <li>Realizar las operaciones para devolver el resultado al usuario.</li>
 * </ul>
 * 
 *
 * @version 3
 * 
 * @author 
 * Tania Mendoza
 * Carlos Lugo
 * Mauricia Peña
 * Evelyn Resendiz
 * Emilio Gonzalez 
 * 
 */
import java.util.ArrayList;
public class CalculadoraFuncionalidad {
    
    /**
     * Método calcula:
     * método para evaluar la expresión ingresada por el usuario.
     * 
     * @param expresion String recibido del usuario con la expresión a evaluar.
     * @return <ul>
     * <li> Resultado de la expresión evaluada. </li>
     * </ul>
     */
    public static double calcula (String expresion){
        
        double res = 0;
        
        if (expresion.isEmpty ()){
            throw new RuntimeException ("expresion vacia");
        }
        ArrayList <String> al = convertirCadena (expresion);
        
        if (verificarInput (al)){
            al = convertirPostfijo (al);
            res = evaluaPostfijo (al);
        }
        return (res);
    }
    
    /**
     * Método verificarInput:
     * 
     * método para verificar si el input es válido para realizar la operación.
     * 
     * @param array String con el input a verificar.
     * @return <ul>
     * <li> true: si la expresion es válida.</li>
     * <li> false: si la expresion no es válida.</li>
     * </ul>
     */  
    public static boolean verificarInput(ArrayList <String> array){
      
        //debe haber balance de parentesis
        if (!balanceParentesis (array)){
            throw new RuntimeException ("Parentesis incorrectos");
        }
        
        //se recorrer todo el arrayList para verificar que no hayan dos numeros ni dos operadores seguidos
        //for para que recorra todo al arrayList
        for (int x = 1; x < array.size (); x ++){
            if(checarOperador (array.get (x - 1)) && checarOperador (array.get (x))){
                throw new RuntimeException ("Error"); //no va a poder checar nada más
            }else {
                if(checarNumero(array.get(x - 1)) && checarNumero(array.get(x))){
                    throw new RuntimeException ("Error"); //no va a poder checar nada más
                }
            }
        } 
        return true;
    }
    
    
    
    /**
     * Método convertirCadena: 
     * método para convertir String (input) en ArrayList con propiedades de los números.
     * @param cadena String recibido del usuario.
     * @return ArrayList arreglo de String con los elementos del input, considerando sus propiedades.
     */

    public static ArrayList<String> convertirCadena (String cadena){
        //variables
        ArrayList <String> array = new ArrayList <>();
        int i = 0;
        String stringAux = "";
        boolean punto = false; //variable que va a contener si ya se encontro un decimal en un numero para evitar que tenga dos
        
        //if para cuidar el primer caracter
        if (checarNumero (String.valueOf (cadena.charAt (0))) || cadena.charAt (0) == '+' || cadena.charAt (0) == '-' || cadena.charAt (0) == '(' || cadena.charAt (0) == ')' || cadena.charAt (0) == '.'){
            //if para ver si es un mas o menos
            if (cadena.charAt (0) == '+' || cadena.charAt (0) == '-'){
                //agrega primero un 0 para que tome al signo como operador
                array.add ("0");
            }else {
                //if para ver si es decimal tenemos que agregarle un 0 al inicio para evitar problemas de redondeo
                if (cadena.charAt (0) == '.'){
                    cadena = "0" + cadena;
                }
            }
        }else {
            //avienta una excepcion ya que no empieza con un caracter adecuado
            throw new RuntimeException ("Primer caracter incorrecto");
        }
        
        //while que recorre cada caracter de la cadena
        while(i<cadena.length()){
            //if para ver si es un digito o un punto decimal
            if(Character.isDigit(cadena.charAt(i)) || cadena.charAt (i) == '.'){ 
                
                //si el número tiene más dígitos, va a entrar un loop
                while(i<cadena.length() && (Character.isDigit(cadena.charAt(i)) || cadena.charAt(i)=='.')){
                    
                    //if para ver si no hay dos puntos decimales en un solo numero
                    if (cadena.charAt (i) == '.' && punto){
                        throw new RuntimeException ("Puntos decimales seguidos");
                    }else {
                        if (cadena.charAt (i) == '.'){
                            punto = true;
                        }
                    }
                    
                    stringAux+=cadena.charAt(i); //va a seguir guardando todo en un string
                    
                    i++;
                }
                array.add (stringAux); //guarda el string del numero ya completo
                stringAux = ""; //vaciamos el auxiliar
                punto = false;
            }
            if(i<cadena.length()){
                //verifica si es un caracter valido y no un & por ejemplo
                verificaCaracter (cadena.charAt (i));
                //if para ver si hay dos operadores seguidos pero validos
                if (i != 0 && i < cadena.length () - 1 && (cadena.charAt (i - 1) == '*' || cadena.charAt (i -1) == '/' || cadena.charAt (i - 1) == '^') && (cadena.charAt (i) == '+' || cadena.charAt (i) == '-') && Character.isDigit (cadena.charAt (i + 1))){
                    array.add ("(");
                    array.add ("0");
                    array.add (String.valueOf (cadena.charAt (i)));
                    array.add (String.valueOf (cadena.charAt (i + 1)));
                    array.add (")");
                    i ++;
                }else {
                    //agrega el signo a la pila
                    array.add(String.valueOf(cadena.charAt(i))); 
                }    
            }
            i++;
        }    
        return array;   
    }
    
    /**
     * Método verificaCaracter: 
     * método que verifica si un caracter dado es válido para realizar la operación. 
     * @param c caracter a evaluar.
     * 
     */
    public static void verificaCaracter (char c){
        //if para ver si es un caracter valido
        if (c != '1' && c != '2' && c != '3' && c != '4' && c != '5' && c != '6' && c != '7' && c != '8' && c != '9' && c != '0' && c != '+' && c != '-' && c != '*' && c != '/' && c != '^' && c != '(' && c != ')' && c != '.'){
            //avienta una excepcion
            throw new RuntimeException ("Caracter invalido");
        }
    }
    
    /**
     * Método balanceParentesis: 
     * 
     * método para verificar si los parentesis de la expresion estan balanceados.
     * 
     * @param array Arreglo con elementos de la expresión a revisar.
     * @return <ul>
     * <li> true: si los paréntesis están balanceados.</li>
     * <li> false: si los paréntesis no están balanceados.</li>
     * </ul>
     */
    public static boolean balanceParentesis(ArrayList <String> array){

        PilaA <String> pila = new PilaA();
        boolean resultado = false;
        
        for (int i = 0; i < array.size (); i ++){
            //if para ver si es un parentesis abierto
            if (array.get (i).equals ("(")){
                pila.push ("(");
            }else {
                if (array.get (i).equals (")")){
                    pila.pop ();
                }
            }
        }
        if (pila.isEmpty ()){
            resultado = true;
        }
        return resultado;
    }
    
    /**
     * Método checarOperador:
     * 
     * método para verificar si el dato es un operador (+,-,*,/,^).
     * 
     * @param dato String con el dato a verificar.
     * @return <ul>
     * <li> true: si es un operador.</li>
     * <li> false: si no es un operador.</li>
     * </ul>
     */

    public static boolean checarOperador(String dato){
        boolean resp = false;
        if(dato.equals ("+") || dato.equals ("-") || dato.equals ("*") || dato.equals ("/") || dato.equals ("^")){
            resp = true; //si es un operador regresa true
        }
        return resp;
    } 
    
    /**
     * Método checarNumero:
     * 
     * método para verificar si el dato es un número (double).
     * 
     * @param dato String con el dato a verificar.
     * @return <ul>
     * <li> true: si es un número (double).</li>
     * <li> false: si no es un número (double).</li>
     * </ul>
     */

    public static boolean checarNumero(String dato){
        boolean resp = true;
        try {
            Double.parseDouble(dato); //intenta convertirlo a double 
        }catch(NumberFormatException e){ //no puede convertir de string a double
            resp=false; 
        }
        return resp;
    }
    
    /**
     * Método jerarquía:
     * 
     * método para determinar la jerarquia de operadores.
     * 
     * @param dato String con el operador cuya jerarquia se va a determinar.
     * @return <ul>
     * <li> 0: si es operador de menor jerarquía.</li>
     * <li> 1: si es operador de suma o resta.</li>
     * <li> 2: si es operador de division o multiplicación.</li>
     * <li> 3: si es operador de exponente.</li>
     * </ul>
     */
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
    
    /**
    * Método convertirPostfijo:
    * 
    * método para pasar expresion de infija a postfija.
    * @param arrayInfijo Arreglo de String de la expresion en notación infija.
    * @return <ul>
    * <li>Arreglo de String de la expresión en notación postfija.</li>
    * </ul>
    */
    public static ArrayList<String> convertirPostfijo(ArrayList<String> arrayInfijo){
        PilaA<String> pila = new PilaA<>(); //pila para trabajar con operadores y numeros
        ArrayList<String> arrayPostfijo = new ArrayList<>(); //arreglo para guardar resultado en forma postfija
        String stringAux; 

        for (int i = 0; i < arrayInfijo.size(); i++) { //recorremos todo el arreglo en infijo
            stringAux = arrayInfijo.get(i); //elemento que vamos a revisar
            //si hay un numero lo agregamos al postfijo
            if (checarNumero(stringAux)){
                arrayPostfijo.add(stringAux);
            } else{
                if (stringAux.equals("(")) {  //si se abre un parentesis, lo agregamos a la pila
                    pila.push(stringAux);
                } else if (stringAux.equals(")")) { //si se cierra un parentesis
                    while (!pila.isEmpty() && !pila.peek().equals("(")) { //buscamos hasta que encontremos el parentesis abierto
                        arrayPostfijo.add(pila.pop()); //todo lo que está en el parentesis lo agregamos al arreglo postfijo y lo quitamos de la pila 
                    }
                    pila.pop(); //quitamos el parentesis abierto de la pila
                } else {  
                    //si el string es un operador, los quitamos del infijo y lo ponemos en el postfijo 
                    while (!pila.isEmpty() && jerarquia(stringAux) <= jerarquia(pila.peek())){
                        arrayPostfijo.add(pila.pop());
                    }
                    //AL MENOS que los operadores de la pila tengan una jerarquia menor
                    pila.push(stringAux); //y en ese caso, empujamos el string a la pila
                }
            }
        }
        //ubicamos esta parte del código fuera del for-loop
        while (!pila.isEmpty()) {
            arrayPostfijo.add(pila.pop());
        }
        return arrayPostfijo;
    }

    /**
     * Método operacion:
     * método para evaluar operaciones.
     * 
     * @param dato1 primer double con el que se realizará la operación.
     * @param dato2 segundo double con el que se realizará la operación.
     * @param operador determina operación a ser realizada.
     * @return <ul>
    * <li>Double con el resultado de la operación.</li>
    * </ul>
    */

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
                if (dato2 == 0){
                    throw new RuntimeException ("Division entre 0");
                }
                resp = dato1 / dato2;
                break;
            case '^':
                resp = Math.pow(dato1, dato2);
                break;
        }
        return resp;
    }
    
    /**
     * Método evaluaPostfijo: 
     * metodo para evaluar expresiones en notación postfija.
     * @param arrayPostfijo Arreglo de expresión en notación postfija.
     * @return <ul>
    * <li>Dato double del resultado de la operación. </li>
    * </ul>
     */
    public static double evaluaPostfijo(ArrayList<String> arrayPostfijo){
        double dato1, dato2, resp=0;
        char operador;
        boolean unaOperacion = false; //variable para ver si se realizo minimo una operacion
        String stringAux;
        PilaA<Double> pila = new PilaA<>();
        for(int i = 0; i<arrayPostfijo.size(); i++){
            stringAux = arrayPostfijo.get(i);
            if(checarNumero(stringAux)){
                pila.push(Double.parseDouble(stringAux));
            }else {
                unaOperacion = true;
                dato2 = pila.pop(); //el dato2 es el push más reciente
                dato1 = pila.pop(); //el dato1 es el push menos reciente 
                operador = stringAux.charAt(0); 
                resp = operacion(dato1, dato2, operador);
                pila.push(resp); 
            }   
        }
        //if para ver si realizo un operacion
        if (!unaOperacion){
            resp = pila.pop ();
        }
        return resp;
    }
}