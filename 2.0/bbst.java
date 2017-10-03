import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.*;

public class bbst 
{

	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		
		AvlTree tree=new AvlTree();
    
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

		tree.increase(350, 100);
		tree.reduce(350, 50);
		tree.count(350);
		
		tree.inRange(300,1000);
		tree.inRange(200, 299);
		tree.inRange(200, 1000);
		tree.inRange(300, 349);
		tree.inRange(350, 350);
		tree.inRange(349, 350);
		
		tree.next(300);
		tree.next(349);
		tree.next(360);
		tree.previous(360);
		tree.previous(350);
		tree.previous(0);
		tree.reduce(271, 6);
		tree.previous(350);
		tree.reduce(271,6);
		tree.previous(350);
		tree.previous(150);


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
		  }tree.clear();
		
	}
}

