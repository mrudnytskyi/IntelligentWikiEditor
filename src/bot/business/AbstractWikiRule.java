package bot.business;

import bot.compiler.VisitorStub;
import bot.core.WikiArticle;

/**
 * Class, representing abstraction for every rule, which is used to check
 * Wikipedia article.
 * 
 * @author Mir4ik
 * @version 0.1 04.09.2014
 */
public abstract class AbstractWikiRule extends VisitorStub {
	
	protected enum ResultType {
		WARNING, ERROR, CRITICAL_ERROR;
	}
	
	protected class CheckResult {
		
		private final boolean state;
		
		private final ResultType type;
		
		private final String message;
		
		protected CheckResult() {
			state = true;
			type = getType();
			message = null;
		}
		
		protected CheckResult(String message) {
			state = false;
			type = getType();
			this.message = message;
		}

		public boolean isSuccesfull() {
			return state;
		}
		
		public String getMessage() {
			return message;
		}
		
		public boolean isWarning() {
			return type.equals(ResultType.WARNING);
		}
		
		public boolean isError() {
			return type.equals(ResultType.ERROR);
		}
		
		public boolean isCriticalError() {
			return type.equals(ResultType.CRITICAL_ERROR);
		}
	}
	
	public abstract CheckResult check(WikiArticle article);
	
	protected ResultType getType() {
		return ResultType.WARNING;
	}
}