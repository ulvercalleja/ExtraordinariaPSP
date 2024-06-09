package examenOrdinario.ejercicio4;

public class Main {
    public static void main(String[] args) {
        int port = 12345;

    ServerUDP servidor = new ServerUDP(port);

        servidor.añadirObservador(new PiramideObserver());
        servidor.añadirObservador(new RomboObserver());
        servidor.añadirObservador(new PrimoObserver());

        servidor.Receptor();
    }
}
