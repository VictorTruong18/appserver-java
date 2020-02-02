package serveur.exceptions;


import serveur.document.IDocument;

public class RetourException extends Exception {
	private static final long serialVersionUID = 1L;
	

	private IDocument document;
	
	public RetourException(IDocument document) {
		this.document = document;
	}
	@Override
	public String toString() {
		return "Desole, le document numero : " + this.document.numero() + " n'a pas pu etre retourner celui"
				+ "-ci doit encore etre disponible dans notre librairie";

	}
}
