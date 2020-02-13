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
        int total = 0;
        char[] input = skus.toCharArray();
        for(char item : input) {
        	if(skuCount.containsKey(item)) {
        		int val = skuCount.get(item) + 1;
        		skuCount.put(item, val);
        	}else
        		skuCount.put(item, 1);
        }
        for(Map.Entry<Character, Integer> entry : skuCount.entrySet()) {
        	int rate = rates[entry.getKey()];
        	if(entry.getKey() == 'A') {
        		total += (entry.getValue()/3)*130 + (entry.getValue()%3)*rate;
        	}else if(entry.getKey() == 'B') {
        		total += (entry.getValue()/2)*45 + (entry.getValue()%2)*rate;
        	}else {
        		total += entry.getValue() * rate;
        	}
        }
        return total;
    }
}
