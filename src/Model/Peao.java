package Model;

import java.io.Serializable;
import javax.swing.JLabel;

/** reponsavel por armazenar a posição do peão
 *  além de conferir se o peão ja saiu do inicio ou não
 * @author luizf
 */
public class Peao extends JLabel implements Serializable{
    public int casa;
    public int id;
    public boolean saiu = false;
    public boolean chegou = false;
    
    public Peao(int i){
        id = i;
    }
}
