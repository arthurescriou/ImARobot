package pstl.robot.slave.graphic;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import pstl.robot.server.reception.ReceiverThread;

public class GridPanel extends JFrame implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1252806592584920922L;

	private Grid grille;
	private Image dot;
	private int echelle;
	private JPanel pane;
	
	public JPanel paintDot(int x ,int y) {
		return new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -6242446013676010934L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(dot, 0+x*echelle, 0+y*echelle, null);
			}
		};
	}


	
	public GridPanel(int port) throws IOException{
		this.grille = new Grid();
		setSize(800, 800);
		echelle=800/grille.getTaille();
		setResizable(false);
		grille.observers.add(this);
		(new ReceiverThread(grille,port)).start();
		
		
		dot = ImageIO.read(new File("dot.jpg"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		setVisible(true);
		pane=paintDot(0, 0);
		this.add(pane);
		this.repaint();
	}
	
	public void refresh(){
		this.remove(pane);
		pane=paintDot(grille.getX(),grille.getY());

		this.add(pane);
		this.revalidate();
		this.repaint();
	}
	
	
	
	public static void main(String[] args) throws Exception {
		Grid grid = new Grid();
		grid.init();
		new GridPanel(4000);
		
	}

	@Override
	public void update(Observable o, Object arg) {
		refresh();
		
	}
}
