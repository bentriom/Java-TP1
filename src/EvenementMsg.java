/**
 * 
 */

/**
 * @author Souf
 *
 */
public final class EvenementMsg extends Evenement {

	private String msg;

	public EvenementMsg(long d, String m) {
		super(d);
		this.msg = m;
	}
	
	@Override
	public void execute() {
		System.out.println(String.valueOf(this.date) + " " + this.msg);
	}

}
