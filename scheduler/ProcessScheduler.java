
/* ProcessScheduler.java*/
/**
 ** Hecho por: Diego Porras
 ** Carnet: 16001742	
 ** Seccion: AN
**/ 

import scheduler.processing.*;
import java.math.Random;

/*
	Clase principal del sistema tiene 3 funciones pricipales:
		1)Interpretar argumentos de linea de comandos
		2)Instanciacion y manejo de objeto cola/politica (clase principal)
		3)Generacion aleatoria de procesos y su adicion a la cola/politica en ejecucion (clase interna RandomProcessGenerator)
*/
public class ProcessScheduler extends Thread{
	private int pid=0;
	//Tiempo default para cada tipo de proceso durante esta ejecucion.
	private double aTime,lTime,cTime,ioTime;
	//Ramngo de tiempo con el que  se crearan procesos
	private double lowTime,upTime;
	private int quantum=0;
	private Policy politica;
	private String nPolitica;
	private final String FIRST_COME_FIRST_SERVED='fcfs';
	private final String LAST_COME_FIRST_SERVED='lcfs';
	private final String RR='rr';

	/*
	*punto de entrada de la aplicacion.
	*Lee los argumentos y crea las configuraciones iniciales para la ejecucion
	*@param args[] Argumentos de linea de comandos. 
	**/
	public static void main(String args[]){
		try{
		//Desempaquetado de parametros
		String nPolitica=args[0].subString(1);
		double lowTime=Double.parseDouble(args[1].split("-")[0]);
		double upTime=Double.parseDouble(args[1].split("-")[1]);
		double aTime=Double.parseDouble(args[2]);
		double ioTime=Double.parseDouble(args[3]);
		double cTime=Double.parseDouble(args[4]);
		double lTime=Double.parseDouble(args[5]);
		if(args.length == 7 ) double quantum=Double.parseDouble(args[6]);
		}catch(Exception e){
			System.err.println("Uno o mas argumenos invalido(s).Revise modos de ejecucion");
		}

		//Parte operativa
		ProcessScheduler pScheduler = new ProcessScheduler(aTime, lTime, cTime,
		 ioTime, lowTime,upTime, nPolitica);
		RandomProcessGenerator rProcess = new RandomProcessGenerator(pScheduler);
		pScheduler.run();
		rProcess.run();
	}



//TODO
	public void run(){}

	public ProcessScheduler(double aTime, double lTime, double cTime,
	 		 double ioTime, double lowTime, double upTime, String nPolitica) {
        this.aTime = aTime;
        this.lTime = lTime;
        this.cTime = cTime;
        this.ioTime = ioTime;
        this.lowTime = lowTime;
        this.upTime = upTime;
        switch(nPolitica){
        	case FIRST_COME_FIRST_SERVED:
        		this.politica= new FistComeFirstServed();
        		break;
        	case LAST_COME_FIRST_SERVED:
        		this.politica=new LastComeFirstServed();
        		break;
        	case RR:
        		this.politica=new RoundRobin(quantum);
        		break;
    	}
    }


    //Setters y getters
    public double getIoTime(){
    	return this.ioTime;
    }
    public double getCTime(){
    	return this.cTime;
    }
    public double getATime(){
    	return this.aTime;
    }
    public double getLTime(){
    	return this.lTime;
    }
    public Policy getPolitica(){
    	return this.politica;
    }
 
}

/* RandomProcessGenerator.java*/

/**
 ** Hecho por: Diego Porras
 ** Carnet: 16001742	
 ** Seccion: AN
**/ 
/*
	Recibe un procesador ProcessScheduler y lo pobla periodicamente con procesos de tipo random
*/
class RandomProcessGenerator extends Thread{
	private ProcessScheduler processor;
	private int pid=0;
	public int kill=0;
	private double lowTime,upTime;
	public RandomProcessGenerator(ProcessScheduler processor){
		this.processor=processor;
		this.lowTime=processor.getLowTime();
		this.upTime=processor.getUpTime();
	}
	
	public void run(){
		Random r = new Random();
		do{
			double t = Math.floor((n + (n2 - n) * r.nextDouble()) * 1000);
			processor.getPolitica().add(createProcess());
			this.sleep(t)
		}
		while(!kill);
	}

	/*
	@return SimpleProcess un proceso de tipo aleatorio.
	*/
	private SimpleProcess createProcess(){
		SimpleProcess process;
		Random rand = new Random();
		switch(rand.nextInt()){
			case 0:
				process = new IOProcess(++pid,processor.getIoTime);
				break;
			case 1:
				process = new ArithmeticProcess(++pid,processor.getATime());
				break;
			case 2:
				process = new LoopProcess(++pid,processor.getLTime());
				break;
			default:
				process = new ConditionalProcess(++pid,processor.getCTime());
		}
		return process;
	}