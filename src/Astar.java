

import java.util.TreeSet;
import java.util.Comparator;
import java.util.LinkedList;

public class Astar {

	private Robot robot;
	private Carte map;
	private double[][] gScore;
	private double[][] fScore;
	private Case goal;
	private Case start;
	
	public Astar(Carte map, Robot robot){
		this.map = map;
		this.robot = robot;
		gScore = new double[map.getNbColonnes()][map.getNbLignes()];
		fScore = new double[map.getNbColonnes()][map.getNbLignes()];
	}
	
	private double manhattanDistance(Case c1, Case c2){
		return Math.abs(c1.getLigne() - c2.getLigne() +
				Math.abs(c1.getColonne() - c2.getColonne()));
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
		return gScore[c.getColonne()][c.getLigne()];
	}
	private void fromStartScore(Case c, double set) {
		gScore[c.getColonne()][c.getLigne()] = set;
	}
	
	private class fScoreComp implements Comparator<Case>{
		@Override
		public int compare(Case c, Case v){
			double diff =  toGoalScore(c) - toGoalScore(v);
			if (diff < 0){
				return -1;
			} else if (diff == 0){
				return 0;
			} else {
				return 1;
			}
		}
	}
		
	public double Compute(Case start, Case goal) {
		this.start = start;
		this.goal = goal;
		TreeSet<Case> closedSet = new TreeSet<Case>(new fScoreComp()); 
		TreeSet<Case> openSet = new TreeSet<Case>(new fScoreComp()); 
		openSet.add(start);
		// came_from[]
		fromStartScore(start, 0);
		toGoalScore(start, fromStartScore(start) + 
				manhattanDistance(start, goal));
		
		while(!openSet.isEmpty()) {
			Case current = openSet.pollFirst();
			if (current == goal) {
				return fromStartScore(goal);
			}
			closedSet.add(current);
			LinkedList<Case> neighbors = getNeighbor(current);
			for(Case neighbor : neighbors){
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
					//came_from
					fromStartScore(neighbor, tryStartScore); 
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
	
	private Case cameFrom(Case c){
		double eps = 0.001;
		LinkedList<Case> neighbors = getNeighbor(c);
		Case precCase = neighbors.getFirst();
		for(Case neighbor : neighbors){
			if (fromStartScore(c) - 
					fromStartScore(neighbor) - 
					neighborCost(c, neighbor) <= eps) {
				return neighbor;
			}
		}
		//PB
		return precCase;
	}
	
	public LinkedList<Evenement> pathFinder(Case start, Case goal){
		if ((this.start != start) || (this.goal != goal)) {
			Compute(start, goal);
		}
		Case current = goal;
		LinkedList<Evenement> totalPath = new LinkedList<Evenement>();
		// insert
		while (current != start) {
			current = cameFrom(current);
			//insert
		}
		return totalPath;
		
	}

}
