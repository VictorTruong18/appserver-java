package serveur.document;

import java.util.Timer;

import serveur.timerTask.FinInterdictionAbonnee;

public class Abonnee {
	private int id;
	private int age;
	private boolean interdisEmprunt;

	public Abonnee(int id, int age) {
		this.id = id;
		this.age = age;
		this.setInterdisEmprunt(false);
	}
	
	public int getId() {
		return id;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setInterdisEmprunt() {
		this.interdisEmprunt = true;
		Timer timer = new Timer();
		timer.schedule(new FinInterdictionAbonnee(timer, this),20000);
	}

	public boolean isInterdisEmprunt() {
		return interdisEmprunt;
	}

	public void setInterdisEmprunt(boolean interdisEmprunt) {
		this.interdisEmprunt = interdisEmprunt;
	}
	
	

	
}
