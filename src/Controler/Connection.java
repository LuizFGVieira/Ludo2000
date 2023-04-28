package Controler;

import Model.Jogador;
import Model.Mensagem;
import Model.Peao;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author luizf
 */
public class Connection implements Runnable{
    public boolean myTurn;
    private Socket socket;
    private ServerSocket serverSocket;
    private final int port = 5000;
    private InetAddress ip;
    private final Jogo jogo;
    private Thread hostThread;

    // Construtor
    public Connection(Jogo jogo) {
        this.jogo = jogo;
    }
    
    public void host(){
        Host h = new Host(this);
        this.hostThread = new Thread(h);
        hostThread.start();
    }
    
    public void initCon(){
        try {
            this.myTurn = true;
            this.ip = InetAddress.getLocalHost();
            this.serverSocket = new ServerSocket(port);
            System.out.println("Servidor TCP iniciado na porta " + port);
            this.socket = this.serverSocket.accept();
            
            jogo.setIsHost(true);
            jogo.gameConnection(this);
            
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            String outputLine = "Ola, bem-vindo ao servidor!";
            out.println(outputLine);
            this.serverSocket.close();
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Falha na conexão!");
        }
    }
    
    public void connect(){
        try {
            this.myTurn = false;
            this.socket = new Socket(ip, port);
            
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println(in.readLine());
            
            this.jogo.gameConnection(this);
            this.jogo.setIsHost(false);
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Falha na conexão!");
        }
    }

    public void enviarJogada(Mensagem msg){
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream()); // cria um stream de saída para o servidor    
            out.writeObject(msg);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
    public void receberJogada(){
        try {  
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream()); // cria um stream de entrada para o servidor
            Mensagem msg = (Mensagem) in.readObject(); // lê o objeto serializado enviado pelo servidor
            
            jogo.sincronizarJogos(msg);
            
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }
    
    @Override
    public void run() {
        while (true) {            
            try {
                receberJogada();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
    
    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }
    
}
