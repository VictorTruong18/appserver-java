package serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import serveur.bibliotheque.Bibliotheque;
import serveur.exceptions.EmpruntException;
import serveur.exceptions.NonRepertorieException;

public class ServiceEmprunt implements Runnable{
	
	// **** ressources partagees : la bibliotheque *******
	private static Bibliotheque bibliotheque;
	
	public static void setBibliotheque(Bibliotheque bibliotheque) {
		ServiceEmprunt.bibliotheque = bibliotheque;
	}
	
	// *************************************************************
	
	private final Socket client;
	private static int cpt = 1;
	private final int numero;

	ServiceEmprunt(Socket socket){
		this.client = socket;
		this.numero = cpt ++;
	}
	
	@Override
	public void run() {
		System.out.println("*********Connexion "+this.numero+" démarrée");
		
		try {
			BufferedReader in = new BufferedReader (new InputStreamReader(client.getInputStream ( )));
			PrintWriter out = new PrintWriter (client.getOutputStream ( ), true);
			out.println("Bienvenue ! Quel document souhaitez-vous emprunter ?");
			out.println(bibliotheque.toString());
			
			String choix_document = in.readLine();
			int choix = Integer.parseInt(choix_document);
			
			out.println("Donnez votre numero d'abonnee :");
			String numero_abonnee = in.readLine();
			int abonnee = Integer.parseInt(numero_abonnee);
			
			try {
				if(bibliotheque.serviceValidation(choix,abonnee)) {
					bibliotheque.emprunt(choix, bibliotheque.getAbonnee(abonnee));
					out.println("le document numero : " + choix + " a ete emprunte avec succes par " + abonnee );
				}
			} catch (EmpruntException | NonRepertorieException e) {
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
