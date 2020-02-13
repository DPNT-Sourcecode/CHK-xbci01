package befaster.solutions.CHK;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckoutSolution {
	int[] rates = new int[128];
	int total = 0;
	Map<Character, List<Offer>> offerMap = new HashMap<>();
	public Integer checkout(String skus) {
		Map<Character, Integer> skuCount = new HashMap<>();
		int[] rates = new int[128];
		rates['A'] = 50;
		rates['B'] = 30;
		rates['C'] = 20;
		rates['D'] = 15;
		rates['E'] = 40;
		rates['F'] = 10;
		int total = 0;
		char[] input = skus.toCharArray();
		for (char item : input) {
			if (item < 65 || item > 70)
				return -1;
			if (skuCount.containsKey(item)) {
				int val = skuCount.get(item) + 1;
				skuCount.put(item, val);
			} else
				skuCount.put(item, 1);
		}
		for (Map.Entry<Character, Integer> entry : skuCount.entrySet()) {
			int rate = rates[entry.getKey()];
			if (entry.getKey() == 'A') {
				total += (entry.getValue() / 5) * 200 + ((entry.getValue() % 5) / 3) * 130
						+ ((entry.getValue() % 5) % 3) * rate;
			} else if (entry.getKey() == 'B') {
				int noOfBs = entry.getValue();
				if (skuCount.containsKey('E'))
					noOfBs -= skuCount.get('E') / 2;
				noOfBs = noOfBs > 0 ? noOfBs : 0;
				total += (noOfBs / 2) * 45 + (noOfBs % 2) * rate;
			} else if (entry.getKey() == 'F') {
				total += ((entry.getValue() / 3) * 2 + (entry.getValue() % 3)) * rate;
			} else {
				total += entry.getValue() * rate;
			}
		}
		return total;
	}

	private void generateOfferMap() {
		offerMap.put('A', Arrays.asList(new Offer[] {new CountOffer(5,200), new CountOffer(3,130)}));
		offerMap.put('B', Arrays.asList(new Offer[] {new CountOffer(2,45)}));
		
		offerMap.put('E', Arrays.asList(new Offer[] {new FreeOffer(2, 'E', 'B')}));
		offerMap.put('F', Arrays.asList(new Offer[] {new FreeOffer(2, 'F', 'F')}));
		
		offerMap.put('H', Arrays.asList(new Offer[] {new CountOffer(10, 80),new CountOffer(5, 45)}));
		offerMap.put('K', Arrays.asList(new Offer[] {new CountOffer(2, 150)}));
		
		offerMap.put('N', Arrays.asList(new Offer[] {new FreeOffer(3, 'N', 'M')}));
		offerMap.put('P', Arrays.asList(new Offer[] {new CountOffer(5, 200)}));
		
		offerMap.put('Q', Arrays.asList(new Offer[] {new CountOffer(3, 80)}));
		offerMap.put('R', Arrays.asList(new Offer[] {new FreeOffer(3, 'R', 'Q')}));
		offerMap.put('U', Arrays.asList(new Offer[] {new FreeOffer(3, 'U','U')}));
		offerMap.put('V', Arrays.asList(new Offer[] {new CountOffer(3, 130), new CountOffer(2, 90)}));
	}	
	private void generateRates() {
		rates['B'] = 30;
		rates['C'] = 20;
		rates['D'] = 15;
		rates['E'] = 40;
		rates['F'] = 10;
		rates['G'] = 20;
		rates['H'] = 10;
		rates['I'] = 35;
		rates['J'] = 60;
		rates['K'] = 80;
		rates['L'] = 90;
		rates['M'] = 15;
		rates['N'] = 40;
		rates['O'] = 10;
		rates['P'] = 50;
		rates['Q'] = 30;
		rates['R'] = 50;
		rates['S'] = 30;
		rates['T'] = 20;
		rates['U'] = 40;
		rates['V'] = 50;
		rates['W'] = 20;
		rates['X'] = 90;
		rates['Y'] = 10;
		rates['Z'] = 50;
	}

	private int applyCountOffers(CountOffer countOffer, int noOfItems, int rate) {
		total += (noOfItems / countOffer.getCount()) * countOffer.getRate();
		noOfItems = noOfItems % countOffer.getCount();
		return noOfItems;
	}

	private void applyFreeOffer(FreeOffer freeOffer, int noOfItems, int rate, Map<Character, Integer> skuCount,
			int[] rates) {
		if (freeOffer.getMainProduct() == freeOffer.getOfferProduct()) {
			total += ((noOfItems / (freeOffer.getCount() + 1)) * freeOffer.getCount()
					+ (noOfItems % (freeOffer.getCount() + 1))) * rate;
		} else {
			int OfferProductCount = skuCount.get(freeOffer.getOfferProduct());
			int count = noOfItems / freeOffer.getCount();
			count = count > OfferProductCount ? OfferProductCount : count;
			total += noOfItems * rate - count * rates[freeOffer.getOfferProduct()];
		}
	}

	interface Offer {

	}

	class CountOffer implements Offer {
		private int count;
		private int rate;

		public CountOffer() {

		}

		public CountOffer(int count, int rate) {
			this.count = count;
			this.rate = rate;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

		public int getRate() {
			return rate;
		}

		public void setRate(int rate) {
			this.rate = rate;
		}
	}

	class FreeOffer implements Offer {
		private int count;
		private char mainProduct;
		private char offerProduct;

		public FreeOffer() {
		}

		public FreeOffer(int count, char mainProduct, char offerProduct) {
			this.count = count;
			this.mainProduct = mainProduct;
			this.offerProduct = offerProduct;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

		public char getMainProduct() {
			return mainProduct;
		}

		public void setMainProduct(char mainProduct) {
			this.mainProduct = mainProduct;
		}

		public char getOfferProduct() {
			return offerProduct;
		}

		public void setOfferProduct(char offerProduct) {
			this.offerProduct = offerProduct;
		}
	}
}

