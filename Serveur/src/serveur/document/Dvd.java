package serveur.document;

import java.util.Timer;

import serveur.exceptions.EmpruntException;
import serveur.timerTask.FinReservation;



public class Dvd extends Document implements IDocument{
	
	private int interdiction = 0;

	public Dvd(int numero, String titre, int interdiction) {
		super(numero, titre);
		this.interdiction = interdiction;
		// TODO Auto-generated constructor stub
	}
	public Dvd(int numero, String titre) {
		super(numero, titre);
	
	}
	
	
	@Override
	public void reserver(Abonnee ab) throws EmpruntException { //La reservation s'effectue si le livre n'a pas deja ete reserve et est disponible a la bibliotheque
		synchronized(this) {
			if(this.interdiction > ab.getAge())
				throw new EmpruntException(ab,this);
			if(this.isReserve() || !this.isDisponible() || ab.isInterdisEmprunt()) //le livre est deja reserve ou n'est pas disponible
				throw new EmpruntException(ab,this);
			Timer timer = new Timer();
			this.setReserve(true);
			timer.schedule(new FinReservation(timer,this),10000); //reserve pendant 10 secondes
			this.setEmprunteur(ab);
			System.out.println("le document numero : " + this.getNumero() + ", intitule : " + this.getTitre()
			+ " est reserve pendant 2 heures par " + ab.getId());
		}
	}

	@Override
	public void emprunter(Abonnee ab) throws EmpruntException { //L'emprunt s'effecture si le livre est reserve a l'abonnee et si il est disponible
		
		synchronized(this) {
			if(!this.isDisponible()) //le livre n'est plus disponible ou la personne voulant l'emprunter n'a pas le bonne Id
				throw new EmpruntException(ab,this);
			if(this.isReserve() & this.getEmprunteur() != ab)
				throw new EmpruntException(ab,this);
			this.setDisponible(false);
			this.setReserve(true);
			System.out.println("le document numero : " + this.getNumero() + ", intitule : " + this.getTitre()
			+ " a bien ete emprunter par " + ab.getId());
		}
		
	}

	

}
