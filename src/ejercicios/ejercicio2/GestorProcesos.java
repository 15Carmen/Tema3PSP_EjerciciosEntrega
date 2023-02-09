package ejercicios.ejercicio2;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class GestorProcesos extends Thread{

    //Decalracion de variables de la clase
    private Socket socket;
    private OutputStream os;
    private InputStream is;

    public GestorProcesos (Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        realizarProceso();
    }

    /**
     * Método que hace la conexión con el cliente y comprueba la dirección web que ha introducido por consola
     */
    public void realizarProceso (){

        //Declaracion de variables
        OutputStreamWriter osw;
        BufferedWriter bw;
        InputStreamReader isr;
        BufferedReader br;
        String direccionWebCliente;

        try {
            os = this.socket.getOutputStream();
            osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);
            bw = new BufferedWriter(osw);
            is = this.socket.getInputStream();

            isr = new InputStreamReader(is, StandardCharsets.UTF_8);
            br = new BufferedReader(isr);

            //Leo la dirección web introducida por el cliente
            direccionWebCliente = br.readLine();

            //Compruebo la dirección del cliente con las que hay escritas en el fichero de texto y mando la respuesta
            bw.write(comprobarDirecciones(direccionWebCliente));
            bw.newLine();
            bw.flush();

            //Cerramos flujo de datos
            bw.close();
            br.close();
            osw.close();
            isr.close();
            is.close();
            os.close();
        } catch (IOException e) {
            System.err.println("Error al conectarse con el cliente");
            e.printStackTrace();
        }
    }


    /**
     * Método al que le pasamos por parámetros
     * @param direccionWebCliente que es la direccion introducida por consola y nos la va a comprobar con las direcciones
     * escritas en el fichero de texto ficheroHostCasero.txt. Si la introducida por el cliente es igual a alguna de las
     * que hay en el fichero, devuelve
     * @return un mensaje con la ip de la dirección web. Si no la encuentra, devuelve un mensaje indicando que la dirección
     * introducida no se encuentra registrada en el fichero.
     */
    private String comprobarDirecciones(String direccionWebCliente){

        //Declaracion de variables
        BufferedReader lecturaFichero;
        String mensajeSalida = null;
        String linea;

        try {
            //Preparo la lectura del fichero
            lecturaFichero = new BufferedReader(new FileReader("src/ejercicios/ejercicio2/ficheroHostCasero.txt"));
            linea = lecturaFichero.readLine();

            while (linea != null){      //Mientras se lea una linea en el fichero

                if (linea.split(" ")[0].equals(direccionWebCliente)){   //Si la dirección introducida es igual a la del fichero
                    //Indicamos la ip de la dirección web
                    mensajeSalida = "La ip de " + direccionWebCliente + " es " + linea.split(" ")[1];
                    break;
                } else {    //Si no encontramos la dirección en el fichero
                    //Indicamos que la dirección no está registrada en el fichero
                    mensajeSalida = "Lo sentimos, direccion web no registrada.";
                }
                linea = lecturaFichero.readLine();  //Leemos la siguiente línea
            }
        } catch (IOException e) {
            System.err.println("Error, archivo no encontrado");
            e.printStackTrace();
        }
        return mensajeSalida;
    }
}
