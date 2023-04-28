package Controler;

/**
 *
 * @author luizf
 */
public class Host implements Runnable{
    /**
     * Objeto da classe Connection
     */
    private Connection con;

    /**
     * Construtor da classe
     * 
     * @param con Connection
     */
    public Host(Connection con) {
        this.con = con;
    }

    @Override
    public void run() {
        con.initCon();
    }
}
