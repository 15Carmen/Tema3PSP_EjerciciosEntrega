package ejercicios.ejercicio1;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Cliente {

    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        DatagramPacket packet;
        DatagramSocket socket;
        InetAddress direccionServidor;
        int puertoServidor = 150303;
        int numero;
        String mensaje = "";
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

                buffer = String.valueOf(numero).getBytes();

                packet = new DatagramPacket(buffer, buffer.length, direccionServidor, puertoServidor);

                // 4 - Envío del datagrama a través de send;
                socket.send(packet);

                // 5 - Recepción de la respuesta

                byte[] bufferEntrada = new byte[64];
                DatagramPacket paqueteEntrada = new DatagramPacket(bufferEntrada, bufferEntrada.length, direccionServidor,
                        puertoServidor);
                socket.receive(paqueteEntrada);
                System.out.println("Mensaje recibido: " + new String(bufferEntrada));

                // 6 - Cierre del socket
                System.out.println("(Cliente): Cerrando conexión...");
                socket.close();
                System.out.println("(Cliente): Conexión cerrada.");
            } while (!mensaje.equals("¡Enhorabuena! Has acertado el número"));

        } catch (SocketException e) {
            System.err.println("Error al conectar con el servidor.");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("No se ha podido enviar o recibir el paquete");
            e.printStackTrace();
        }
    }
}
