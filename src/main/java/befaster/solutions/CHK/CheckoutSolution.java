package befaster.solutions.CHK;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckoutSolution {
	public int[] rates = new int[128];
	public int total = 0;
	public Map<Character, List<Offer>> offerMap = new HashMap<>();
	public Map<Character, MetaData> skuCount = new HashMap<>();

	public Integer checkout(String skus) {
		total = 0;
		offerMap = new HashMap<>();
		skuCount = new HashMap<>();
		generateRates();
		generateOfferMap();
		char[] input = skus.toCharArray();
		for (char item : input) {
			if (item < 65 || item > 90)
				return -1;
			if (skuCount.containsKey(item)) {
				MetaData val = skuCount.get(item);
				val.setCount(val.getCount() + 1);
				skuCount.put(item, val);
			} else
				skuCount.put(item, new MetaData(1, 0));
		}
		this.applyAnyThreeOffer();
		for (Map.Entry<Character, MetaData> entry : skuCount.entrySet()) {
			int rate = rates[entry.getKey()];
			MetaData data = entry.getValue();
			int noOfItems = data.getCount();
			if (offerMap.containsKey(entry.getKey())) {
				List<Offer> offers = offerMap.get(entry.getKey());
				for (Offer offer : offers) {
					if (offer instanceof CountOffer) {
						CountOffer countOffer = (CountOffer) offer;
						noOfItems = this.applyCountOffers(countOffer, noOfItems, data);
					} else if (offer instanceof FreeOffer) {
						FreeOffer freeOffer = (FreeOffer) offer;
						noOfItems = this.applyFreeOffer(freeOffer, noOfItems, rate, data);
					}
				}
			}
			data.setValue(data.getValue() + noOfItems * rate);
			total += noOfItems * rate;
		}

		/*
		 * for (Map.Entry<Character, Integer> entry : skuCount.entrySet()) { int rate =
		 * rates[entry.getKey()]; if (entry.getKey() == 'A') { total +=
		 * (entry.getValue() / 5) * 200 + ((entry.getValue() % 5) / 3) * 130 +
		 * ((entry.getValue() % 5) % 3) * rate; } else if (entry.getKey() == 'B') { int
		 * noOfBs = entry.getValue(); if (skuCount.containsKey('E')) noOfBs -=
		 * skuCount.get('E') / 2; noOfBs = noOfBs > 0 ? noOfBs : 0; total += (noOfBs /
		 * 2) * 45 + (noOfBs % 2) * rate; } else if (entry.getKey() == 'F') { total +=
		 * ((entry.getValue() / 3) * 2 + (entry.getValue() % 3)) * rate; } else { total
		 * += entry.getValue() * rate; } }
		 */
		return total;
	}

	public void generateOfferMap() {
		offerMap.put('A', Arrays.asList(new Offer[] { new CountOffer(5, 200), new CountOffer(3, 130) }));
		offerMap.put('B', Arrays.asList(new Offer[] { new CountOffer(2, 45) }));

		offerMap.put('E', Arrays.asList(new Offer[] { new FreeOffer(2, 'E', 'B') }));
		offerMap.put('F', Arrays.asList(new Offer[] { new FreeOffer(2, 'F', 'F') }));

		offerMap.put('H', Arrays.asList(new Offer[] { new CountOffer(10, 80), new CountOffer(5, 45) }));
		offerMap.put('K', Arrays.asList(new Offer[] { new CountOffer(2, 150) }));

		offerMap.put('N', Arrays.asList(new Offer[] { new FreeOffer(3, 'N', 'M') }));
		offerMap.put('P', Arrays.asList(new Offer[] { new CountOffer(5, 200) }));

		offerMap.put('Q', Arrays.asList(new Offer[] { new CountOffer(3, 80) }));
		offerMap.put('R', Arrays.asList(new Offer[] { new FreeOffer(3, 'R', 'Q') }));
		
		offerMap.put('U', Arrays.asList(new Offer[] { new FreeOffer(3, 'U', 'U') }));
		offerMap.put('V', Arrays.asList(new Offer[] { new CountOffer(3, 130), new CountOffer(2, 90) }));
	}

	public void generateRates() {
		rates['A'] = 50;
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
	
	public void applyAnyThreeOffer() {
		AnyThree offer = new AnyThree();
		char[] products = offer.getAnyThreeOfferProducts();
		int count = 0;
		for(char prod : products) {
			if(skuCount.containsKey(prod)) {
				if(skuCount.get(prod) != null) {
					MetaData data = skuCount.get(prod);
					count += data.getCount();
				}
			}
		}
		total += (count/3)*offer.getRate();
		count = count - count%3;
		if(count == 0)
			return;
		for(char prod: products) {
			if(skuCount.containsKey(prod)) {
				if(skuCount.get(prod) != null) {
					MetaData data = skuCount.get(prod);					
					if(count==0)
						break;
					else if(data.getCount() >= count) {
						data.setCount((data.getCount()-count));
						skuCount.put(prod, data);
						break;
					}else {
						count -= data.getCount();
						data.setCount(0);
						skuCount.put(prod, data);
					}
				}
			}
		}
	}

	public int applyCountOffers(CountOffer countOffer, int noOfItems, MetaData data) {
		data.setValue(data.getValue() + (noOfItems / countOffer.getCount()) * countOffer.getRate());
		total += (noOfItems / countOffer.getCount()) * countOffer.getRate();
		noOfItems = noOfItems % countOffer.getCount();
		return noOfItems;
	}

	public int applyFreeOffer(FreeOffer freeOffer, int noOfItems, int rate, MetaData data) {
		if (freeOffer.getMainProduct() == freeOffer.getOfferProduct()) {
			data.setValue(data.getValue() + ((noOfItems / (freeOffer.getCount() + 1)) * freeOffer.getCount()
					+ (noOfItems % (freeOffer.getCount() + 1))) * rate);
			total += ((noOfItems / (freeOffer.getCount() + 1)) * freeOffer.getCount()
					+ (noOfItems % (freeOffer.getCount() + 1))) * rate;
		} else {
			MetaData computedValue = skuCount.get(freeOffer.getOfferProduct());
			if (computedValue != null) {
				int offerProductCount = computedValue.getCount();// bcount
				int count = noOfItems / freeOffer.getCount(); // Ecount

				if (offerProductCount > 0) {
					total -= computedValue.getValue();
					total += noOfItems * rate;
					offerProductCount = offerProductCount > count ? offerProductCount - count : 0;
					if (offerMap.containsKey(freeOffer.getOfferProduct())) {
						List<Offer> offers = offerMap.get(freeOffer.getOfferProduct());
						for (Offer offer : offers) {
							if (offer instanceof CountOffer) {
								CountOffer countOffer = (CountOffer) offer;
								offerProductCount = this.applyCountOffers(countOffer, offerProductCount, data);
							} else if (offer instanceof FreeOffer) {
								FreeOffer freeOfferNew = (FreeOffer) offer;
								offerProductCount = this.applyFreeOffer(freeOfferNew, offerProductCount,
										rates[freeOffer.getOfferProduct()], data);
							}
						}
					}
					total += offerProductCount * rates[freeOffer.getOfferProduct()];
					skuCount.put(freeOffer.getOfferProduct(), new MetaData(offerProductCount, 0));
				}
			}else
				return noOfItems;
		}
		return 0;
	}

	interface Offer {

	}
	class AnyThree implements Offer{
		final char[] offerProducts;
		int rate;
		
		public AnyThree() {
			offerProducts = new char[]{'Z','S','T','Y','X'};
			rate = 45; 
		}
		
		public char[] getAnyThreeOfferProducts() {
			return offerProducts;
		}
		public int getRate() {
			return rate;
		}
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

	class MetaData {
		public MetaData() {
		};

		public MetaData(int count, int value) {
			this.value = value;
			this.count = count;
		}

		private int count;
		private int value;

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}
	}
}



