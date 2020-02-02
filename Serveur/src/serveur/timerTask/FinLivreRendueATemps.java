package serveur.timerTask;

import java.util.Timer;
import java.util.TimerTask;

import serveur.document.Livre;


public class FinLivreRendueATemps extends TimerTask{

	private Timer timer;
	private Livre livre;

	
	public FinLivreRendueATemps(Timer timer,Livre livre) {
		this.timer = timer;
		this.livre = livre;
	
	}


	@Override
	public void run() {
		this.livre.setRendueATemps(false);
		this.timer.cancel();
		
	}

}
