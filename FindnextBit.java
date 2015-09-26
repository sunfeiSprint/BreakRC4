package findKey;

import java.util.ArrayList;

/*
 * this class helps to guess the next bit of key 
 * 
 * if return -1 means IV is not weak IV
 * 
 * if return int > 0 the statical Analysis class should increase the Frequency
 * */

public class FindnextBit {
	private final  int N = 160;
	



	int numberOfrounds;
	int[] keyArray;
	int firstOutput;
	
	public FindnextBit(){
		
	}
	
	public FindnextBit(int numberOfrounds, int[] keyArray, int firstOutput) {
		this.numberOfrounds = numberOfrounds;
		this.keyArray = keyArray;
		this.firstOutput = firstOutput;
	}
	
	public int guess(){
		//System.out.println(this.keyArray[0] + " "+ this.keyArray[1] +" " + this.keyArray[2]);
	
		int[] S = new int[N];
		int[] Si = new int[N];
		int[] jArray = new int[20];
 		//init S[160]
		for (int i=0; i<N;i++)
		S[i]=i;
				
		int j=0;

	    for (int i=0; i<numberOfrounds; i++){
	    	int temp;
		    j =   ( j+  S[i] + keyArray[i] )%N;
		    jArray[i] = j;
		    temp = S[j]; S[j] = S[i]; S[i]=temp;
		}	
	    
	    //Construct Si table
	    
	    for (int i=0; i<N;i++){
	    	Si[S[i]]= i;
	    }
	    
	    	    
	    int X = S[1];
	    int Y = S[X];
	  
	    int foundKey = -1;
	    

    	//Korek attack 2 13.75%
	    if (	S[1] == numberOfrounds && 
	    		firstOutput == numberOfrounds){//weak IV
	    	foundKey = (Si[0] - S[numberOfrounds] - jArray[numberOfrounds-1] + N + N) % N;
	    	if (foundKey <90){
	    		return foundKey;
	    	}
	    }
	    
    	//Korek attack 3 13.75%
	    if (S[1]== numberOfrounds && 
	    		firstOutput == (1-numberOfrounds+N)%N){//weak IV
	    	
	    	foundKey = (Si[firstOutput] - S[numberOfrounds] - jArray[numberOfrounds-1] + N + N) % N;
	    	if (foundKey <90){
	    		return foundKey;
	    	}
	    }
	    
	    //Korek attack 6 13.75%
	    
	    if (S[numberOfrounds]== numberOfrounds && 
	    		S[1] == 0 && 
	    		firstOutput == numberOfrounds && 
	    		foundKey == -1){//weak IV
	    	
	    	foundKey = (1 - S[numberOfrounds] - jArray[numberOfrounds-1] + N + N) % N;
	    	if (foundKey < 90){
	    		return foundKey;
	    	}
	    }
	    
	    //Korek attack 7 13.75%
	    
	    if (S[numberOfrounds]== numberOfrounds && 
	    		firstOutput == S[1] && 
	    		S[1] == (1-numberOfrounds+ N + N)%N){//weak IV
	    	
	    	foundKey = (1 - S[numberOfrounds] - jArray[numberOfrounds-1] + N + N) % N;
	    	if (foundKey >=90){
	    		foundKey = -1;
	    	}
	    }

	    
    	//FMS
	    if ((X+Y)%N == numberOfrounds && 
	    		numberOfrounds >= X){
	    	foundKey =(Si[firstOutput] - jArray[numberOfrounds-1] - S[numberOfrounds] + N + N) % N; //compute key
	    	if (foundKey <90){
	    		return foundKey;
	    	}
	    }
	    
	    
	    //Korek attack 5 5.07%
	    if (Si[firstOutput]== 2 && 
	    		S[numberOfrounds] ==1){//weak IV
	    	
	    	foundKey = (1 -S[numberOfrounds] - jArray[numberOfrounds-1] + N + N) % N; 
	    	if (foundKey < 90){
	    		return foundKey;
	    	}
	    }
	    
    	//Korek attack 1 
	    if ((X+Y)%N == numberOfrounds && 
	    		S[1]<numberOfrounds && 
	    		Si[firstOutput] != 1 && 
	    		Si[firstOutput] != S[S[1]]){
	    	foundKey =(Si[firstOutput] - jArray[numberOfrounds-1] - S[numberOfrounds] + N + N) % N; //compute key
	    	if (foundKey <90){
	    		return foundKey;
	    	}
	    }
	    
	    
	    //Korek attack 4 5.07%
	    if (S[1]== numberOfrounds && 
	    		firstOutput !=(1-numberOfrounds + N + N) % N && 
	    		firstOutput != numberOfrounds && 
	    		Si[firstOutput] < numberOfrounds &&
	    		Si[(Si[firstOutput]-numberOfrounds + N + N) % N] !=1){//weak IV
	    	
	    	foundKey = (Si[(Si[firstOutput]-numberOfrounds + N + N)%N] - S[numberOfrounds] - jArray[numberOfrounds-1] + N) % N;
	    	if (foundKey < 90){
	    		return foundKey;
	    	}
	    }
	    
	    //Korek attack 8 5.07%
	    if ( S[numberOfrounds] == numberOfrounds && 
	    		S[1] >= (-1 * numberOfrounds + N) % N && 
	    		Si[firstOutput] != 1 && 
	    		S[1]==((Si[firstOutput]- numberOfrounds +N ) % N)
	    		){//weak IV
	    	
	    	foundKey = (1 - S[numberOfrounds] - jArray[numberOfrounds-1] + N + N) % N;

	    	if (foundKey < 90){
	    		return foundKey;
	    	}
	    }

	    	    
		 //Korek attack 9 cannot be used
		 //Korek attack 10 cannot be used
		 //Korek attack 11 cannot be used
		 //Korek attack 12 cannot be used
		 //Korek attack 13 cannot be used
		 //Korek attack 14 cannot be used
	    	    

	    if (foundKey >= 90){
	    	foundKey = -1;
	    }
		return foundKey;
	}

	public int getNumberOfrounds() {
		return numberOfrounds;
	}

	public void setNumberOfrounds(int numberOfrounds) {
		this.numberOfrounds = numberOfrounds;
	}

	public int[] getKeyArray() {
		return keyArray;
	}

	public void setKeyArray(int[] keyArray) {
		this.keyArray = keyArray;
	}

	public int getFirstOutput() {
		return firstOutput;
	}

	public void setFirstOutput(int firstOutput) {
		this.firstOutput = firstOutput;
	}
	
	

}
