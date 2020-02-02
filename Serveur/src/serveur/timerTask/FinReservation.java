package serveur.timerTask;

import java.util.Timer;
import java.util.TimerTask;

import serveur.document.Document;

public class FinReservation extends TimerTask {
	
	private Timer timer;
	private Document document;
	
	public FinReservation(Timer timer,Document document) {
		this.timer = timer;
		this.document = document;
	}

	@Override
	public void run() {
		document.setReserve(false);
		this.timer.cancel();
		
	}

}
