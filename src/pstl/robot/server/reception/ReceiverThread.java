package pstl.robot.server.reception;

import pstl.robot.slave.inter.ISlave;

public class ReceiverThread extends Thread {
	private int port;
	private ISlave grille;
	public ReceiverThread(ISlave grille,int port) {
		super();
		this.grille=grille;
		this.port = port;
	}
	
	public void run(){
		new Receiver(port,grille);
	}
}
