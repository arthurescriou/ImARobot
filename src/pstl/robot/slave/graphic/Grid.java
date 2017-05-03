package pstl.robot.slave.graphic;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import pstl.robot.server.reception.Requete;
import pstl.robot.slave.inter.ISlave;

public class Grid extends Observable implements ISlave{
	private int taille=20;
	private int[][] grille;
	private int x,y;
	public ArrayList<Observer> observers=new ArrayList<Observer>();
	
	
	public Grid(int taille) {
		super();
		this.taille = taille;
		grille=new int[taille][taille];
	}
	
	public Grid(){
		this(20);
	}
	
	public void init(){
		for (int i = 0; i < taille; i++) {
			for (int j = 0; j < taille; j++) {
				grille[i][j]=0;
			}
		}
		grille[0][0]=1;
		x=0;
		y=0;
	}
	

	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getTaille() {
		return taille;
	}

	public boolean move(Requete r){
		boolean res=moveR(r);
		for (Observer o : observers) {
			o.update(this, new Object());
		}
		return res;
	}
	
	private boolean moveR(Requete r){
		switch (r) {
		case DOWN:
			if(y!=taille-1){
				grille[x][y]=0;
				y++;
				grille[x][y]=1;
				return true;
			}
			return false;
		case RIGHT:
			if(x!=taille-1){
				grille[x][y]=0;
				x++;
				grille[x][y]=1;
				return true;
			}
			return false;
		case UP:
			if(y!=0){
				grille[x][y]=0;
				y--;
				grille[x][y]=1;
				return true;
			}
			return false;
		case LEFT:
			if(x!=0){
				grille[x][y]=0;
				x--;
				grille[x][y]=1;
				return true;
			}
			return false;

		default:
			return false;
		}
	}
	
	public int getXY(int x,int y){
		return grille[x][y];
	}
	

}
