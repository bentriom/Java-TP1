
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
	public DonneesSimulation(Carte map,
			LinkedList<Robot> robots,
			LinkedList<Incendie> incendies) {
		/* Création de la carte */
		this.carte = map;
		this.incendies = incendies;
		this.robots = robots;
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
	
	public LinkedList<Robot> getRobots() {
		return robots;
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
	
	public Incendie getIncendie(Case c) {
		for (Incendie i : this.incendies) {
			if (i.getPosition() == c)
				return i;
		}
		return null;
	}
	
	public LinkedList<Incendie> getIncendies() {
		return incendies;
	}
	
	public void ajouteIncendie(Incendie i) {
		this.incendies.add(i);
	}	
}
