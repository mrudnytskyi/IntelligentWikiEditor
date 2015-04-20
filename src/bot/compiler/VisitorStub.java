/*
 * VisitorStub.java	08.10.2014
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

import bot.compiler.AST.Bold;
import bot.compiler.AST.CategoryDeclaration;
import bot.compiler.AST.Characters;
import bot.compiler.AST.Code;
import bot.compiler.AST.Comment;
import bot.compiler.AST.Content;
import bot.compiler.AST.Document;
import bot.compiler.AST.Gallery;
import bot.compiler.AST.Heading;
import bot.compiler.AST.ImageDeclaration;
import bot.compiler.AST.Indent;
import bot.compiler.AST.Italics;
import bot.compiler.AST.Link;
import bot.compiler.AST.Literal;
import bot.compiler.AST.Math;
import bot.compiler.AST.Nowiki;
import bot.compiler.AST.OrderedList;
import bot.compiler.AST.OrderedListItem;
import bot.compiler.AST.Paragraph;
import bot.compiler.AST.Pre;
import bot.compiler.AST.Reference;
import bot.compiler.AST.SmartLink;
import bot.compiler.AST.Table;
import bot.compiler.AST.TemplateDeclaration;
import bot.compiler.AST.UnorderedList;
import bot.compiler.AST.UnorderedListItem;

/**
 * Concrete realization of {@link Visitor} with methods, doing nothing.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 08.10.2014
 */
public class VisitorStub implements Visitor {

	@Override
	public void visitContent(Content content) {}

	@Override
	public void visitHeading(Heading heading) {}

	@Override
	public void visitLiteral(Literal literal) {}

	@Override
	public void visitComment(Comment comment) {}

	@Override
	public void visitParagraph(Paragraph paragraph) {}

	@Override
	public void visitUnorderedList(UnorderedList unorderedList) {}

	@Override
	public void visitUnorderedListItem(UnorderedListItem unorderedListItem) {}

	@Override
	public void visitOrderedList(OrderedList orderedlist) {}

	@Override
	public void visitOrderedListItem(OrderedListItem orderedListItem) {}

	@Override
	public void visitDocument(Document document) {}

	@Override
	public void visitBold(Bold bold) {}

	@Override
	public void visitItalics(Italics italics) {}

	@Override
	public void visitIndent(Indent indent) {}

	@Override
	public void visitLink(Link link) {}

	@Override
	public void visitSmartLink(SmartLink smartLink) {}

	@Override
	public void visitNowiki(Nowiki nowiki) {}

	@Override
	public void visitPre(Pre pre) {}

	@Override
	public void visitTable(Table table) {}

	@Override
	public void visitCategoryDeclaration(CategoryDeclaration category) {}

	@Override
	public void visitCode(Code code) {}

	@Override
	public void visitTemplateDeclaration(TemplateDeclaration template) {}

	@Override
	public void visitGallery(Gallery gallery) {}

	@Override
	public void visitMath(Math math) {}

	@Override
	public void visitCharacters(Characters characters) {}

	@Override
	public void visitReference(Reference reference) {}

	@Override
	public void visitImageDeclaration(ImageDeclaration image) {}

}