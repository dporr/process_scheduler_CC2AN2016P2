/* ArithmeticProcess.java*/

/**
 ** Hecho por: Jeff Ortiz
 ** Carnet: 13002201
 ** Seccion: AN
**/ 

//Esta clase representa los procesos aritmeticos
package scheduler.processing;

public class ArithmeticProcess extends SimpleProcess{

	/** El campo time es un double que identifica el tiempo del proceso, Time es IGUAL para todos 
		los procesos que sean del mismo tipo.
        **/
 protected static double time;

 	/**
		Inicializa el ArithmeticProcess con un id especifico y el tiempo que tardara en ejecutarse
		@param id representa el id que se le asigna al ArithmeticProcess
		@param time representa el tiempo que va a tomar el proceso en ejecutarse
	**/	
 		public ArithmeticProcess(int id, double time){
 			super(id);
 			this.time = time;
 		}

 		/**
		Devuelve el tiempo del ArithmeticProcess
		@return devuelve el double que representa el tiempo del proceso
		**/	

		public double getTime(){
			return this.time;
		}

		 /**
		Formato imprimible para objetos ArithmeticProcess
		@return devuelve un String de la forma [id:id_del_proceso+time:time_del_proceso]
		**/
		public String toString() {
		return "[id:"+ this.id+" time:"+this.time+"A]";
		}
}
