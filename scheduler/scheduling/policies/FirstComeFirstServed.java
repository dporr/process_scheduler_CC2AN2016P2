
/* FirstComeFirstServed.java*/

/**
 ** Hecho por: Jeff Ortiz
 ** Carnet: 13002201
 ** Seccion: AN
**/ 

/** 
	Esta clase representa la definicion  de la politica FirstComeFirstServed
        , la cual representa la "cola" en la que se
        debe calendarizar los procesos,
        forma en la que esta politica atiende los procesos:
        como bien su nombre lo dice , el primer proceso en llegar a la cola es
        primero en ser atendido.

	@author Jeff Ortiz
**/
package scheduler.scheduling.policies;
import scheduler.processing.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class FirstComeFirstServed extends Policy
								 implements Enqueable{

protected static ConcurrentLinkedQueue<SimpleProcess> cola;
	/** @ Constructor que inicializa la cola vacia e inicializa los campos de la clase**/
	public FirstComeFirstServed(){
		super();
		this.cola = new ConcurrentLinkedQueue<SimpleProcess>();
	}

	/** @void metodo void que agrega una instancia de SimpleProcess a la cola **/
public synchronized void add(SimpleProcess p){
	System.out.print("Se ha ingresado un nuevo proceso a la cola:");
	cola.add(p);
	this.size++;
	this.totalProcesses++;
	System.out.println(p.toString());
}
	/** @void metodo void que remueve el primer proceso en la cola (proceso ha ser 
		atendido) 
        **/

	public synchronized void remove(){
		if (cola.size() != 0) {
			cola.poll();
			this.size--;
		}
	}	
	/** Devuelve el siguiente proceso a ser atendido. No lo remueve de la
            cola
            @return devuelve la instancia de el SimpleProcess siguiente a 
            ser atendido. 
        **/
  
	public synchronized SimpleProcess next(){
		if (this.cola.size() == 0) return null;
			return cola.peek();
		}
}