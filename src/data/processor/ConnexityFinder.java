package data.processor;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;

import data.AddressBlock;
import data.HexLine;
import data.LineProcessor;

public class ConnexityFinder implements LineProcessor {

	HashSet<AddressBlock> blocks;
	HexLine currentRelativeAddress;

	public ConnexityFinder() {
		blocks = new HashSet<>();
	}

	public void init() {
		blocks.clear();
		currentRelativeAddress = null;
	}

	private void insert(AddressBlock insertBlock) {
		if (Integer.compareUnsigned(insertBlock.getStart(), insertBlock.getEnd()) > 0) {
			System.out.println("Invalid address block : " + insertBlock);
			return;
		}

		Iterator<AddressBlock> it = blocks.iterator();
		AddressBlock b;
		while (it.hasNext()) {
			b = it.next();
			if (b.isConnex(insertBlock)) {
				b.absorb(insertBlock);
				it.remove();
				insert(b);
				return;
			}
		}
		blocks.add(insertBlock);
	}

	public void displayConnexities() {
		AddressBlock[] array = new AddressBlock[blocks.size()];
		array = blocks.toArray(array);
		Arrays.sort(array, new Comparator<AddressBlock>() {
			@Override
			public int compare(AddressBlock arg0, AddressBlock arg1) {
				return Integer.compareUnsigned(arg0.getStart(), arg1.getStart());
			}
		});

		for (AddressBlock a : array) {
			System.out.println(a);
		}
	}

	public int getTotalConnextitiesSize() {
		int size = 0;
		for (AddressBlock a : blocks)
			size += a.getAlignedSize();
		return size;
	}

	@Override
	public void processLine(HexLine l) {
		if (l.getType() == 0x04 && l.getSize() == 2) {
			currentRelativeAddress = l;
		} else if (l.getType() == 0x00) {
			AddressBlock c = new AddressBlock(l, currentRelativeAddress);
			insert(c);
		} else if (l.getType() == 0x01) {
			// TODO
			System.out.println("Unsupported line " + l);
		} else {
			System.out.println("Unsupported line " + l);
		}
	}

	public static void main(String[] args) {
		ConnexityFinder cf = new ConnexityFinder();
		cf.init();
		cf.insert(new AddressBlock(0, 3));
		cf.insert(new AddressBlock(6, 7));
		cf.insert(new AddressBlock(8, 12));
		cf.insert(new AddressBlock(14, 15));
		cf.displayConnexities();
		System.out.println(cf.getTotalConnextitiesSize());
	}
}
