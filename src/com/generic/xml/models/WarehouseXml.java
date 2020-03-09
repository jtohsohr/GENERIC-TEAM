package com.generic.xml.models;

import java.util.List;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;


@Root(name = "Warehouse")
public class WarehouseXml {
	
	@Attribute
	String id;
	
	@Attribute
	String name;
	
	@ElementList(inline = true, required = false)
	List<ShipmentXml> shipments;
	
	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}
	
	public List<ShipmentXml> getShipments() {
		return shipments;
	}
	

}
