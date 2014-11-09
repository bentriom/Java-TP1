
import java.io.FileNotFoundException;
import java.util.LinkedList;
import ihm.*;

public class Simulateur implements Simulable {

    private IGSimulateur ihm;
    private DonneesSimulation data;
	private long dateCourrante = 0;
	private long dateEvtMax = 0;
	private LinkedList<Evenement> evenements = new LinkedList<Evenement>();
    
	// constructeur lisant les donnees
	public Simulateur(String[] args) {
		if (args.length < 1) {
			System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
			System.exit(1);
		}
		
		try {
			data = LecteurDonnees.creeDonnees(args[0]);
			int col = data.getCarte().getNbColonnes();
			int lig = data.getCarte().getNbLignes();
			int taille = data.getCarte().getTailleCases();
			ihm = new IGSimulateur(col, lig, 30, this);
	        dessine();
		} catch (FileNotFoundException e) {
			System.out.println("fichier " + args[0] + " inconnu ou illisible");
		} catch (ExceptionFormatDonnees e) {
			System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
		}

    
    @Override 
	public void next(){
    }

    @Override 
	public void restart(){
    }
    

   
    private void dessine(){
		Affichage.dessineCases(data.getCarte(), ihm);
		Affichage.dessineIncendies(data, ihm);
		Affichage.dessineRobots(data, ihm);
    }

    
	/* Méthodes */
	public void incrementeDate() {
		this.dateCourrante++;
	}
	
	/* On ajoute les evenements dans le desordre
	 * Le manager se chargera de mieux organiser la liste d'evts
	 */
	public void ajouteEvenement(Evenement e) {
		this.evenements.add(e);
		if (dateEvtMax < e.getDate())
			dateEvtMax = e.getDate();
	}
	
	public LinkedList<Evenement> getEvts() {
		return this.evenements;
	}
	
	public boolean simulationTerminee() {
		return (dateCourrante > dateEvtMax);
	}
}
