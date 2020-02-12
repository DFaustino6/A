import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

public class TM {
	private Collection<String> s;
	private Hashtable<String,String> tokensMap = new Hashtable<String,String>();
	private ArrayList<String> tokens;
	private ArrayList<String> tokenResult= new ArrayList<String>();
	
	public TM(Collection<String> s) {
		this.s=s;
		initialiazeTable();
		tokens = new ArrayList<String>();
		tokenizer();
		tokenClassifier();
	}
	
	private void tokenizer() {
		Iterator<String> itr= s.iterator();
		String token="";
		while(itr.hasNext()) {
			String line=itr.next();
			for(int i=0;i<line.length();i++) {
				char c=line.charAt(i);
				if(tokensMap.containsKey(Character.toString(c))) {
					tokens.add(Character.toString(c));
					token="";
				}
				else if(c==':' && line.charAt(i+1)=='=') {
					tokens.add(":=");
					i++;
					token="";
				}
				else if(c=='.') {
					token="";
					token+=c;
				}
				else if(isNumeric(Character.toString(c))) {
					token+=c;
					if(i+1<line.length() && !isNumeric(Character.toString(line.charAt(i+1))))
							tokens.add(token);
				}
				else if(c==' ' || tokensMap.containsKey(Character.toString(c)) ||  c==':' ){	
					tokens.add(token);
					token="";			
				}
				else{
					token+=c;	
				}	
			}
				
		}
			
	}
	
	private boolean isSpecial(char c) { //operadores e espacos sao char especiais
		if(c=='_' || c=='.' || isSeperator(c))
			return false;
		return !(isNumeric(Character.toString(c)) || isLetter(c));
	}
	
	private boolean isSeperator(char c) {
		if(c=='(' || c==')')
			return true;
		return false;
	}
	
	private String tokenTypeFormat(String token, String type) {
		return "<"+type+","+"\""+token+"\""+">";
	}
	
	private void initialiazeTable() {
		tokensMap.put(":=","assign");
		tokensMap.put("+","plus");
		tokensMap.put("-","minus");
		tokensMap.put("*","times");
		tokensMap.put("/","div");
		tokensMap.put("(","lparen");
		tokensMap.put(")","rparen");
		tokensMap.put("read","keyword");
		tokensMap.put("write","keyword");
		/*tokensMap.put("==","isEquals");
		tokensMap.put("===","isEqualsAndSameType");*/
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
	

}
