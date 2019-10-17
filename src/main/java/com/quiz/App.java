package com.quiz;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {

	private static Scanner scanner;

	public static void main(String[] args) throws IOException {

		// Start - input stream
		/*
		 *  2[abc]3[ab]c
		 *  10[a]c2[ab]
		 *  2[3[a]b]
		 */
		scanner = new Scanner(System.in);
		System.out.print("Input : ");

		String inputString = scanner.nextLine();
		System.out.println("Your input : " + inputString);
		// End - input stream

		// Start - Check pattern string.  
		LinkedList<Integer> posFisrt = new LinkedList<Integer>();
		LinkedList<Integer> posLast = new LinkedList<Integer>();
		LinkedList<Integer> pos = new LinkedList<Integer>();

		
		// Will change to (\d*+\[+\w*+\]) and loop for nested. --\d*+(\[+\w*+\])
		String pattern = "(\\[|\\])";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(inputString);
		String preFlag = "";
		boolean type = false; // t = nested , f = false

		while (m.find()) {
//			System.out.println("Match number " + m.group());
//			System.out.println("start(): " + m.start());
//			System.out.println("end(): " + m.end());

			if (m.group().contentEquals("[")) {
				if (preFlag.equals("[")) {
					posFisrt.addFirst(m.start());
					type = true;
				} else {
					posFisrt.add(m.start());
				}
				preFlag = "[";
			} else {
				if (preFlag.equals("]")) {
					posLast.addFirst(m.start());
				} else {
					if (type)
						posLast.add(m.start());
					else
						posFisrt.add(m.start());
				}
				preFlag = "]";
			}
		}

		// merge it.
		pos.addAll(posFisrt);
		pos.addAll(posLast);

		// End - Check pattern string.
		if (!pos.isEmpty() && (pos.size() % 2) == 0) {

			pos.stream().forEach(System.out::println);
			// Start - process.
			System.out.println("total [] is " + pos.size() / 2);
			StringBuffer sb = new StringBuffer();
			int bFirst;
			int bPreLast = 0;
			int bLast;
			while (!pos.isEmpty()) {

				if (type) { // nested []
					bFirst = pos.pollFirst();
					bLast = pos.pollLast();
					System.out.println(inputString.substring(bFirst + 1, bLast));
				} else { // normal []
					bFirst = pos.pollFirst();
					bLast = pos.pollFirst();
					System.out.println("in [] " + inputString.substring(bFirst + 1, bLast));
					
					//get index bPreLast - bFirst
					System.out.println("pre [] :" + inputString.substring(bPreLast, bFirst));
					
					//(\d*$) find pettern agian.
					
					bPreLast = bLast + 1;
				}

			}
			// End - process.

		} else {
			System.err.println("Input was wrong pattern.");
		}

		// Close it.
		scanner.close();
	}
}
