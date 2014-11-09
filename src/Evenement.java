
public abstract class Evenement {
	protected long date;
	
	public Evenement(long d) {
		this.date = d;
	}
	
	public long getDate() {
		return this.date;
	}

	public abstract void execute();
}
