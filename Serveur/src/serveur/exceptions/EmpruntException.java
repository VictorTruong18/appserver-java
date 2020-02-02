package serveur.exceptions;

import serveur.document.Abonnee;
import serveur.document.IDocument;

public class EmpruntException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private Abonnee abonnee;
	private IDocument document;
	
	public EmpruntException(Abonnee abonnee, IDocument document) {
		this.abonnee = abonnee;
		this.document = document;
	}
	@Override
	public String toString() {
			return "Desole, le document numero : " + this.document.numero() + " n'a pas pu etre emprunter par l'abonne "
				+ "numero : " + this.abonnee.getId();

	}
	
}
