/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yudi
 */
public class Listener extends Thread {

    GUIClient gui;
    DataOutputStream out;
    String msg;

    public Listener(GUIClient gui, DataOutputStream out) {
        this.gui = gui;
        this.out = out;
        start();
    }

    @Override
    public void run() {
        while (true) {
            msg = this.gui.getText();
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (!this.gui.getText().equals(msg)) {
                try {
                    out.writeUTF(this.gui.getText());
                } catch (IOException ex) {
                    Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

}
