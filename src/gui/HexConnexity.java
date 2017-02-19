package gui;

import data.HexFile;
import data.processor.ConnexityFinder;
import data.processor.CrcChecker;

public class HexConnexity {
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Usage : HexConnextity [-onlySize] [fileName]");
			return;
		}

		boolean onlySize = args[0].compareTo("-onlySize") == 0;

		String fileName;
		if (onlySize)
			fileName = args[1];
		else
			fileName = args[0];

		HexFile x = new HexFile();

		CrcChecker checker = new CrcChecker();
		checker.init();
		x.addProcessor(checker);

		ConnexityFinder cf = new ConnexityFinder();
		cf.init();
		x.addProcessor(cf);

		x.processFile(fileName);

		if (onlySize) {
			System.out.println(cf.getTotalConnextitiesSize());
		} else {
			cf.displayConnexities();
			System.out.println(cf.getTotalConnextitiesSize());
		}

		if (checker.getResult() == false)
			System.out.println(checker.getMsg());
	}
}
