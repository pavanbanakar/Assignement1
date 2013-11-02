package assignement1;


import java.io.File;
import java.util.Scanner;

//Author : Yoann ROBIN
//IDStudent : 7118538


public class Matrix {

	private int[][] a;
	private int rows=0;
	private int columns=0;
	
	//return the number of rows in the matrix
	public int getRows(){
		return this.rows;
	}
	
	//return the number of columns in the matrix
	public int getColumns(){
		return this.columns;
	}
	
	//initialize a matrix from a 2D array and its number of rows and columns
	public Matrix(int[][] in, int rows, int columns){
		a=in;
		this.rows = rows;
		this.columns = columns;
	}
	
	//initialize a matrix from a file
	public Matrix(String file) throws Exception{
		Scanner input;
		input = new Scanner(new File(file));
		// pre-read in the number of rows/columns
		
		int n=0;
		input = new Scanner(new File(file));
		while(input.hasNextInt()){
			++n;
			input.nextInt();
		}
		input = new Scanner(new File(file));
		while(input.hasNextLine()){
			input.nextLine();
			++rows;
		}
		columns = n/rows;
		
		this.a = new int[rows][columns];

		input.close();

		// read in the data
		input = new Scanner(new File(file));
		for(int i = 0; i < rows; ++i)
		{
		    for(int j = 0; j < columns; ++j)
		    {
		        if(input.hasNextInt())
		        {
		            this.a[i][j] = input.nextInt();
		        }
		    }
		}
		input.close();
	}
	
	
	//return the moment mpq
	public double moment(int p, int q){
		double stack = 0;
		for(int i = 0; i < rows; ++i)
		{
		    for(int j = 0; j < columns; ++j)
		    {
		        stack = stack + (double) Math.pow(i, p) * (double) Math.pow(j, q) * a[i][j];
		    }
		}
		return stack;
	}
	
	//return the central moment upq
	public double centralMoment(int p, int q){
		double stack = 0;
		double m10 = this.moment(1, 0);
		double m01 = this.moment(0, 1);
		double m00 = this.moment(0, 0);
		
		double xc = m10/m00;
		double yc = m01/m00;
		for(int i = 0; i < rows; ++i)
		{
		    for(int j = 0; j < columns; ++j)
		    {
		        stack = stack + (double) Math.pow(i - xc, p) * (double) Math.pow(j - yc, q) * a[i][j];
		    }
		}
		return stack;
	}
	
	
	//return the slant angle of the matrix
	public double slantAngle(){
		return(Math.toDegrees(Math.atan(-this.centralMoment(1, 1)/this.centralMoment(0, 2))));
	}
	
	public Matrix invert(){
		
		int[][] Mfin = new int[rows][columns];
		for(int i = 0; i < rows; ++i)
		{
		    for(int j = 0; j < columns; ++j)
		    {
		    		Mfin[i][j] = 1-a[i][j] ;
		    }
		}

		Matrix Mresult = new Matrix(Mfin, rows, columns);
		return Mresult;
}
	
	
	//return the matrix with slant correction
	public Matrix correctSlant(){
			double m01 = this.moment(0, 1);
			double m00 = this.moment(0, 0);
			
			double yc = m01/m00;
			int[][] Mfin = new int[rows][columns];
			for(int i = 0; i < rows; ++i)
			{
			    for(int j = 0; j < columns; ++j)
			    {
			        Mfin[i][j] = 0;
			    }
			}

			for(int i = 0; i < rows; ++i)
			{
			    for(int j = 0; j < columns; ++j)
			    {
			    	int yp = i;
			    	int xp = (int) (j + (i - yc)*(-centralMoment(1, 1)/centralMoment(0, 2)));
			    	
			    	try{
			    		Mfin[yp][xp] = a[i][j];
			    	}
			    	catch (Exception e){}
			    }
			}
			Matrix Mresult = new Matrix(Mfin, rows, columns);
			return Mresult;
	}
	
	
	//return the height of the lowest pixel!=1
	private int getSouth(){
		int result = 0;
		for(int i = 0; i < rows; ++i)
		{
		    for(int j = 0; j < columns; ++j)
		    {
		    	if (this.a[i][j] == 0){
		    		result = i;
		    	}
		    }
		}
		return result;
	}
	
