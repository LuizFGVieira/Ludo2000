package Model;

import java.io.Serializable;

/**
 *
 * @author luizf
 */
public class Mensagem implements Serializable{
    public Peao peaoMovido;
    public Peao peaoCapturado;
    public Jogador jogador;
    public int valDado;

    public Mensagem() {
        this.jogador = null;
        this.peaoCapturado = null;
        this.peaoMovido = null;
        this.valDado = 0;
    }
    
    public void resetarMsg(){
        this.jogador = null;
        this.peaoCapturado = null;
        this.peaoMovido = null;
        this.valDado = 0;
    }
    
}
