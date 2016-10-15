
/* ProcessScheduler.java*/
/**
 ** Hecho por: Diego Porras
 ** Carnet: 16001742	
 ** Seccion: AN
**/ 

import scheduler.processing.*;
import scheduler.scheduling.policies.*;
import java.util.Random;
import java.util.Scanner;
/*
	Clase principal del sistema tiene 3 funciones pricipales:
		1)Interpretar argumentos de linea de comandos
		2)Instanciacion y manejo de objeto cola/politica (clase principal)
		3)Generacion aleatoria de procesos y su adicion a la cola/politica en ejecucion (clase interna RandomProcessGenerator)
*/
public class ProcessScheduler extends Thread{
	private int pid=0;
	//Tiempo default para cada tipo de proceso durante esta ejecucion.
	private long aTime,lTime,cTime,ioTime;
	//Ramngo de tiempo con el que  se crearan procesos
	private double lowTime,upTime;
	private long quantum=0;
	private Policy politica;
	private String nPolitica;
	private final String FIRST_COME_FIRST_SERVED="fcfs";
	private final String LAST_COME_FIRST_SERVED="lcfs";
	private final String RR="rr";
	//Variables que llevan el conteo de procesos atendidios y no atendidos//
	private int patendidos,noatendidosm,totalProcesos;
	/*
	*punto de entrada de la aplicacion.
	*Lee los argumentos y crea las configuraciones iniciales para la ejecucion
	*@param args[] Argumentos de linea de comandos. 
	**/
	public static void main(String args[]){
		try{
		//Desempaquetado de parametros
		String nPolitica=args[0].substring(1);
		double lowTime=Double.parseDouble(args[1].split("-")[0]);
		double upTime=Double.parseDouble(args[1].split("-")[1]);
		long aTime=(long)(Double.parseDouble(args[2])* 1000);
		long ioTime=(long)(Double.parseDouble(args[3])* 1000);
		long cTime=(long)(Double.parseDouble(args[4])* 1000);
		long lTime=(long)(Double.parseDouble(args[5]) * 1000);
		long quantum=0;
		if(args.length == 7 ) quantum=(long)Double.parseDouble(args[6])* 1000;
				//Parte operativa
		ProcessScheduler pScheduler = new ProcessScheduler(aTime, lTime, cTime,
		 ioTime,lowTime,upTime, nPolitica,quantum);
		RandomProcessGenerator rProcess = new RandomProcessGenerator(pScheduler);
		rProcess.start();
		pScheduler.start();
		Scanner sc = new Scanner(System.in);
		String line="n";
		do{
			line = sc.next();
		}while(!line.equalsIgnoreCase("q"));
		pScheduler.salida();
				//do while para mantener escucha sobre q y Enter
		}catch(Exception e){
			//e.printStackTrace();
			System.err.println("Uno o mas argumentos invalido(s).Revise modos de ejecucion");
		}

	}
	public void salida(){
		System.out.println("\nExiting....");
		if (nPolitica.equalsIgnoreCase(FIRST_COME_FIRST_SERVED))
				nPolitica="**FirstComeFirstServed**";
		if(nPolitica.equalsIgnoreCase(LAST_COME_FIRST_SERVED))
				nPolitica = "**LastComeFirstServed**";
		if (nPolitica.equalsIgnoreCase(RR))
				nPolitica = "**RoundRobin**";
		System.out.println("Politica: "+ nPolitica);
		System.out.println("Estado actual de la cola: " + this.getPolitica());
		int atendidos=this.getAtendidos();
		System.out.println("Numero Procesos que se atendieron: "+atendidos);
		System.out.println("\nNumero Procesos en cola (sin atenderse): ");
		int totalprocesos=this.getTotalProcesos();
		System.out.println(totalprocesos-atendidos);
		double time = this.getATime()+this.getCTime()+this.getIoTime()+this.getLTime();
		int averagetime =(int)(time/4);
		System.out.println("Tiempo promedio "+ averagetime+"ms");
		System.out.println("\nThis program has finished.");
		System.exit(0);
	}
	public void run(){
		String p=this.nPolitica;
       if(!p.equalsIgnoreCase(RR)){
       		simplePolicy();
       	}else{
       		rrPolicy();
       	}
       }
    /**private void rrPolicy(){
    	do{
       		SimpleProcess next = politica.next();
       		ArithmeticProcess aP;
       		ConditionalProcess cP;
       		LoopProcess lP;
       		IOProcess ioP;

       		if(next==null) continue;
       		if(next instanceof ArithmeticProcess) next = aP = (ArithmeticProcess)next;
       		if(next instanceof ConditionalProcess) next = cP = (ConditionalProcess)next;
       		if(next instanceof LoopProcess) next =lP = (LoopProcess)next;
       		if(next instanceof IOProcess) next = ioP = (IOProcess)next;
       		System.out.println("Atendiendo: " + next);
   			try{
				this.sleep(quantum);
			}catch(InterruptedException e){
				System.out.println("Se ha detenido el proceso de forma abrupta");
				System.exit(0);
			}
			long currTime=next.getTime();
			next.setTime(currTime-quantum);
			System.out.println(">>>>>"+next.getTime());
			if((next.getTime()/quantum)>0){
				politica.add(next);
				politica.remove();
			}else{
				patendidos++;
				System.out.print("Se ha removido un proceso: "+next);
       			System.out.print("Estado de la cola de proceso: "+politica.toString());
			}
       	}while(true);
    }**/
    
