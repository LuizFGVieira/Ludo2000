package Model;

import java.util.ArrayList;

/**
 *
 * @author luizf
 */
public class Jogador {
    public int id;
    public String nome;
    public ArrayList<Peao> peao = new ArrayList<>(4);
    public ArrayList<Casa> caminho = new ArrayList();

    public Jogador(int id, String nome, Tabuleiro tabuleiro) {
        this.nome = nome;
        this.id = id;
        
        setCaminho(tabuleiro);
        
        for(int i = 0; i < 4; i++){
            peao.add(new Peao(i));
        }
        

    }
    
    public void setCaminho(Tabuleiro tabuleiro){
        if(id == 1){
            caminho.add(tabuleiro.casas.get(0));
            caminho.add(tabuleiro.casas.get(1));
            caminho.add(tabuleiro.casas.get(2));
            caminho.add(tabuleiro.casas.get(3));
            for(int i = 8; i<=58; i++){
                caminho.add(tabuleiro.casas.get(i));
            }
            
            for(int i = 60; i<=65; i++){
                caminho.add(tabuleiro.casas.get(i));
            }
            
        }else{
            caminho.add(tabuleiro.casas.get(4));
            caminho.add(tabuleiro.casas.get(5));
            caminho.add(tabuleiro.casas.get(6));
            caminho.add(tabuleiro.casas.get(7));
            
            for(int i = 21; i<=59; i++){
                caminho.add(tabuleiro.casas.get(i));
            }
            
            for(int i = 8; i<=19; i++){
                caminho.add(tabuleiro.casas.get(i));
            }
            
            for(int i = 66; i<=71; i++){
                caminho.add(tabuleiro.casas.get(i));
            }
            
        }
    }
    
    
}
