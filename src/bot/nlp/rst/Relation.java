package bot.nlp.rst;

import java.util.Objects;

/**
 * Class, representing relations between {@link ElementaryTextElement}s. All
 * set of possible relations are written in enumeration {@link Type}.
 * 
 * @author Mir4ik
 * @version 0.1 20.10.2014
 */
/*
 * TODO:
 * 1. more relations
 * 2. rewrite structure, because not all relations have nucleus and satellite
 * 3. write method as isCircumstance() and others for every relation?
 */
public class Relation {
	
	public enum Type {
		CIRCUMSTANCE, MOTIVATION, ENABLEMENT
	}
	
	private ElementaryDiscourseUnit nucleus;
	
	private ElementaryDiscourseUnit satellite;
	
	private Type type;
	
	public Relation(ElementaryDiscourseUnit n, ElementaryDiscourseUnit s, Type t) {
		setNucleus(n);
		setSatellite(s);
		setType(t);
	}
	
	public ElementaryDiscourseUnit getNucleus() {
		return nucleus;
	}
	
	private void setNucleus(ElementaryDiscourseUnit nucleus) {
		Objects.requireNonNull(nucleus, "Nucleus can not be null!");
		this.nucleus = nucleus;
	}
	
	public ElementaryDiscourseUnit getSatellite() {
		return satellite;
	}

	private void setSatellite(ElementaryDiscourseUnit satellite) {
		Objects.requireNonNull(satellite, "Satellite can not be null!");
		this.satellite = satellite;
	}
	
	public Type getType() {
		return type;
	}
	
	private void setType(Type type) {
		Objects.requireNonNull(type, "Type can not be null!");
		this.type = type;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[Relation ");
		sb.append(type);
		sb.append(" from ");
		sb.append(nucleus);
		sb.append(" to ");
		sb.append(satellite);
		sb.append("]");
		return sb.toString();
	}
}