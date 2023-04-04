package view;

import java.awt.BorderLayout;
import Model.*;
//import view.Main;
import Controler.*;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.border.EmptyBorder;

//import View.Main;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.*;

public class menu_principal extends JDialog {

	private final JPanel contentPanel = new JPanel();
        private Services s = new Services();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)throws Exception {
		try {
			menu_principal dialog = new menu_principal();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		AudioAcerto y = new AudioAcerto(); // Chamando a classe aonde está o audio.
        y.AudioAcerto ();
		
	}

	/**
	 * Create the dialog.
	 */
	public menu_principal() throws UnknownHostException {
		String ip_adress = s.getLocalIP();
		JTextField IP = new javax.swing.JTextField();
		
		setResizable(false);//TRAVA O TAMANHO DA JANELA
		setBounds(10, 10, 1090, 740);
		setIconImage(Toolkit.getDefaultToolkit().getImage("Files/icone.png"));
		setTitle("Magic Ludo 2000");
		//getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		//contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		//--------------------------INICIO DO C----------------------------//
		
		JLabel OPRules = new JLabel("");
		OPRules.setBounds(290, 100, 0, 0);
		//OPRules.setBounds(290, 100, 500, 400);
		OPRules.setIcon(new ImageIcon("Files/Fundo3.png"));
		getContentPane().add(OPRules);
		
		JLabel Campo_IP = new JLabel("");
		Campo_IP.setBounds(310,300,0,0);
		//Campo_IP.setIcon(new ImageIcon("Files/Fundo3.png"));
		Campo_IP.setForeground(new Color(192, 62, 255));
		Campo_IP.setText("ENDEREÇO IP:  " + ip_adress);//TROQUE O IP AQUI!!!!!!!!!!!!!!!
		Campo_IP.setBorder(null);
		getContentPane().add(Campo_IP);
		
		IP.setBackground(new java.awt.Color(15, 0, 91));//ALTERAR COR
		IP.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
		IP.setForeground(new java.awt.Color(192, 62, 255));
		IP.setText("Insira o IP do jogo");
		IP.setBorder(null);
		IP.setDisabledTextColor(new java.awt.Color(204, 204, 204));
		IP.setBounds(310,300,0,0);
		getContentPane().add(IP);
		
		JLabel bt_cliente = new JLabel("");
		bt_cliente.setBounds(730, 200, 0, 0);
		bt_cliente.setIcon(new ImageIcon("Files/BT_cliente.png"));
		bt_cliente.setBorder(null);
		getContentPane().add(bt_cliente);
		
		JLabel bt_host = new JLabel("");
		bt_host.setBounds(350, 200, 0, 0);
		bt_host.setIcon(new ImageIcon("Files/BT_host.png"));
		bt_host.setBorder(null);
		getContentPane().add(bt_host);
		
		JLabel OPGame = new JLabel("");
		OPGame.setBounds(290, 100, 0, 0);
		//OPGame.setBounds(290, 100, 500, 400);
		OPGame.setIcon(new ImageIcon("Files/ABA_jogar.png"));
		getContentPane().add(OPGame);
		
		JLabel Fundo_I = new JLabel("");
		Fundo_I.setBounds(0, 0, 0, 0);
		Fundo_I.setLabelFor(Fundo_I);
		//Fundo.setIcon(new ImageIcon("Files/Fundo.png"));
		Fundo_I.setIcon(new ImageIcon("Files/Fundo_INATIVO.png"));
		getContentPane().add(Fundo_I);
		
		JLabel bt_rules = new JLabel("");
		bt_rules.setBounds(770, 370, 300, 100);
		bt_rules.setIcon(new ImageIcon("Files/BT_REGRAS.png"));
		bt_rules.setBorder(null);
		getContentPane().add(bt_rules);
		
		
		JLabel bt_jogar = new JLabel("");
		bt_jogar.setBounds(10, 370, 300, 100);
		bt_jogar.setIcon(new ImageIcon("Files/BT_Jogar.png"));
		bt_jogar.setBorder(null);
		getContentPane().add(bt_jogar);
		
		JLabel Fundo = new JLabel("");
		Fundo.setBounds(0, 0, 1080, 740);
		Fundo.setLabelFor(Fundo);
		//Fundo.setIcon(new ImageIcon("Files/Fundo.png"));
		Fundo.setIcon(new ImageIcon("Files/Fundo.png"));
		getContentPane().add(Fundo);
		
		
			
		//---------------------------------------------------------------------
		//---------------------------------------------------------------------
		//------------------------FOLHA DE EVENTOS-----------------------------
		//---------------------------------------------------------------------
		//---------------------------------------------------------------------
		
		
		//EVENTOS DO BOTAO DE JOGAR
		bt_jogar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
				OPGame.setBounds(290, 100, 500, 400);
				Fundo_I.setBounds(0, 0, 1080, 740);
				bt_cliente.setBounds(350, 200, 150, 50);
				bt_host.setBounds(580, 200, 150, 50);
				IP.setBounds(310,300,200,30);
				Campo_IP.setBounds(580,300,200,30);
				
				OPRules.setBounds(100, 100, 0, 0);
				
				
			}
			/*@Override
			public void mouseEntered(MouseEvent e) {//efeito hover em bt_jogar
				bt_jogar.setForeground(new Color(255,0,0));
				bt_jogar.setBackground(new Color(0,191,255));
			}
			@Override
			public void mouseExited(MouseEvent e) {//efeito hover em bt_jogar
				bt_jogar.setBackground(new Color(44,47,51));
				bt_jogar.setForeground(new Color(0,0,0));
			}*/
		});
		
		//EVENTOS DO FUNDO DE INATIVIDADE
				Fundo_I.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						
						OPGame.setBounds(290, 100, 0, 0);
						Fundo_I.setBounds(0, 0, 0, 0);
						bt_host.setBounds(350, 200, 0, 0);
						bt_cliente.setBounds(730, 200, 0, 0);
						IP.setBounds(310,300,0,0);
						IP.setText("Insira o IP do jogo");
						Campo_IP.setBounds(850, 200, 0, 0);
						
						OPRules.setBounds(100, 100, 0, 0);
						
						
					}
					
				});	
				//EVENTOS DO ABA DE OPÇOES DE JOGO
				OPGame.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						
						OPGame.setBounds(290, 100, 500, 400);
						Fundo_I.setBounds(0, 0, 1080, 740);
						
						if(IP.getText().equals("")) {
							IP.setText("Insira o IP do jogo");
						}
						if(IP.getText().equals("Insira o IP do jogo")) {
							IP.setText("Insira o IP do jogo");
						}
						
						if(IP.getText() != "Insira o IP do jogo" && IP.getText() != "") {
							//
						}
						
					}
					
				});
				
				//EVENTOS DO IP
				IP.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						
						
						if(IP.getText().equals("Insira o IP do jogo")) {
							IP.setText("");
						}
						else {
							//IP.setText("Insira o IP do jogo");
						}
						
						
					}
					
				});
				//EVENTOS DO BT_cliente
				bt_cliente.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						String vazio = "";
						String Placeholder = "Insira o IP do jogo";
						
						
						if(IP.getText().equals("Insira o IP do jogo") ) {
							JOptionPane.showMessageDialog(null, "ERROR 404 - Endereço IP não encontrado", "IP Invalido", JOptionPane.ERROR_MESSAGE);
						}//fim do IF
						
						else if(IP.getText().equals("")) {
							JOptionPane.showMessageDialog(null, "ERROR 404 - Endereço IP nullo ou inexistente", "IP nulo", JOptionPane.ERROR_MESSAGE);
						}//fim do IF
						
						else if(IP.getText() != vazio && IP.getText() != Placeholder){
                                                    try {
                                                        if(s.verificaIP(IP.getText())){
                                                            System.out.println("opa");
                                                            Ludo dialog = new Ludo();//instacia o quadro
                                                            try {
                                                                dialog.inicia();
                                                            } catch (IOException e1) {
                                                                // TODO Auto-generated catch block
                                                                e1.printStackTrace();
                                                            }
                                                        }else{
                                                            JOptionPane.showMessageDialog(null, "ERROR 404 - Endereço IP nullo ou inexistente", "IP nulo", JOptionPane.ERROR_MESSAGE);
                                                            
                                                        }
                                                    } catch (SocketException ex) {
                                                        Logger.getLogger(menu_principal.class.getName()).log(Level.SEVERE, null, ex);
                                                    }
							
						}//fim do IF
						
					}//fim do evento de clique 
					
				});
				//EVENTOS DO BT_Rules
				bt_rules.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						
						
						OPGame.setBounds(290, 100, 0, 0);
						Fundo_I.setBounds(0, 0, 0, 0);
						bt_host.setBounds(350, 200, 0, 0);
						bt_cliente.setBounds(730, 200, 0, 0);
						IP.setBounds(310,300,0,0);
						IP.setText("Insira o IP do jogo");
						Campo_IP.setBounds(730, 200, 0, 0);
						
						
						OPRules.setBounds(90, 30, 900, 650);
						Fundo_I.setBounds(0, 0, 1080, 740);
						
					}
					
				});
		
	}

}
