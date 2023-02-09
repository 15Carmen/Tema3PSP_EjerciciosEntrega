package ejercicios.ejercicio2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorTCP {
    public static void main(String[] args) {
        //Declaramos las variables
        int puerto = 41600;
        try {
            // 1 - Crear socket de tipo servidor y le indicamos el puerto
            ServerSocket socketServidor = new ServerSocket(puerto);
            Socket peticionCliente;

            while (true) {
                // 2 - Queda a la espera de peticiones y las acepta cuando las recibe
                System.out.println("ServidorTCP se encuentra a la escucha de peticiones de los clientes...");
                peticionCliente = socketServidor.accept();
                System.out.println("(ServidorTCP) conexión establecida...");
                new GestorProcesos(peticionCliente).start();
            }
        } catch (IOException e) {
            System.err.println("Error en la creación del Socket ServidorTCP");
            e.printStackTrace();
        }
    }
}
