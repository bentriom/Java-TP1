package simulation;

/**
 * Classe qui contient toutes les informations de la simulation : cartes, robots, incendies
 * Le constructeur prend en argument le nombre de lignes, colonnes et la taille des cases
 * @author Mahmoud Bentriou, Mathias Biehler, Cyril Dutrieux
 *
 */

import java.util.LinkedList;

import elements.Incendie;
import elements.Robot;
import environnement.Carte;
import environnement.Case;

public class DonneesSimulation {
	

	private LinkedList<Robot> robots;
	private LinkedList<Incendie> incendies;
	private Carte carte;
	
	/**
	 * Constructeur des données de simulation
	 * @param map carte sur laquelle se déroule la simulation
	 * @param robots ensemble des robots de la simulation
	 * @param incendies ensemble des incendies de la simulation
	 */
	public DonneesSimulation(Carte map,
			LinkedList<Robot> robots,
			LinkedList<Incendie> incendies) {
		/* Création de la carte */
		this.carte = map;
		this.incendies = incendies;
		this.robots = robots;
		Robot.setMap(map);
	}
	
	/* Méthodes pour la carte : un seul accesseur, on utilise ensuite les méthodes de Carte */
	
	/**
	 * Accesseur de la carte
	 * @return la carte de la simulation
	 */
	public Carte getCarte() {
		return this.carte;
	}
	
	/* Méthodes pour la liste de robots */
	
	/**
	 * Accesseur sur le nombre de robots dans la simulation
	 * @return le nombre de robot dans la simulation
	 */
	public int getSizeRobot() {
		return this.robots.size();
	}
	
	/**
	 * Accesseur d'un robot
	 * @param numRobot indice du robot voulu
	 * @return le robot voulu
	 */
	public Robot getRobot(int numRobot) {
		return robots.get(numRobot);
	}
	
	/**
	 * Accesseur de l'ensemble des robots
	 * @return la collection de robots
	 */
	public LinkedList<Robot> getRobots() {
		return robots;
	}
	
	/**
	 * ajoute un robot a la collection de robots
	 * @param r robot a ajouter
	 */
	public void ajouteRobot(Robot r) {
		this.robots.add(r);
	}
	
	/* Méthode pour la liste d'incendies */
	
	
	/**
	 * Accesseur sur le nombre d'incendies dans la simulation
	 * @return le nombre d'incendie dans la simulation
	 */
	public int getSizeIncendie() {
		return this.incendies.size();
	}
	
	/**
	 * Accesseur sur un incendie 
	 * @param numIncendie index de l'incendie voulu
	 * @return l'incendie voulu
	 */
	public Incendie getIncendie(int numIncendie) {
		return this.incendies.get(numIncendie);
	}
	
	/**
	 * Accede a un incendie présent sur une case
	 * @param c Case etudiée
	 * @return l'incendie si il y en a un,  null sinon
	 */
	public Incendie getIncendie(Case c) {
		for (Incendie i : this.incendies) {
			if (i.getPosition() == c)
				return i;
		}
		return null;
	}
	
	/**
	 * Accede a la collection d'incendie de la simulation
	 * @return la collection d'incendies
	 */
	public LinkedList<Incendie> getIncendies() {
		return incendies;
	}
	
	
	/**
	 * Ajoute un incendie a la simulation
	 * @param i incendie a ajouter
	 */
	public void ajouteIncendie(Incendie i) {
		this.incendies.add(i);
	}	
}
