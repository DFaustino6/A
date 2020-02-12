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
			int lineLen = line.length();
			for(int i=0;i<lineLen;i++) {
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
				else if(c=='.') //se encontra um ponto, mesmo que n tenha nada antes, e porque muito possivelmente e um numero a seguir
					token+=c;
				else if(Character.isDigit(c)) { //se comecar por um numero...
					token+=c;
					if(i+1<lineLen && (!Character.isDigit(line.charAt(i+1)) && line.charAt(i+1)!='.')) { 
						tokens.add(token);
						token="";
					}
				} else if(Character.isAlphabetic(c)){
					token+=c;
					while(i+1<lineLen && (Character.isAlphabetic(line.charAt(i+1)) || Character.isDigit(line.charAt(i+1))))
						token+=line.charAt(++i);
					tokens.add(token);
					token="";
				}	
			}
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

	

}
