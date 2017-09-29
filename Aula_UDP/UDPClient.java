package Aula_UDP;

/**
 * UDPClient: Cliente UDP
 * Descricao: Envia uma msg em um datagrama e recebe a mesma msg do servidor
 */

import java.net.*;
import java.io.*;

public class UDPClient{
    public static void main(String args[]){ 
        /* args[0]: mensagem  e args[1]: ip destino */
        DatagramSocket aSocket = null;   

        try {
            aSocket = new DatagramSocket(6666); //cria um socket datagrama
		                        
            SendThread sendThread = new SendThread(aSocket);

            RecieveThread recieveThread = new RecieveThread(aSocket);
            
        } catch (SocketException e){
	    System.out.println("Socket: " + e.getMessage());
        }catch (IOException e){
	    System.out.println("IO: " + e.getMessage());
        } //catch
    } //main		      	
} //class
