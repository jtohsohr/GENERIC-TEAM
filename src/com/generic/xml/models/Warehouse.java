package com.generic.xml.models;

import java.util.List;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;


@Root(name = "Warehouse")
public class Warehouse {
	
	@Attribute
	String id;
	
	@Attribute
	String name;
	
	@ElementList(inline = true, required = false)
	List<Shipment> shipments;
	/*
	public Warehouse(String type, String id) {
		shipments = new ArrayList<Shipment>();
	}
	*/
	
	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}
	
	public List<Shipment> getShipments() {
		return shipments;
	}
	

}
