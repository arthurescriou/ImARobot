package pstl.robot.slave.transmission;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;








import java.util.Enumeration;

import javax.comm.CommPort;
import javax.comm.CommPortIdentifier;
import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.UnsupportedCommOperationException;

import pstl.robot.server.reception.Requete;
import pstl.robot.slave.inter.ISlave;

public class ToNucleo implements ISlave {
	private InputStream in;
	private OutputStream out;

	public void connect(String portName) throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException, IOException {
		CommPortIdentifier portId=null;
		 Enumeration portIdentifiers = CommPortIdentifier.getPortIdentifiers();
		 while (portIdentifiers.hasMoreElements())
		 {
		     CommPortIdentifier pid = (CommPortIdentifier) portIdentifiers.nextElement();
		     System.out.println( "port : " + pid.getName());
			if(pid.getPortType() == CommPortIdentifier.PORT_SERIAL &&
		        pid.getName().equals(portName)) 
		     {
				portId = pid;
		         break;
		     }
		 }
		
		 
		
		CommPortIdentifier portIdentifier = portId;
		
		if (portIdentifier.isCurrentlyOwned()) {
			System.out.println("Error: Port is currently in use");
		} else {
			int timeout = 2000;
			CommPort commPort = portIdentifier.open(this.getClass().getName(), timeout);
			if (commPort instanceof SerialPort) {
				SerialPort serialPort = (SerialPort) commPort;
				serialPort.setSerialPortParams(57600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
						SerialPort.PARITY_NONE);

				in = serialPort.getInputStream();
				out = serialPort.getOutputStream();

			}
		}
	}

	public void write(char c) {
		try {
			out.write((int) c);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int read() {
		try {
			return in.read();
		} catch (IOException e) {
			throw new Error("mauvaise lecture " + e.getMessage());
		}
	}

	@Override
	public boolean move(Requete r) {
		switch (r) {
		case STOP:
			write('c');
			break;

		default:
			break;
		}
		return false;
	}

	public static void main(String[] args) throws InterruptedException, NoSuchPortException, PortInUseException, UnsupportedCommOperationException, IOException {
		ToNucleo tonu=new ToNucleo();
		tonu.connect("/dev/ttyACM0");
		tonu.write('e');
		Thread.sleep(200);
		tonu.write('b');
	}
}
