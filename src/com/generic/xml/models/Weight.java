package com.generic.xml.models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Text;

/**
 * @author Seyi Ola
 *
 */
public class Weight {

	@Text
	double weight;

	@Attribute(name = "unit")
	String unit;

	public double getWeight () {
		return weight;
	}

	public String getUnit () {
		return unit;
	}
}
