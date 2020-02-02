package serveur;

import java.io.IOException;

import serveur.bibliotheque.Bibliotheque;
import serveur.document.Abonnee;
import serveur.document.Dvd;
import serveur.document.Livre;

public class ApplicationServeur {
	private final static int PORT_RESERVATION = 2500;
	private final static int PORT_EMPRUNT = 2600;
	private final static int PORT_RETOURS = 2700;
	
	public static void main(String[] args) throws Exception {
		Bibliotheque bibliotheque = new Bibliotheque();
		
		bibliotheque.ajouter_document(new Livre(1, "AppServJava"));
		bibliotheque.ajouter_document(new Livre(2, "AppWebJava"));
		bibliotheque.ajouter_document(new Livre(3, "AppRefJava"));
		for (int i = 1; i <= 20; i++) {
			bibliotheque.ajouter_abonnee(new Abonnee(i,i));;
		}
		bibliotheque.ajouter_document(new Dvd(11, "Django Unchained"));
		bibliotheque.ajouter_document(new Dvd(22, "Inglorious Basterds",16));
		bibliotheque.ajouter_document(new Dvd(33, "Taxi Driver",12));
		
		ServiceEmprunt.setBibliotheque(bibliotheque);
		ServiceReservation.setBibliotheque(bibliotheque);
		ServiceRetour.setBibliotheque(bibliotheque);
		
		try {
			new Thread(new Serveur(PORT_RESERVATION)).start();
			new Thread(new Serveur(PORT_EMPRUNT)).start();
			new Thread(new Serveur(PORT_RETOURS)).start();
			System.out.println("Serveur lance sur les ports " + PORT_RESERVATION + " , " + PORT_EMPRUNT + " , " + PORT_RETOURS);
		} catch(IOException e) {
			System.err.println("Pb lors de la création du serveur : " +  e);
		}
	}
}
