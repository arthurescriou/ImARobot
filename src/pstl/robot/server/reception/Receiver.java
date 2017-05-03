package pstl.robot.server.reception;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.PatternSyntaxException;

import pstl.robot.slave.graphic.Grid;
import pstl.robot.slave.inter.ISlave;

public class Receiver {
	int port = 4000;
	ServerSocket serverSock;
	ISlave grille;

	public Receiver(int port, ISlave grille) {
		this.port = port;
		this.grille = grille;
		connect();
	}

	public Receiver(Grid grille) {
		this.grille = grille;
		connect();
	}

	private void connect() {
		Socket clientSock;
		BufferedReader br;
		while (true) {
			try {

				ServerSocket serverSock = new ServerSocket(port);
				System.out.println("Listenning to port " + port);

				clientSock = serverSock.accept();
				System.out.println("Connected to " + clientSock.getInetAddress() + ":" + port);

				br = new BufferedReader(new InputStreamReader(clientSock.getInputStream()));
				System.out.println();
				String line = br.readLine();
				System.out.println(line);
				Requete r = parseRequete(line);
				System.out.println(r);
				grille.move(r);

				br.close();
				serverSock.close();
				clientSock.close();
			} catch (Exception e) {
				e.printStackTrace();
				break;
			} finally {
				try {
					if (serverSock != null) {

						serverSock.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

		private Requete parseRequete(String req) {
		if (req.equals("DOWN")) {
			return Requete.DOWN;
		}
		if (req.equals("UP")) {
			return Requete.UP;
		}
		if (req.equals("RIGHT")) {
			return Requete.RIGHT;
		}
		if (req.equals("LEFT")) {
			return Requete.LEFT;
		}
		if (req.equals("STOP")) {
			return Requete.STOP;
		}
		try {
			String[] tokens = req.split(",");
			Double x = new Double(tokens[0]);
			Double y = new Double(tokens[1]);
			if (Math.abs(x) < 1 && Math.abs(y) < 1)
				return Requete.STOP;
			if (Math.abs(x) < Math.abs(y)) {
				if (y < -1)
					return Requete.LEFT;
				return Requete.RIGHT;
			}
			if (x < -1)
				return Requete.UP;
			return Requete.DOWN;

		} catch (PatternSyntaxException | NumberFormatException e) {
			throw new Error("WRONG REQUETE : " + req);
		}
	}
}
