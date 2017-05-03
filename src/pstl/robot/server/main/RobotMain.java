package pstl.robot.server.main;


import java.io.IOException;

import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.comm.UnsupportedCommOperationException;

import pstl.robot.server.reception.Receiver;
import pstl.robot.slave.transmission.ToPython;

public class RobotMain {

	public static void main(String[] args) throws InterruptedException,  IOException, NoSuchPortException, PortInUseException, UnsupportedCommOperationException {
			
			
			new Receiver(4000,new ToPython("essai.py"));
		
	}

}
