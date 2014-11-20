package elements;

import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.LinkedList;

import simulation.Evenement;
import simulation.EvtDeplacement;
import environnement.Carte;
import environnement.Case;
import environnement.Direction;
import environnement.NatureTerrain;
/**
 * Class imlémentant le Driver,
 *  entité permettant de calculer le plus court chemin vers différents objectifs
 * @author dutrieux, biehler, bentriou
 *
 */
public class Driver {

	private Robot robot;
	private Carte map;
	private double[][] gScore;
	private double[][] fScore;
	private Case[][] cameFrom;
	private Case goal;
	private Case start;
	
	/**
	 * Constructeur du Driver
	 * @param map carte sur lequel les calculs vont s'effectuer
	 * @param robot robot ayant a se déplacer
	 */
	public Driver(Carte map, Robot robot){
		this.map = map;
		this.robot = robot;
		gScore = new double[map.getNbColonnes()][map.getNbLignes()];
		fScore = new double[map.getNbColonnes()][map.getNbLignes()];
		cameFrom = new Case[map.getNbColonnes()][map.getNbLignes()];
	}
	
	/**
	 * Heuristique calculant une estimation basse du cout (temps) de déplacement entre deux cases.
	 * Ici l'heuristique est la norme-1 de (c1 - c2)*taille_des_cases/vitesse_du_robot.
	 * @param c1 case départ
	 * @param c2 case arrivée
	 * @return l'estimation du cout entre deux cases
	 */
	private double manhattanDistance(Case c1, Case c2){
		double res = Math.abs(c1.getLigne() - c2.getLigne() +
				Math.abs(c1.getColonne() - c2.getColonne()));
		res = map.getTailleCases()*res;
		return res/robot.getVitesse(NatureTerrain.TERRAIN_LIBRE);
	}
	
	/**
	 * Calcule le coût (temps) de déplacement entre une case et sa voisine
	 * @param c1 case départ
	 * @param c2 case arrivée
	 * @return le coût de déplacement entre une case et sa voisine
	 */
	private double neighborCost(Case c1, Case c2){
		int distance = map.getTailleCases();
		return ((double)distance)/(2*robot.getVitesse(c1.getNature())) + 
				((double)distance)/(2*robot.getVitesse(c2.getNature()));
	}
	
	/**
	 * Cherche les voisin d'une case 
	 * @param c case étudiée
	 * @return une collection contenant les voisins de la case etudiée
	 */
	private LinkedList<Case> getNeighbor(Case c){
		LinkedList<Case> neighbor = new LinkedList<Case>();
		Case v;
		for( Direction d : Direction.values()){
			v = map.getVoisin(c, d);
			if(v != c){
				neighbor.add(v);
			}
		}
		return neighbor;
	}
	
	/**
	 * Accede au coût minimum estimé pour aller de start a goal en passant par la case c.
	 * ie : le cout entre start et c, plus l'estimation du cout entre c et goal
	 * @param c case etudiée
	 * @return le coût estimé pour une case jusqu'au but.
	 */
	private double toGoalScore(Case c) {
		return fScore[c.getColonne()][c.getLigne()];
	}
	
	/**
	 * Met à jour le coût minimum estimé pour une case jusqu'au but
	 * ie : le cout entre start et c, plus l'estimation du cout entre c et goal
	 * @param c case etudiée
	 * @param set estimation du coût en passant par cette case
	 */
	private void toGoalScore(Case c, double set) {
		fScore[c.getColonne()][c.getLigne()] = set;
	}
		
	/**
	 * Accede au coût minimum pour aller de start à la Case c.
	 * @param c case etudiée
	 * @return le coût minimum pour aller de start à la Case c.
 	 *
	 */
	private double fromStartScore(Case c) {
		if (c == null)
			System.out.println("Cest nul mdr!!!");
		return gScore[c.getColonne()][c.getLigne()];
	}
	
	/**
	 * Met à jour le coût minimum pour aller de start à la Case c.
	 * @param c case etudiée
	 * @param set le coût minimum pour aller de start à la Case c.
	 */
	private void fromStartScore(Case c, double set) {
		gScore[c.getColonne()][c.getLigne()] = set;
	}
	
	/**
	 * implémentation d'un comparateur entre Cases pour utiliser les PriorityQueue
	 * @author dutrieux, bentriou, biehler
	 *
	 */
	private class fScoreComp implements Comparator<Case>{
		
		@Override
		public int compare(Case c, Case v){
			double diff =  toGoalScore(c) - toGoalScore(v);
			if (c.equals(v))
				return 0;
			if (diff < 0){
				return -1;
			} else if (diff == 0){
				return 1;
			} else {
				return 1;
			}
		}
	}
		
