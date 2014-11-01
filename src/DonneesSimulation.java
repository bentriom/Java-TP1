/**
 * Classe qui contient toutes les informations de la simulation : cartes, robots, incendies
 * Le constructeur prend en argument le nombre de lignes, colonnes et la taille des cases
 * @author Mahmoud Bentriou, Mathias Biehler, Cyril Dutrieux
 *
 */

import java.util.LinkedList;

public class DonneesSimulation {
	
	/* Donn�es */
	private LinkedList<Robot> robots;
	private LinkedList<Incendie> incendies;
	private Carte carte;
	
	/* Constructeur, pas d'utilit� de faire un constructeur de copie ici */
	public DonneesSimulation(int lignes, int colonnes, int taillecase) {
		/* Initialisation des listes */
		incendies = new LinkedList<Incendie>();
		robots = new LinkedList<Robot>();
		/* Cr�ation de la carte */
		this.carte = new Carte(lignes,colonnes,taillecase);
	}
	
	/* M�thodes pour la carte : un seul accesseur, on utilise ensuite les m�thodes de Carte */	
	public Carte getCarte() {
		return this.carte;
	}
	
	/* M�thodes pour la liste de robots */
	public int getSizeRobot() {
		return this.robots.size();
	}
	
	public Robot getRobot(int numRobot) {
		return this.robots[numRobot];
	}
	
	public void ajouteRobot(Robot r) {
		this.robots.add(r);
	}
	
	/* M�thode pour la liste d'incendies */
	public int getSizeIncendie() {
		return this.incendies.size();
	}
	
	public Robot getIncendie(int numIncendie) {
		return this.incendies[numIncendie];
	}
	
	public void ajouteIncendie(Incendie i) {
		this.incendies.add(i);
	}	
}
