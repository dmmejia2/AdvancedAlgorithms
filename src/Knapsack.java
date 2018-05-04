//Daniel Mejia - Advanced Algorithms
public class Knapsack {
	public static int[][] knapsack(int [] v, int [] w, int capacity){
		int [][] B = new int[v.length][capacity+1];
		for(int i=0; i<B.length; i++){
			B[i][0] = 0;
		}
		for(int i=0;i<B[0].length;i++){
			B[0][i] = 0;
		}
		for(int i=1;i<B.length;i++){
			for(int j=1;j<B[i].length;j++){
				int temp = B[i][j-1];
				if(temp<B[i-1][j]){
					temp = B[i-1][j];
				}
				if(j-w[i]>=0){
					if(temp<v[i]+B[i-1][j-w[i]]){
						temp=v[i]+B[i-1][j-w[i]];
					}
				}
				B[i][j]=temp;
			}
		}
		//Prints Knapsack array
		for(int i=0; i<B.length;i++){
			for(int j=0; j<B[i].length;j++){
				System.out.print(" "+B[i][j]);
			}
			System.out.println("");
		}
		int i=B.length-1;
		int j=B[0].length-1;
		while(i>0){
			if(B[i][j]==B[i-1][j]){
				i--;
			}else{
				System.out.println("Take item: "+i+" With a value of: "+v[i]);
				j=j-w[i];
				i--;
			}
		}
		return B;		
	}

	public static void main(String [] args){
		//Zero added to element 0 to offset the starting point
		//Of the array when v[i]+B[i-1][j-w[i]] is evaluated
		int [] weights = {0,1,2,3,4,5};
		int [] values = {0,1,3,7,8,9};
		int capacity = 5;
		knapsack(values,weights,capacity);
	}
}
