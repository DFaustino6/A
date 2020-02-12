import java.util.ArrayList;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		ArrayList<String> lines = new ArrayList<String>();
		 while(sc.hasNextLine())
			 lines.add(sc.nextLine());
		TM tm = new TM(lines);
		System.out.println(tm.toString());
		sc.close();
	}

}
