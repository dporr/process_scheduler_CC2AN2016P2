
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
/*Cola que nos sirve para clonar la original y poder hacer referencia al siguiente 
  proceso a ser atendido*/
protected static ConcurrentLinkedQueue<SimpleProcess> colaClon;
	
	/** @ Constructor que inicializa la cola vacia e inicializa los campos de la clase**/
	public FirstComeFirstServed(){
		super();
		this.cola = new ConcurrentLinkedQueue<SimpleProcess>();
	}

	/** @void metodo void que agrega una instancia de SimpleProcess a la cola **/
public synchronized void add(SimpleProcess p){
	System.out.println("Se ha ingresado un nuevo proceso a la cola");
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
			System.out.println("Se ha removido un proceso de la cola");
			System.out.println(cola.poll().toString());
			this.size--;
		}
			else {
				System.out.println("No se ha removido proceso, COLA VACIA");
			}
}	
	/** Devuelve el siguiente proceso a ser atendido. No lo remueve de la
            cola
            @return devuelve la instancia de el SimpleProcess siguiente a 
            ser atendido. 
        **/
  
	public synchronized SimpleProcess next(){
		SimpleProcess result = null;
		if (this.cola.size() == 0) {
			System.out.println("Cola Vacia");
		}
			else{
				colaClon = cola;
				colaClon.poll();
				result =  colaClon.peek();
		}
	return result;}
}