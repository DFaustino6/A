import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

public class TM {
	private Collection<String> s;
	private Hashtable<String,String> tokensMap = new Hashtable<String,String>();
	private ArrayList<String> tokens;
	private ArrayList<String> tokenResult= new ArrayList<String>();
	//did it work?
	public TM(Collection<String> s) {
		this.s=s;
		initialiazeTable();
		tokens = new ArrayList<String>();
		tokenizer();
		tokenClassifier();
	}
	
	private void tokenizer() {
		
		Iterator<String> itr=s.iterator();
		String token = "";
		String s = "";
		
		while(itr.hasNext()) {
			
			s = (String) itr.next();
			for(int i=0;i<s.length();i++) {
				
				char c = s.charAt(i);
				
				
				if(c == ':' || c==' ' || tokensMap.containsKey(token)) { //handles spaces, compound operators (:=,etc)
					if(!token.equals(""))
						tokens.add(token);
					token="";
					
				} else if(tokensMap.containsKey(Character.toString(c))) { //simple operators (+,-,/,%,*,!,^,etc)
					
					if(!token.equals(""))
						tokens.add(token);
					tokens.add(Character.toString(c));
					i++;
					token="";
				}
				
				if(c!=' ')	
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
		//tokensMap.put("==","keyword");
	}
	
	@SuppressWarnings("unused")
	private String showTokens() {
		String res = "";
		Iterator<String> itr = tokens.iterator();
		while(itr.hasNext())
			res+=itr.next()+"\r\n";
		return res; 
		
	}
	
	@Override
	public String toString() {
		String res = "";
		Iterator<String> itr = tokenResult.iterator();
		while(itr.hasNext())
			res+=itr.next()+"\r\n";
		return res;
	}
	
	private void tokenClassifier() {
		Iterator<String> itr = tokens.iterator();
		String s="";
		while(itr.hasNext()) {
			s= itr.next();
			if(tokensMap.containsKey(s))
				tokenResult.add(tokenTypeFormat(s,tokensMap.get(s)));
			else if(isNumeric(s))
				tokenResult.add(tokenTypeFormat(s,"number"));
			else if(!s.equals(""))
				tokenResult.add(tokenTypeFormat(s,"id"));		
		}
	}
	
	private boolean isNumeric(String n){
		try {
			Double.parseDouble(n);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private boolean isLetter(Character c) {
		return Character.isLetter(c);
	}
	
	private boolean isSpecial(char c) {
		return !(isNumeric(Character.toString(c)) || isLetter(c));
	}
}
