/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yudi
 */
public class Listener extends Thread {

    private GUI gui;
    private MulticastSocket s = null;

    public Listener(GUI gui) {
        this.gui = gui;

        try {
            InetAddress group = InetAddress.getByName("224.0.0.1");
            /* cria um socket multicast */
            this.s = new MulticastSocket(6789);
            /* adiciona o host ao grupo */
            this.s.joinGroup(group);
            System.out.println("Entrou no grupo");
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.start();

    }

    @Override
    public void run() {
        byte[] buffer = new byte[1000];
        System.out.println("buffer criado");
        /* aguarda o recebimento de msgs de outros peers */
        while (true) {
            try {
                System.out.println("Esperando msg");
                DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
                this.s.receive(messageIn);
                System.out.println("--------------------Msg recebida");
                int x = this.gui.getDescritor();
                this.gui.setTextJPane(new String(messageIn.getData()));
                this.gui.setDesrciptor(x);
            } catch (IOException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
