package data;

public class Tools {
	private static String digits = "0123456789ABCDEF";

	public static String hexToString(byte[] data) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i != data.length; i++) {
			int v = data[i] & 0xff;

			buf.append(digits.charAt(v >> 4));
			buf.append(digits.charAt(v & 0xf));
		}
		return buf.toString();
	}

	public static void stringToHex(String hexNumber, byte[] buffer, int start, int size) throws IllegalArgumentException {
		hexNumber = hexNumber.toUpperCase();
		int length = hexNumber.length();
		if (length % 2 != 0)
			hexNumber = '0' + hexNumber;
		if (hexNumber.length() != size * 2)
			throw new IllegalArgumentException("Wrong number size : " + hexNumber);
		if (buffer.length < start + size)
			throw new IllegalArgumentException("Number will not fit in the buffer");

		for (int i = 0; i < size; i++) {
			byte a;
			for (a = 0; a < 16; a++) {
				if (digits.charAt(a) == hexNumber.charAt(2 * i))
					break;
			}
			byte b;
			for (b = 0; b < 16; b++) {
				if (digits.charAt(b) == hexNumber.charAt(2 * i + 1))
					break;
			}
			if (digits.charAt(a) != hexNumber.charAt(2 * i) || digits.charAt(b) != hexNumber.charAt(2 * i + 1))
				throw new IllegalArgumentException("Not a number");

			buffer[i + start] = (byte) ((a << 4) + b);
		}
	}
}
