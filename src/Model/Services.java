package Model;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.apache.commons.validator.routines.InetAddressValidator;

public class Services {
	public Services() {
		// TODO Auto-generated constructor stub
	}
	
	public String getLocalIP() throws UnknownHostException {
		return InetAddress.getLocalHost().getHostAddress();
	}
	public boolean verificaIP(String IP) throws SocketException{
		InetAddressValidator validator = new InetAddressValidator();
		validator = InetAddressValidator.getInstance();
		return validator.isValidInet4Address(IP);
	}
}
