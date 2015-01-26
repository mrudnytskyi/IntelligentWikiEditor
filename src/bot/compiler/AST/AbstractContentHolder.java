package bot.compiler.AST;

import java.util.Objects;

/**
 * Class represents abstraction for every AST node with content.
 * 
 * @author Mir4ik
 * @version 0.1 20.11.2014
 */
public abstract class AbstractContentHolder implements ContentHolder {
	
	protected final Content[] content;

	public AbstractContentHolder(Content[] content) {
		Objects.requireNonNull(content, "Content array can not be null!");
		this.content = content;
	}

	public Content[] getContent() {
		return content;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < content.length; i++) {
			sb.append(content[i].toString());
		}
		return sb.toString();
	}
}