package simulation;

// Ensimag 2014-15 - 2A POO  

import java.io.*;
import java.util.*;

import elements.Incendie;
import elements.NatureRobot;
import elements.Robot;
import elements.RobotAChenille;
import elements.RobotAPattes;
import elements.RobotARoue;
import elements.RobotVolant;
import environnement.Carte;
import environnement.Case;
import environnement.NatureTerrain;
import environnement.ExceptionOutOfMap;

/**
 * Lecteur de cartes au format spectifie dans le sujet.
 * Les donnees sur les cases, robots puis incendies sont lues dans le fichier,
 * puis simplement affichees. 
 * A noter: pas de verification semantique sur les valeurs numeriques lues.
 *
 * IMPORTANT:
 * 
 * Cette classe ne fait que LIRE les infos et les afficher.
 * A vous de modifier ou d'ajouter des methodes, inspirees de celles presentes
 * (ou non), qui CREENT les objets au moment adequat pour construire une
 * instance de la classe DonneesSimulation a partir d'un fichier.
 * 
 * Vous pouvez par exemple ajouter une methode qui cree et retourne un objet
 * contenant toutes les donnees lues: 
 *    public static DonneesSimulation creeDonnees(String fichierDonnees);
 * Et faire des méthode creeCase(), creeRobot(), ... qui liset les données,
 * créent les objets adéquats et les ajoutent ds l'instance de DonneesSimulation.
 * 
 */
public class LecteurDonnees {


	/**
	 * Lit et affiche le contenu d'un fichier de donnees (cases, 
	 * robots et incendies).
	 * Methode de classe, utilisation: LecteurDonnees.lire(fichierDonnees)
	 * @param fichierDonnees nom du fichier a lire
	 */
	public static void lire(String fichierDonnees) 
			throws FileNotFoundException, ExceptionFormatDonnees {
		System.out.println("\n == Lecture du fichier" + fichierDonnees);
		LecteurDonnees lecteur = new LecteurDonnees(fichierDonnees);
		Carte map = lecteur.lireCarte();
		lecteur.lireIncendies(map);		
		lecteur.lireRobots(map);
		scanner.close();
		System.out.println("\n == Lecture terminee");
	}

	public static DonneesSimulation creeDonnees(String fichierDonnees)
		throws FileNotFoundException, ExceptionFormatDonnees {
		System.out.println("\n == Lecture du fichier" + fichierDonnees);
		LecteurDonnees lecteur = new LecteurDonnees(fichierDonnees);
		Carte map = lecteur.lireCarte();
		LinkedList<Incendie> fire = lecteur.lireIncendies(map);		
		LinkedList<Robot> rob = lecteur.lireRobots(map);
		scanner.close();
		System.out.println("\n == Lecture terminee");
		DonneesSimulation data = new DonneesSimulation(map, rob, fire);
		return data;
	}
	
	// Tout le reste de la classe est prive!
	
	private static Scanner scanner;

	/**
	 * Constructeur prive; impossible d'instancier la classe depuis l'exterieur
	 * @param fichierDonnees nom du fichier a lire
	 */
	private LecteurDonnees(String fichierDonnees) 
			throws FileNotFoundException {
		scanner = new Scanner(new File(fichierDonnees));
		scanner.useLocale(Locale.US);				
	}
	
	/**
	 * Lit et affiche les donnees de la carte.
	 * @throws ExceptionFormatDonnees
	 */
	private Carte lireCarte() throws ExceptionFormatDonnees {
		ignorerCommentaires();
		try {
			int nbLignes = scanner.nextInt();
			int nbColonnes = scanner.nextInt();
			int tailleCases = scanner.nextInt();	// en m
			System.out.println("Carte " + nbLignes + "x" + nbColonnes
					+ "; taille des cases = " + tailleCases);
			
			Case[][] tabCases = new Case[nbLignes][nbColonnes];
			for (int lig = 0; lig < nbLignes; lig++) {
				for (int col = 0; col < nbColonnes; col++) {
					tabCases[lig][col] = lireCase(lig, col);
				}
			}
			
			Carte map = new Carte(nbLignes, nbColonnes, tailleCases, tabCases);
			
			return map;
			
		} catch (NoSuchElementException e) {
			throw new ExceptionFormatDonnees("Format invalide. "
					+ "Attendu: nbLignes nbColonnes tailleCases");
		}
		// une ExceptionFormat levee depuis lireCase est remontee telle quelle
	}
		
	/**
	 * Lit et affiche les donnees d'une case.
	 */
	private Case lireCase(int lig, int col) throws ExceptionFormatDonnees {
		ignorerCommentaires();		
		String chaineNature = new String();
		NatureTerrain n;
		
		try {
			chaineNature = scanner.next();

			verifieLigneTerminee();
			
			n = NatureTerrain.valueOf(chaineNature);
			
		} catch (NoSuchElementException e) {
			throw new ExceptionFormatDonnees("format de case invalide. "
					+ "Attendu: nature altitude [valeur_specifique]");
		}
		Case c = new Case(lig, col, n);
		return c;
	}

	/** 
	 * Lit et affiche les donnees des incendies.
	 */
	private LinkedList<Incendie> lireIncendies(Carte map) throws ExceptionFormatDonnees {
		ignorerCommentaires();
		try {
			int nbIncendies = scanner.nextInt();
			System.out.println("Nb d'incendies = " + nbIncendies);
			LinkedList<Incendie> fireList = new LinkedList<Incendie>();
			Incendie fire;
			for (int i = 0; i < nbIncendies; i++) {
				fire = lireIncendie(i, map);
				fireList.add(fire);
			}
			return fireList;
		} catch (NoSuchElementException e) {
			throw new ExceptionFormatDonnees("Format invalide. "
					+ "Attendu: nbIncendies");
		}		
	}
	
