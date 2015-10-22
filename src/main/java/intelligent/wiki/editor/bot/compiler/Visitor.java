/*
 * Visitor.java	17.01.2015
 * Copyright (C) 2015 Myroslav Rudnytskyi
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
package intelligent.wiki.editor.bot.compiler;

import intelligent.wiki.editor.bot.compiler.AST.*;
import intelligent.wiki.editor.bot.compiler.AST.Math;

/**
 * Interface for every class, working with article Abstract Syntax Tree.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 17.01.2015
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

	default void visitTemplateArgument(TemplateArgument templateArgument) {

	}
}