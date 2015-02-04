package bot;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;

import bot.core.ApplicationException;
import bot.core.Trainer;
import bot.io.FilesFacade;
import bot.nlp.Snippet;
import bot.nlp.SnippetTopic;

/**
 * Class for starting application. See <code>main(String[] args)</code> method 
 * documentation for arguments using.
 * 
 * @author Mir4ik
 * @version 0.1 25.1.2015
 */
public class RunnerTrainer {

	private static class TXTFilter implements FilenameFilter {

		@Override
		public boolean accept(File dir, String name) {
			if (name.endsWith(".txt")) {
				return true;
			} else {
				return false;
			}
		}
	}

	public static void main(String[] args) throws ApplicationException {
		if (args.length != 2) {
			System.err.println("Usage: <src directory> <template>");
			System.exit(1);
		}
		String srcDir = args[0];
		String templateFile = args[1];
		File source = new File(srcDir);
		File template = new File(templateFile);
		if (source.isFile() || template.isDirectory()) {
			System.err.println("Wrong parameters!");
			System.exit(1);
		}
		File[] srcFiles = source.listFiles(new TXTFilter());
		Map<Snippet, SnippetTopic> map = 
				new HashMap<Snippet, SnippetTopic>();
		for (File file : srcFiles) {
			String[] fileContent = FilesFacade.readTXT(file).split("---");
			map.put(new Snippet(fileContent[0], ""), 
					SnippetTopic.valueOf(fileContent[1]));
		}
		Trainer t = new Trainer(map);
		t.train();
		FilesFacade.writeXML(templateFile, t.getTemplate());
	}
}