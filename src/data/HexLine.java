package data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HexLine {
	private byte[] bytes;

	private final static Pattern pattern = Pattern
			.compile("^:([0-9a-fA-F]{2})([0-9a-fA-F]{4})([0-9a-fA-F]{2})([0-9a-fA-F]*)([0-9a-fA-F]{2})$");

	public HexLine(String line) {
		setFromStr(line);
	}

	public void setFromStr(String line) throws IllegalArgumentException {
		Matcher matcher = pattern.matcher(line);
		if (!matcher.find() || matcher.groupCount() != 5)
			throw new IllegalArgumentException("Can't match line");

		byte dataSize = Byte.parseByte(matcher.group(1), 16);
		setSize(dataSize);

		// size
		Tools.stringToHex(matcher.group(1), bytes, 0, 1);
		// address
		Tools.stringToHex(matcher.group(2), bytes, 1, 2);
		// type
		Tools.stringToHex(matcher.group(3), bytes, 3, 1);
		// data
		Tools.stringToHex(matcher.group(4), bytes, 4, dataSize);
		// checksum
		Tools.stringToHex(matcher.group(5), bytes, 4 + dataSize, 1);

	}

	public void setSize(byte size) {
		bytes = new byte[size + 5];
		bytes[0] = size;
	}

	public byte getSize() {
		return bytes[0];
	}

	public void setAddress(short address) {
		bytes[1] = (byte) (address >> 8);
		bytes[2] = (byte) (address & 0xFF);
	}

	public short getAddress() {
		return (short) ((Byte.toUnsignedInt(bytes[1]) << 8) + Byte.toUnsignedInt(bytes[2]));
	}

	public void setType(byte type) {
		bytes[3] = type;
	}

	public byte getType() {
		return bytes[3];
	}

	public void setData(byte[] datas) {
		if (datas.length != getSize())
			throw new IllegalArgumentException("Wrong data size");
		for (int i = 0; i < datas.length; i++)
			bytes[i + 4] = datas[i];
	}

	public byte[] getData() {
		byte[] datas = new byte[getSize()];
		for (int i = 0; i < datas.length; i++)
			datas[i] = bytes[i + 4];
		return datas;
	}

	public void setChecksum(byte checksum) {
		bytes[4 + getSize()] = checksum;
	}

	public void computeChecksum() {
		byte sum = 0;
		for (int i = 0; i < bytes.length - 1; i++) {
			sum += bytes[i];
		}
		setChecksum((byte) (-sum));
	}

	public byte getChecksum() {
		return bytes[4 + getSize()];
	}

	public boolean isValid() {
		byte sum = 0;
		for (int i = 0; i < bytes.length; i++) {
			sum += bytes[i];
		}
		return sum == 0;
	}

	@Override
	public String toString() {
		return ":" + Tools.hexToString(bytes);
	}

	public static void main(String[] args) {
		HexLine x = new HexLine(":10010000214601360121470136007EFE09D2190140");
		System.out.println(x);
		x = new HexLine(":10010000214601360121470136007EFE09D2190140");
		System.out.println(x);
		System.out.println(x.getSize());
		System.out.println(x.getAddress());
		System.out.println(x.getType());
		System.out.println(Tools.hexToString(x.getData()));
		System.out.println(x.getChecksum());
		System.out.println(x.isValid());
		x = new HexLine(":100110002146017EB7C20001FF5F16002148011988");
		System.out.println(x);
		System.out.println(x.isValid());
		x = new HexLine(":10012000194E79234623965778239EDA3F01B2CAA7");
		System.out.println(x);
		System.out.println(x.isValid());
		x = new HexLine(":100130003F0156702B5E712B722B732146013421C7");
		System.out.println(x);
		System.out.println(x.isValid());
		x = new HexLine(":100130003F0156702B5E712B722B732146013421C8");
		System.out.println(x);
		System.out.println(x.isValid());
		x.computeChecksum();
		System.out.println(x);
		System.out.println(x.isValid());
		x = new HexLine(":00000001FF");
		System.out.println(x);
		System.out.println(x.isValid());
	}
}
