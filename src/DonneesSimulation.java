/**
 * Classe qui contient toutes les informations de la simulation : cartes, robots, incendies
 * Le constructeur prend en argument le nombre de lignes, colonnes et la taille des cases
 * @author Mahmoud Bentriou, Mathias Biehler, Cyril Dutrieux
 *
 */

import java.util.LinkedList;

public class DonneesSimulation {
	
	/* Données */
	private LinkedList<Robot> robots;
	private LinkedList<Incendie> incendies;
	private Carte carte;
	
	/* Constructeur, pas d'utilité de faire un constructeur de copie ici */
	public DonneesSimulation(int lignes, int colonnes, int taillecase) {
		/* Initialisation des listes */
		incendies = new LinkedList<Incendie>();
		robots = new LinkedList<Robot>();
		/* Création de la carte */
		this.carte = new Carte(lignes,colonnes,taillecase);
	}
	
	/* Méthodes pour la carte : un seul accesseur, on utilise ensuite les méthodes de Carte */	
	public Carte getCarte() {
		return this.carte;
	}
	
	/* Méthodes pour la liste de robots */
	public int getSizeRobot() {
		return this.robots.size();
	}
	
	public Robot getRobot(int numRobot) {
		return robots.get(numRobot);
	}
	
	public void ajouteRobot(Robot r) {
		this.robots.add(r);
	}
	
	/* Méthode pour la liste d'incendies */
	public int getSizeIncendie() {
		return this.incendies.size();
	}
	
	public Incendie getIncendie(int numIncendie) {
		return this.incendies.get(numIncendie);
	}
	
	public void ajouteIncendie(Incendie i) {
		this.incendies.add(i);
	}	
}
