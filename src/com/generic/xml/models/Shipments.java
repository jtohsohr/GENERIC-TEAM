package com.generic.xml.models;

import java.util.List;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class Shipments {
	@ElementList(inline = true, name = "Warehouse")
	List<Warehouse> warehouses;
	
	public List<Warehouse> getWarehouse() {
		return warehouses;
	}
}
