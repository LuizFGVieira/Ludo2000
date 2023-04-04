package Controler;

import Model.Jogo;
import view.JanelaPrincipal;
import java.io.IOException;

/**
 *
 * @author luizf
 */
public class Ludo {

    public static void main(String[] args) throws IOException {
        JanelaPrincipal janela = new JanelaPrincipal();
        Jogo jogo = new Jogo(janela);
    }
    
    public void inicia() throws IOException{
    	JanelaPrincipal janela = new JanelaPrincipal();
    	Jogo jogo = new Jogo(janela);
    }
}
