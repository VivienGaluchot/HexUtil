package data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class HexFile {

	private List<HexLine> lines;

	public HexFile() {
		lines = new ArrayList<>();
	}
	
	public void loadFromFile(String fileName){
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.forEach(this::addLine);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addLine(String line) {
		lines.add(new HexLine(line));
	}

	public List<HexLine> getLines() {
		return Collections.unmodifiableList(lines);
	}

	public boolean isValid() {
		for (HexLine l : lines)
			if (!l.isValid())
				return false;
		return true;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer();
		for (HexLine l : lines)
			buf.append(l + "\n");
		return buf.toString();
	}

	public static void main(String[] args) {
		HexFile x = new HexFile();
		x.loadFromFile("test.hex");
		System.out.println(x);
		System.out.println(x.isValid());
	}
}
