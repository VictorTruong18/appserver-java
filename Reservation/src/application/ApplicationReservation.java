package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ApplicationReservation {
	
	private final static int PORT_RESERVATION = 2600;
	private final static String HOST = "localhost"; 

	public static void main(String[] args) {
		Socket socket_reservation = null;
		
		try {
				
		
		socket_reservation = new Socket(HOST, PORT_RESERVATION);
		BufferedReader sin = new BufferedReader (new InputStreamReader(socket_reservation.getInputStream ( )));
		PrintWriter sout = new PrintWriter (socket_reservation.getOutputStream ( ), true);
					
		BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));			
		
		
		System.out.println("Connection au serveur " + socket_reservation.getInetAddress() + ":"+ socket_reservation.getPort());
		
		
		
		String msg_accueil_serveur; //output
		msg_accueil_serveur = sin.readLine();
		System.out.println(msg_accueil_serveur);
		
		
		String liste_documents_serveur;
		liste_documents_serveur = sin.readLine();
		System.out.println(liste_documents_serveur);
		
		String choix_document_client;
		choix_document_client = clavier.readLine();
		sout.println(choix_document_client);
		
		
		String num_abonnee_serveur;
		num_abonnee_serveur = sin.readLine();
		System.out.println(num_abonnee_serveur);
		
		
		String num_abonnee_client;
		num_abonnee_client = clavier.readLine();
		sout.println(num_abonnee_client);
		
		String resultat_reservation_serveur;
		resultat_reservation_serveur = sin.readLine();
		System.out.println(resultat_reservation_serveur);
		System.out.println("fin de la session utilisateur");
		System.out.println("");
				
	
		
		}
		catch (IOException e) { System.err.println(e); }
		// Refermer dans tous les cas la socket
		try { 
		if (socket_reservation != null) socket_reservation.close(); 
		
		} 
		catch (IOException e2) { ; }

	}

}
