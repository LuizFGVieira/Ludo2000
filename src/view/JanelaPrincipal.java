package view;

import Model.Casa;
import Model.Jogador;
import Model.Peao;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author luizf
 */
public class JanelaPrincipal extends JFrame{
    private final ArrayList<ImageIcon> imgDado = new ArrayList<>(7);
    public final ArrayList<ImageIcon> imgPeao = new ArrayList<>(4);
    public final JLabel dado;
    public JPanel registro;
    public JPanel jogo;
    public JTextArea registroLog;
    public JPanel previsao;
    public ArrayList<JLabel> marcador = new ArrayList<>();
    public JButton passaVez;
    public JLabel turn;
    
    public JanelaPrincipal() throws IOException{
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1020, 675);
        setVisible(true);
        setResizable(false);
        setBackground(Color.yellow);
        setIconImage(Toolkit.getDefaultToolkit().getImage("Files/icone.png"));
        setTitle("Magic Ludo 2000");
        
        criaMarcador();
        criaIcones();
        
        dado = new JLabel(imgDado.get(6));
        dado.setBounds(275, 275, 60, 60);
        
        add(dado);   
        
        registro = new JPanel();
        registro.setBounds(605, 65, 400, 600);
        
        registroLog = new JTextArea(20,25);     
        registroLog.setBounds(605, 0, 0, 0);
        registroLog.setEditable(false);
        registroLog.setMargin(new Insets(0,20,20,20));
        registroLog.setFont(new Font("Sans-Serif", Font.PLAIN, 16));
        registroLog.setLineWrap(true);
        
        passaVez = new JButton("Passar a Vez");
        passaVez.setMargin(new Insets(15,100,15,100)); 
        
        JScrollPane scrollPane = new JScrollPane(registroLog);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        registro.add(scrollPane);
        registro.add(passaVez);
        add(registro);   
        
        previsao = new JPanel();
        previsao.setBounds(0, 0, 30, 30);
        previsao.setVisible(false);
        previsao.setBackground(Color.MAGENTA);
        add(previsao);
        
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);      
        
        JMenu recomeco = new JMenu();
        recomeco.setText("Recomeçar");
        menuBar.add(recomeco);
        
        JMenu regras = new JMenu();
        regras.setText("Regras");  
        regras.setLocation(100, 0);
        menuBar.add(regras);
        
        regras.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                JOptionPane.showMessageDialog(null, """
                                                    Resumo
                                                    
                                                    1\u00ba: Role o dado no centro do tabuleiro;
                                                    2\u00ba: Caso role 6, pode sair com um pe\u00e3o, caso contr\u00e1rio mova um pe\u00e3o que ja tenha saido
                                                    3\u00ba: Se cair em uma casa ocupada pelo oponente, o pe\u00e3o do oponente volta ao inicio
                                                    
                                                    Objetivo: Dar a volta no tabuleiro com seus quatro pe\u00f5es""",
                        "Regras", 
                        WIDTH);
            }      
        });
        
        
        turn = new JLabel();
        turn.setFont(new Font("Sans-Serif", Font.PLAIN, 22));
        turn.setBounds(615, 5, 300, 50);
        add(turn);
    }
    
    public void setMarcador(Casa casa){
        if(casa.ocupada.size()>1){
            for(JLabel m : marcador){
                if(!m.isVisible()){
                    System.out.println("\n Entrou Aqui !!");
                    m.setVisible(true);
                    m.setLocation(casa.x+10, casa.y+10);
                    setComponentZOrder(m, getComponentCount()-getComponentCount());
                    m.setText(""+casa.ocupada.size());
                } 
            }    
        }
        
    }
    
    private void criaMarcador(){
        for(int i = 0; i < 4; i++){
            marcador.add(new JLabel());
            marcador.get(i).setText("2");
            marcador.get(i).setForeground(Color.WHITE);
            marcador.get(i).setFont(new Font("Sans-Serif", Font.BOLD, 12));
            marcador.get(i).setVisible(false);
            marcador.get(i).setBounds(0, 0, 30, 30);
            add(marcador.get(i));
        }
    }
    
    private void criaIcones(){
        BufferedImage img;
        
        try {
            img = ImageIO.read(new File("Files/dado1.png"));   
            imgDado.add(new ImageIcon(img));
            img = ImageIO.read(new File("Files/dado2.png"));   
            imgDado.add(new ImageIcon(img));
            img = ImageIO.read(new File("Files/dado3.png"));   
            imgDado.add(new ImageIcon(img));
            img = ImageIO.read(new File("Files/dado4.png"));   
            imgDado.add(new ImageIcon(img));
            img = ImageIO.read(new File("Files/dado5.png"));   
            imgDado.add(new ImageIcon(img));
            img = ImageIO.read(new File("Files/dado6.png"));   
            imgDado.add(new ImageIcon(img));
            img = ImageIO.read(new File("Files/dado7.png"));   
            imgDado.add(new ImageIcon(img));
            img = ImageIO.read(new File("Files/peaoVermelho.png"));   
            imgPeao.add(new ImageIcon(img));
            img = ImageIO.read(new File("Files/peaoVermelhoFocus.png"));   
            imgPeao.add(new ImageIcon(img));
            img = ImageIO.read(new File("Files/peaoVerde.png"));   
            imgPeao.add(new ImageIcon(img));
            img = ImageIO.read(new File("Files/peaoVerdeFocus.png"));   
            imgPeao.add(new ImageIcon(img));
        } catch (IOException e) {
        }
    }
    
    public void setDado(int valDado){
        dado.setIcon(imgDado.get(valDado-1));
        revalidate();
        repaint();
    }
    
    public void criaPeao(Peao peao, Casa casa, int jogador){

        if(jogador == 1){
            peao.setIcon(imgPeao.get(0));
        }else{
            peao.setIcon(imgPeao.get(2));
        }
        
        peao.setBounds(casa.x , casa.y, 34, 49);
        peao.setLayout(null);
        add(peao);
    }
    
    public void criaTabuleiro(){
        BufferedImage img = null;
        
        try {
            img = ImageIO.read(new File("Files/tabuleiro.jpg"));
        } catch (IOException e) {
        }
        
        ImageIcon imageIcon = new ImageIcon(img);
        JLabel tabuleiro = new JLabel();
        
        tabuleiro.setBounds(5, 5, 600, 600);
        tabuleiro.setIcon(imageIcon);
        
        jogo = new JPanel();
        jogo.setLayout(null);
        jogo.setBounds(20, 5, 600, 600);
        add(jogo);
        
        jogo.add(tabuleiro);
        revalidate();
        repaint();
    }
    
    public void setPeaoPosition(Peao peao, Jogador jogador){
        peao.setLocation(jogador.caminho.get(peao.casa).x, jogador.caminho.get(peao.casa).y);
        revalidate();
        repaint();
    }
    
}
