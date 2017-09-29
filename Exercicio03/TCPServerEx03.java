package Exercicio03;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yudi
 */
public class TCPServerEx03 {

    public void comecar(GUIServer guiServer) {

        ArrayList<Socket> listaSockets = new ArrayList();

        int serverPort = guiServer.getPorta(); // porta do servidor
            /* cria um socket e mapeia a porta para aguardar conexao */

        ListenerThread listenerThread = new ListenerThread(listaSockets, serverPort, guiServer);

    } //main
} //class

/**
 * Classe TaskThread: Thread responsavel pela comunicacao Descricao: Rebebe um
 * socket, cria os objetos de leitura e escrita e aguarda msgs clientes
 */
class TaskThreadSvEx3 extends Thread {

    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;
    GUIServer gui;
    ArrayList<Socket> listaSockets;

    public TaskThreadSvEx3(Socket aClientSocket, GUIServer gui, ArrayList<Socket> listaSockets) {
        this.gui = gui;
        this.listaSockets = listaSockets;
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

        try {
            while (true) {

                String data = in.readUTF();   /* aguarda o envio de dados */

                gui.setText(data); /* Recebe dados */
                sendMsgsSockets(data);

            } /* While */

        } catch (EOFException e) {
            System.out.println("EOF: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("leitura: " + e.getMessage());
        } //catch

    } //run

    public void sendMsgsSockets(String msg) {
        try {
            for (int i = 0; i < this.listaSockets.size(); i++) {
                Socket iterator = listaSockets.get(i);
                DataOutputStream out = new DataOutputStream(iterator.getOutputStream()); /* Configurar outputStream para escrita no socket */
                out.writeUTF(msg);
            }
        } catch (Exception e) {
            System.out.println("Erro " + e);
        }

    }

} //class

class ListenerThread extends Thread {

    private ArrayList<Socket> listaSockets;
    private int serverPort;
    private GUIServer guiServer;

    public ListenerThread(ArrayList<Socket> listaSockets, int serverPort, GUIServer guiServer) {
        this.listaSockets = listaSockets;
        this.serverPort = serverPort;
        this.guiServer = guiServer;
        this.start();
    }

    public void run() {
        System.out.println("Servidor aguardando conexao ...");
        /* aguarda conexoes */
        Socket clientSocket;
        try {
            while (true) {
                clientSocket = new ServerSocket(serverPort).accept();
                listaSockets.add(clientSocket);

                System.out.println("Cliente conectado ... Criando thread ...");
                /* cria um thread para atender a conexao */
                TaskThreadSvEx3 c = new TaskThreadSvEx3(clientSocket, guiServer, listaSockets);
            }
        } catch (IOException ex) {
            Logger.getLogger(ListenerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
