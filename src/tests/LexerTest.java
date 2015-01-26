package tests;

import java.util.List;

import bot.compiler.Lexer;
import bot.compiler.Token;
import bot.io.MediaWikiFacade;
import bot.io.MediaWikiFacade.Language;

public class LexerTest {

	public static void main(String[] args) {
		try {
			String text = new MediaWikiFacade(Language.UKRAINIAN)
				.getArticleText("12-й_інженерний_полк_(Україна)");
			@SuppressWarnings("unused")
			String text1 = "=просто стаття Вікі= що має "
					+ "якусь байду ==Історія== та ===Культура=== чи може ше шось"
					+ " з такого ====Список==== списку. =====Джерело===== та "
					+ "======Примітка ====== і всякі інші ======= Фігні=======";
			System.out.println(text);
			System.out.println("----------");
			List<Token> list = new Lexer().tokenize(text);
			
			for (Token t : list) {
				System.out.println(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}