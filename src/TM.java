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
	/*
	private void tokenizer() {
		
		Iterator<String> itr=s.iterator();
		String token = "";
		String s = "";
		
		while(itr.hasNext()) {
			
			s = (String) itr.next();
			for(int i=0;i<s.length();i++) {
				
				char c = s.charAt(i);
				
				if(isSpecial(c) )
				if( isSpecial(c) || tokensMap.containsKey(token)) { //handles spaces, compound operators (:=,etc)
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
		
	}*/
	
	private void tokenizer() {
		Iterator<String> itr=s.iterator();
		String token = "";
		String tokenSpecial = "";
		String s = "";
		
		while(itr.hasNext()) {
			
			s = (String) itr.next();
			for(int i=0;i<s.length();i++) {
				char c = s.charAt(i);
				/*
				 * Acontece que, sempre que encontramos um character especial exceto '_' este nao consideramos especial, podemos terminar o token
				 * sendo entao que 1===1 sao 3 tokens, '1', '===' e '1' outra vez.
				 * percorremos a string, sempre que encontrarmos um character nao especial, adicionamos ao token. Assim que encontramos um character
				 * especial, terminamos o token anterior, desde que este nao seja composto apenas de chars especiais ( se tivermos '==' seguido de '=')
				 * adicionamos ao '==' em vez, isto serve para tratar de operadors com varios simbolos como e caso do assign, equals, notEquals, etc.
				 * Sempre que comecamos um token de especiais, inserimos o token de nao especiais no array de tokens e adicionamos o char especial a um token
				 * de especiais
				 * So: se tivermos um token com '1' e de seguida encontrarmos '=', guardamos o '1' e adicionamos acrescentamos '=' ao pre-token
				 * enquanto forem chars especiais adicionamos a esse pre-token, quando mudarem para nao especiais, adicionamos o token especial a lista de tokens
				 * e recomecamos o trabalho
				 * os passos para \READ A 1*=B\ seriam:
				 * token+=R, token+=E, token+=A, token+=D, \found a special\, insert(token),token="", special==' ' => ignore,
				 * token+=A, \found a special\, insert(token),token="", special==' ' => ignore,
				 * token+=1, \found a special\, insert(token),token="",specialToken+='*',specialToken+='=' \found a non special\, insert(tokenSpecial),tokenSpeciak="",
				 * token+=B, \found end of line\, insert(token), \finished\
				 */
				if(isSpecial(c)) {
					if(tokenSpecial.equals("")) {
						tokens.add(token);
						token="";
					}
					if(c!=' ')	
						tokenSpecial+=c;
					
				} else {
					if(!tokenSpecial.equals("")) {
						tokens.add(tokenSpecial);
						tokenSpecial="";
					}
					if(c!=' ')	
						token+=c;;
				}
				
			}
			tokens.add(token);
			token="";
			
		}
		
	}
	private boolean isSpecial(char c) { //operadores e espacos sao char especiais
		if(c=='_')
			return false;
		return !(isNumeric(Character.toString(c)) || isLetter(c));
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
		//tokensMap.put("===","equalsTo");
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
