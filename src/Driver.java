

import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.LinkedList;

public class Driver {

	private Robot robot;
	private Carte map;
	private double[][] gScore;
	private double[][] fScore;
	private Case[][] cameFrom;
	private Case goal;
	private Case start;
	
	public Driver(Carte map, Robot robot){
		this.map = map;
		this.robot = robot;
		gScore = new double[map.getNbColonnes()][map.getNbLignes()];
		fScore = new double[map.getNbColonnes()][map.getNbLignes()];
		cameFrom = new Case[map.getNbColonnes()][map.getNbLignes()];
	}
	
	private double manhattanDistance(Case c1, Case c2){
		double res = Math.abs(c1.getLigne() - c2.getLigne() +
				Math.abs(c1.getColonne() - c2.getColonne()));
		res = map.getTailleCases()*res;
		return res/robot.getVitesse(NatureTerrain.TERRAIN_LIBRE);
	}
	
	private double neighborCost(Case c1, Case c2){
		int distance = map.getTailleCases();
		return ((double)distance)/(2*robot.getVitesse(c1.getNature())) + 
				((double)distance)/(2*robot.getVitesse(c2.getNature()));
	}
	
	private LinkedList<Case> getNeighbor(Case c){
		LinkedList<Case> neighbor = new LinkedList<Case>();
		Case v;
		for( Direction d : Direction.values()){
			v = c.getVoisin(d);
			if(v!=c){
				neighbor.add(v);
			}
		}
		return neighbor;
	}
	
	private double toGoalScore(Case c) {
		return fScore[c.getColonne()][c.getLigne()];
	}
	private void toGoalScore(Case c, double set) {
		fScore[c.getColonne()][c.getLigne()] = set;
	}
		
	private double fromStartScore(Case c) {
		if (c == null)
			System.out.println("Cest nul mdr!!!");
		return gScore[c.getColonne()][c.getLigne()];
	}
	private void fromStartScore(Case c, double set) {
		gScore[c.getColonne()][c.getLigne()] = set;
	}
	
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
					openSet.remove(neighbor);
					fromStartScore(neighbor, tryStartScore); 
					openSet.add(neighbor);
					toGoalScore(neighbor,
							fromStartScore(neighbor) + 
							manhattanDistance(neighbor, goal));
					if (!openSet.contains(neighbor)){
						openSet.add(neighbor);
					}
				}		
			}
		}
		return -1;		
	}
	
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
					if (!openSet.contains(neighbor)){
						openSet.add(neighbor);
					}
				}		
			}
			
		}
		return -1;		
	}
	
	private Case cameFrom(Case c){
		double eps = 1;
		LinkedList<Case> neighbors = getNeighbor(c);
		Case precCase = neighbors.getFirst();
		for(Case neighbor : neighbors){
			// peut utiliser valeur non init
			if (fromStartScore(precCase)>fromStartScore(neighbor)) {
				precCase = neighbor;
			}
			if (fromStartScore(c) - 
					fromStartScore(neighbor) - 
					neighborCost(c, neighbor) <= eps) {
				return neighbor;
			}
		}
		return precCase;
	}
	
	public LinkedList<Evenement> pathFinder(){
		Case current = this.goal;
		LinkedList<Evenement> totalPath = new LinkedList<Evenement>();
		totalPath.addLast( new EvtDeplacement((long)fromStartScore(current), robot, current));
		while (current != this.start) {
			System.out.println(current);
			current = cameFrom[current.getColonne()][current.getLigne()];
			double cost = fromStartScore(current);
			totalPath.addFirst( new EvtDeplacement((long)cost, robot, current));
			System.out.println("temps, case : " + cost + ", " + current);
		}
		return totalPath;
		
	}

}
