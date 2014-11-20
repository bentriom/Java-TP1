
import java.awt.Color;
import java.io.FileNotFoundException;

import managerPack.Manager;
import managerPack.Manager3;
import simulation.Simulateur;
import ihm.*;

/**
 * Classe "main" qui lance la simulation avec le manageur 3
 * @author Mahmoud Bentriou, Mathias Biehler, Cyril Dutrieux
 *
 */

public class XeMan3 {
	public static void main(String[] args) {
		Simulateur simu = new Simulateur(args);
		Manager M = new Manager3(simu);
		simu.setManager(M);
		M.manage();
	}
}