	//return the height of the highest pixel!=1
	private int getNorth(){
		int result = 0;
		for(int i = rows-1; i >= 0; --i)
		{
		    for(int j = columns-1; j >= 0; --j)
		    {
		    	if (this.a[i][j] == 0){
		    		result = i;
		    	}
		    }
		}
		return result;
	}
	
	//return the column of the first pixel!=1 from right
	private int getEast(){
		int result = 0;
		for(int j = 0; j < columns; ++j)
		{
			for(int i = 0; i < rows; ++i)
			{
		    	if (this.a[i][j] == 0){
		    		result = j;
		    	}
		    }
		}
		return result;
	}
	
	//return the column of the first pixel!=1 from left
	private int getWest(){
		int result = 0; 
		for(int j = columns-1; j >= 0; --j)
		{
			for(int i = rows-1; i >= 0; --i)
			{
		    	if (this.a[i][j] == 0){
		    		result = j;
		    	}
		    }
		}
		return result;
	}
	
	//return a normalized matrix of size hnew * wnew
	public Matrix normalize(int hnew, int wnew){
		int h1 = getNorth();
		int h2 = getSouth();
		int w1 = getWest();
		int w2 = getEast();
		int hold = h2 - h1;
		int wold = w2 - w1;
		
		double alpha = (double)wnew/(double)wold;
		double beta = (double)hnew/(double)hold;
		
		int[][] Mfin = new int[hnew][wnew];
		for(int i = 0; i < hnew; ++i)
		{
		    for(int j = 0; j < wnew; ++j)
		    {
		        Mfin[i][j] = 1;
		    }
		}
		
		
		for(int i = 0; i < hnew; ++i)
		{
		    for(int j = 0; j < wnew; ++j)
		    {
		    	try{
		    		Mfin[i][j] = a[(int)(i/beta)][(int)(j/alpha)];
		    	}
		    	catch (Exception e){}
		    }
		}
		Matrix Mresult = new Matrix(Mfin,hnew, wnew);
		return Mresult;
	}
	
	
	//return a matrix containing the contours of the original matrix
	public Matrix getContour(){
			
			int[][] Mfin = new int[rows][columns];
			for(int i = 0; i < rows; ++i)
			{
			    for(int j = 0; j < columns; ++j)
			    {
			    	if((i==0)|(j==0)|(i==rows-1)|(j==columns-1)){
			    		Mfin[i][j]=1-a[i][j];
			    	}
			    	else{
			    		Mfin[i][j] = 1;
			    	}
			    }
			}
			
			int previous=1;
			for(int i = 0; i < rows; ++i)
			{
			    for(int j = 0; j < columns; ++j)
			    {
			    	if (a[i][j]!=previous){
			    		if (a[i][j]==0){
			    			Mfin[i][j]=0;
			    		}
			    		else{
			    			Mfin[i][j-1]=0;
			    		}
			    	}
			    	previous = a[i][j];
			    }
			    previous = 1;
			}
			
			
			previous=1;
			for(int j = 0; j < columns; ++j)
			{	
				for(int i = 0; i < rows; ++i)
			    {
					if (a[i][j]!=previous){
			    		if (a[i][j]==0){
			    			Mfin[i][j]=0;
			    		}
			    		else{
			    			Mfin[i-1][j]=0;
			    		}
			    	}
					previous = a[i][j];	
			    }
				previous = 1;
			}
			
			
			Matrix Mresult = new Matrix(Mfin,rows, columns);
			return Mresult.invert();
		}
	
	
	//return a String to be displayed in the console
	//(you can copy-paste from console to excel to have a better view of the result
	public String toString(){
		String stack = "";
		for(int i = 0; i < rows; ++i)
		{
		    for(int j = 0; j < columns; ++j)
		    {
		    	if (j!=0){
		    			stack = stack + "\t" + this.a[i][j];
		    	}
		    	else{
		    		stack = stack + this.a[i][j];
		    	}
		    }
		    stack = stack + "\n";
		}
		return stack;
	}
	

}
