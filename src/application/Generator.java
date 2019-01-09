package application;
import java.util.*;
public class Generator {
   public static Integer[][] generate(int n){
	   Integer[] arr=new Integer[n*n];
	   ArrayList<Integer> a=new ArrayList<Integer>();
	   for(int i=0;i<n*n/2;i++) {
		   int k=(int)(Math.random()*8);
		   a.add(k);a.add(k);}
	   
		   Collections.shuffle(a);
		   arr=a.toArray(new Integer[n*n]);
		   
	   int i=0;
	   Integer puz[][]=new Integer[n][n];
	   for(int p=0;p<n;p++)
		   for(int q=0;q<n;q++)
			   puz[p][q]=arr[i++];
	   return puz;
		   
	   
	   
   }
   
}