package bot.compiler;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Class representing lexical tokens.
 * 
 * @author Mir4ik
 * @version 0.1 26.10.2014
 */
/*
 * TODO
 * 1. rewrite as Flyweight pattern?
 */
public class Token {
	
	public enum TokenType {
		ASTERISK,				/* * */
		SHARP,					/* # */
		AMPERSAND,				/* & */
		COLON,					/* : */
		SEMICOLON,				/* ; */
		SLASH,					/* / */
		LEFT_BRACKET,			/* < */
		RIGHT_BRACKET,			/* > */
		LEFT_SQUARE, 			/* [ */
		RIGHT_SQUARE, 			/* ] */
		LEFT_BRACE,				/* { */
		RIGHT_BRACE,			/* } */
		VERTICAL_BAR,			/* | */
		DOUBLE_LEFT_SQUARE, 	/* [[ */
		DOUBLE_RIGHT_SQUARE, 	/* ]] */
		DOUBLE_LEFT_BRACE,		/* {{ */
		DOUBLE_RIGHT_BRACE,		/* }} */
		SINGLE_QUOTE,			/* ' */
		DOUBLE_SINGLE_QUOTE,	/* '' */
		TRIPLE_SINGLE_QUOTE,	/* ''' */
		EQUALS_SIGN,			/* = */
		EQUALS_SIGN2,			/* == */
		EQUALS_SIGN3,			/* === */
		EQUALS_SIGN4,			/* ==== */
		EQUALS_SIGN5,			/* ===== */
		EQUALS_SIGN6,			/* ====== */
		START_COMMENT,			/* <!-- */
		END_COMMENT,			/* --> */
		TEXT
	}
	
	private String content;
	
	private TokenType type;
	
	private int position = -1;
	
	private static final Map<String, Token> tokensPool = 
			new HashMap<String, Token>();
	
	static {
		tokensPool.put("*", new Token(TokenType.ASTERISK));
		tokensPool.put("#", new Token(TokenType.SHARP));
		tokensPool.put("&", new Token(TokenType.AMPERSAND));
		tokensPool.put(":", new Token(TokenType.COLON));
		tokensPool.put(";", new Token(TokenType.SEMICOLON));
		tokensPool.put("/", new Token(TokenType.SLASH));
		tokensPool.put("<", new Token(TokenType.LEFT_BRACKET));
		tokensPool.put(">", new Token(TokenType.RIGHT_BRACKET));
		tokensPool.put("|", new Token(TokenType.VERTICAL_BAR));
		tokensPool.put("[", new Token(TokenType.LEFT_SQUARE));
		tokensPool.put("]", new Token(TokenType.RIGHT_SQUARE));
		tokensPool.put("{", new Token(TokenType.LEFT_BRACE));
		tokensPool.put("}", new Token(TokenType.RIGHT_BRACE));
		tokensPool.put("[[", new Token(TokenType.DOUBLE_LEFT_SQUARE));
		tokensPool.put("]]", new Token(TokenType.DOUBLE_RIGHT_SQUARE));
		tokensPool.put("{{", new Token(TokenType.DOUBLE_LEFT_BRACE));
		tokensPool.put("}}", new Token(TokenType.DOUBLE_RIGHT_BRACE));
		tokensPool.put("'", new Token(TokenType.SINGLE_QUOTE));
		tokensPool.put("''", new Token(TokenType.DOUBLE_SINGLE_QUOTE));
		tokensPool.put("'''", new Token(TokenType.TRIPLE_SINGLE_QUOTE));
		tokensPool.put("=", new Token(TokenType.EQUALS_SIGN));
		tokensPool.put("==", new Token(TokenType.EQUALS_SIGN2));
		tokensPool.put("===", new Token(TokenType.EQUALS_SIGN3));
		tokensPool.put("====", new Token(TokenType.EQUALS_SIGN4));
		tokensPool.put("=====", new Token(TokenType.EQUALS_SIGN5));
		tokensPool.put("======", new Token(TokenType.EQUALS_SIGN6));
		tokensPool.put("<!--", new Token(TokenType.START_COMMENT));
		tokensPool.put("-->", new Token(TokenType.END_COMMENT));
	}
	
