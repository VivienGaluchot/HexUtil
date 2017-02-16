package data;

import java.util.HashMap;
import java.util.Map;

public class BinSpace {
	private HexFile file;
	
	private short lowAddress;
	private short highAddress;
	
	private Map<Short, HexLine> addressMap;
	
	private short currentAddressReference;

	public BinSpace(HexFile file) {
		this.file = file;
		addressMap = new HashMap<>();
		currentAddressReference = 0;
	}
	
	public void processFile(HexFile file){
		for(HexLine l : file.getLines()){
			
		}
	}
	
	public static void main(String[] args) {
		HexFile x = new HexFile();
		x.loadFromFile("test.hex");
		System.out.println(x);
		System.out.println(x.isValid());
	}
}
