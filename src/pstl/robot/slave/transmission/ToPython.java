package pstl.robot.slave.transmission;

import java.io.IOException;

import pstl.robot.server.reception.Requete;
import pstl.robot.slave.inter.ISlave;

public class ToPython implements ISlave {
	private String python;
	
	public ToPython(String python) {
		super();
		this.python = python;
	}

	@Override
	public boolean move(Requete r) {
		switch (r) {
		case STOP:
			runPython('b');
			break;
			
		case RIGHT:
			runPython('d');
			break;
			
		case LEFT:
			runPython('e');
			break;
			
		case UP:
			runPython('t');
			break;
			
		case DOWN:
			runPython('g');
			break;
		default:
			break;
		}
		
		return true;
	}
	
	public void runPython(char c){ 
		System.out.println();
	    String[] cmd = {
	      "python",
	      python,
	      c+"",
	    };
	    try {
	    	Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
