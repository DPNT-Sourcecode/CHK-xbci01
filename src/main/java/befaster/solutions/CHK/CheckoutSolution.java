package befaster.solutions.CHK;

import java.util.HashMap;
import java.util.Map;

public class CheckoutSolution {
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
        for(char item : input) {
        	if(item < 65 || item > 70)
        		return -1;
        	if(skuCount.containsKey(item)) {
        		int val = skuCount.get(item) + 1;
        		skuCount.put(item, val);
        	}else
        		skuCount.put(item, 1);
        }
        for(Map.Entry<Character, Integer> entry : skuCount.entrySet()) {
        	int rate = rates[entry.getKey()];
        	if(entry.getKey() == 'A') {
        		total += (entry.getValue()/5)*200+ ((entry.getValue()%5)/3)*130 + ((entry.getValue()%5)%3)*rate;
        	}else if(entry.getKey() == 'B') {        		
        		int noOfBs = entry.getValue();
        		if(skuCount.containsKey('E'))
        			noOfBs -= skuCount.get('E')/2;        		
        		noOfBs = noOfBs>0? noOfBs : 0;
        		total += (noOfBs/2)*45 + (noOfBs%2)*rate;
        	}else if(entry.getKey() == 'F') {
        		total += (entry.getValue()/2)*rate +(entry.getValue()%2)*rate;
        	}
        	else {
        		total += entry.getValue() * rate;
        	}
        }
        return total;
    }
}

