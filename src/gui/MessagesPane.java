package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 * 
 * @author Mir4ik
 * @version 0.1 06.04.2014
 */
public class MessagesPane extends JPanel {

	private static final long serialVersionUID = -7051388875654936594L;
	
    private static final String[] MONTH = {"January", "February", "March",
    	"April", "May", "June", "July", "August", "September", "October",
    	"November", "December"};

	private final JTextPane pane = new JTextPane();
	
	private final AttributeSet errorHighliter = 
		StyleContext.getDefaultStyleContext().addAttribute(
		SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.RED);
	
	private final AttributeSet warnHighliter = 
		StyleContext.getDefaultStyleContext().addAttribute(
		SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.BLUE);
	
	public MessagesPane() {
		setLayout(new BorderLayout());
		add(new JScrollPane(pane), BorderLayout.CENTER);
		pane.setEditable(false);
	}
	
	public void info(String text) {
		append(text, null);
	}
	
	public void error(String text) {
		append(text, errorHighliter);
	}
	
	public void warn(String text) {
		append(text, warnHighliter);
	}
	
	private void append(String text, AttributeSet highliter) {
		StringBuilder sb = new StringBuilder();
		sb.append(convertDate(System.currentTimeMillis()));
		sb.append(" ");
		sb.append(text);
		sb.append(System.getProperty("line.separator"));
		String dateText = sb.toString();
		Document doc = pane.getStyledDocument();
		try {
			doc.insertString(doc.getLength(), dateText, highliter);
		} catch (BadLocationException ignoredException) {}
	}
	
	private static String convertDate(long time) {
		if (time < 0) {
			throw new IllegalArgumentException("Time can not be negative!");
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(time));
		StringBuilder sb = new StringBuilder();
		sb.append(calendar.get(Calendar.HOUR_OF_DAY));
		sb.append(':');
		sb.append(calendar.get(Calendar.MINUTE));
		sb.append(':');
		sb.append(calendar.get(Calendar.SECOND));
		sb.append(',');
		sb.append(' ');
		sb.append(calendar.get(Calendar.DAY_OF_MONTH));
		sb.append(' ');
		sb.append(MONTH[calendar.get(Calendar.MONTH)]);
		sb.append(' ');
		sb.append(calendar.get(Calendar.YEAR));
		return sb.toString();
	}
}