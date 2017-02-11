import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
//import java.util.LinkedList;
import java.util.Set;

public class PartTest {

	public static void main(String[] args) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		Random RSize = new Random();
		int randsize = RSize.nextInt(100);
		System.out.println(randsize);

		for (int i =0; i<=randsize; i++){ //generate random numbers <=500 of a random size <=100
			Random rnd=new Random();
			int randnbr = rnd.nextInt(501);
			list.add(randnbr);
		}
		
		int n=list.size();				//size of the list
		System.out.println(list.size());
		int m= (int) (Math.floor(sum(list)/2)); //the halfsum we want our subset to be closest to.
		System.out.println(m);
		boolean P[][]=Possible(list); //see function below
		
		//Extract the last column 
		boolean[] T = new boolean [m+1];
		for (int i = 0; i<=m ; i++ ){
			T[i]=P[i][list.size()];
		}

		//look for the sum closest to half of the maximum sum
		//that is possible with all n elements
		int index=0;
		for (int i = 0; i<=m ; i++ ){
			if (T[i]=true){
				index=i;  //highest index on the last column that is true.
			}
		} 
		
		int sumA1 = index;
		System.out.println(sumA1);
		ArrayList<Integer> A = new ArrayList<Integer>();
		//There exist a subset that sums to sumA1. This subset will minimize the difference
		//Backward trail. Starting point is P[sumA1][n]
		int x=sumA1;
		int y =n;
		while(x>=0 && y>=1){
			if (P[x][y] && P[x][y-1]){
				y--;		//if the Boolean on the left is true, move left
			}
			else{				//else include the value of the list of this column
				if (P[x][y]){	//and move up by that value
					A.add(list.get(y-1));
					x=x-list.get(y-1)+1;
				}
				x--;
			}
		}
		//output the set which has the sum closest to half of total sum
		System.out.println(A);	
		
		ArrayList<Integer> B = list;
		int len = A.size();
		
		for(int i = 0; i<= len-1; i++){ //eliminate elements that are in A from list
			int z1= B.indexOf(A.get(i));
			B.remove(z1);
		}
		//Output the other set
		System.out.println(B);
		
		System.out.println(sum(B)-sum(A));
			

	}
	//returns a matrix of boolean.
	//We want to find the subset for which the largest index of the last column is true
	//For each i,j, In our arraylist of integers arr, from start to end,
	//P[i][j] is true if there exist a subset of {arr[0],..,arr[j-1]} that sums to i.
	private static boolean[][] Possible(ArrayList<Integer> arr){
		int n=arr.size();
		int m= (int) (Math.floor(sum(arr)/2));
		
		boolean[][] P = new boolean [m+1][n+1];
		for (int i=0; i<=n; i++){
			P[0][i]=true;			//The first row is true since the null set is a subset
									//of all sets and it sums to 0.
		}
		for (int i=1; i<=m; i++){   //The first column except for the first element is false. 
			P[i][0]=false;			//The null set can only have the zero sum
		}
		//if there exist a subset {arr[0],..arr[j-2]} that sums to i, then that would also be
		//the subset for {arr[0],..arr[j-1]} that sums to i. Else, if there exist a 
		//subset in {arr[0],..,arr[j-2]} that sums to i-arr[j-1], then adding arr[j-1] would make P[i][j] true.
		for (int i = 1; i<=m; i++){
			for(int j =1; j<=n;j++){
				if (i>=arr.get(j-1)){ 
					P[i][j] = P[i][j-1] || P[i-arr.get(j-1)][j-1];
				}else{
					P[i][j]=P[i][j-1];
				}		
			}
		}
		return P;
	}
	//sum of an array list
	private static int sum(ArrayList<Integer> arr){	
		int Sum =0;
		for (int elt:arr){
			Sum+=elt;
		}
		return Sum;
	}
    
    		
	

}
