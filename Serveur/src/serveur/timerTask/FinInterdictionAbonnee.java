package serveur.timerTask;

import java.util.Timer;
import java.util.TimerTask;

import serveur.document.Abonnee;

public class FinInterdictionAbonnee extends TimerTask {
	
	private Timer timer;
	private Abonnee abonnee;
	
	public FinInterdictionAbonnee(Timer timer, Abonnee abonnee) {
		this.timer = timer;
		this.abonnee = abonnee;
	}

	
	@Override
	public void run() {
		this.abonnee.setInterdisEmprunt(false);
		this.timer.cancel();
		
	}
	

}
