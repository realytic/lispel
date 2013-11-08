package lispel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

import lispel.axioms.basic.*;
import lispel.axioms.integers.*;
import lispel.axioms.lists.*;

public class Parser {
	private static enum TokenType {
		LBRACKET,
		RBRACKET,
		APOSTROPHE,
		ATOM,
	}
	
	private static class Token {
		public TokenType type;
		public String value;
		
		public Token(TokenType type, String value) {
			this.type  = type;
			this.value = value;
		}
		
		public String toString() {
			switch(type) {
			case LBRACKET: return "(";
			case RBRACKET: return ")";
			case APOSTROPHE: return "'";
			case ATOM: return value;
			default: return null;
			}
		}
	}
	
	private static boolean isAtomChar(char c) {
		return !( Character.isWhitespace(c) || (c == '(') || (c == ')') );
	}
	
	private static LinkedList<Token> tokenize(String what) {
		LinkedList<Token> tokens = new LinkedList<Token>();
		
		for (int i = 0; i < what.length(); i++) {
			char c = what.charAt(i);
			
			if (Character.isWhitespace(c))
				continue;
			if (c == '(') {
				tokens.add(new Token(TokenType.LBRACKET, null));
			} else if (c == ')') {
				tokens.add(new Token(TokenType.RBRACKET, null));
			} else if (c == '\'') {
				tokens.add(new Token(TokenType.APOSTROPHE, null));
			} else if (c == '"') { /* allow string atoms */
				StringBuffer buffer = new StringBuffer();
				buffer.append('"');
				i++;
				while ((i < what.length()) && !(what.charAt(i) == '"')) {
					buffer.append(what.charAt(i));
					i++;
				}
				buffer.append('"');
				tokens.add(new Token(TokenType.ATOM, buffer.toString()));
			} else {
				StringBuffer buffer = new StringBuffer();
				while ((i < what.length()) && isAtomChar(what.charAt(i))) {
					buffer.append(what.charAt(i));
					i++;
				}
				i--;
				tokens.add(new Token(TokenType.ATOM, buffer.toString()));
			}
		}
		
		return tokens;
	}
	
	private static Token require(LinkedList<Token> tokens, TokenType type) throws Exception {
		if (tokens.isEmpty() || (tokens.peek().type != type))
			throw new Exception(type + " expected!");
		return tokens.poll();
	}
	
	private static List parseList(LinkedList<Token> tokens) throws Exception {
		require(tokens, TokenType.LBRACKET);
		LinkedList<Expression> expressions = new LinkedList<Expression>();
		while(!tokens.isEmpty() && tokens.peek().type != TokenType.RBRACKET) {
			expressions.add(parseExpression(tokens));
		}
		require(tokens, TokenType.RBRACKET);
		
		return new List(expressions);
	}
	
	private static List parseApostrophe(LinkedList<Token> tokens) throws Exception {
		require(tokens, TokenType.APOSTROPHE);
		LinkedList<Expression> quoteExpression = new LinkedList<Expression>();
		quoteExpression.add(new Atom("quote"));
		quoteExpression.add(parseExpression(tokens));
		return new List(quoteExpression);
	}
	
	private static Atom parseAtom(LinkedList<Token> tokens) throws Exception {
		return new Atom(require(tokens, TokenType.ATOM).value);
	}
	
	public static Expression parseExpression(LinkedList<Token> tokens) throws Exception {
		if (tokens.isEmpty())
			throw new Exception("Expression expected!");
		
		if (tokens.peek().type == TokenType.LBRACKET) {
			return parseList(tokens);
		} else if (tokens.peek().type == TokenType.APOSTROPHE) {
			return parseApostrophe(tokens);
		} else {
			return parseAtom(tokens);
		}
	}
	
	public static void main(String[] args) {
		Environment env = new Environment(null);
		
		/* add axioms to the core language */
		
		/* basic axioms */
		env.bind("eq",   new AxiomEq());
		env.bind("atom", new AxiomAtom());
		env.bind("set",  new AxiomSet());
		env.bind("eval", new AxiomEval());
		
		/* lists */
		env.bind("car",  new AxiomCar());
		env.bind("cdr",  new AxiomCdr());
		env.bind("cons", new AxiomCons());
		
		/* integers */
		env.bind("+",    new AxiomPlus());
		env.bind("-",    new AxiomMinus());
		env.bind("*",    new AxiomMultiply());
		env.bind("/",    new AxiomDivide());
		env.bind("=",    new AxiomEquals());
		env.bind("<",    new AxiomSmaller());
		
		System.out.println("muLisp");
		
		String current = "";
		InputStreamReader converter = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(converter);
		
		while (!(current.equals("'quit"))) {
			System.out.print("> ");
		  	try {
				current = in.readLine();
			} catch (IOException exception) { exception.printStackTrace(); }
			
			try {
				System.out.println(parseExpression(tokenize(current)).eval(env));
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	}
}
