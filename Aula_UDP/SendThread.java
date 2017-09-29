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
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yudi
 */
public class SendThread extends Thread {

    String ip;
    DataInputStream in;
    DataOutputStream out;
    DatagramSocket clientSocket;

    public SendThread(DatagramSocket aClientSocket) {
        this.clientSocket = aClientSocket;
        this.start();  /* inicializa a thread */

    } //construtor

    /* metodo executado ao iniciar a thread - start() */
    /* Metodo executado quando a thread e iniciada */
    public void run() {

        int serverPort = 6666; // porta do servidor
        Scanner scanner = new Scanner(System.in);
        System.out.print("Você: ");
        this.ip = scanner.nextLine();
        InetAddress aHost = null;

        try {
            aHost = InetAddress.getByName(this.ip);
        } catch (UnknownHostException ex) {
            Logger.getLogger(SendThread.class.getName()).log(Level.SEVERE, null, ex);
        }

        scanner = new Scanner(System.in);

        try {
            while (true) {
                System.out.println("Digite uma mensagem");
                String entrada = scanner.nextLine();
                byte[] m = entrada.getBytes(); // transforma a mensagem em bytes

                /* cria um pacote datagrama */
                byte[] buffer = new byte[1000];
                DatagramPacket request = new DatagramPacket(m, m.length, aHost, serverPort);
                /* envia o pacote */
                this.clientSocket.send(request);

            }

        } catch (EOFException e) {
            System.out.println("EOF: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("leitura: " + e.getMessage());
        } //catch

    } //run

}
