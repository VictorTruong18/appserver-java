package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ApplicationEmprunt {
	private final static int PORT_EMPRUNT = 2500;
	private final static String HOST = "localhost"; 
	public static void main(String[] args) {
		Socket socket_emprunt = null;
		
		try {
		
		
				
				socket_emprunt = new Socket(HOST, PORT_EMPRUNT);
				BufferedReader sin = new BufferedReader (new InputStreamReader(socket_emprunt.getInputStream ( )));
				PrintWriter sout = new PrintWriter (socket_emprunt.getOutputStream ( ), true);
				
				BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));			
				
				System.out.println("Connection au serveur " + socket_emprunt.getInetAddress() + ":"+ socket_emprunt.getPort());
				
				
				String msg_accueil; //output
				msg_accueil = sin.readLine();
				System.out.println(msg_accueil);
				
				String liste_documents; //output
				liste_documents = sin.readLine();
				System.out.println(liste_documents);
				
				String choix_document; //input
				choix_document = clavier.readLine();
				sout.println(choix_document);
				
				String demande_numero_abonnee; //output
				demande_numero_abonnee = sin.readLine();
				System.out.println(demande_numero_abonnee);
				
				String num_abonnee; //input
				num_abonnee = clavier.readLine();
				sout.println(num_abonnee);
				
				String reponse_validation_emprunt; //output
				reponse_validation_emprunt = sin.readLine();
				
				System.out.println(reponse_validation_emprunt);
				System.out.println("fin de la session utilisateur");
				System.out.println("");
				
				
		}
		catch (IOException e) { System.err.println(e); }
		// Refermer dans tous les cas la socket
		try { if (socket_emprunt != null) socket_emprunt.close(); 
		
		} 
		catch (IOException e2) { ; }	
	
	}
}
