package bot.compiler;

import bot.compiler.AST.*;
import bot.compiler.AST.Math;

/**
 * Interface for every class, working with article Abstract Syntax Tree.
 * 
 * @author Mir4ik
 * @version 0.1 17.1.2015
 */
public interface Visitor {
	
	void visitContent(Content content);
	
	void visitHeading(Heading heading);
	
	void visitLiteral(Literal literal);
	
	void visitComment(Comment comment);
	
	void visitParagraph(Paragraph paragraph);
	
	void visitUnorderedList(UnorderedList unorderedList);
	
	void visitUnorderedListItem(UnorderedListItem unorderedListItem);
	
	void visitOrderedList(OrderedList orderedlist);
	
	void visitOrderedListItem(OrderedListItem orderedListItem);
	
	void visitDocument(Document document);
	
	void visitBold(Bold bold);
	
	void visitItalics(Italics italics);
	
	void visitIndent(Indent indent);
	
	void visitLink(Link link);
	
	void visitSmartLink(SmartLink smartLink);
	
	void visitNowiki(Nowiki nowiki);
	
	void visitPre(Pre pre);
	
	void visitTable(Table table);
	
	void visitCategoryDeclaration(CategoryDeclaration category);
	
	void visitCode(Code code);
	
	void visitTemplateDeclaration(TemplateDeclaration template);
	
	void visitGallery(Gallery gallery);
	
	void visitMath(Math math);

	void visitCharacters(Characters characters);
	
	void visitReference(Reference reference);
	
	void visitImageDeclaration(ImageDeclaration image);
}