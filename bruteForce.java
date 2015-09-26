package findKey;

import java.util.ArrayList;

public class bruteForce {
	private final  int N = 160;
	ArrayList<int[]> dataList;
	public bruteForce(ArrayList dataList) {
		this.dataList = dataList;
	}

	public int[] bruteForce(int[] key,int keyLength){
        //brute force last 2 bits
		for (int i =0;i<=89;i++){
			for (int j = 0; j<=89;j++){
				for (int k = 0; k<=89;k++){
					//test 20 data
					key[keyLength-1] = i;
					key[keyLength-2] = j;
					key[keyLength-3] = k;
					System.out.println("trying key"+ key[3] +" "+ key[4]+ " " + key[5] +" "+ key[6] +" "+key[7]);
					if (verify(key,keyLength,20)){//if found key pass
						return key;
					}       		
				}
			}
		}
		return null;
	} 	
	
	
	public boolean verify(int[] key,int keyLength,int verifyNum){
		
		//verify number of records according to verifyNum
		for (int k=0;k<verifyNum;k++){ 
    		int[] data = (int[])dataList.get(k);
    		key[0] = data[0];
    		key[1] = data[1];
    		key[2] = data[2];
    		
    	
    		
    		int[] S = new int[N];
    		//init S[160]
    		for (int i=0; i<N;i++)
    		S[i]=i;
    				
    		int j=0;

    	    for (int i=0; i<N; i++){
    	    	int temp;
    		    j =   ( j+  S[i] + key[i%keyLength] )%N;
    		    temp = S[j]; S[j] = S[i]; S[i]=temp;
    		}	           
    	    
    	    int X = S[1];
    	    int Y = S[X];
    	    int output = S[( X + Y)% N ];
    	    
//   	    System.out.println(key[0] +" "+key[1]+" "+key[2]+ " calculated output is " + output);
    	    
   	    if (S[( S[1]+ S[S[1]])% N ] != data[3]){
    	    	return false;
    	    } 	    
		}
		return true;
	}
}
