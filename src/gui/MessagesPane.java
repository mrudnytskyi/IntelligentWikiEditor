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
 * MessagesPane is a text component, created to show different types of 
 * messages: errors, warnings and information messages. Every type is
 * highlighted using color: for errors it is red, for warnings - blue and only
 * information is not highlighted and painted using black color.
 * <p>
 * Every message has it's own time, which is displayed also.
 * <p>
 * Note, that this component use {@link JTextPane} for displaying messages and
 * is subclass of {@link JPanel}.
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
	
	/**
	 * Appends information message to pane.
	 * 
	 * @param text	message
	 */
	public void info(String text) {
		append(text, null);
	}
	
	/**
	 * Appends error message to pane.
	 *  
	 * @param text	message
	 */
	public void error(String text) {
		append(text, errorHighliter);
	}
	
	/**
	 * Appends warning message to pane.
	 * 
	 * @param text	message
	 */
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
		} catch (BadLocationException ignoredException) {
			// can not catch 
		}
	}
	
	private String convertDate(long time) {
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