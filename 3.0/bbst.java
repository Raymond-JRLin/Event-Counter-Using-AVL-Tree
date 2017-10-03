import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.*;
import java.util.Arrays;

public class bbst 
{

	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		
		AvlTree tree=new AvlTree();
    
		try{
			Scanner input = new Scanner(new File(args[0]));
			int [] arrayKey;
			int [] arrayCount;
			int k=0;
			int c=0;
			String lengthString = input.nextLine();
			int length = Integer.parseInt(lengthString);
			arrayKey = new int[length];
		    arrayCount = new int[length];
		    while(input.hasNextLine()){
		    	String str = input.nextLine();
		    	String[] Array = str.split(" ");
		    	int[] n = new int[Array.length];                       //use n[] to store key and count in each line
		    	for(int j = 0;j<Array.length;j++){
		    		n[j]=Integer.parseInt(Array[j]);
		    	}
		    	try{
		    		
		    		arrayKey[k] = n[0];                                //use arrayKey[] to store every key value
		    		k=k+1;
		    		arrayCount[c] =	n[1];                              //use arrayCount[] to store every count number
					c=c+1;
		    	}                                                      //k or c finally will be actuall length of arrayKey and arrayCount
		    	catch(ArrayIndexOutOfBoundsException t){
		 
		    	}
			}
			tree.initial(arrayKey, arrayCount, k);                     //to call initial method
		}catch(FileNotFoundException t){
			t.printStackTrace();
		}
		
		

		String command;

		Scanner cmd = new Scanner(System.in);                          //scanner different commands and call corresponding methods
		  	String cmd1 = "increase";
		  	String cmd2 = "reduce";
		  	String cmd3 = "count";
		  	String cmd4 = "inrange";
		  	String cmd5 = "next";
		  	String cmd6 = "previous";
		  	while(!(command=cmd.nextLine()).equals("quit")){
			  
				String str = command;
				String[] Array = str.split(" ");
			  	int[] l = new int[Array.length];
			  	if(Array[0].equals(cmd1)==true){
					l[0]=Integer.parseInt(Array[1]);
				  	l[1]=Integer.parseInt(Array[2]);
				  	tree.increase(l[0], l[1]);}
			  	else if(Array[0].equals(cmd2)==true){
				  	l[0]=Integer.parseInt(Array[1]);
				  	l[1]=Integer.parseInt(Array[2]);
				  	tree.reduce(l[0], l[1]);
			  	}
			  	else if(Array[0].equals(cmd3)==true){
				  	l[0]=Integer.parseInt(Array[1]);				  
				  	tree.count(l[0]);
			  	}
			  	else if(Array[0].equals(cmd4)==true){
				  	l[0]=Integer.parseInt(Array[1]);
				  	l[1]=Integer.parseInt(Array[2]);
				  	tree.inRange(l[0],l[1]);
			  	}
			  	else if(Array[0].equals(cmd5)==true){
				  	l[0] = Integer.parseInt(Array[1]);
				  	tree.next(l[0]);
			  	}
			  	else if(Array[0].equals(cmd6)==true){
				  	l[0] = Integer.parseInt(Array[1]);
				  	tree.previous(l[0]);
			  	}
		  	}
		  	System.out.print("quit");
		
	}
	
}

