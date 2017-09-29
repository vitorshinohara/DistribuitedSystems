package SerializacaoSocket;import java.net.*;import java.io.*;/** * Servidor TCP que recebe dois objetos serializados * * @author rodrigo */public class Servidor {    public static void main(String args[]) {        ServerSocket serverSocket;        Socket clientSocket;        ObjectInputStream objIn;        ObjectOutputStream objOut;        DataInputStream textIn;        try {            System.out.println("Mapeando porta ...");            serverSocket = new ServerSocket(6666);            System.out.println("Servidor aguardando conexões ...");            clientSocket = serverSocket.accept();            System.out.println("Criando objetos de leitura/escrita ...");                        objIn = new ObjectInputStream(clientSocket.getInputStream());            textIn = new DataInputStream(clientSocket.getInputStream());            while (true) {                if (textIn.readUTF().equals("Sair")) {                    System.out.println("SAIR RECEBIDO");                    break;                }                System.out.println("Aguardando objetos serializados ...");                Compromisso c1 = (Compromisso) objIn.readObject();                System.out.println("\nObjetos Recebidos\n");                System.out.println("Compromisso\n" + "Nome:" + c1.getCompromisso() + "\nData: " + c1.getData() + "\nHora: " + c1.getHora());            } /* While */                        System.out.println("\nSistema finalizado!");        } catch (Exception e) {            System.out.println(e);        } //catch    } //main}