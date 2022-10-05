import java.sql.Timestamp;

public class timeStamp {
	public static void main(String[] args) {
		
		Timestamp timeStamp = new Timestamp(1000);
		
		System.out.println(timeStamp.toString());
		System.out.println(new Timestamp(System.currentTimeMillis()));

	}
}
