public class editDistanceTest3{	

	public static void printArray(int[][] A){
		for(int i=0;i<A.length;i++){
			for(int j=0;j<A[i].length;j++)
				System.out.print(A[i][j]+" ");
			System.out.println();
		}  
	}

	public static int editDistance(String s1, String s2, int[][]costMatrix, int deleteCost, int replaceCost, int insertCost){
		int [][] d = new int[s1.length()+1][s2.length()+1];
		for(int i=0;i<=s1.length();i++)
			d[i][0] = i; // deletion
		for(int j=0;j<=s2.length();j++)
			d[0][j] = j; // insertion
		for(int j=0; j<s2.length();j++)
			for(int i=0;i<s1.length();i++)
				if(s1.charAt(i) == s2.charAt(j)) //match
					d[i+1][j+1] = d[i][j];
				else{
					replaceCost = costMatrix[i][j];
					deleteCost = costMatrix[i][j];
					insertCost = costMatrix[i][j];
					d[i+1][j+1]=Math.min(d[i][j]+replaceCost,Math.min(d[i+1][j]+deleteCost,d[i][j+1]+insertCost));
				}
		System.out.println("");
		printArray(d);

		int i=s1.length();
		int j=s2.length();
		String fromWhere="";
		while(d[i][j]!=0){
			if(d[i-1][j-1]<d[i-1][j]&&d[i-1][j-1]<d[i][j-1]){
				//replace
				System.out.println("Replace");
				fromWhere = "Replace";
				i--;
				j--;
			}else if(d[i-1][j]<d[i-1][j-1]&&d[i-1][j]<d[i][j-1]){
				System.out.println("Insert");
				fromWhere = "Insert";
				//insert
				i--;
			}else if(d[i][j-1]<d[i-1][j-1]&&d[i][j-1]<d[i-1][j]){
				System.out.println("Delete");
				fromWhere = "Delete";
				//delete
				j--;
			}else{
				if(fromWhere =="Delete"){
					System.out.println("Delete");
					j--;
				}else if(fromWhere == "Insert"){
					System.out.println("Insert");
					i--;
				}else{
					System.out.println("Replace");
					i--;
					j--;
				}

			}
		}

		return d[s1.length()][s2.length()];
	}

	public static void main(String[] args)   {
		String S1 = "ATGC";
		String S2 = "ATGC";
		//int[][] matrix = new int[4][4];
		int[][] matrix = {{0,1,2,3},{1,0,2,2},{2,2,0,1},{3,2,1,0}};


		System.out.println(S1+" "+ S2 +" " + editDistance(S1,S2,matrix,2,3,4));
		editDistance(S1,S2,matrix,2,3,4);


	}	
}	