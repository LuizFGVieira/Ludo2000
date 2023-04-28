package Model;

import java.io.Serializable;
import java.util.Stack;

/**
 *
 * @author luizf
 */
public class Casa implements Serializable{
    public int x;
    public int y;
    public Stack<Jogador> ocupada;
    
    
    public Casa(int x, int y){
        ocupada = new Stack<>();
        this.x = x;
        this.y = y;
    }
}
