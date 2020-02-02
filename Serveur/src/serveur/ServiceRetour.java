package serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import serveur.bibliotheque.Bibliotheque;
import serveur.exceptions.EmpruntException;
import serveur.exceptions.NonRepertorieException;
import serveur.exceptions.RetourException;

public class ServiceRetour implements Runnable {
	
	// **** ressources partagees : la bibliotheque *******
	private static Bibliotheque bibliotheque;
			
	public static void setBibliotheque(Bibliotheque bibliotheque) {
			ServiceRetour.bibliotheque = bibliotheque;
	}
			
	// *************************************************************
	private final Socket client;
	private static int cpt = 1;
	private final int numero;
	
	
	ServiceRetour(Socket socket){
		this.client = socket;
		this.numero = cpt ++;
	}
	
	@Override
	public void run() {
	System.out.println("*********Connexion "+this.numero+" démarrée");
		
		try {
			BufferedReader in = new BufferedReader (new InputStreamReader(client.getInputStream ( )));
			PrintWriter out = new PrintWriter (client.getOutputStream ( ), true);
			out.println("Bienvenue ! Quels documents souhaitez vous retourner ?");
			out.println(bibliotheque.toString());
			
			String choix_document = in.readLine();
			int choix = Integer.parseInt(choix_document);
				try {
					if(bibliotheque.serviceValidation(choix)) 
						bibliotheque.getDocument(choix).retour();
						out.println("Le document numero : " + choix + " a ete retourne avec succes");
					
				} catch (RetourException | NonRepertorieException e) {
					out.println(e);
					e.printStackTrace();
					
				}
			
		}
		catch (IOException e) {
		}
		System.out.println("*********Connexion "+this.numero+" terminée");
		try {client.close();} catch (IOException e2) {}
		
	}


}
