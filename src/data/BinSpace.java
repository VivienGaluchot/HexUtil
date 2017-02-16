package data;

import java.util.TreeMap;

public class BinSpace {
	private TreeMap<Integer, HexLine> addressMap;

	private int currentAddressReference;

	public BinSpace() {
		addressMap = new TreeMap<>();
		currentAddressReference = 0;
	}

	public void processFile(HexFile file) {
		for (HexLine l : file.getLines()) {
			if ((l.getType() == 0x04) && l.getSize() == 2) {
				byte[] data = l.getData();
				currentAddressReference = (Byte.toUnsignedInt(data[0]) << 8) + Byte.toUnsignedInt(data[1]);
			} else if (l.getType() == 0x00) {
				short address = l.getAddress();
				int trueAddress = (currentAddressReference << 16) + address;
				addressMap.put(trueAddress, l);
			} else if (l.getType() == 0x01) {
				// TODO
			} else {
				System.out.println("Unsupported line " + l);
			}
		}
	}

	public void printConnexBlocs() {
		Integer lastAddr = null;
		for (Integer addr : addressMap.keySet()) {
			if (lastAddr == null) {
				System.out.print(Integer.toHexString(addr) + " -> ");
			} else if (addr > lastAddr + 1) {
				System.out.println(Integer.toHexString(lastAddr));
				System.out.print(Integer.toHexString(addr) + " -> ");
			}
			lastAddr = addr + addressMap.get(addr).getSize();
		}
		System.out.println(Integer.toHexString(lastAddr));
	}

	public static void main(String[] args) {
		HexFile x = new HexFile();
		x.loadFromFile(args[0]);
		// System.out.println(x);
		System.out.println("Validity :" + x.isValid());

		BinSpace sp = new BinSpace();
		sp.processFile(x);
		sp.printConnexBlocs();
	}
}
