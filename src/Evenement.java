
public abstract class Evenement implements Comparable<Evenement> {
	protected long date;
	protected Robot robot;
	
	public Evenement(long d, Robot r) {
		this.date = d;
		this.robot = r;
	}
	
	public long getDate() {
		return this.date;
	}
	
	public void setDate(long d) {
		this.date = d;
	}

	public int compareTo(Evenement E) {
		long res = (E.getDate() - this.date);
		if (this.equals(E)) 
			return 0;
		if (res>0) 
			return -1;
		else if (res==0) 
			return -1;
		else
			return 1;
	}
	
	public String toString() {
		return "Evenement avec date : " + String.valueOf(date);
	}
	
	public abstract void execute();
}
