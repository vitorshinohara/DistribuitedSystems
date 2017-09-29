package Exercicio04;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yudi
 */
public class TCPServerEx04 {

    public void comecar(GUIServer guiServer) {

        int serverPort = guiServer.getPorta(); // porta do servidor
            /* cria um socket e mapeia a porta para aguardar conexao */

        ListenerThread listenerThread = new ListenerThread(serverPort, guiServer);

    } //main
} //class

/**
 * Classe TaskThread: Thread responsavel pela comunicacao Descricao: Rebebe um
 * socket, cria os objetos de leitura e escrita e aguarda msgs clientes
 */
class TaskThreadSvEx4 extends Thread {

    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;
    GUIServer gui;

    public TaskThreadSvEx4(Socket aClientSocket, GUIServer gui) {
        this.gui = gui;
        try {
            this.clientSocket = aClientSocket;
            in = new DataInputStream(clientSocket.getInputStream()); /* Configurar outputStream para ler do socket */

            out = new DataOutputStream(clientSocket.getOutputStream()); /* Configurar outputStream para escrita no socket */

            this.start();  /* inicializa a thread */

        } catch (IOException e) {
            System.out.println("Connection:" + e.getMessage());
        } //catch
    } //construtor

    /* metodo executado ao iniciar a thread - start() */
    /* Metodo executado quando a thread e iniciada */
    @Override
    public void run() {

        try {
            while (true) {

                String dado = in.readUTF();   /* aguarda o envio de dados */

                gui.setText(dado); /* Recebe dados */


                out.writeUTF(dado);

                String[] partes = dado.split(" ");

                switch (partes[1]) {
                    case "TIME":
                        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                        Date time = new Date();
                        dado = "[Servidor]: Hora local: " + timeFormat.format(time);
                        out.writeUTF(dado);
                        gui.setText(dado);

                        break;

                    case "DATE":
                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        Date date = new Date();
                        dado = "[Servidor]: Data local: " + dateFormat.format(date);
                        out.writeUTF(dado);
                        gui.setText(dado);

                        break;

                    case "FILES":
                        File dir = new File(partes[2]);
                        File[] filesList = dir.listFiles();
                        int tam = filesList.length;
                        try {
                            for (File file : filesList) {
                                if (file.isFile()) {
                                    dado = file.getName() + "\n";
                                    out.writeUTF(dado);
                                    gui.setText(dado);
                                }

                            }
                        } catch (Exception e) {
                            System.out.println("Erro ao encontrar diretório");
                        }

                        break;

                    case "DOWN":
                        break;

                    case "EXIT":
                        out.writeUTF("/sair");
                        this.in.close();
                        this.out.close();
                        this.interrupt(); /* FECHA TUDO */

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

class ListenerThread extends Thread {

    private final int serverPort;
    private final GUIServer guiServer;

    public ListenerThread(int serverPort, GUIServer guiServer) {
        this.serverPort = serverPort;
        this.guiServer = guiServer;
        this.start();
    }

    @Override
    public void run() {
        /* aguarda conexoes */
        try {
            ServerSocket listenSocket = new ServerSocket(serverPort);
            while (true) {
                Socket clientSocket = new Socket(); /* Cria um novo socket para cada cliente */

                clientSocket = listenSocket.accept();

                /* cria um thread para atender a conexao */
                TaskThreadSvEx4 c = new TaskThreadSvEx4(clientSocket, guiServer);
            }
        } catch (IOException ex) {
            Logger.getLogger(ListenerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
