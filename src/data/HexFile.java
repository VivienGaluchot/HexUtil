package data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import data.processor.ConnexityFinder;
import data.processor.CrcChecker;
import data.processor.Printer;

public class HexFile {
	ArrayList<LineProcessor> processors;

	public HexFile() {
		processors = new ArrayList<>();
	}

	public void addProcessor(LineProcessor pr) {
		processors.add(pr);
	}

	public void processFile(String fileName) {
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.forEach(this::process);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void process(String strLine) {
		HexLine line = new HexLine(strLine);
		for (LineProcessor pr : processors)
			pr.processLine(line);
	}

	public static void main(String[] args) {
		HexFile x = new HexFile();
		x.addProcessor(new Printer());

		CrcChecker checker = new CrcChecker();
		checker.init();
		x.addProcessor(checker);

		ConnexityFinder cf = new ConnexityFinder();
		cf.init();
		x.addProcessor(cf);

		x.processFile("test.hex");

		System.out.println(checker.getMsg());
		cf.displayConnexities();
	}
}
