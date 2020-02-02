package serveur.document;

import java.util.Timer;

import serveur.exceptions.EmpruntException;

import serveur.exceptions.RetourException;
import serveur.timerTask.FinReservation;

public abstract class Document implements IDocument{
	private int numero;
	private String titre;
	private boolean isReserve;
	private boolean isDisponible;
	private Abonnee emprunteur;
	
	public Document(int numero, String titre) {
		this.setNumero(numero);
		this.setTitre(titre);
		this.setReserve(false);
		this.setDisponible(true);
		this.setEmprunteur(null);
	}
	
	@Override
	public int numero() {
		return this.getNumero();
	}

	@Override
	public void reserver(Abonnee ab) throws EmpruntException { //La reservation s'effectue si le livre n'a pas deja ete reserve et est disponible a la bibliotheque
		synchronized(this) {
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
			if(this.isDisponible())
				throw new RetourException(this);
			this.setDisponible(true);
			this.setReserve(false);
			this.setEmprunteur(null);
			System.out.println("le document numero : " + this.getNumero() + ", intitule : " + this.getTitre()
					+ " a bien ete rendue nous vous remercions et vous souhaitons un joyeux noel" );
		}
	}

	public boolean isReserve() {
		return isReserve;
	}

	public void setReserve(boolean isReserve) {
		this.isReserve = isReserve;
	}

	public boolean isDisponible() {
		return isDisponible;
	}

	public void setDisponible(boolean isDisponible) {
		this.isDisponible = isDisponible;
	}

	public Abonnee getEmprunteur() {
		return emprunteur;
	}

	public void setEmprunteur(Abonnee emprunteur) {
		this.emprunteur = emprunteur;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

}
