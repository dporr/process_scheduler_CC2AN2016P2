/* ProcessScheduler.java*/

/**
 ** Hecho por: Diego Porras
 ** Carnet: 16001742	
 ** Seccion: AN
**/ 

/*
	Clase principal del sistema tiene 3 funciones pricipales:
		1)Interpretar argumentos de linea de comandos.
		2)Instanciacion y manejo de objeto cola/politica
		3)Generacion aleatoria de procesos y su adicion a la cola/politica en ejecucion.
*/
public class ProcessScheduler{
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
		//Desempaquetado de parametros
		String nPolitica=args[0].subString(1);
		double lowTime=Double.parseDouble(args[1].split("-")[0]);
		double upTime=Double.parseDouble(args[1].split("-")[1]);
		double aTime=Double.parseDouble(args[2]);
		double ioTime=Double.parseDouble(args[3]);
		double cTime=Double.parseDouble(args[4]);
		double lTime=Double.parseDouble(args[5]);
		if(args.length == 7 ) double quantum=Double.parseDouble(args[6]);

		//Parte operativa
		ProcessScheduler pScheduler = new ProcessScheduler(aTime, lTime, cTime,
		 ioTime, lowTime,upTime, nPolitica);
	}

	public ProcessScheduler(){}
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

   	/*
	@return SimpleProcess un proceso de tipo aleatorio.
	*/
	private SimpleProcess createProcess(){
		SimpleProcess process;
		Random rand = new Random();
		switch(rand.nextInt()){
			case 0:
				process = new IOProcess(++pid,ioTime);
				break;
			case 1:
				process = new ArithmeticProcess(++pid,ioTime);
				break;
			case 2:
				process = new LoopProcess(++pid,ioTime);
				break;
			default:
				process = new ConditionalProcess(++pid,ioTime);
		}
		return process;
	}

}
