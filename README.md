# ProyectoCalculadora
Calculadora infija-postfija que puede ejecutar operaciones aritméticas con números reales. 
Desarrollada en la IDE de Netbeans con Java.

En general, el proyecto cumple con los siguientes requisitos: 
- Utiliza pilas como estructura de datos para el manejo de las expresiones.
- La capacidad de trabajar con punto decimal y números negativos o positivos.
- Maneja excepciones sin que se interrumpa el programa (ejemplo: 3*/2).
- Una interfaz gráfica amigable para el usuario.

Contexto
Infijio: orden natural de las operaciones (ejemplo: a + b)
Postfijo: orden para facilitar el cálculo computacional (ejemplo: a b +)

## src
- PilaADT
  - Interfaz para diagramar la clase de Pila
- PilaA
  - Implementación con arreglos de la estructura de pila, comúnmente conocida como STACK.
  - Métodos: push, pop, peek, isEmpty, toString, getters, setters y constructor.
- ExcepcionColecciónVacia
  - Excepción que se utiliza en la clase PilaA en el caso de que está vacía.
- CalculadoraFuncionalidad
  - Contiene toda la funcionalidad de la calculadora.
  - En resumen: recibe un String, lo convierte en un arrayList, verifica que sea válido y pone las operaciones en un orden postfijo. Las operaciones se ordenan por su jerarquía y se evaluan con una pila auxiliar. 
  - Métodos:
    - verificaCaracter: verifica que el un caracter sea válido (+, -, *, /, ^)
    - checarNumero: verifica que el número sea válido con un try Double.parseDouble(dato)
    - convertirCadena: recibe el input del usuario como un String y lo regresa como un arrayList. Utiliza verificaCaracter y checarNumero.
    - balanceParentesis: recorre el arrayList y en una pila hace push cuando encuentre un "(" y pop en un ")". Si la pila está vacía cuando termina de recorrer el array, están balanceados.
    - jerarquia: usa un switch para checar la jerarquía de un operador (0, 1, 2, 3)
    - verificarInput: recorre todo el arrayList para verificar que no hayan dos numeros (ejemplo: 1.1.3) ni dos operadores seguidos
    - operacion: realiza la operación entre dos datos que corresponde a un operador (dato, dato, operador)
    - convertirPostfijo(*): convierte las operaciones infijas a postfijas. El metodo funciona metiendo datos con un String y pila auxiliar. Va checando si es un número o un operador y dependiendo de la jerarquía controla los pops de la pila y lo que se añade al arreglo final postfijo.
    - evaluaPostfijo(*): evalua la operación que ya está en el arreglo postifjo.
    - calcula: usa convertirPostifjo() y posteriormente evaluaPostfijo() para regresar el resultado de la operación. También checa si está vacío. 
- Calculadora
  - Interface gráfica para el usuario
  - Cuando se presiona el botón del igual, usa CalculadoraFuncionalidad.calcula() con el String de la operación y regresa el resultado en la label de resultado (o regresa un String de "Error" si hubo una excepción.

#### Limitaciones
- Las operaciones no pueden incluir espacios
- Para multiplicar se debe usar el signo de multiplicación, no paréntesis
- La interfaz gráfica no es amigable con operaciones muy grandes


## Autores:
Tania Mendoza
Carlos Lugo
Mauricia Peña
Evelyn Reséndiz 
Emilio González 
