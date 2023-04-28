package Controler;

import Model.Casa;
import Model.Jogador;
import Model.Mensagem;
import Model.Peao;
import Model.Tabuleiro;
import view.JanelaPrincipal;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author luizf
 */
public final class Jogo {
    public Tabuleiro tabuleiro = new Tabuleiro();
    //private Jogador vez;
    private int fase; // 0 - rolar dado :: 1 - mexer peao
    private int valDado;
    private final Random dado = new Random();
    private final JanelaPrincipal janela;
    private Jogador jogador1 = new Jogador(1, "Jogador 1 (Vermelho)", tabuleiro);
    private Jogador jogador2 = new Jogador(2, "Jogador 2 (Verde)", tabuleiro);
    private boolean isHost;
    private Thread gameCon;
    private Connection connection;
    private Peao peaoFocus;
    private Mensagem msg = new Mensagem();
    
    public Jogo(JanelaPrincipal j){
        
        this.janela = j;      
           
        iniciarJogador(jogador1);
        iniciarJogador(jogador2);
        
        janela.criaTabuleiro(); 
        
        janela.registroLog.setText("+ Jogador 1:");

        janela.dado.addMouseListener(new MouseAdapter(){  
            @Override
            public void mouseClicked(MouseEvent e){    
                if(connection.myTurn){
                    if(fase == 0){
                        rolarDado();
                    }                
                }
                
               /*if (fase == 0 && connection.myTurn){
                   if(vez.id == 1){
                       for(Peao peao : jogador1.peao){
                            peao.setIcon(janela.imgPeao.get(1));
                        }
                        janela.revalidate();
                        janela.repaint();
                    }else{
                       for(Peao peao : jogador2.peao){
                            peao.setIcon(janela.imgPeao.get(3));
                        }
                        janela.revalidate();
                        janela.repaint();
                    }
                   
                   fase = 1;
                   rolarDado();
               }*/  
            }  
        });
        janela.passaVez.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){  
                if(fase != 0 && connection.myTurn){
                    passarVez();
                }
            }
        });
        
        iniciarTurno();
    }
    
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    public void iniciarJogador(Jogador jogador){  
        
        for (int i = 0; i < 4; i++) {
            voltarInicio(jogador.peao.get(i), jogador.caminho, jogador);
            janela.criaPeao(jogador.peao.get(i), jogador.caminho.get(jogador.peao.get(i).casa), jogador.id);
            jogador.peao.get(i).addMouseListener(new MouseAdapter(){  
                 @Override
                 public void mouseClicked(MouseEvent e){  
                    if(isHost){
                        if(jogador == jogador1 && connection.myTurn && fase == 1){
                            if(janela.previsao.isVisible()){
                                janela.previsao.setVisible(false);
                            }
                            mexerPeao(jogador, (Peao) e.getSource());
                        }
                    }else{
                        if(jogador == jogador2 && connection.myTurn && fase == 1){
                            if(janela.previsao.isVisible()){
                                janela.previsao.setVisible(false);
                            }
                            mexerPeao(jogador, (Peao) e.getSource());
                        }
                    }
                 }  
                 
                 @Override
                 public void mouseEntered(MouseEvent e){
                    if(isHost){
                        if(jogador == jogador1 && connection.myTurn && fase == 1){
                            preverMovimento(jogador, (Peao) e.getSource());
                        }
                    }else{
                        if(jogador == jogador2 && connection.myTurn && fase == 1){
                            preverMovimento(jogador, (Peao) e.getSource());
                        }
                    }

                 }
                 
                 @Override
                 public void mouseExited(MouseEvent e){
                     if(janela.previsao.isVisible()){
                         janela.previsao.setVisible(false);
                     }
                 }
             });  
        }
    }
    
    public void voltarInicio(Peao peao, ArrayList<Casa> caminho, Jogador jogador){
        
        if(caminho.get(0).ocupada.isEmpty()){
            peao.casa = 0;
            caminho.get(0).ocupada.push(jogador);
            
        }else if(caminho.get(1).ocupada.isEmpty()){
            peao.casa = 1;
            caminho.get(1).ocupada.push(jogador);
            
        }else if(caminho.get(2).ocupada.isEmpty()){
            peao.casa = 2;
            caminho.get(2).ocupada.push(jogador);
            
        }else{
            peao.casa = 3;
            caminho.get(3).ocupada.push(jogador);
        }
        peao.saiu = false;
    }
    
    public void preverMovimento(Jogador jogador, Peao peao){
        if(peao.saiu){
            janela.previsao.setLocation(jogador.caminho.get(peao.casa+valDado).x, jogador.caminho.get(peao.casa+valDado).y+12);
            janela.previsao.setVisible(true);
        }else if(valDado == 6){
            janela.previsao.setLocation(jogador.caminho.get(4).x, jogador.caminho.get(4).y+12);
            janela.previsao.setVisible(true);
        }
        
    }
    
    public void sincronizarJogos(Mensagem msg){
        connection.setMyTurn(true);
        this.valDado = msg.valDado;
        janela.setDado(valDado);
        janela.registroLog.setText(janela.registroLog.getText()+"\n   - Dado rolado: "+valDado);
        
        if(msg.peaoMovido != null){
            if(isHost){
                
                this.jogador2.peao.get(msg.peaoMovido.id).saiu = msg.peaoMovido.saiu;
                this.jogador2.peao.get(msg.peaoMovido.id).casa = msg.peaoMovido.casa;

            }else{

                this.jogador1.peao.get(msg.peaoMovido.id).saiu = msg.peaoMovido.saiu;
                this.jogador1.peao.get(msg.peaoMovido.id).casa = msg.peaoMovido.casa;

                janela.setPeaoPosition(jogador1.peao.get(msg.peaoMovido.id), jogador1);
            }
  
            janela.registroLog.setText(janela.registroLog.getText()+"\n   - Peao movido: "+ msg.peaoMovido.id);
        }
        
        
        if(isHost){
            janela.registroLog.setText(janela.registroLog.getText()+"\n\n+ Jogador 1");
        }else{
            janela.registroLog.setText(janela.registroLog.getText()+"\n\n+ Jogador 2");
        }
        
        iniciarTurno();
        
    }
    
    public void iniciarTurno(){
        this.fase = 0;
        for(int i = 0; i < 4; i++){
            janela.setPeaoPosition(jogador1.peao.get(i), jogador1);
        }
        
        for(int i = 0; i < 4; i++){
            janela.setPeaoPosition(jogador2.peao.get(i), jogador2);
        }
    }
    
    public void passarVez(){
        
        if(isHost){
            janela.registroLog.setText(janela.registroLog.getText()+"\n\n+ Jogador 2");
        }else{
            janela.registroLog.setText(janela.registroLog.getText()+"\n\n+ Jogador 1");
        }
         
        msg.valDado = this.valDado;
        connection.enviarJogada(this.msg);
        this.msg.resetarMsg();
        
        connection.setMyTurn(false);
        this.fase = 0;
        
        /*connection.enviarJogada(peaoFocus, vez, valDado);
        if(connection.myTurn){
            janela.registroLog.setText(janela.registroLog.getText()+"\n   - Passou a vez");
            fase = 0;
            janela.setDado(7);
            if(vez.id == 1){
                for(Peao peao : jogador1.peao){
                    peao.setIcon(janela.imgPeao.get(0));
                }
                vez = jogador2;

            }else{
                for(Peao peao : jogador2.peao){
                    peao.setIcon(janela.imgPeao.get(2));
                }
                vez = jogador1;
            }
            janela.registroLog.setText(janela.registroLog.getText()+"\n\n"+vez.nome+" : ");
            janela.revalidate();
            janela.repaint();
            
        }
        peaoFocus = null;*/
    }
    
    public void rolarDado(){
        valDado = dado.nextInt(6)+1;
        janela.setDado(valDado);
        
        janela.registroLog.setText(janela.registroLog.getText()+"\n   - Dado rolado: "+valDado);
        
        this.fase = 1;
        /*if(valDado != 6){
            boolean peaoSolto = false;
            for(int i = 0; i<4; i++){
                if(vez.peao.get(i).saiu){
                    peaoSolto = true;
                    break;
                }
            }
            if(!peaoSolto){
                passarVez();
            }
        }*/
    }
    
    public int checarCasa(int casa){
        Jogador vez;
        if(isHost){
            vez = jogador1;
        }else{
            vez = jogador2;
        }
        
        if(vez.caminho.get(casa).ocupada.peek() != vez && vez.caminho.get(casa).ocupada.size() == 1){
            if(vez.id == 1){
                for(Peao peao: jogador2.peao){
                    if(jogador2.caminho.get(peao.casa) == vez.caminho.get(casa)){
                        jogador2.caminho.get(peao.casa).ocupada.pop();
                        voltarInicio(peao, jogador2.caminho, jogador2);
                        janela.setPeaoPosition(peao,jogador2);
                    }
                }
            }else{
                for(Peao peao: jogador1.peao){
                    if(jogador1.caminho.get(peao.casa) == vez.caminho.get(casa)){
                        jogador1.caminho.get(peao.casa).ocupada.pop();
                        voltarInicio(peao, jogador1.caminho, jogador1);
                        janela.setPeaoPosition(peao,jogador1);
                    }
                }
            }
            janela.registroLog.setText(janela.registroLog.getText()+"\n   - Peao do Oponente volta ao inicio");
            return 1;
        }else if(vez.caminho.get(casa).ocupada.peek() == vez){
            return 1;
        }else{
            return 0;
        }
    }
    
    
    public void mexerPeao(Jogador jogador, Peao peao){
        Jogador vez;
        if(isHost){
            vez = jogador1;
        }else{
            vez = jogador2;
        }
        
        // Peão clicado não saiu do inicio e dado = 6
        if(peao.casa < 4 && valDado == 6){
            jogador.caminho.get(peao.casa).ocupada.pop();
            peao.casa = 4;   
            
            if(!vez.caminho.get(peao.casa).ocupada.isEmpty()){
                if(checarCasa(peao.casa) == 1){
                    janela.registroLog.setText(janela.registroLog.getText()+"\n   - Peao movido: "+ peao.id);
                    peao.saiu = true;
                    jogador.caminho.get(peao.casa).ocupada.push(jogador);
                    janela.setPeaoPosition(peao, jogador);
                    
                    this.msg.jogador = jogador;
                    this.msg.peaoMovido = peao;
                    //janela.setMarcador(jogador.caminho.get(peao.casa));
                    passarVez(); 
                    
                }else{
                    voltarInicio(peao, jogador.caminho, jogador);
                }
            }else{
                janela.registroLog.setText(janela.registroLog.getText()+"\n   - Peao movido: "+ peao.id);
                peao.saiu = true;
                jogador.caminho.get(peao.casa).ocupada.push(jogador);
                janela.setPeaoPosition(peao, jogador);
                
                this.msg.jogador = jogador;
                this.msg.peaoMovido = peao;
                
                passarVez();
            }   
        }else if(peao.saiu){
            jogador.caminho.get(peao.casa).ocupada.pop();
            peao.casa += valDado; 
            if(!vez.caminho.get(peao.casa).ocupada.isEmpty()){
                if(checarCasa(peao.casa) == 1){
                    janela.registroLog.setText(janela.registroLog.getText()+"\n   - Peao movido: "+ peao.id);
                    jogador.caminho.get(peao.casa).ocupada.push(jogador);
                    janela.setPeaoPosition(peao, jogador);
                    
                    this.msg.jogador = jogador;
                    this.msg.peaoMovido = peao;
                    
                    //janela.setMarcador(jogador.caminho.get(peao.casa));
                    passarVez();                   
                }else{
                    peao.casa -= valDado;
                }
                
            }else{
                janela.registroLog.setText(janela.registroLog.getText()+"\n   - Peao movido: "+ peao.id);
                jogador.caminho.get(peao.casa).ocupada.push(jogador);
                janela.setPeaoPosition(peao, jogador);
                
                this.msg.jogador = jogador;
                this.msg.peaoMovido = peao;
                
                passarVez();
            }
            
        }else if(valDado != 6){
            JOptionPane.showMessageDialog(null,"Você tem que rolar 6 para mexer este peão!","Regras",0);
            return;
        }
          
    }
    
    public void gameConnection(Connection con){
        this.gameCon = new Thread(con);
        gameCon.start();
    }

    public void setIsHost(boolean isHost) {
        this.isHost = isHost;
    } 

    public void setValDado(int valDado) {
        this.valDado = valDado;
    }
    
}