    private void simplePolicy(){
       		do{
       			long t=0;
       			SimpleProcess next = politica.next();
       			if(next==null) continue;
       			if(next instanceof ArithmeticProcess) t=aTime;
       			if(next instanceof ConditionalProcess) t=cTime;
       			if(next instanceof LoopProcess) t=lTime;
       			if(next instanceof IOProcess) t=ioTime; 
       			System.out.println("Atendiendo: " + next);
   				try{
					this.sleep(t);
				}catch(InterruptedException e){
					System.out.println("Se ha detenido el proceso de forma abrupta");
					System.exit(0);
				}
       			politica.remove();
       			patendidos++;
       			System.out.print("Se ha removido un proceso: "+next);
       			System.out.print("Estado de la cola de proceso: "+politica.toString());
       		}while(true);
    }   
	public ProcessScheduler(long aTime, long lTime, long cTime,
	 		 long ioTime, double lowTime, double upTime, String nPolitica,long quantum) {
        this.aTime = aTime;
        this.lTime = lTime;
        this.cTime = cTime;
        this.ioTime = ioTime;
        this.lowTime = lowTime;
        this.upTime = upTime;
        this.nPolitica=nPolitica;
        this.quantum=quantum;
        if(nPolitica.equalsIgnoreCase(FIRST_COME_FIRST_SERVED)) 
        	this.politica= new FirstComeFirstServed();
		if(nPolitica.equalsIgnoreCase(LAST_COME_FIRST_SERVED)) 
	  		this.politica=new LastComeFirstServed();
		if(nPolitica.equalsIgnoreCase(RR)) 
        		this.politica=new RoundRobin(quantum);
    }
      /*
	@return tiempo del proceso IO.
	*/
    public long getIoTime(){
    	return this.ioTime;
    }

    /*
	@return tiempo del proceso Condicional.
	*/
    public long getCTime(){
    	return this.cTime;
    }
    /*
	@return tiempo del proceso Aritmetico.
	*/
    public long getATime(){
    	return this.aTime;
    }
    /*
	@return tiempo del proceso Loop.
	*/
    public long getLTime(){
    	return this.lTime;
    }

    /*
	@return politica ej ejecucion
	*/
    public Policy getPolitica(){
    	return this.politica;
    }
    /*
	@return tiempo minimo entre generacion de procesos nuevos.
	*/
    public double getLowTime(){
    	return this.lowTime;
    }
    /*
	@return tiempo maximo entre generacion de procesos nuevos.
	*/
    public double getUpTime(){
    	return this.upTime;
    }
 	/*
	@return cantidad de procesos atendidos por este procesador.
	*/
    public int getAtendidos(){
    	return this.patendidos;
    }
    /*
	@return cantidad de procesos creados.
	*/
	public int getTotalProcesos(){
		return this.totalProcesos;
	}
	/*
	@param aumenta en 1 la cantidad de procesos
	*/
	public void addProceso(){
		this.totalProcesos++;
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
	private int totalprocesos=0;
	//public boolean kill=false;
	private double lowTime,upTime;
	public RandomProcessGenerator(ProcessScheduler processor){
		this.processor=processor;
		this.lowTime=processor.getLowTime();
		this.upTime=processor.getUpTime();
	}
	
	public void run(){
		Random r = new Random();
		do{
			long t = (long)((lowTime + ((upTime - lowTime) * r.nextDouble()))*1000);
			processor.getPolitica().add(createProcess());
			processor.addProceso();
			System.out.print("Estado de la cola de proceso: "+processor.getPolitica().toString());
			try{
				this.sleep(t);
			}catch(InterruptedException e){
				System.out.println("Se ha detenido el proceso de forma abrupta");
				System.exit(0);
			}
		}
		while(true);
	}

	/*
	@return SimpleProcess un proceso de tipo aleatorio.
	*/
	private SimpleProcess createProcess(){
		SimpleProcess process;
		Random rand = new Random();
		switch(rand.nextInt(3)){
			case 0:
				process = new IOProcess(++pid,processor.getIoTime());
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
}