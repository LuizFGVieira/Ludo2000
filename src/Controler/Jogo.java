package Controler;

import Model.Casa;
import Model.Jogador;
import Model.Mensagem;
import Model.Peao;
import Model.Tabuleiro;
import java.awt.Color;
import view.JanelaPrincipal;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author luizf
 */
public final class Jogo {
    public Tabuleiro tabuleiro = new Tabuleiro();
    private int fase; // 0 - rolar dado :: 1 - mexer peao
    private int valDado;
    private final Random dado = new Random();
    private final JanelaPrincipal janela;
    private final Jogador jogador1 = new Jogador(1, "Jogador 1 (Vermelho)", tabuleiro);
    private final Jogador jogador2 = new Jogador(2, "Jogador 2 (Verde)", tabuleiro);
    private boolean isHost;
    private Thread gameCon;
    private Connection connection;
    private final Mensagem msg = new Mensagem();
    private boolean isWinner = false;
    
    public Jogo(JanelaPrincipal j){
        
        this.janela = j;      
           
        if(isHost){
            iniciarJogador(jogador1);
            iniciarJogador(jogador2);
        }else{
            iniciarJogador(jogador2);
            iniciarJogador(jogador1);
        }
        
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
               
            }  
        });
        janela.passaVez.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){  
                if(fase != 0 && connection.myTurn){
                    desistir();
                }
            }
        });
        
        //iniciarTurno();
       
    }
    
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    public void iniciarJogador(Jogador jogador){  
        
        for (int i = 0; i < 4; i++) {
            voltarInicio(jogador.peao.get(i), jogador);
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
    
    public void voltarInicio(Peao peao, Jogador jogador){
        
        if(jogador.caminho.get(0).ocupada.isEmpty()){
            peao.casa = 0;
            jogador.caminho.get(0).ocupada.push(jogador);
            
        }else if(jogador.caminho.get(1).ocupada.isEmpty()){
            peao.casa = 1;
            jogador.caminho.get(1).ocupada.push(jogador);
            
        }else if(jogador.caminho.get(2).ocupada.isEmpty()){
            peao.casa = 2;
            jogador.caminho.get(2).ocupada.push(jogador);
            
        }else{
            peao.casa = 3;
            jogador.caminho.get(3).ocupada.push(jogador);
        }
        peao.saiu = false;
        this.msg.peaoCapturado = peao;
    }
    
    public void preverMovimento(Jogador jogador, Peao peao){
        if(peao.saiu){
            if(peao.casa + valDado > 60){
                janela.previsao.setLocation(jogador.caminho.get(peao.casa).x, jogador.caminho.get(peao.casa).y+12);
            }else{
                janela.previsao.setLocation(jogador.caminho.get(peao.casa+valDado).x, jogador.caminho.get(peao.casa+valDado).y+12);
            }
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
        
        if(msg.peaoCapturado != null){
            if(isHost){
                jogador1.caminho.get(jogador1.peao.get(msg.peaoCapturado.id).casa).ocupada.pop();
                jogador1.peao.get(msg.peaoCapturado.id).casa = msg.peaoCapturado.casa;
                jogador1.peao.get(msg.peaoCapturado.id).saiu = msg.peaoCapturado.saiu;
                jogador1.caminho.get(jogador1.peao.get(msg.peaoCapturado.id).casa).ocupada.push(jogador1);
            }else{
                jogador2.caminho.get(jogador2.peao.get(msg.peaoCapturado.id).casa).ocupada.pop();
                jogador2.peao.get(msg.peaoCapturado.id).casa = msg.peaoCapturado.casa;
                jogador2.peao.get(msg.peaoCapturado.id).saiu = msg.peaoCapturado.saiu;
                jogador2.caminho.get(jogador2.peao.get(msg.peaoCapturado.id).casa).ocupada.push(jogador2);
            }
            janela.registroLog.setText(janela.registroLog.getText()+"\n   - Peao do Oponente volta ao inicio");
        }
        
        if(msg.peaoMovido != null){
            if(isHost){
                jogador2.caminho.get(jogador2.peao.get(msg.peaoMovido.id).casa).ocupada.pop(); 
                this.jogador2.peao.get(msg.peaoMovido.id).chegou = msg.peaoMovido.chegou;
                this.jogador2.peao.get(msg.peaoMovido.id).saiu = msg.peaoMovido.saiu;
                this.jogador2.peao.get(msg.peaoMovido.id).casa = msg.peaoMovido.casa;
                if(this.jogador2.peao.get(msg.peaoMovido.id).chegou){
                    janela.remove(this.jogador2.peao.get(msg.peaoMovido.id));
                }else{
                    jogador2.caminho.get(jogador2.peao.get(msg.peaoMovido.id).casa).ocupada.push(jogador2);
                }
                

            }else{
                jogador1.caminho.get(jogador1.peao.get(msg.peaoMovido.id).casa).ocupada.pop();
                this.jogador1.peao.get(msg.peaoMovido.id).chegou = msg.peaoMovido.chegou;
                this.jogador1.peao.get(msg.peaoMovido.id).saiu = msg.peaoMovido.saiu;
                this.jogador1.peao.get(msg.peaoMovido.id).casa = msg.peaoMovido.casa;
                if(this.jogador1.peao.get(msg.peaoMovido.id).chegou){
                    janela.remove(this.jogador1.peao.get(msg.peaoMovido.id));
                    janela.registroLog.setText(janela.registroLog.getText()+"\n   - Peao Chegou");
                }else{
                    jogador1.caminho.get(jogador1.peao.get(msg.peaoMovido.id).casa).ocupada.push(jogador1);
                }
               
            }
            
            janela.registroLog.setText(janela.registroLog.getText()+"\n   - Peao movido: "+ msg.peaoMovido.id);
       
        }
        
        
        if(isHost){
            janela.registroLog.setText(janela.registroLog.getText()+"\n\n+ Jogador 1");
        }else{
            janela.registroLog.setText(janela.registroLog.getText()+"\n\n+ Jogador 2");
        }
        
        iniciarTurno();
        
        if(msg.gameOver){
            this.isWinner = msg.isWinner;
            gameOver();
        }
        
    }
    
    public void iniciarTurno(){
        this.fase = 0;
        for(int i = 0; i < 4; i++){
            janela.setPeaoPosition(jogador1.peao.get(i), jogador1);
        }
        
        for(int i = 0; i < 4; i++){
            janela.setPeaoPosition(jogador2.peao.get(i), jogador2);
        }
        
        if(isHost){
            janela.turn.setForeground(Color.red);
        }else{
            janela.turn.setForeground(Color.GREEN);
        }
        janela.turn.setText("Seu Turno!");
    }
    
    public void passarVez(){
        
        if(isHost){
            for(int i = 0; i < 4; i++){

                jogador1.peao.get(i).setIcon(janela.imgPeao.get(0));
            }
            
            janela.registroLog.setText(janela.registroLog.getText()+"\n\n+ Jogador 2");
            
            janela.turn.setForeground(Color.GREEN);
        }else{
            for(int i = 0; i < 4; i++){  
                jogador2.peao.get(i).setIcon(janela.imgPeao.get(2));
            }
            janela.registroLog.setText(janela.registroLog.getText()+"\n\n+ Jogador 1");
            janela.turn.setForeground(Color.red);
        }
        janela.turn.setText("Turno do Oponente");
         
        msg.valDado = this.valDado;
        connection.enviarJogada(this.msg);
        this.msg.resetarMsg();
        
        connection.setMyTurn(false);
        this.fase = 0;
        
    }
    
    public void rolarDado(){
        boolean movimentoPossivel = false;
        valDado = dado.nextInt(6)+1;
        janela.setDado(valDado);
        
        janela.registroLog.setText(janela.registroLog.getText()+"\n   - Dado rolado: "+valDado);
        
        this.fase = 1;
        
        if(isHost){
            for(int i = 0; i < 4; i++){
                if((valDado != 6 && jogador1.peao.get(i).saiu) || (valDado == 6)){
                    if(jogador1.peao.get(i).casa + valDado < 60){
                        movimentoPossivel = true;
                        jogador1.peao.get(i).setIcon(janela.imgPeao.get(1));
                    }      
                }
            }
            if(!movimentoPossivel){
                passarVez();
            }
        }else{
            for(int i = 0; i < 4; i++){
                if((valDado != 6 && jogador2.peao.get(i).saiu) || (valDado == 6)){
                    if(jogador2.peao.get(i).casa + valDado < 60){
                        movimentoPossivel = true;
                        jogador2.peao.get(i).setIcon(janela.imgPeao.get(3));
                    } 
                }
            }
            if(!movimentoPossivel){
                passarVez();
            }
        }
    }
    
    public boolean checarCasa(int casa, Jogador jogador){
        /*
        * É casa especial
        */
        if(casa == 12 || casa == 25 || casa == 38 || casa == 51){
            return true;
        }
        
        if(isHost){
            
            /*
            *   Caso 1: Casa ocupada pelo próprio jogador
            */
            if(jogador.caminho.get(casa).ocupada.peek() == jogador1){

                switch (jogador.caminho.get(casa).ocupada.size()) {
                    case 1:
                        for (int i = 0; i < 4; i++) {
                            if (jogador.peao.get(i).casa == casa) {
                                jogador.peao.get(i).setIcon(janela.imgPeaoVermelhoJunto.get(0));
                            }
                        }
                        break;
                    case 2:
                        for (int i = 0; i < 4; i++) {
                            if (jogador.peao.get(i).casa == casa) {
                                jogador.peao.get(i).setIcon(janela.imgPeaoVermelhoJunto.get(1));
                            }
                        }
                        break;
                    case 3:
                        for (int i = 0; i < 4; i++) {
                            if (jogador.peao.get(i).casa == casa) {
                                jogador.peao.get(i).setIcon(janela.imgPeaoVermelhoJunto.get(2));
                            }
                        }
                        break;
                    default:
                        break;
                }
                janela.repaint();
                janela.revalidate();
                return true;
            }
            
            /*
            *   Caso 2: Casa ocupada por um peão do oponente
            */
            else if(jogador.caminho.get(casa).ocupada.size() == 1){
                for(int i = 0; i < 4; i++){
                    if(jogador.caminho.get(casa) == jogador2.caminho.get(jogador2.peao.get(i).casa)){
                        jogador.caminho.get(casa).ocupada.pop();
                        voltarInicio(jogador2.peao.get(i), jogador2);
                        janela.setPeaoPosition(jogador2.peao.get(i), jogador2);
                        janela.registroLog.setText(janela.registroLog.getText()+"\n   - Peao do oponente volta ao início");
                    }
                }
                return true;
            }
            
            /*
            *   Caso 3: Casa ocupada por mais de um peão do oponente
            */
            else{return false;}
            
        }else{
            
            /*
            *   Caso 1: Casa ocupada pelo próprio jogador
            */
            if(jogador.caminho.get(casa).ocupada.peek() == jogador2){
                System.out.println(jogador.caminho.get(casa).ocupada.size());
                switch (jogador.caminho.get(casa).ocupada.size()) {
                    case 1:
                        for (int i = 0; i < 4; i++) {
                            if (jogador.peao.get(i).casa == casa) {
                                jogador.peao.get(i).setIcon(janela.imgPeaoVerdeJunto.get(0));
                            }
                        }                 
                        
                        break;
                    case 2:
                        for (int i = 0; i < 4; i++) {
                            if (jogador.peao.get(i).casa == casa) {
                                jogador.peao.get(i).setIcon(janela.imgPeaoVerdeJunto.get(1));
                            }
                        }
                        
                        break;
                    case 3:
                        for (int i = 0; i < 4; i++) {
                            if (jogador.peao.get(i).casa == casa) {
                                jogador.peao.get(i).setIcon(janela.imgPeaoVerdeJunto.get(2));
                            }
                        }

                        break;
                    default:
                        break;
                }
                
                janela.revalidate();
                janela.repaint();
                
                return true;
            
            }
            
            /*
            *   Caso 2: Casa ocupada por um peão do oponente
            */
            else if(jogador.caminho.get(casa).ocupada.size() == 1){
                for(int i = 0; i < 4; i++){
                    if(jogador.caminho.get(casa) == jogador1.caminho.get(jogador1.peao.get(i).casa)){
                        voltarInicio(jogador1.peao.get(i), jogador1);
                        janela.setPeaoPosition(jogador1.peao.get(i), jogador1);
                        janela.registroLog.setText(janela.registroLog.getText()+"\n   - Peao do oponente volta ao início");
                    }
                }
                return true;
            }
            
            /*
            *   Caso 3: Casa ocupada por mais de um peão do oponente
            */
            else{return false;}
        }
    }
    
    public void mexerPeao(Jogador jogador, Peao peao){
        System.out.println("Peao: "+peao.id);
        System.out.println("    Casa: "+peao.casa);
        System.out.println("    Saiu: "+peao.saiu);
        System.out.println("    Chegou: "+peao.chegou);
        System.out.println("Casa: "+jogador.caminho.get(peao.casa).ocupada.empty());
        /*
        *   Caso 1: Peão no início
        */
        if(peao.casa < 4 && valDado == 6){
            
            /*
            *   Caso 1.1: Primeira casa ocupada
            */
            if(!jogador.caminho.get(4).ocupada.isEmpty()){
                if(checarCasa(4, jogador)){
                    jogador.caminho.get(peao.casa).ocupada.pop();
                    peao.saiu = true;
                    peao.casa = 4;
                    jogador.caminho.get(peao.casa).ocupada.push(jogador);
                    this.msg.jogador = jogador;
                    this.msg.peaoMovido = peao;
                    janela.setPeaoPosition(peao, jogador);
                    janela.registroLog.setText(janela.registroLog.getText()+"\n   - Peao movido: "+ peao.id);
                    passarVez();
                }
            }
            
            /*
            *   Caso 1.2: Primeira casa desocupada
            */
            else{
                jogador.caminho.get(peao.casa).ocupada.pop();
                peao.saiu = true;
                peao.casa = 4;
                jogador.caminho.get(peao.casa).ocupada.push(jogador);
                this.msg.jogador = jogador;
                this.msg.peaoMovido = peao;
                janela.setPeaoPosition(peao, jogador);
                janela.registroLog.setText(janela.registroLog.getText()+"\n   - Peao movido: "+ peao.id);
                passarVez();
            }
            
        }
        
        /*
        *   Caso 2: Peão já está em jogo
        */
        else if(peao.saiu){
            
            /*
            *   Verificando se a peça vai chegar ao final
            */
            if(peao.casa + valDado < 60){

                jogador.caminho.get(peao.casa).ocupada.pop();
                peao.casa += valDado;

                /*
                *   Caso 2.1: Casa ocupada
                */
                if(!jogador.caminho.get(peao.casa).ocupada.isEmpty()){

                    if(checarCasa(peao.casa, jogador)){
                        jogador.caminho.get(peao.casa).ocupada.push(jogador);
                        System.out.println(jogador.caminho.get(peao.casa).ocupada.peek().nome);
                        this.msg.jogador = jogador;
                        this.msg.peaoMovido = peao;
                        janela.setPeaoPosition(peao, jogador);
                        janela.registroLog.setText(janela.registroLog.getText()+"\n   - Peao movido: "+ peao.id);
                        passarVez();
                    }else{
                        peao.casa -= valDado;
                        jogador.caminho.get(peao.casa).ocupada.push(jogador);
                    }
                }

                /*
                *   Caso 2.2: Casa desocupada
                */
                else{
                    jogador.caminho.get(peao.casa).ocupada.push(jogador);
                    this.msg.jogador = jogador;
                    this.msg.peaoMovido = peao;
                    janela.setPeaoPosition(peao, jogador);
                    janela.registroLog.setText(janela.registroLog.getText()+"\n   - Peao movido: "+ peao.id);
                    passarVez();
                }
                
            /*
            *   Peão chegou no final
            */  
            }else if(peao.casa + valDado == 60){
                jogador.caminho.get(peao.casa).ocupada.pop();
                peao.chegou = true;
                peao.saiu = false;
                janela.remove(peao);
                janela.revalidate();
                janela.repaint();
                janela.registroLog.setText(janela.registroLog.getText()+"\n   - Peao  Chegou");
                if(jogador.peao.get(0).chegou && jogador.peao.get(1).chegou
                        && jogador.peao.get(2).chegou && jogador.peao.get(3).chegou){
                    System.out.println("Acabou o jogo");
                    
                }else{
                    this.msg.jogador = jogador;
                    this.msg.peaoMovido = peao;
                    passarVez(); 
                }
            }

            
   
        }
        
          
    }
    
    public void gameOver(){
        if(isWinner){
            System.out.println("Você Ganhou!!!");
        }else{
            System.out.println("Você Perdeu!!!");
        }
    }
    
    public void desistir(){
        System.out.println("Desistir");
    }
    
    public void gameConnection(Connection con){
        this.gameCon = new Thread(con);
        gameCon.start();
    }

    public void setIsHost(boolean isHost) {
        this.isHost = isHost;
        if(isHost){
            janela.turn.setForeground(Color.red);
            janela.turn.setText("Seu Turno!");
        }else{
            janela.turn.setForeground(Color.GREEN);
            janela.turn.setText("Turno do Oponente");
        }
    } 

    public void setValDado(int valDado) {
        this.valDado = valDado;
    }
    
}
