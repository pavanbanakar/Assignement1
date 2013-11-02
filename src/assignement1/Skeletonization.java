package assignement1;

public class Skeletonization {
	
	public int[][] Skeletonize(int[][] result, int NumberOfRowsOriginal, int NumberOfColumnsOriginal)
    {
        for (int i = 0; i < NumberOfRowsOriginal; i++)
        {
            for (int j = 0; j < NumberOfColumnsOriginal; j++)
            {
                if (i == 0 || i == (NumberOfRowsOriginal) - 1 || j == 0 || j == (NumberOfColumnsOriginal) - 1 ||j==(NumberOfColumnsOriginal)-2)
                {
                    result[i][j] = 1; //Ignoring Border Pixels .Assumption that border pixels doesn't have image 
                }
                else
                {
                    
                    int a = 0;
                    {
                        if ( j != NumberOfColumnsOriginal - 1)
                        {
                            a = GetNumberOfZeroTo1Transactions(result, i, j);
                        }

                    }
                    int b = 0;
                    {
                        if ( j != NumberOfColumnsOriginal - 1)
                        {
                            b = GetNumberOfSurroundingBlackPixels(result, i, j);
                        }
                    }

                    if (result[i][j] == 0 && 2 <= b && b <= 6 && a == 1
                        && (result[i - 1][j] * result[i][j + 1] * result[i + 1][j] == 0)
                        && (result[i][j + 1] * result[i + 1][j] * result[i][j - 1] == 0))
                    {
                        result[i][j] = 1;
                    }

                }
            }
        }

        for (int i = 0; i < NumberOfRowsOriginal; i++)
        {
            for (int j = 0; j < NumberOfColumnsOriginal; j++)
            {
                if (i == 0 || i == (NumberOfRowsOriginal) - 1 || j == 0 || j == (NumberOfColumnsOriginal) - 1 || j == (NumberOfColumnsOriginal) - 2)
                {
                    result[i][j] = 1; //Ignoring Border Pixels .Assumption that border pixels doesnt have image 
                }
                else
                {

                    int a = 0;
                    {
                        if ( j != NumberOfColumnsOriginal - 1)
                        {
                            a = GetNumberOfZeroTo1Transactions(result, i, j);
                        }

                    }
                    int b = 0;
                    {
                        if ( j != NumberOfColumnsOriginal - 1)
                        {
                            b = GetNumberOfSurroundingBlackPixels(result, i, j);
                        }
                    }

                    if (result[i][j] == 0 && 2 <= b && b <= 6 && a == 1
                            && (result[i - 1][j] * result[i][j + 1] * result[i + 1][j] == 0)
                            && (result[i][j + 1] * result[i + 1][j] * result[i][j - 1] == 0))
                        {
                            result[i][j] = 1;
                        }

                }
            }
        }
        
       
        return result;
    }

    private int GetNumberOfZeroTo1Transactions(int[][] result, int i, int j)
    {
        int countOFZeroToOneTransactions = 0;

        if (result[i - 1][j] == 0 && result[i - 1][j + 1] == 1)
        {
            countOFZeroToOneTransactions++;

        }
        if (result[i - 1][j + 1] == 0 && result[i][j + 1] == 1)
        {
            countOFZeroToOneTransactions++;
        }
        if (result[i][j + 1] == 0 && result[i + 1][j + 1] == 1)
        {
            countOFZeroToOneTransactions++;
        }
        if (result[i + 1][j + 1] == 0 && result[i + 1][j] == 1)
        {
            countOFZeroToOneTransactions++;
        }
        if (result[i + 1][j] == 0 && result[i + 1][j - 1] == 1)
        {
            countOFZeroToOneTransactions++;
        }
        if (result[i + 1][j - 1] == 0 && result[i][j - 1] == 1)
        {
            countOFZeroToOneTransactions++;
        }
        if (result[i][j - 1] == 0 && result[i - 1][j - 1] == 1)
        {
            countOFZeroToOneTransactions++;
        }
        if (result[i - 1][j - 1] == 0 && result[i - 1][j] == 1)
        {
            countOFZeroToOneTransactions++;
        }
        return countOFZeroToOneTransactions;
    }

    private static int GetNumberOfSurroundingBlackPixels(int[][] result, int i, int j)
    {
        return (result[i - 1][j] + result[i - 1][j + 1] + result[i][j + 1] + result[i + 1][j + 1] + result[i + 1][j] +
                result[i + 1][j - 1] + result[i][j - 1] + result[i - 1][j - 1]);
      
    }

}
