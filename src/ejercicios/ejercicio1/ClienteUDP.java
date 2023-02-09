package ejercicios.ejercicio1;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class ClienteUDP {

    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        //Declaracion de variables
        DatagramPacket packetEnviar;
        DatagramPacket packetRecibir;
        DatagramSocket socket;
        InetAddress direccionServidor;
        int puertoServidor = 41700;
        int numero;
        String mensaje;
        byte[] buffer;

        try {
            // 1 - Obtención de la dirección del servidor usando el método getLocalHost()
            direccionServidor = InetAddress.getLocalHost();

            System.out.println("Bienvenido a ADIVINA EL NUMERO SECRETO!");

            // 2 - Creación del socket UDP
            socket = new DatagramSocket();

            do {

                // 3 - Generación del datagrama
                System.out.println("Introduce un numero: ");
                numero = sc.nextInt();

                buffer = String.valueOf(numero).getBytes(); //buffer para enviar mensajes
                packetEnviar = new DatagramPacket(buffer, buffer.length, direccionServidor, puertoServidor);

                // 4 - Envío del datagrama a través de send;
                socket.send(packetEnviar);

                // 5 - Recepción de la respuesta
                buffer = new byte[64]; //buffer para recibir mensajes
                packetRecibir = new DatagramPacket(buffer, buffer.length, direccionServidor, puertoServidor);
                socket.receive(packetRecibir);

                //Saco el mensaje enviado por el servidor y lo imprimo por consola
                mensaje = new String(packetRecibir.getData()).trim();
                System.out.println(mensaje);

            } while (!mensaje.equals("ENHORABUENA, HA ACERTADO!"));

            // 6 - Cierre del socket
            System.out.println("(ClienteTCP): Cerrando conexión...");
            socket.close();
            System.out.println("(ClienteTCP): Conexión cerrada.");

        } catch (SocketException e) {
            System.err.println("Error al conectar con el servidor.");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("No se ha podido enviar o recibir el paquete");
            e.printStackTrace();
        }
    }
}
