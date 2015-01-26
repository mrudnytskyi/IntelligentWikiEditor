package bot.compiler.AST;

import java.util.Objects;

import bot.compiler.Visitor;

/**
 * Class represents image declaration node.
 * 
 * @author Mir4ik
 * @version 0.1 22.11.2014
 */
/*
 * TODO
 * 1. add image properties
 */
public class ImageDeclaration implements Content {

	protected final CharSequence image;
	
	public ImageDeclaration(CharSequence image) {
		Objects.requireNonNull(image, "Image can not be null!");
		this.image = image;
	}
	
	public CharSequence getImage() {
		return image;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[[Файл:");
		sb.append(image);
		sb.append("]]");
		return sb.toString();
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visitImageDeclaration(this);
	}

	@Override
	public CharSequence getWikiSource() {
		return toString();
	}
}