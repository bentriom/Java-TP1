
public class Horloge {
	
	private static long date;
	
	public Horloge(){
		date = 0;
	}
	public static long getDate(){
		long test = date;
		return test;
	}
	
	public void increDate(){
		date+=1000;
	}
	
	
}
