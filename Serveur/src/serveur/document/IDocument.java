package serveur.document;

import serveur.exceptions.EmpruntException;
import serveur.exceptions.RetourException;


public interface IDocument {
	int numero();
	void reserver(Abonnee ab) throws EmpruntException ;
	void emprunter(Abonnee ab) throws EmpruntException;
	void retour() throws RetourException;
}
