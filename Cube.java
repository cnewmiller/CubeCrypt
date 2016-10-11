/*
 * Code written by Clayton Newmiller
 * 
 * */

package rubix;
//@SuppressWarnings("unused")


public class Cube {
	protected class Face{
		int squares[][];
		int n;
		Face(int N){
			this.n=N;
			this.squares=new int[n][n];//columns, rows
		}
		Face(int n, int filler){
			this.n=N;
			this.squares=new int[n][n];//columns, rows
			this.init(filler);
		}
		private void init(int filler){
//			System.out.printf("in init, n is %d\n", this.n);
			for (int i=0;i<this.n;i++){
//				System.out.printf("in init loop1\n");
				for (int j=0;j<n;j++){
					this.squares[i][j]=filler;
//					System.out.printf("filler: %d\n",this.squares[i][j]);
				}
			}
		}
		
		
	}
	public Cube(int n){
		this.N=n;
		
		for (int i=0;i<6;i++){
			this.sides[i]=new Face(N,i);
//			System.out.printf("in constructor loop %d\n", i);
//			this.printFace(i);
		}
	}
	private int N;
	protected Face sides[] = new Face[6]; //definitively a cube
	/* front=0
	 * top=1
	 * right=2
	 * bottom=3
	 * left=4
	 * back=5
	 * */
	public void rotateY(int row, int rot){ //0<=row<n, rotates downward
		for (int i=0;i<rot;i++){
			int temp[] = this.sides[0].squares[row];
			this.sides[0].squares[row] = this.sides[1].squares[row];
			this.sides[1].squares[row] = this.sides[5].squares[row];
			this.sides[5].squares[row] = this.sides[3].squares[row];
			this.sides[3].squares[row] = temp;
			if (row == 0){
				rotateFaceCounterclockwise(4);
			}
			else if (row == N-1){
				rotateFaceClockwise(2);
			}
		}
	}
	public void rotateX(int row, int rot){ //0<=row<n, counts rows from the back, turns counterclockwise
		for (int i=0;i<rot;i++){
			int temp[] = this.sides[4].squares[row];
			this.sides[4].squares[row] = this.sides[1].squares[row];
			this.sides[1].squares[row] = this.sides[2].squares[row];
			this.sides[2].squares[row] = this.sides[3].squares[row];
			this.sides[3].squares[row] = temp;
			if (row == 0){
				rotateFaceClockwise(5);
			}
			else if (row == N-1){
				rotateFaceCounterclockwise(0);
			}
		}
	}
	public void rotateZ(int row, int rot){ //0<=row<n, counts downward
		for (int x=0;x<rot;x++){
			for (int n=0;n<this.N;n++){
				int temp = this.sides[0].squares[0][row];
				for (int j=0;j<this.N-1;j++){//start at [0][row] and go right so the corner is at the end
					this.sides[0].squares[j][row] = this.sides[0].squares[j+1][row];
				}
				this.sides[0].squares[N-1][row] = this.sides[2].squares[0][row];
				for (int j=0;j<this.N-1;j++){//side 2
					this.sides[2].squares[j][row] = this.sides[2].squares[j+1][row];
				}
				this.sides[2].squares[N-1][row] = this.sides[5].squares[0][row];
				for (int j=0;j<this.N-1;j++){//start at [0][row] and go right so the corner is at the end
					this.sides[5].squares[j][row] = this.sides[5].squares[j+1][row];
				}
				this.sides[5].squares[N-1][row] = this.sides[4].squares[0][row];
				for (int j=0;j<this.N-1;j++){//start at [0][row] and go right so the corner is at the end
					this.sides[4].squares[j][row] = this.sides[4].squares[j+1][row];
				}
				this.sides[4].squares[N-1][row] = temp;
			}
			if (row == 0){
				rotateFaceCounterclockwise(1);
			}
			else if (row == N-1){
				rotateFaceClockwise(3);
			}
		}
	}
	protected void rotateFaceCounterclockwise(int face){ //rotates counterclockwise
		int temp[][] = new int[N][N];
		int n=N-1;
		for (int i=0;i<N;i++){
			for (int j=0;j<N;j++){
				temp[i][n-j] = this.sides[face].squares[j][i];
			}
		}
		this.sides[face].squares = temp;
		
	}
	protected void rotateFaceClockwise(int face){
		int temp[][] = new int[N][N];
		int n=N-1;
		for (int i=0;i<N;i++){
			for (int j=0;j<N;j++){
				temp[n-i][j] = this.sides[face].squares[j][i];
			}
		}
		this.sides[face].squares = temp;
	}
	
	public void printFace(int face){
		System.out.println("\n------");
		int a[][]= this.sides[face].squares;
		for (int l=0;l<a[0].length;l++){
			for (int k=0;k<a.length;k++){
				if (a[k][l]<0x10 && a[k][l]>=0x0){
					System.out.print("{0"+Integer.toHexString(0xff & a[k][l])+"}\t");
	            }
	            else
	            	System.out.print("{"+Integer.toHexString(0xff & a[k][l])+"}\t");
				
			}
			System.out.println();
		}
		System.out.println("------\n");
	}
	public void printCube(){
		for (int i=0;i<6;i++){
			System.out.printf("Printing side %d...\n", i);
			printFace(i);
		}
	}
	
	
	public static void main(String[] args) {
		int N = 3;
		Cube rub = new Cube(N);
		rub.printCube();
		int x = 500;
		for (int i = 0; i<=x;i++){
			rub.rotateY(i%N, i%4);
			rub.rotateX(i%N, i%4);
			rub.rotateZ(i%N, i%4);
		}
		rub.printCube();
		System.out.printf("MIXED UP... UNMIXING...-------------------\n");
		for (int i=x;i>=0;i--){
			rub.rotateZ(i%N, 4-i%4);
			rub.rotateX(i%N, 4-i%4);
			rub.rotateY(i%N, 4-i%4);
		}
		rub.printCube();
	}

}
