package serveur.exceptions;



public class NonRepertorieException extends Exception{
	private static final long serialVersionUID = 1L;
	
	private int document;
	private int abonnee;
	
	public NonRepertorieException(int document,int abonnee) {
		this.document = document;
		this.abonnee = abonnee;
	}
	
	public NonRepertorieException(int document) {
		this.document = document;
	}
	@Override
	public String toString() {
		return "Desole, le document numero : " + this.document + " n'a pas ete reconnue " +
				"ou l'abonnee numero : " + this.abonnee + " n'est pas reference dans notre liste de membres";

	}
}
