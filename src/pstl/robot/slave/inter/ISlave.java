package pstl.robot.slave.inter;

import pstl.robot.server.reception.Requete;

public interface ISlave {
	public boolean move(Requete r);
}
