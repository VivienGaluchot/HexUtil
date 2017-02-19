package data.processor;

import java.util.ArrayList;

import data.HexLine;
import data.LineProcessor;

public class CrcChecker implements LineProcessor {

	private boolean valid;
	private ArrayList<Integer> errorLines;
	private int i;

	public CrcChecker() {
		valid = false;
		errorLines = new ArrayList<>();
	}

	public void init() {
		i = 0;
		valid = true;
		errorLines.clear();
	}

	public boolean getResult() {
		return valid;
	}

	public String getMsg() {
		if (valid)
			return "Valid file";
		else {
			String r = "Error at lines : ";
			for (Integer it : errorLines)
				r = r + " " + it;
			return r;
		}
	}

	@Override
	public void processLine(HexLine l) {
		i++;
		if (!l.isValid()) {
			valid = false;
			errorLines.add(i);
		}
	}

}
