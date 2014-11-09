
public class TestManag {

	public static void main(String[] args) {
		Simulateur S = new Simulateur();
		ManagerTest T = new ManagerTest(S);
		T.getSimu();
		T.manage();
		S = T.getSimu();
		for (Evenement E : T.getSimu().getEvts()) {
			E.execute();
		}
		
	}
}
