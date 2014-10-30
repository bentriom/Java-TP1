
public class TestCarte {

	public static void main(String[] args) {
		System.out.println("hello world!");
		
		Carte LesEssarts = new Carte(2,2,10);
		LesEssarts.affiche();
		
		if(LesEssarts.voisinExiste(LesEssarts.getCase(0, 0), Direction.NORD)) {
			System.out.println("Existe !!");
		}
		else {
			System.out.println("Nexiste pas !!");
		}
	}

}
