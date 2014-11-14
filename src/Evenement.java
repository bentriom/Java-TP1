
public abstract class Evenement implements Comparable<Evenement> {
	protected long date;
	
	public Evenement(long d) {
		this.date = d;
	}
	
	public long getDate() {
		return this.date;
	}

	public int compareTo(Evenement E) {
		long res = (E.getDate() - this.date);
		
		if (res>0) 
			return -1;
		else if (res==0) 
			return 0;
		else
			return 1;
	}
	
	public String toString() {
		return "Evenement avec date : " + String.valueOf(date);
	}
	
	public abstract void execute();
}
