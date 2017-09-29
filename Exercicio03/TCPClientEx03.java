/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exercicio03;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 *
 * @author yudi
 */
public class TCPClientEx03 {

    public static void main(String args[]) {
        /* args[0]: mensagem  e args[1]: ip do servidor */
        //String ipServidor = args[1];
        String ipServidor = "127.0.0.1";
        int serverPort = 7896;   /* especifica a porta */

        Socket s = null;
        try {

            s = new Socket(ipServidor, serverPort);  /* conecta com o servidor */

            TaskThreadEx2 c = new TaskThreadEx2(s);

        } catch (UnknownHostException e) {
            System.out.println("Socket:" + e.getMessage());
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("leitura:" + e.getMessage());
        } //catch

    } //main

} //class TCPClient

class TaskThreadEx2 extends Thread {

    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;
    

    public TaskThreadEx2(Socket aClientSocket) {
        try {
            clientSocket = aClientSocket;
            in = new DataInputStream(clientSocket.getInputStream()); /* Configurar outputStream para ler do socket */

            out = new DataOutputStream(clientSocket.getOutputStream()); /* Configurar outputStream para escrita no socket */

            this.start();  /* inicializa a thread */

        } catch (IOException e) {
            System.out.println("Connection:" + e.getMessage());
        } //catch
    } //construtor

    /* metodo executado ao iniciar a thread - start() */
    /* Metodo executado quando a thread e iniciada */
    public void run() {
        Scanner scanner = new Scanner(System.in);

        try {
            while (true) {

                String entrada = scanner.nextLine();
                out.writeUTF(entrada);      	//é envia uma string para o servidor

                System.out.println("Informacao enviada.");
                String data = in.readUTF();
                System.out.println(data);

                if (data.equals("ACKSAIR")) {
                    in.close();
                    out.close();
                    clientSocket.close();   //finaliza a conexao
                    break;
                }

            } /* While */

        } catch (EOFException e) {
            System.out.println("EOF: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("leitura: " + e.getMessage());
        } //catch

    } //run

} //class

