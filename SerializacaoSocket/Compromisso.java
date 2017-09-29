/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SerializacaoSocket;

import java.io.Serializable;

/**
 *
 * @author yudi
 */
public class Compromisso implements Serializable{
    
    private int idCliente;
    private String compromisso;
    private String data;
    private String hora;

    public Compromisso(int idCliente, String compromisso, String data, String hora) {
        this.idCliente = idCliente;
        this.compromisso = compromisso;
        this.data = data;
        this.hora = hora;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getCompromisso() {
        return compromisso;
    }

    public void setCompromisso(String compromisso) {
        this.compromisso = compromisso;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
     
    
}
