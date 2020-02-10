package com.generic.main;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {

		/*
		 * Scanner scanner = new Scanner(System.in);
		 * System.out.println("Import (I) or Export (E) Warehouse Contents"); String
		 * response = scanner.nextLine(); if(response.equalsIgnoreCase("I")) { //Call
		 * JSON Parser }
		 */

		Scanner input = new Scanner(System.in);
		int number = 0;
		boolean isNumber;

		System.out.println("Enter a whole number please");
		do {

			if (input.hasNextInt()) {
				number = input.nextInt();
				isNumber = true;
			} else {
				System.out.println("I do not understand you");
				isNumber = false;
				input.next();
			}
		} while (!(isNumber));
		System.out.println("you entered  " + number + "number");
	}
}