	/**
	 * Calcul du coût du plus court chemin entre start et goal. 
	 * Ici on utilise A* avec l'heuristique manhattanDistance.
	 * @param start case départ
	 * @param goal case arrivée
	 * @param nextTo boolean indiqué si on peut s'arreter a coté de goal ou pas
	 * @return le coût du plus court chemin entre start et goal ou (-1) si le but est inaccessible
	 */
	public double aStar(Case start, Case goal, boolean nextTo) {
		this.start = start;
		this.goal = goal;
		PriorityQueue<Case> closedSet = new PriorityQueue<Case>(1, new fScoreComp()); 
		PriorityQueue<Case> openSet = new PriorityQueue<Case>(1, new fScoreComp()); 
		fromStartScore(start, 0);
		openSet.add(start);
		toGoalScore(start, fromStartScore(start) + 
				manhattanDistance(start, goal));
		
		while(!openSet.isEmpty()) {
			Case current = openSet.poll();
			if (current == goal) {
				return fromStartScore(goal);
			}
			closedSet.add(current);
			LinkedList<Case> neighbors = getNeighbor(current);
			for(Case neighbor : neighbors){
				if (neighbor == goal && nextTo) {
					this.goal = current;
					return fromStartScore(current);
				}
				if (closedSet.contains(neighbor)){
					continue;
				}
				if (robot.getVitesse(neighbor.getNature()) == 0){
					continue;
				}
				double tryStartScore = fromStartScore(current) +
						neighborCost(current, neighbor);
				
				if (!openSet.contains(neighbor) || 
						tryStartScore < fromStartScore(neighbor)){
					cameFrom[neighbor.getColonne()][neighbor.getLigne()] = current;
				
					fromStartScore(neighbor, tryStartScore); 

					openSet.remove(neighbor);
					toGoalScore(neighbor,
							fromStartScore(neighbor) + 
							manhattanDistance(neighbor, goal));
					openSet.add(neighbor);
				}		
			}
		}
		return -1;		
	}
	
	/**
	 * Calcul du coût du plus court chemin entre start et le point d'eau le plus proche. 
	 * On utilise Djikstra pour rechercher en largeur la présence d'eau.
	 * Si l'eau est trouvé, on met en mémoire le nouveau goal pour l'atteindre, 
	 * cette mise en mémoire est ensuite utilisée par pathFinder.
	 * @param start case départ
	 * @param nextTo nextTo boolean indiqué si on peut s'arreter a coté de goal ou pas.
	 * @return le coût du plus court chemin entre start et l'eau ou (-1) si il n'y a pas d'eau accessible
	 */
	public double findWater(Case start, boolean nextTo) {
		this.start = start;
		PriorityQueue<Case> closedSet = new PriorityQueue<Case>(1, new fScoreComp()); 
		PriorityQueue<Case> openSet = new PriorityQueue<Case>(1, new fScoreComp()); 
		openSet.add(start);
		fromStartScore(start, 0);
		
		while(!openSet.isEmpty()) {
			Case current = openSet.poll();
			if (current.getNature() == NatureTerrain.EAU) {
				this.goal = current;
				return fromStartScore(current);
			}
			closedSet.add(current);
			LinkedList<Case> neighbors = getNeighbor(current);
			for(Case neighbor : neighbors){
				if  (neighbor.getNature() == NatureTerrain.EAU && nextTo){
					this.goal = current;
					return fromStartScore(current);
				}
				if (closedSet.contains(neighbor) || 
						robot.getVitesse(neighbor.getNature()) == 0){
					continue;
				}
				double tryStartScore = fromStartScore(current) +
						neighborCost(current, neighbor);
				
				if (!openSet.contains(neighbor) || 
						tryStartScore < fromStartScore(neighbor)){
					cameFrom[neighbor.getColonne()][neighbor.getLigne()] = current;
					fromStartScore(neighbor, tryStartScore); 
					openSet.remove(neighbor);
					toGoalScore(neighbor, fromStartScore(neighbor));
					openSet.add(neighbor);
				}		
			}
			
		}
		return -1;		
	}

	
	/**
	 * Calcul le plus court chemin suite a l'appel (obligatoire) du findWater ou aStar precedent.
	 * Crée la liste d'Evenements de déplacements élémentaires pour parcourir le plus court chemin.
	 * @return la liste d'Evenements de déplacements élémentaires pour parcourir le plus court chemin
	 */
	public LinkedList<Evenement> pathFinder(){
		Case current = this.goal;
		LinkedList<Evenement> totalPath = new LinkedList<Evenement>();
		totalPath.addLast( new EvtDeplacement((long)fromStartScore(current), robot, current));
		while (current != this.start) {
			current = cameFrom[current.getColonne()][current.getLigne()];
			double cost = fromStartScore(current);
			totalPath.addFirst( new EvtDeplacement((long)cost, robot, current));
		}
		return totalPath;
		
	}

}
