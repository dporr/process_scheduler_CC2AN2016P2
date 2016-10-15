
/* LastComeFirstServed.java*/

/**
 ** Hecho por: Jeff Ortiz
 ** Carnet: 13002201
 ** Seccion: AN
**/

/** 
	Esta clase representa la definicion  de la politica LastComeFirstServed
        , la cual representa la "pila" en la que se
        debe calendarizar los procesos,
        forma en la que esta politica atiende los procesos
        como bien su nombre lo dice, el ultimo proceso en llegar es el 
        primero en ser atendido.

	@author Jeff Ortiz
**/

package scheduler.scheduling.policies;
import scheduler.processing.*;
import java.util.Stack;

public class LastComeFirstServed extends Policy
								 implements Enqueable{

public static Stack<SimpleProcess> pila;
	/** Constructor que inicializa la pila vacia e inicializa los campos
		heredados de la super clase**/
	public LastComeFirstServed(){
		super();
		this.pila = new Stack<SimpleProcess>();
	}

/** ingresa una instancia de SimpleProcess,
            @param p Proceso a ingresar en la pila de la politica 
        **/
public synchronized void add(SimpleProcess p){
	System.out.print("Se ha ingresado un nuevo proceso a la pila:");
	pila.push(p);
	this.size++;
	this.totalProcesses++;
	System.out.println(p.toString());
}
	/**@void metodo void que remueve el ultimo proceso que se ingreso
		hasta ese momento a la pila **/
	public synchronized void remove(){
		if (this.pila.size()!=0) {
			System.out.print("Se ha removido un proceso de la pila:");
			System.out.println(pila.pop().toString());
			this.size--;
		}
		else{
			System.out.println("No se ha removido proceso, PILA VACIA");
		}
	}

	/** Devuelve el siguiente proceso a ser atendido. No lo remueve de la
            pila
            @return devuelve la instancia de el SimpleProcess siguiente a 
            ser atendido. 
        **/
	public synchronized SimpleProcess next(){
		if (this.pila.size()==0) {
			return null;
		}
			return pila.peek();
	}
	/** @return el estado de la cola como String 
    **/
	public synchronized String toString(){
        String result = this.pila.toString();
        return result;
    }

}
