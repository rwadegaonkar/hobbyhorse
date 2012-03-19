package com.spring.conf;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
	public static void main(String args[]) throws ParseException {
		DateFormat formatter;
		formatter = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss");
		String[] newDateArray = new String("Mon Mar 12 23:12:45 PDT 2012").split("\\s");
		Date date = (Date) formatter.parse(new String(newDateArray[1]
				+ " " + newDateArray[2] + ", " + newDateArray[5] + " "
				+ newDateArray[3]));
		System.out.println(date);
	}
}
