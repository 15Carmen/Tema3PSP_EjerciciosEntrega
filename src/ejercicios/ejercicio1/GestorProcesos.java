package ejercicios.ejercicio1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Random;

public class GestorProcesos extends Thread{

    //Declaracion de varibles
    private static int numeroSecreto = (int) (Math.random()*100+1);   //Generamos un numero secreto random
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

    /**
     * Método que va a recibir el numero del cliente y va comprobar si es igual que el numero secreto
     * mandando un mensaje en consecuencia
     */
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

    /**
     * Método que compara el
     * @param num que es introducido por el usuario (ClienteTCP) con el
     * @param numeroSecreto que es generado aleatoriamente y nos
     * @return un mensaje según el numero del cliente es mayor, menor o igual que el numero secreto.
     */
    public static String comprobarNumero(int num, int numeroSecreto) {
        String resultado;
        if (num == numeroSecreto) {
            resultado = "ENHORABUENA, HA ACERTADO!";
        } else if (num < numeroSecreto) {
            resultado = "El numero es menor que el numero secreto";
        } else {
            resultado = "El numero es mayor que el numero secreto";
        }
        return resultado;
    }
}
