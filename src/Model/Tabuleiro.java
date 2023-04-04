package Model;

import java.util.ArrayList;

/**
 *
 * @author luizf
 */
public class Tabuleiro {
    public ArrayList<Casa> casas = new ArrayList<>();
    
    public Tabuleiro(){
         
        // Casas Iniciais Vermelhas (0 1 2 3)
        Casa c = new Casa(78, 68);
        casas.add(c);
        c = new Casa(133+5, 68);
        casas.add(c);
        c = new Casa(78, 138);
        casas.add(c);   
        c = new Casa(133+5, 138);
        casas.add(c);
        
        // Casas Iniciais Verdes (4 5 6 7)
        c = new Casa(488+10, 68);
        casas.add(c);     
        c = new Casa(433+5, 68);
        casas.add(c);      
        c = new Casa(488+10, 138);
        casas.add(c);     
        c = new Casa(433+5, 138);
        casas.add(c);
        
        // Tabuleiro Geral (8 até 59)
        c = new Casa(44+5, 238);
        casas.add(c);       
        c = new Casa(84+5, 238);
        casas.add(c);        
        c = new Casa(124+5, 238);
        casas.add(c);        
        c = new Casa(164+5, 238);
        casas.add(c);     
        c = new Casa(204+5, 238);
        casas.add(c);
        
        c = new Casa(244+5, 198);
        casas.add(c);       
        c = new Casa(244+5, 158);
        casas.add(c);  
        c = new Casa(244+5, 118);
        casas.add(c);        
        c = new Casa(244+5, 78);
        casas.add(c);        
        c = new Casa(244+5, 38);
        casas.add(c);
        
        c = new Casa(244+5, -2);
        casas.add(c);        
        c = new Casa(284+5, -2);
        casas.add(c);       
        c = new Casa(324+5, -2);
        casas.add(c); 
        
        c = new Casa(324+5, 38);
        casas.add(c);       
        c = new Casa(324+5, 78);
        casas.add(c);  
        c = new Casa(324+5, 118);
        casas.add(c);       
        c = new Casa(324+5, 158);
        casas.add(c);       
        c = new Casa(324+5, 198);
        casas.add(c); 
        
        c = new Casa(364+5, 238);
        casas.add(c);
        c = new Casa(404+5, 238);
        casas.add(c);
        c = new Casa(444+5, 238);
        casas.add(c);
        c = new Casa(484+5, 238);
        casas.add(c);
        c = new Casa(524+5, 238);
        casas.add(c);       
     
        c = new Casa(564+5, 238);
        casas.add(c);
        c = new Casa(564+5, 278);
        casas.add(c);
        c = new Casa(564+5, 318);
        casas.add(c); 
        
        c = new Casa(524+5, 318);
        casas.add(c); 
        c = new Casa(484+5, 318);
        casas.add(c);       
        c = new Casa(444+5, 318);
        casas.add(c);
        c = new Casa(404+5, 318);
        casas.add(c);
        c = new Casa(364+5, 318);
        casas.add(c);
        
        c = new Casa(324+5, 358);
        casas.add(c);  
        c = new Casa(324+5, 398);
        casas.add(c);   
        c = new Casa(324+5, 438);
        casas.add(c);      
        c = new Casa(324+5, 478);
        casas.add(c);    
        c = new Casa(324+5, 518);
        casas.add(c);
        
        c = new Casa(324+5, 558);
        casas.add(c); 
        c = new Casa(284+5, 558);
        casas.add(c);
        c = new Casa(244+5, 558);
        casas.add(c);
        
        c = new Casa(244+5, 518);
        casas.add(c);
        c = new Casa(244+5, 478);
        casas.add(c);
        c = new Casa(244+5, 438);
        casas.add(c);
        c = new Casa(244+5, 398);
        casas.add(c);
        c = new Casa(244+5, 358);
        casas.add(c);
        
        c = new Casa(204+5, 318);
        casas.add(c);
        c = new Casa(164+5, 318);
        casas.add(c);
        c = new Casa(124+5, 318);
        casas.add(c);
        c = new Casa(84+5, 318);
        casas.add(c);
        c = new Casa(44+5, 318);
        casas.add(c);
        
        c = new Casa(4+5, 318);
        casas.add(c);
        c = new Casa(4+5, 278);
        casas.add(c);
        c = new Casa(4+5, 238);
        casas.add(c);
        
        // Casas Finais Vermelhas (60 até 65)
        c = new Casa(44+5, 278);
        casas.add(c);
        c = new Casa(84+5, 278);
        casas.add(c);
        c = new Casa(124+5, 278);
        casas.add(c);
        c = new Casa(164+5, 278);
        casas.add(c);
        c = new Casa(204+5, 278);
        casas.add(c);
        c = new Casa(244+5, 278);
        casas.add(c);
        
        // Casas Finais Verdes (66 até 71)
        c = new Casa(284+5, 38);
        casas.add(c);
        c = new Casa(284+5, 78);
        casas.add(c);
        c = new Casa(284+5, 118);
        casas.add(c);
        c = new Casa(284+5, 158);
        casas.add(c);
        c = new Casa(284+5, 198);
        casas.add(c);
        c = new Casa(284+5, 238);
        casas.add(c);
        
    }

    
    
}
