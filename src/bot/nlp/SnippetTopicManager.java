package bot.nlp;

/**
 * Enumeration contains topics of {@link Snippet}s.
 * 
 * @author Mir4ik
 * @version 0.1 20.1.2015
 */
public enum SnippetTopic {
	
	INTRO,
	ETYMOLOGY,
	HISTORY,
	ECONOMY,
	POPULATION,
	CULTURE,
	ARHITECTURE,
	PEOPLE;
	
	@Override
	public String toString() {
		switch (this) {
		case CULTURE:
			return "Культура";
		case ECONOMY:
			return "Економіка";
		case HISTORY:
			return "Історія";
		case POPULATION:
			return "Населення";
		case PEOPLE:
			return "Персоналії";	
		case ARHITECTURE:
			return "Архітектура";
		case ETYMOLOGY:
			return "Етимологія";
		default:
			return "";
		}
	}
	
	public static int count() {
		return SnippetTopic.values().length;
	}
}