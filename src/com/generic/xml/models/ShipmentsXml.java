package com.generic.xml.models;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class ShipmentsXml {
	@ElementList(inline = true, name = "Warehouse")
	List<WarehouseXml> warehouses;

	public List<WarehouseXml> getWarehouse() {
		return warehouses;
	}
}
