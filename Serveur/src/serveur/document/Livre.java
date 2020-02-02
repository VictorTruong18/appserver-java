package serveur.document;

import java.util.List;
import java.util.Random;
import java.util.Timer;


import serveur.exceptions.EmpruntException;
import serveur.exceptions.RetourException;
import serveur.timerTask.FinLivreRendueATemps;
import serveur.timerTask.FinReservation;

public class Livre extends Document implements IDocument {
	
	private Boolean rendueATemps;
	private Boolean isAbimee;
	private List<Abonnee> abonneeAvertir;

	public Livre(int numero, String titre) {
		super(numero, titre);
		this.rendueATemps = true;
		this.isAbimee = false;
		this.abonneeAvertir = null;
		
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void reserver(Abonnee ab) throws EmpruntException { //La reservation s'effectue si le livre n'a pas deja ete reserve et est disponible a la bibliotheque
		synchronized(this) {
			if(!this.isDisponible()) {
				this.abonneeAvertir.add(ab);
			}
			if(this.isReserve() || !this.isDisponible() || ab.isInterdisEmprunt()) //le livre est deja reserve ou n'est pas disponible
				throw new EmpruntException(ab,this);
			Timer timer = new Timer();
			this.setReserve(true);
			timer.schedule(new FinReservation(timer,this),30000); //reserve pendant 10 secondes
			this.setEmprunteur(ab);
			System.out.println("le document numero : " + this.getNumero() + ", intitule : " + this.getTitre()
			+ " est reserve pendant 2 heures par " + ab.getId());
		}
	}
	
	@Override
	public void emprunter(Abonnee ab) throws EmpruntException { //L'emprunt s'effecture si le livre est reserve a l'abonnee et si il est disponible
		
		synchronized(this) {
			if(!this.isDisponible() || ab.isInterdisEmprunt()) //le livre n'est plus disponible ou la personne voulant l'emprunter n'a pas le bonne Id
				throw new EmpruntException(ab,this);
			if(this.isReserve() & this.getEmprunteur() != ab)
				throw new EmpruntException(ab,this);
			Timer timer = new Timer();
			timer.schedule(new FinLivreRendueATemps(timer,this),1000000); 
			this.setDisponible(false);
			this.setReserve(true);
			this.setEmprunteur(ab);
			System.out.println("le document numero : " + this.getNumero() + ", intitule : " + this.getTitre()
			+ " a bien ete emprunter par " + ab.getId());
		}
		
	}
	
	@Override
	public void retour() throws RetourException { //Le retour s'effectue si le livre n'etait plus disponible
		synchronized(this) {
			this.constatEtatDuLivre();
			if(this.isDisponible()) {
				throw new RetourException(this);
			}
			if(!this.rendueATemps || this.isAbimee) {
				System.out.println("le livre numero: " + this.getNumero() + " a ete rendue en retard ou a ete abimee");
				System.out.println("l'abonnee numero: " + this.getEmprunteur().getId() + " est temporairement interdit d'emprunt et de reservation");
				this.getEmprunteur().setInterdisEmprunt();
			}
			if(this.abonneeAvertir.isEmpty()) {
				sendAlertEmail();
			}
			this.setDisponible(true);
			this.setReserve(false);
			this.setEmprunteur(null);
			System.out.println("le document numero : " + this.getNumero() + ", intitule : " + this.getTitre()
					+ " a bien ete rendue nous vous remercions et vous souhaitons un joyeux noel" );
		}
	}

	public Boolean getRendueATemps() {
		return rendueATemps;
	}

	public void setRendueATemps(Boolean rendueATemps) {
		this.rendueATemps = rendueATemps;
	}
	
	public void constatEtatDuLivre() {
		Random rand = new Random();
		int nombreAleatoire = rand.nextInt(10 - 1 + 1);
    	
    		if(nombreAleatoire < 5) {
    			this.isAbimee = true;
    		}   	
    	
	}
	
	public void sendAlertEmail() {
		
	}


}
