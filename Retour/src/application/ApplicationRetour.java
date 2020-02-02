package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ApplicationRetour {
	private final static int PORT_RETOUR = 2700;
	private final static String HOST = "localhost";  

	public static void main(String[] args) {
		Socket socket_retour = null;
		try {
			
				socket_retour = new Socket(HOST, PORT_RETOUR);
				BufferedReader sin = new BufferedReader (new InputStreamReader(socket_retour.getInputStream ( )));
				PrintWriter sout = new PrintWriter (socket_retour.getOutputStream ( ), true);
				
				BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));			
			
				
				System.out.println("Connection au serveur " + socket_retour.getInetAddress() + ":"+ socket_retour.getPort());
			

				String msg_accueil_serveur;
				msg_accueil_serveur = sin.readLine();
				System.out.println(msg_accueil_serveur);
				
				String liste_documents_serveur;
				liste_documents_serveur = sin.readLine();
				System.out.println(liste_documents_serveur);
				
				String choix_document_client;
				choix_document_client = clavier.readLine();
				sout.println(choix_document_client);
				
				String resultat_retour_serveur;
				resultat_retour_serveur = sin.readLine();
				System.out.println(resultat_retour_serveur);
				System.out.println("fin de la session utilisateur");
				System.out.println("");
			
		
		}
		catch (IOException e) { System.err.println(e); }
		// Refermer dans tous les cas la socket
		try { 
		if (socket_retour != null) socket_retour.close(); 
		} 
		catch (IOException e2) { ; }

	}

}
