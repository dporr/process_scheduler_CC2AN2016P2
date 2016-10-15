
/* RoundRobin.java*/

/**
 ** Hecho por: Jeff Ortiz
 ** Carnet: 13002201
 ** Seccion: AN
**/ 

/** 
	Esta clase representa la definicion  de la politica RoundRobin
        , la cual representa la "ListaEncadenada" en la que se
        debe calendarizar los procesos,
        forma en la que esta politica atiende los procesos:
       "FirstComeFirstServed", y si no alcanza ese tiempo para concluir
       el proceso, el proceso pasa a espera de nuevo.

	@author Jeff Ortiz
**/

package scheduler.scheduling.policies;
import scheduler.processing.*;
import java.util.LinkedList;

public class RoundRobin extends Policy
						implements Enqueable{

protected static double quantum;
protected static LinkedList<SimpleProcess>lista;

/** @ Constructor que inicializa la lista vacia e inicializa los campos de la clase**/

/**NOTA: quantum representa el tiempo que el cual tomara este proceso en ser atendido **/
	public RoundRobin(double quantum){
		super();
		this.lista = new LinkedList<SimpleProcess>();
		this.quantum = quantum;
	}
/** ingresa un proceso a la cola de procesos de la politica RoundRobin
            @param p Proceso a ingresar en la cola de la politica 
        **/
	public synchronized void add(SimpleProcess p){
		System.out.println("Se ha ingresado un nuevo proceso a la Lista");
		this.lista.add(p);
		this.size++;
		this.totalProcesses++;
		System.out.println(p.toString());				
	}

	/** @void metodo void que remueve el primer proceso en la cola (proceso ha ser 
		atendido) 
        **/

	public synchronized void remove(){
		if (this.lista.size()!=0) {
			System.out.println("Se ha removido un proceso de la Lista");
			System.out.println(this.lista.remove().toString());
		}
			else{
				System.out.println("No se ha removido proceso,LISTA VACIA");
			}
	}

	/** Devuelve el siguiente proceso a ser atendido. No lo remueve de la
            cola
            @return devuelve la instancia de el SimpleProcess siguiente a 
            ser atendido. 
    **/
	public synchronized SimpleProcess next(){
		SimpleProcess result = null;
		if (this.lista.size() ==0){
			System.out.println("Lista Vacia");
		}
			else if (this.lista.size() == 1) {
				return result;
			}
				else{
					result = this.lista.get(1);
		}
	return result;
	}
	/** @return el estado de la cola como String 
    **/
	public synchronized String toString(){
        String result = this.lista.toString();
        return result;
    }
}