	public static Token getToken(String representation) {
		return tokensPool.get(representation);
	}
	
	public Token(String content, int position) {
		this(TokenType.TEXT);
		Objects.requireNonNull(content, "Content can not be null!");
		this.content = content;
		this.position = position;
	}
	
	private Token(TokenType type) {
		this.type = type;
	}
	
	public int getPosition() {
		return position;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(type.equals(TokenType.TEXT) ? content : type.toString());
		sb.append(position != -1 ? " at position " + position : "");
		return sb.toString();
	}
	
	public boolean isAsterisk() {
		return type.equals(TokenType.ASTERISK);
	}
	
	public boolean isSharp() {
		return type.equals(TokenType.SHARP);
	}
	
	public boolean isAmpersand() {
		return type.equals(TokenType.AMPERSAND);
	}
	
	public boolean isColon() {
		return type.equals(TokenType.COLON);
	}
	
	public boolean isSemicolon() {
		return type.equals(TokenType.SEMICOLON);
	}
	
	public boolean isSlash() {
		return type.equals(TokenType.SLASH);
	}
	
	public boolean isLeftBracket() {
		return type.equals(TokenType.LEFT_BRACKET);
	}
	
	public boolean isRightBracket() {
		return type.equals(TokenType.RIGHT_BRACKET);
	}
	
	public boolean isVerticalBar() {
		return type.equals(TokenType.VERTICAL_BAR);
	}
	
	public boolean isLeftSquare() {
		return type.equals(TokenType.LEFT_SQUARE);
	}
	
	public boolean isRightSquare() {
		return type.equals(TokenType.RIGHT_SQUARE);
	}
	
	public boolean isLeftBrace() {
		return type.equals(TokenType.LEFT_BRACE);
	}
	
	public boolean isRightBrace() {
		return type.equals(TokenType.RIGHT_BRACE);
	}
	
	public boolean isDoubleLeftSquare() {
		return type.equals(TokenType.DOUBLE_LEFT_SQUARE);
	}
	
	public boolean isDoubleRightSquare() {
		return type.equals(TokenType.DOUBLE_RIGHT_SQUARE);
	}
	
	public boolean isDoubleLeftBrace() {
		return type.equals(TokenType.DOUBLE_LEFT_BRACE);
	}
	
	public boolean isDoubleRightBrace() {
		return type.equals(TokenType.DOUBLE_RIGHT_BRACE);
	}
	
	public boolean isSingleQuote() {
		return type.equals(TokenType.SINGLE_QUOTE);
	}
	
	public boolean isDoubleSingleQuote() {
		return type.equals(TokenType.DOUBLE_SINGLE_QUOTE);
	}
	
	public boolean isTripleSinglQuote() {
		return type.equals(TokenType.TRIPLE_SINGLE_QUOTE);
	}
	
	public boolean isEqualsSign() {
		return type.equals(TokenType.EQUALS_SIGN);
	}
	
	public boolean isEqualsSign2() {
		return type.equals(TokenType.EQUALS_SIGN2);
	}
	
	public boolean isEqualsSign3() {
		return type.equals(TokenType.EQUALS_SIGN3);
	}
	
	public boolean isEqualsSign4() {
		return type.equals(TokenType.EQUALS_SIGN4);
	}
	
	public boolean isEqualsSign5() {
		return type.equals(TokenType.EQUALS_SIGN5);
	}
	
	public boolean isEqualsSign6() {
		return type.equals(TokenType.EQUALS_SIGN6);
	}
	
	public boolean isStartComment() {
		return type.equals(TokenType.START_COMMENT);
	}
	
	public boolean isEndComment() {
		return type.equals(TokenType.END_COMMENT);
	}
	
	public boolean isText() {
		return type.equals(TokenType.TEXT);
	}
}