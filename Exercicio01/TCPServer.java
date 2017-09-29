/** * TCPServer: Servidor para conexao TCP com Threads * Descricao: Recebe uma conexao, cria uma thread, recebe uma mensagem e finaliza a conexao */import java.net.*;import java.io.*;public class TCPServer {    public static void main (String args[]) {        try{            int serverPort = 7896; // porta do servidor            /* cria um socket e mapeia a porta para aguardar conexao */            ServerSocket listenSocket = new ServerSocket(serverPort);                        //while(true) {                System.out.println ("Servidor aguardando conexao ...");				/* aguarda conexoes */                Socket clientSocket = listenSocket.accept();                                System.out.println ("Cliente conectado ... Criando thread ...");                /* cria um thread para atender a conexao */                TaskThread c = new TaskThread(clientSocket);            //} //while                    } catch(IOException e) {	    System.out.println("Listen socket:"+e.getMessage());	} //catch    } //main} //class/** * Classe TaskThread: Thread responsavel pela comunicacao * Descricao: Rebebe um socket, cria os objetos de leitura e escrita e aguarda msgs clientes  */class TaskThread extends Thread {    DataInputStream in;    DataOutputStream out;    Socket clientSocket;        public TaskThread (Socket aClientSocket) {        try {            clientSocket = aClientSocket;            in = new DataInputStream( clientSocket.getInputStream()); /* Configurar outputStream para ler do socket */            out = new DataOutputStream( clientSocket.getOutputStream()); /* Configurar outputStream para escrita no socket */            this.start();  /* inicializa a thread */        } catch(IOException e) {	    System.out.println("Connection:"+e.getMessage());	} //catch    } //construtor        /* metodo executado ao iniciar a thread - start() */    /* Metodo executado quando a thread e iniciada */    public void run(){            try {                   while(true){                                     String data = in.readUTF();   /* aguarda o envio de dados */                    if (data.equals("SAIR")) {                                                try{                            out.writeUTF("ACKSAIR");                            System.out.println("SAIR ENVIADO");                            in.close(); out.close(); clientSocket.close();  /* finaliza a conexao com o cliente */                              }catch(Exception e){                            System.out.println("Erro");                        }                                                break;                    } // if                    System.out.println ("Cliente disse: " + data);                    out.writeUTF("RECEBIDO");                 } /* While */                            } catch (EOFException e){               System.out.println("EOF: "+e.getMessage());            } catch(IOException e) {               System.out.println("leitura: "+e.getMessage());            } //catch                } //run    } //class