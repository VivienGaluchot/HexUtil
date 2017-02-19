package data.processor;

import data.HexLine;
import data.LineProcessor;

public class Printer implements LineProcessor {

	@Override
	public void processLine(HexLine l) {
		System.out.println(l);
	}

}