	/**
	 * Lit et affiche les donnees du i-eme incendie.
	 * @param i
	 */
	private Incendie lireIncendie(int i, Carte map) throws ExceptionFormatDonnees {
		ignorerCommentaires();		
		System.out.print("Incendie " + i + ": ");
		
		try {
			int lig = scanner.nextInt();
			int col = scanner.nextInt();
			int intensite = scanner.nextInt();
			if (intensite <= 0) {
				throw new ExceptionFormatDonnees("incendie " + i 
						+ "nb litres pour eteindre doit etre > 0");				
			}
			verifieLigneTerminee();
            Case positionIncendie = null;
            try {
            	positionIncendie = map.getCase(lig, col);
            }
            catch (ExceptionOutOfMap e) {
                System.out.println(" ##### Un incendie est placé en dehors de la map : on va le placer en (0,0) #####");
                try {
                	positionIncendie = map.getCase(0, 0);
                } catch (ExceptionOutOfMap e2) { System.out.println("On n'a pas reussi a le placer en (0,0);"); }
            }
			Incendie fire = new Incendie(positionIncendie, intensite);
			System.out.println("position = (" + lig + "," + col
					+ ");\t intensite = " + intensite);
			return fire;
		} catch (NoSuchElementException e) {
			throw new ExceptionFormatDonnees("format d'incendie invalide. "
					+ "Attendu: ligne colonne intensite");		
		}
	}

	/** 
	 * Lit et affiche les donnees des robots.
	 */
	private LinkedList<Robot> lireRobots(Carte map) throws ExceptionFormatDonnees {
		ignorerCommentaires();
		try {
			int nbRobots = scanner.nextInt();
			System.out.println("Nb de robots = " + nbRobots);
			LinkedList<Robot> robotList = new LinkedList<Robot>();
			Robot robot;
			for (int i = 0; i < nbRobots; i++) {
				robot = lireRobot(i, map);
				robotList.add(robot);
			}
			return robotList;
		} catch (NoSuchElementException e) {
			throw new ExceptionFormatDonnees("Format invalide. "
					+ "Attendu: nbRobots");
		}		
	}
	
	/**
	 * Lit et affiche les donnees du i-eme robot.
	 * @param i
	 */
	private Robot lireRobot(int i, Carte map) throws ExceptionFormatDonnees {
		ignorerCommentaires();
		System.out.print("Robot " + i + ": ");
		
		try {
			int lig = scanner.nextInt();
			int col = scanner.nextInt();
			System.out.print("position = (" + lig + "," + col + ");");
			String type = scanner.next();
			// GERER LES TYPES
			System.out.print("\t type = " + type);
			NatureRobot n = NatureRobot.valueOf(type);
			
			// lecture eventuelle d'une vitesse du robot (entier)
			System.out.print("; \t vitesse = ");
			String s = scanner.findInLine("(\\d+)");	// 1 or more digit(s) ?
			// pour lire un flottant:    ("(\\d+(\\.\\d+)?)");
			Robot rob;
            Case positionRobot = null;
            try {
                positionRobot = map.getCase(lig, col);
            }
            catch (ExceptionOutOfMap e) {
                System.out.println(" ##### Un robot est placé en dehors de la map : on va le placer en (0,0) ##### ");
                try {
                	positionRobot = map.getCase(0, 0);
                } catch (ExceptionOutOfMap e2) { System.out.println("On n'a pas reussi a le placer en (0,0);"); }
            }
			if (s == null) {
				switch (n) {
					case DRONE : 
						rob = new RobotVolant(positionRobot, 0);
						break;
					case PATTES : 
						rob = new RobotAPattes(positionRobot, 0);
						break;
					case CHENILLES : 
						rob = new RobotAChenille(positionRobot, 0);
						break;
					case ROUES : 
						rob = new RobotARoue(positionRobot, 0);
						break;
					default : rob = new RobotARoue(positionRobot, 0);

				}	
			} else {
				double vitesse = (double) Integer.parseInt(s);
								
				switch (n) {
					case DRONE : 
						rob = new RobotVolant(positionRobot, 0, vitesse);
						break;
					case PATTES : 
						rob = new RobotAPattes(positionRobot, 0, vitesse);
						break;
					case CHENILLES : 
						rob = new RobotAChenille(positionRobot, 0, vitesse);
						break;
					case ROUES : 
						rob = new RobotARoue(positionRobot, 0, vitesse);
						break;
					default : rob = new RobotARoue(positionRobot, 0, vitesse);
				}
				vitesse = rob.getVitesse(NatureTerrain.TERRAIN_LIBRE);
				System.out.print(vitesse);
			}	
			verifieLigneTerminee();
			System.out.println();
			return rob;
			
		} catch (NoSuchElementException e) {
			throw new ExceptionFormatDonnees("format de robot invalide. "
					+ "Attendu: ligne colonne type [valeur_specifique]");		
		}
	}

	/** Ignore toute (fin de) ligne commencant par '#' */
	private void ignorerCommentaires() {
		while(scanner.hasNext("#.*")) {
				scanner.nextLine();
		}
	}
	
	/**
	 * Verifie qu'il n'y a plus rien a lire sur cette ligne (int ou float).
	 * @throws ExceptionFormatDonnees 
	 */
	private void verifieLigneTerminee() throws ExceptionFormatDonnees {
		if (scanner.findInLine("(\\d+)") != null) {
			throw new ExceptionFormatDonnees("format invalide, donnees en trop.");
		}		
	}
}

