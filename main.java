package findKey;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;


public class main {
		static final int bruteForceBit = 3;
	    public static void main(String [] args) {
        // The name of the file to open.
        String fileName = "A00.data";        
     
        ArrayList<int[]> listData;
        Stack<int[]> keyStack = new Stack();
        
        int nRead = 0;
        int keyLength;
        int numTuples;
    	byte[] buffer = new byte[4];
        
        try {
        	
        	System.out.println("=================================================");
        	System.out.println("Starting the program");
        	
        	//analysisList = new ArrayList();
            FileInputStream inputStream = 
                new FileInputStream(fileName);
                      
        	
        	nRead = inputStream.read(buffer);        	
        	keyLength = ((buffer[0] & 0xFF)) | ((buffer[1] & 0xFF)<<8)
        	        | ((buffer[2] & 0xFF)<<16) | (buffer[3] & 0xFF<<24);        	
        	int[] keyArray = new int[keyLength];
        	
        	System.out.println("=================================================");
        	System.out.println("keyArray size is "+ keyLength);
        	
        	nRead = inputStream.read(buffer);
        	numTuples = ((buffer[0] & 0xFF)) | ((buffer[1] & 0xFF)<<8)
        	        | ((buffer[2] & 0xFF)<<16) | (buffer[3] & 0xFF<<24);
        	     	
        	System.out.println("=================================================");
        	System.out.println("number of tuples in the files is "+ numTuples);
        	
        	listData = new ArrayList();
        	
            int[] data;
        	int count = 0;
        	
        	System.out.println("=================================================");
        	System.out.println("Starting to read the data");
        	
        	while((nRead = inputStream.read(buffer)) != -1) {//loop to read data
            	data = new int[4];
            	data[0] = buffer[0] & 0xff;
            	data[1] = buffer[1] & 0xff;
            	data[2] = buffer[2] & 0xff;
            	data[3] = buffer[3] & 0xff;//first word of output
            	listData.add(data);//ADD DATA[] TO A LIST
            }   
            inputStream.close();   
            

            for (int k = 0;k<keyLength-3-bruteForceBit;k++){
            	Analysis analysis = new Analysis();
            	analysis.initFreq();
            	for (int i=0;i<listData.size();i++){ 
            		data = listData.get(i);
            		keyArray[0] = data[0];
            		keyArray[1] = data[1];
            		keyArray[2] = data[2];
            	
                    FindnextBit find = new FindnextBit(k+3,keyArray,data[3]);
        		
            		int key = find.guess();
          		
            		if (key != -1){
            			analysis.addFrequency(key);
            		}         
            	}
            	//analysisList.add(analysis);
            	//================================================
            	//push the result into a stack for backtrack
            	analysis.guess();
            	
            	for(int t = 3; t>=1;t--){
            		int[] pushItem = new int[k+1];
            		for (int j=0;j<pushItem.length-1;j++){
            			pushItem[j] = keyArray[j+3];
            		}
            		pushItem[pushItem.length-1] = (Integer)analysis.getGuessedKey().get(t);
            		keyStack.push(pushItem);
            	}            	
            	//================================================
            	keyArray[k+3] = (Integer)analysis.getGuessedKey().get(1);
            	//System.out.println("set keyArray 3 = " + keyArray[k+3]);
            }
            
            System.out.println("stack size is" + keyStack.size()); 
            
            int size = keyStack.size();
            for (int i = 0;i<size;i++){
            	int[] test = (int[])keyStack.pop();
            	System.out.print("stored value ");
            	for (int j=0;j<test.length;j++){
        		System.out.print(test[j]+ " ");
            	}
            	System.out.println("");
            }

            
            boolean keyfound = false;
            
            while (keyfound == false && !keyStack.isEmpty()){
            	int[] verifyKey = new int[keyLength];
            	int[] popItem = (int[]) keyStack.pop();
                        	      	
            	if (popItem.length == keyLength -3 - bruteForceBit){//if length is ok
                	for (int i=0;i<popItem.length;i++){
                		verifyKey[i+3] = popItem[i];	
                	}   
                    bruteForce b = new bruteForce(listData);
                    b.bruteForce(verifyKey, keyLength);
                    if (b.bruteForce(verifyKey, keyLength)!=null){
                    	System.out.println("found key");
                    	keyfound = true;
                    }
            	}else{
            		
            	}         	         	
            }                   	
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");         
        }        
    }
}

