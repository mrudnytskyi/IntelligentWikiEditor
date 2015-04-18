/*
 * Lexer.java	26.10.2014
 * Copyright (C) 2014 Myroslav Rudnytskyi
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 */
package bot.compiler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;

/**
 * Class for dividing char streams to {@link Token}s and 
 * making lexical analysis for Wikipedia article.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 26.10.2014
 */
// TODO: write glueTokens()
public class Lexer implements Tokenizer {
	
	private static final String DIVIDERS = "*#<>!-[]{}'=";
	
	@Override
	public List<Token> tokenize(CharSequence s) throws CompilerException {
		Objects.requireNonNull(s, "Char sequence can not be null!");
		checkBrackets(s);
		List<String> strings = getStringTokens(s);
		List<Token> tokens = getTokens(strings);
		return glueTokens(tokens);
	}
	
	private void checkBrackets(CharSequence s) throws CompilerException {
		int counterBrace = 0, counterBracket = 0, counterSquare = 0;
		for (int i = 0; i < s.length(); i++) {
			char current = s.charAt(i);
			switch (current) {
			case '{':	counterBrace++;		break;
			case '<':	counterBracket++;	break;
			case '[':	counterSquare++;	break;
			case '}':	counterBrace--;		break;
			case '>':	counterBracket--;	break;
			case ']':	counterSquare--;	break;
			}
		}
		if (counterBrace != 0) {
			throw new CompilerException("Unbalanced braces! {}");
		}
		if (counterBracket != 0) {
			throw new CompilerException("Unbalanced brackets! <>");
		}
		if (counterSquare != 0) {
			throw new CompilerException("Unbalanced squares! []");
		}
	}
	
	private List<String> getStringTokens(CharSequence sequence) {
		StringTokenizer divider = new StringTokenizer(
				sequence.toString(), DIVIDERS, true);
		List<String> list = new ArrayList<String>(divider.countTokens());
		while (divider.hasMoreElements()) {
			list.add(divider.nextToken());
		}
		Object[] array = list.toArray();
		List<String> resultList = new ArrayList<String>(array.length);
		int skipNextToken = 0;
		for (int i = 0; i < array.length; i++) {
			if (skipNextToken != 0) {
				skipNextToken--;
				continue;
			}
			String current = (String) array[i];
			if (i != array.length - 1 && current.length() == 1) {
				String next = (String) array[i + 1];
				switch (current.charAt(0)) {
				case '[':
					if (next.equals("[")) {
						resultList.add("[[");
						skipNextToken = 1;
						continue;
					}
					break;
				case ']':
					if (next.equals("]")) {
						resultList.add("]]");
						skipNextToken = 1;
						continue;
					}
					break;
				case '{':
					if (next.equals("{")) {
						resultList.add("{{");
						skipNextToken = 1;
						continue;
					}
					break;
				case '}':
					if (next.equals("}")) {
						resultList.add("}}");
						skipNextToken = 1;
						continue;
					}
					break;
				case '\'':
					if (next.equals("'")) {
						if (i < array.length - 2) {
							String next2 = (String) array[i + 2];
							if (next2.equals("'")) {
								resultList.add("'''");
								skipNextToken = 2;
								continue;
							} else {
								resultList.add("''");
								skipNextToken = 1;
								continue;
							}
						}
					}
					break;
				case '=':
					int offset = 0;
					// 6 maximum: =, ==, ===, ====, =====, ======
					while (true/*offset < 6*/) {
						if (i < array.length - offset && 
								array[i + offset].equals("=")) {
							offset++;
						} else {
							break;
						}
					}
					StringBuilder sb = new StringBuilder();
					for (int j = 0; j < offset; j++) {
						sb.append("=");
					}
					resultList.add(sb.toString());
					skipNextToken = offset - 1;
					continue;
				case '<':
					if (next.equals("!")) {
						if (i < array.length - 2) {
							String next2 = (String) array[i + 2];
							if (next2.equals("-")) {
								if (i < array.length - 3) {
									String next3 = (String) array[i + 3];
									if (next3.equals("-")) {
										resultList.add("<!--");
										skipNextToken = 3;
										continue;
									}
								}
							}
						}
					}
					break;
				case '-':
					if (next.equals("-")) {
						if (i < array.length - 2) {
							String next2 = (String) array[i + 2];
							if (next2.equals(">")) {
								resultList.add("-->");
								skipNextToken = 2;
								continue;
							}
						}
					}
					break;
				}
			}
			resultList.add(current);
		}
		return resultList;
	}
	
	private List<Token> getTokens(List<String> tokens) {
		Iterator<String> iterator = tokens.iterator();
		List<Token> results = new ArrayList<Token>(tokens.size());
		int position = 0;
		while (iterator.hasNext()) {
			String current = iterator.next();
			Token token = Token.getToken(current);
			if (token == null) {
				token = new Token(current, position);
			}
			results.add(token);
			position += current.length();
		}
		return results;
	}
	
	private List<Token> glueTokens(List<Token> tokens) {
		return tokens;
	}
}