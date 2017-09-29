/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;

/**
 *
 * @author yudi
 */
public class ListenerThread extends Thread {

    private final MulticastSocket mcSocket;
    private final ChatMulticastGUI chatGUI;

    public ListenerThread(ChatMulticastGUI chatGUI, MulticastSocket mcSocket) {
        this.mcSocket = mcSocket;
        this.chatGUI = chatGUI;
    }

    @Override
    public void run() {
        MulticastSocket s = null;
        while (true) {
            try {

                byte[] buffer = new byte[1000];
                DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
                mcSocket.receive(messageIn);

                chatGUI.mostrarMsg(new String(messageIn.getData()));

            } catch (SocketException e) {
                System.out.println("Socket: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("IO: " + e.getMessage());
            } finally {
                if (s != null) {
                    s.close(); //fecha o socket
                }
            }
        } //finally
    }

}
