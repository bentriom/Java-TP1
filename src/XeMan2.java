import java.awt.Color;
import java.io.FileNotFoundException;

import managerPack.Manager;
import managerPack.Manager2;
import simulation.Simulateur;
import ihm.*;


public class XeMan2 {
	public static void main(String[] args) {
		Simulateur simu = new Simulateur(args);
		Manager M = new Manager2(simu);
		simu.setManager(M);
		M.manage();
	}
}