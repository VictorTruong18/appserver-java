package serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import serveur.bibliotheque.Bibliotheque;
import serveur.exceptions.EmpruntException;
import serveur.exceptions.NonRepertorieException;

public class ServiceReservation implements Runnable{
	
	// **** ressources partagees : la bibliotheque *******
	private static Bibliotheque bibliotheque;
		
	public static void setBibliotheque(Bibliotheque bibliotheque) {
		ServiceReservation.bibliotheque = bibliotheque;
	}
		
	// *************************************************************
	private final Socket client;
	private static int cpt = 1;
	private final int numero;
	
	ServiceReservation(Socket socket){
		this.client = socket;
		this.numero = cpt ++;
	}
	
	@Override
	public void run() {
		System.out.println("*********Connexion "+this.numero+" démarrée");
		
		try {
			BufferedReader in = new BufferedReader (new InputStreamReader(client.getInputStream ( )));
			PrintWriter out = new PrintWriter (client.getOutputStream ( ), true);
			out.println("Bienvenue ! Quels documents souhaitez vous reserver ?");
			out.println(bibliotheque.toString());
			
			String choix_document = in.readLine();
			int choix = Integer.parseInt(choix_document);
			
			out.println("Donnez votre numero d'abonnee :");
			
			String numero_abonnee = in.readLine();
			int abonnee = Integer.parseInt(numero_abonnee);
			
			try {
				if(bibliotheque.serviceValidation(choix,abonnee)) {
				bibliotheque.reservation(choix, bibliotheque.getAbonnee(abonnee));
				out.println("le document numero : " + choix + " a ete reserve avec succes pendant 2 heures par l'abonne numero : " + abonnee );
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
