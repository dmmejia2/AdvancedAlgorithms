
public class FloydWarshall {

	public FloydWarshall() {
		// TODO Auto-generated constructor stub
	}

	public static int[][] floydWarshall(int[][] W){
		int n = W.length;
		int[][] D0 = new int[W.length][W.length];
		D0 = W;
		printD(D0);

		for(int k=0; k<n; k++){
			int[][] DK = new int[n][n];

			for(int i=0;i<n;i++){
				for(int j=0; j<n;j++){
					DK[i][j] = Math.min(D0[i][j], D0[i][k]+D0[k][j]);

				}
			}
			System.out.println("D"+(k+1));
			printD(DK);
			System.out.println("");
			D0 = DK;


		}

		return D0;
	}

	public static void printD(int [][] d){
		for(int i=0; i<d.length; i++){
			for(int j=0; j<d[i].length;j++){
				System.out.print(" "+d[j][i]);
			}
			System.out.println("");
		}
	}

	public static void main(String[] args){
		int[][] d = new int[6][6];
				d[0][0] = 0;
				d[0][1] = 200;
				d[0][2] = 200;
				d[0][3] = 6;
				d[0][4] = 200;
				d[0][5] = 200;
				d[1][0] = 1;
				d[1][1] = 0;
				d[1][2] = 9;
				d[1][3] = 5;
				d[1][4] = 6;
				d[1][5] = 200;
				d[2][0] = 200;
				d[2][1] = 200;
				d[2][2] = 0;
				d[2][3] = 200;
				d[2][4] = 2;
				d[2][5] = 3;
				d[3][0] = 200;
				d[3][1] = 10;
				d[3][2] = 200;
				d[3][3] = 0;
				d[3][4] = 200;
				d[3][5] = 200;
				d[4][0] = 200;
				d[4][1] = 200;
				d[4][2] = 200;
				d[4][3] = 4;
				d[4][4] = 0;
				d[4][5] = 200;
				d[5][0] = 200;
				d[5][1] = 200;
				d[5][2] = 200;
				d[5][3] = 200;
				d[5][4] = 7;
				d[5][5] = 0;
				floydWarshall(d);

	}

}
