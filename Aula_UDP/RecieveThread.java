/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aula_UDP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;

/**
 *
 * @author yudi
 */
public class RecieveThread extends Thread {

    DataInputStream in;
    DataOutputStream out;
    DatagramSocket clientSocket;

    public RecieveThread(DatagramSocket aClientSocket) {
        clientSocket = aClientSocket;

        this.start();  /* inicializa a thread */

    } //construtor

    /* metodo executado ao iniciar a thread - start() */
    /* Metodo executado quando a thread e iniciada */
    public void run() {

        try {
            while (true) {

                byte[] buffer = new byte[1000]; // cria um buffer para receber requisies

                /* cria um pacote vazio */
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                this.clientSocket.receive(request);  // aguarda a chegada de datagramas

                /* imprime e envia o datagrama de volta ao cliente */
                System.out.println("Cliente: " + new String(request.getData()));

            }

        } catch (EOFException e) {
            System.out.println("EOF: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("leitura: " + e.getMessage());
        } //catch

    } //run

}
