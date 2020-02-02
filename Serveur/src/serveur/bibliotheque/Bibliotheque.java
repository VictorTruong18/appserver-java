package serveur.bibliotheque;

import java.util.ArrayList;
import java.util.List;

import serveur.document.Abonnee;
import serveur.document.IDocument;
import serveur.exceptions.EmpruntException;
import serveur.exceptions.NonRepertorieException;
import serveur.exceptions.RetourException;


public class Bibliotheque {
	
		private List<IDocument> documents;
		private List<Abonnee> abonnees;
		
		public Bibliotheque() {
			documents = new ArrayList<IDocument>();
			abonnees = new ArrayList<Abonnee>();
		}

		
		public void ajouter_abonnee(Abonnee abonnee) {
			if(!abonnees.contains(abonnee))
				abonnees.add(abonnee);
		}

		
		public void ajouter_document(IDocument document) {
			if(!documents.contains(document))
				documents.add(document);
		}

		

	
		public void reservation(int numero, Abonnee abonnee) throws EmpruntException {
				this.getDocument(numero).reserver(abonnee);
		}

		
		public void emprunt(int numero, Abonnee abonnee) throws EmpruntException {
				this.getDocument(numero).emprunter(abonnee);
		}

	
		public void retour(int numero) throws RetourException {
				this.getDocument(numero).retour();
		}
		
		public Abonnee getAbonnee(int num_abonnee) {
			for(Abonnee ab : abonnees) {
				if(ab.getId() == num_abonnee) {
					return ab;
				}
			}
			return null;
		}
		
		public IDocument getDocument(int num_document) {
			for(IDocument doc : documents) {
				if(doc.numero() == num_document) {
					return doc ;
				}
			}
			return null;
		}
		
	
		public Boolean serviceValidation(int numero, int abonnee) throws NonRepertorieException{
			if(this.getDocument(numero) != null  & this.getAbonnee(abonnee) != null){
				return true;
			}
			else {
				throw new NonRepertorieException(numero,abonnee);
			}
		}
		
		public Boolean serviceValidation(int numero) throws NonRepertorieException{
			if(this.getDocument(numero) != null){
				return true;
			}
			else {
				throw new NonRepertorieException(numero);
			}
		}
		
		
		@Override
		public String toString() {
			String s = "";
			s += "Nos documents :";
			for(IDocument doc : documents) {
				s += doc.numero() + " ";
			}
			return s;
			
		}

}


