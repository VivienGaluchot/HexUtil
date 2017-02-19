package data;

public class AddressBlock {
	private int start;
	private int end;

	public AddressBlock(int start, int end) {
		this.start = start;
		this.end = end;
	}

	public AddressBlock(HexLine line, HexLine relativeAddress) {
		int relative = 0;
		if (relativeAddress != null) {
			byte[] data = relativeAddress.getData();
			relative = (Byte.toUnsignedInt(data[0]) << 8) + Byte.toUnsignedInt(data[1]);
		}
		Short address = line.getAddress();
		int trueAddress = (relative << 16) + Short.toUnsignedInt(address);

		int size = Byte.toUnsignedInt(line.getSize());

		if (Integer.compareUnsigned(trueAddress + size, trueAddress) > 0) {
			start = trueAddress;
			end = trueAddress + size - 1;
		} else {
			System.out.println("Debordement des int");
		}
		if (start == 0xffff8000)
			System.out.println("x");
	}

	public boolean isConnex(AddressBlock b) {
		if ((!equalUnsigned(end, -1) && equalUnsigned(b.start, end + 1))
				|| (!equalUnsigned(start, 0) && equalUnsigned(b.end, start - 1)))
			return true;

		// align 4
		if (Integer.remainderUnsigned(start, 4) == 0 && Integer.compareUnsigned(start, b.end + 3) <= 0
				&& Integer.compareUnsigned(start, b.end) > 0)
			return true;
		if (Integer.remainderUnsigned(b.start, 4) == 0 && Integer.compareUnsigned(b.start, end + 3) <= 0
				&& Integer.compareUnsigned(b.start, end) > 0)
			return true;

		// align 2
		if (Integer.remainderUnsigned(start, 2) == 0 && Integer.compareUnsigned(start, b.end + 2) <= 0
				&& Integer.compareUnsigned(start, b.end) > 0)
			return true;
		if (Integer.remainderUnsigned(b.start, 2) == 0 && Integer.compareUnsigned(b.start, end + 2) <= 0
				&& Integer.compareUnsigned(b.start, end) > 0)
			return true;

		return false;
	}

	private boolean equalUnsigned(int a, int b) {
		return Integer.compareUnsigned(a, b) == 0;
	}

	public void absorb(AddressBlock b) {
		if (Integer.compare(b.start, start) < 0)
			start = b.start;
		if (Integer.compare(b.end, end) > 0)
			end = b.end;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}

	public int getSize() {
		return end - start + 1;
	}

	@Override
	public String toString() {
		return "0x" + Integer.toHexString(start) + "->" + "0x" + Integer.toHexString(end) + " (" + getSize() + ")";
	}
}
