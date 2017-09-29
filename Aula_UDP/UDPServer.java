package Aula_UDP;

/**
 * UDPServer: Servidor UDP Descricao: Recebe um datagrama de um cliente, imprime
 * o conteudo e retorna o mesmo datagrama ao cliente
 */
import java.net.*;
import java.io.*;

public class UDPServer {

    public static void main(String args[]) {
        DatagramSocket aSocket = null;
        try {
            aSocket = new DatagramSocket(6666); // cria um socket datagrama em uma porta especifica

            while (true) {

                SendThread sendThread = new SendThread(aSocket);
                RecieveThread recieveThread = new RecieveThread(aSocket);

            } //while
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } //catch
    } //main
}//class
