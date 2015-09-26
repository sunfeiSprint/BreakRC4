package findKey;

import java.util.HashMap;
import java.util.Map;

/*
 * this class keep track of the frequency
 * 
 * guess will return most frequent 3 possible key
 * */

public class Analysis {
	
	int[] keyFrequency;
	
	double threadHold = 1.3;//TODO, Adjust threadHold

	HashMap<Integer,Integer> guessedKey;

	public Analysis() {
		keyFrequency = new int[90];
		guessedKey = new HashMap();
	}
	
	public void initFreq(){
		for (int i=0;i<90;i++){
		this.keyFrequency[i] = 0;
		}
	}
	
	public void addFrequency(int findKey){
		this.keyFrequency[findKey] = this.keyFrequency[findKey] + 1;		
	}
	
	public HashMap guess(){
		int Rank1 = -1;
		int Rank2 = -1;
		int Rank3 = -1;
		int Rank1Freq = 0;
		int Rank2Freq = 0;
		int Rank3Freq = 0;
		
//		for (int i = 0; i<keyFrequency.length;i++){
//			System.out.println("Rank1 " + i + "appears " + keyFrequency[i] +" times");
//		}
//				
		for (int i = 0; i<keyFrequency.length;i++){
			if (keyFrequency[i]>=Rank1Freq){
				Rank3 = Rank2;
				Rank2 = Rank1;
				Rank1 = i;
				
				Rank3Freq = Rank2Freq;
				Rank2Freq = Rank1Freq;
				Rank1Freq = keyFrequency[i];
			}else if (keyFrequency[i]<Rank1Freq&&keyFrequency[i]>=Rank2Freq){
				Rank3 = Rank2;
				Rank2 = i;
				
				Rank3Freq = Rank2Freq;
				Rank2Freq = keyFrequency[i];
			}else if (keyFrequency[i]<Rank2Freq&&keyFrequency[i]>=Rank3Freq){
				Rank3 = i;
				Rank3Freq = keyFrequency[i];
			}
			
		}
		
		guessedKey.put(1, Rank1);
		//check if Rank2, Rank3 Frequency more than threadhold
//		double test = (double)Rank1Freq/Rank2Freq;

//		if (test <= threadHold){
		guessedKey.put(2, Rank2);
//		}
//		test = (double)Rank1Freq/Rank3Freq;

//		if (test <= threadHold){
		guessedKey.put(3, Rank3);
//		}	
		
		System.out.println("Rank1 " + Rank1 + " appears " + Rank1Freq +" times");
		System.out.println("Rank2 " + Rank2 + " appears " + Rank2Freq +" times");
		System.out.println("Rank3 " + Rank3 + " appears " + Rank3Freq +" times");
		return this.guessedKey;
	}
	
	
	
	public int[] getKeyFrequency() {
		return keyFrequency;
	}

	public void setKeyFrequency(int[] keyFrequency) {
		this.keyFrequency = keyFrequency;
	}

	
	public HashMap<Integer, Integer> getGuessedKey() {
		return guessedKey;
	}

	public void setGuessedKey(HashMap<Integer, Integer> guessedKey) {
		this.guessedKey = guessedKey;
	}
 

}
