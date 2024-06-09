package examenOrdinario.ejercicio2;

public class Persona extends Thread{
    private static final long MIN_TIEMPO_DESCANSO = 10000;
    private static final long MAX_TIEMPO_DESCANSO = 60000;

    private Bano bano;
    private int idPersona;

    public Persona(Bano bano, int idPersona) {
        this.bano = bano;
        this.idPersona = idPersona;
    }

    @Override
    public void run() {
        while(true){
            bano.entrar(idPersona);
            simularUso();
            bano.salir(idPersona);
        }
    }
    
    public void simularUso(){
        
        System.out.printf("Persona %d esta usando el baño... \n", idPersona);
        try {
            sleep((long) (Math.random()* (MAX_TIEMPO_DESCANSO - MIN_TIEMPO_DESCANSO + 1) + MIN_TIEMPO_DESCANSO));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
         
    }
}
