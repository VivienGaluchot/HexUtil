package data;

public class AddressBlock {
	private int start;
	private int end;

	public AddressBlock(int start, int end) {
		this.start = start;
		this.end = end;
	}

	public AddressBlock(HexLine line, HexLine relativeAddress) {
		int trueAddress = line.getAddressRelativeTo(relativeAddress);
		int size = line.getIntSize();
		if (Integer.compareUnsigned(trueAddress + size, trueAddress) > 0) {
			start = trueAddress;
			end = trueAddress + size - 1;
		} else {
			System.out.println("Debordement des int");
		}
	}

	public boolean isConnex(AddressBlock b) {
		if ((!equalUnsigned(end, -1) && equalUnsigned(b.start, end + 1))
				|| (!equalUnsigned(start, 0) && equalUnsigned(b.end, start - 1)))
			return true;

		// superposition
		if (Integer.compareUnsigned(end, b.start) > 0 && Integer.compareUnsigned(b.end, start) > 0)
			return true;

		// align 4
		if (Integer.remainderUnsigned(start, 4) == 0 && Integer.compareUnsigned(start, b.end + 4) <= 0
				&& Integer.compareUnsigned(start, b.end) > 0)
			return true;
		if (Integer.remainderUnsigned(b.start, 4) == 0 && Integer.compareUnsigned(b.start, end + 4) <= 0
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

	public int getAlignedEnd() {
		return (Integer.divideUnsigned(end, 4) + 1) * 4 - 1;
	}

	public int getAlignedSize() {
		return getAlignedEnd() - start + 1;

	}

	@Override
	public String toString() {
		if (end != getAlignedEnd())
			return "0x" + Integer.toHexString(start) + "->" + "0x" + Integer.toHexString(end) + "/0x"
					+ Integer.toHexString(getAlignedEnd()) + " : " + getSize() + "/" + getAlignedSize();
		else
			return "0x" + Integer.toHexString(start) + "->" + "0x" + Integer.toHexString(end) + " : " + getSize();
	}
}
