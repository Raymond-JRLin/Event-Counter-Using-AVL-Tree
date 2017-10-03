import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.*;

public class bbst 
{

	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		
		AVLTree tree=new AVLTree();
    
		try{
			Scanner input = new Scanner(new File(args[0]));
		    while(input.hasNextLine()){
		    	String str = input.nextLine();
		    	String[] Array = str.split(" ");
		    	int[] n = new int[Array.length];
		    	for(int j = 0;j<Array.length;j++){
		    		n[j]=Integer.parseInt(Array[j]);
		    	}
		    try{
		    	tree.insert(n[0], n[1]);
		    }
		    catch(ArrayIndexOutOfBoundsException t){
		 
		    }
		}
	}catch(FileNotFoundException t){
		t.printStackTrace();
	}
		String command;

		Scanner cmd = new Scanner(System.in);
		  //System.out.println("please command:");
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
				  tree.Increase(l[0], l[1]);}
			  else if(Array[0].equals(cmd2)==true){
				  l[0]=Integer.parseInt(Array[1]);
				  l[1]=Integer.parseInt(Array[2]);
				  tree.Reduce(l[0], l[1]);
			  }
			  else if(Array[0].equals(cmd3)==true){
				  l[0]=Integer.parseInt(Array[1]);				  
				  tree.Count(l[0]);
			  }
			  else if(Array[0].equals(cmd4)==true){
				  l[0]=Integer.parseInt(Array[1]);
				  l[1]=Integer.parseInt(Array[2]);
				  tree.Inrange(l[0],l[1]);
			  }
			  else if(Array[0].equals(cmd5)==true){
				  l[0] = Integer.parseInt(Array[1]);
				  tree.Next(l[0]);
			  }
			  else if(Array[0].equals(cmd6)==true){
				  l[0] = Integer.parseInt(Array[1]);
				  tree.Previous(l[0]);
			  }
		  }tree.clear();
		
	}
}

