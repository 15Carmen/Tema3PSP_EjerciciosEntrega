package ejercicios.ejercicio1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Random;

public class GestorProcesos extends Thread{
    private static int numeroSecreto = new Random().nextInt(0, 100);
    DatagramSocket socket;
    DatagramPacket packet;

    public GestorProcesos(DatagramSocket socket, DatagramPacket packet) {
        super();
        this.socket = socket;
        this.packet = packet;
    }

    @Override
    public void run() {
        numeroSecreto();
    }

    public void numeroSecreto() {

        //Recibo el numero del cliente
        int numero = Integer.parseInt(new String(packet.getData()).trim());

        //compruebo si el numero introducido por el usuario es igual al numero secreto y envío el mensaje que corresponda según el resultado
        String mensaje = comprobarNumero(numero, numeroSecreto);
        byte[] mensajeEnviado = mensaje.getBytes();
        DatagramPacket paqueteSalida = new DatagramPacket(mensajeEnviado, mensajeEnviado.length,packet.getAddress(),packet.getPort());
        try {
            socket.send(paqueteSalida);
        } catch (IOException e) {
            System.out.println("Error enviando el paquete");
            e.printStackTrace();
        }
    }

    public static String comprobarNumero(int num, int numeroSecreto) {
        String resultado;
        if (num == numeroSecreto) {
            resultado = "¡Enhorabuena! Has acertado el número";
        } else if (num < numeroSecreto) {
            resultado = "El número mandado es menor que el número secreto";
        } else {
            resultado = "El número mandado es mayor que el número secreto";
        }
        return resultado;
    }
}
