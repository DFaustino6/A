import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;

public class TM {
	private Collection<String> s;
	private Hashtable<String,String> tokensMap = new Hashtable<String,String>();
	private ArrayList<String> tokens;
	private ArrayList<String> tokenResult;
	
	public TM(Collection<String> s) {
		this.s=s;
		initialiazeTable();
		tokens = new ArrayList<String>();
		tokenizer();
	}
	
	private void tokenizer() {
		
		Iterator<String> itr=s.iterator();
		String token = "";
		String s = "";
		
		while(itr.hasNext()) {
			
			s = (String) itr.next();
			for(int i=0;i<s.length();i++) {
				
				char c = s.charAt(i);
				if(c == ':' || tokensMap.containsKey(Character.toString(c)) || tokensMap.containsKey(token) || c==' ') {
					if(!token.equals(""))
						tokens.add(token);
					token="";
				} if(c!=' ')	
					token+=s.charAt(i);
			} //end for
			tokens.add(token);
			token="";
		}
		
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
		tokensMap.put("(","lparen");
		tokensMap.put(")","rparen");
		tokensMap.put("read","keyword");
		tokensMap.put("write","keyword");
	}
	
	public String showTokens() {
		String res = "";
		Iterator<String> itr = tokens.iterator();
		while(itr.hasNext())
			res+=itr.next()+"\r\n";
		return res;
		
	}
}
