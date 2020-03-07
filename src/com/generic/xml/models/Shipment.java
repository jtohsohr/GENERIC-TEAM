package com.generic.xml.models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Shipment")
public class Shipment {

	@Attribute(name = "type")
	String type;
	
	@Attribute(name = "id")
	String id;
	
	@Element(name = "ReceiptDate", required = false)
	long receiptDate;

	@Element
	Weight Weight;
		
	public String getType() {
		return type;
	}
	
	public String toString() {
		return String.format("ID: %s || Type: %s || Receipt Date: %s || Weight: %.2f || Unit: %s\n" , id, type, Long.toString(receiptDate), Weight.getWeight(), Weight.getUnit());
		
	}

	public long getReceiptDate() {
		return receiptDate;
	}
	
	
	

}
