/**
 * Classe main qui permet de lancer la simulation graphique
 * @author Mahmoud Bentriou, Mathias Biehler, Cyril Dutrieux
 */

import java.awt.Color;
import java.io.FileNotFoundException;

import managerPack.Manager;
import managerPack.Manager3;
import simulation.Simulateur;
import ihm.*;

public class RunSimulation {
	public static void main(String[] args) {
		Simulateur simu = new Simulateur(args);
		Manager M = new Manager3(simu);
		simu.setManager(M);
		M.manage();
	}
}
