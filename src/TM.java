import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

public class TM {
	private Collection<String> s;
	private Hashtable<String,String> tokensMap = new Hashtable<String,String>();
	private ArrayList<String> tokens;
	private ArrayList<String> tokenIdentified;
	
	public TM(Collection<String> s) {
		this.s=s;
		initialiazeTable();
		tokens = new ArrayList<String>();
		getToken();
	}
	
	private String getToken() {
		
		Iterator<String> itr=s.iterator();
		String token = "";
		
		/*while(itr.hasNext()) {
			
			String s = (String) itr.next();*/
			String s = "aaa bbb:=B*C read A read B sum := A + B*99 write sum/2";
			
			for(int i=0;i<s.length();i++) {
				char c = s.charAt(i);
				if(c == ':' || tokensMap.containsKey(Character.toString(c)) || tokensMap.containsKey(token) || c==' ') {
					System.out.println("Token: <"+token+">");
					tokens.add(token);
					token="";
				}
					
				if(c!=' ')	
					token+=s.charAt(i);
			}
		//}
		tokens.add(token);
		itr = tokens.iterator();
		while(itr.hasNext())
			System.out.println(itr.next());
		return null;
	}
	
	private String tokenTypeFormat(String token, String type) {
		return "<"+type+","+"\""+token+"\""+">";
	}
	
	private void initialiazeTable() {
		tokensMap.put(":=","assign");
		tokensMap.put("+","plus");
		tokensMap.put("-","minus");
		tokensMap.put("*","time");
		tokensMap.put("/","div");
		tokensMap.put("lparen","(");
		tokensMap.put("rparen",")");
		tokensMap.put("read","keyword");
		tokensMap.put("write","keyword");
	}
}
