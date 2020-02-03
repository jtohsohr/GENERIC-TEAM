package com.generic.main;

public class Shipment {
	private String shipmentID;
	private String freight;
	private float weight;
	private int receiptDate; // Need to figure out the date format

	/**
	 * A Shipment with specified shipment ID, shipment method, gross weight, and
	 * receipt date.
	 * 
	 * @param id the id of this shipment.
	 * @param f  the shipment method of this shipment.
	 * @param w  the weight of this shipment in pounds.
	 * @param d  the receipt date of this shipment.
	 */
	public Shipment(String id, String f, float w, int d) {
		shipmentID = id;
		freight = f;
		weight = w;
		receiptDate = d;
	}

	/**
	 * Returns the shipment ID.
	 * 
	 * @return The shipment ID.
	 */
	public String getShipmentID() {
		return shipmentID;
	}

	/**
	 * Sets the shipment ID.
	 * 
	 * @param shipmentID The shipment ID.
	 */
	public void setShipmentID(String shipmentID) {
		this.shipmentID = shipmentID;
	}

	/**
	 * Returns the shipment freight method.
	 * 
	 * @return The shipment freight method.
	 */
	public String getFreight() {
		return freight;
	}

	/**
	 * Sets the shipment freight method.
	 * 
	 * @param freight The shipment freight method.
	 */
	public void setFreight(String freight) {
		this.freight = freight;
	}

	/**
	 * Returns the shipping weight.
	 * 
	 * @return The shipping weight.
	 */
	public float getWeight() {
		return weight;
	}

	/**
	 * Sets the shipment weight.
	 * 
	 * @param weight The shipment weight.
	 */
	public void setWeight(float weight) {
		this.weight = weight;
	}

	/**
	 * Returns the shipment receipt date.
	 * 
	 * @return The shipment receipt date.
	 */
	public int getReceiptDate() {
		return receiptDate;
	}

	/**
	 * Sets the shipment receipt date.
	 * 
	 * @param receiptDate The shipment receipt date.
	 */
	public void setReceiptDate(int receiptDate) {
		this.receiptDate = receiptDate;
	}

}
