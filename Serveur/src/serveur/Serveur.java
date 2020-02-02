package serveur;

import java.io.IOException;
import java.net.ServerSocket;

import serveur.bibliotheque.Bibliotheque;

public class Serveur implements Runnable{
	
	private ServerSocket aServerSocket;
	private int portId;
	
	
	Serveur(int port) throws IOException {
		portId = port;
		aServerSocket = new ServerSocket(port);
		
	}

	@Override
	public void run() {
		try {
			while(true) {
				switch(this.portId) {
					case 2500:
						new Thread(new ServiceEmprunt(aServerSocket.accept())).start();
						System.out.println("connexion 2500");
						
						break;
					case 2600:
						new Thread(new ServiceReservation(aServerSocket.accept())).start();
						System.out.println("connexion 2600");
						break;
					case 2700:
						new Thread(new ServiceRetour(aServerSocket.accept())).start();
						System.out.println("connexion 2700");
						break;
				}	
			}
		}
		catch (IOException e) { 
			try {this.aServerSocket.close();} catch (IOException e1) {}
			System.err.println("Pb sur le port d'ecoute :"+e);
		
		}
	}
}
