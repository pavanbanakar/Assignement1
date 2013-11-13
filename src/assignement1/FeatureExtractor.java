package assignement1;

import java.util.Vector;

public class FeatureExtractor {
	public static Vector<int[]> ProjectionHistograms(int[][] result,
			int NumberOfRowsOriginal, int NumberOfColumnsOriginal) {
		// Horizontal Histogram
		int[] m_HorizontalFeatureVector = new int[NumberOfRowsOriginal];
		int[] m_VerticalFeatureVector = new int[NumberOfColumnsOriginal];
		for (int i = 0; i < NumberOfRowsOriginal; i++) {
			int count = 0;
			for (int j = 0; j < NumberOfColumnsOriginal; j++) {
				if (result[i][j] == 0) {
					count++;
				}

			}
			m_HorizontalFeatureVector[i] = count;
		}
		// Vertical Histogram
		for (int j = 0; j < NumberOfColumnsOriginal; j++) {
			int count = 0;
			for (int i = 0; i < NumberOfRowsOriginal; i++) {
				if (result[i][j] == 0) {
					count++;
				}

			}
			m_VerticalFeatureVector[j] = count;
		}

		Vector<int[]> objNewVector = new Vector<int[]>();
		objNewVector.add(m_HorizontalFeatureVector);
		objNewVector.add(m_VerticalFeatureVector);
		return objNewVector;
	}

	public static Vector<int[]> CrossingFeatureExtraction(int[][] result,
			int NumberOfRowsOriginal, int NumberOfColumnsOriginal) {
		int[] m_HorizontalFeatureVector = new int[NumberOfRowsOriginal];
		int[] m_VerticalFeatureVector = new int[NumberOfColumnsOriginal];
		for (int i = 0; i < NumberOfRowsOriginal; i++) {
			int count = 0;
			for (int j = 0; j < NumberOfColumnsOriginal; j++) {
				if (j != 0) {
					if (result[i][j - 1] == 1 && result[i][j] == 0) {
						count++;
					}
				}
			}
			m_HorizontalFeatureVector[i] = count;
		}

		for (int j = 0; j < NumberOfColumnsOriginal; j++) {
			int count = 0;
			for (int i = 0; i < NumberOfRowsOriginal; i++) {
				if (i != 0) {
					if (result[i - 1][j] == 1 && result[i][j] == 0) {
						count++;
					}
				}

			}
			m_VerticalFeatureVector[j] = count;
		}

		Vector<int[]> objNewVector = new Vector<int[]>();
		objNewVector.add(m_HorizontalFeatureVector);
		objNewVector.add(m_VerticalFeatureVector);
		return objNewVector;
	}

	public static Vector<int[]> DistancesFeatureExtraction(int[][] result,
			int NumberOfRowsOriginal, int NumberOfColumnsOriginal) {
		int[] leftList = new int[NumberOfRowsOriginal];
		for (int i = 0; i < NumberOfRowsOriginal; i++) {

			for (int j = 0; j < NumberOfColumnsOriginal; j++) {
				if (result[i][j] == 0) {
					leftList[i] = j;
					break;

				}
			}

		}

		int[] upList = new int[NumberOfColumnsOriginal];

		for (int j = 0; j < NumberOfColumnsOriginal; j++) {

			for (int i = 0; i < NumberOfRowsOriginal; i++) {
				if (result[i][j] == 0) {
					upList[j] = i;
					break;

				}
			}

		}

		int[] rightList = new int[NumberOfRowsOriginal];
		for (int i = 0; i < NumberOfRowsOriginal; i++) {
			for (int j = NumberOfColumnsOriginal - 1; j >= 0; j--) {
				if (result[i][j] == 0) {
					rightList[i] = j;
					break;

				}
			}

		}

		int[] downList = new int[NumberOfColumnsOriginal];
		for (int j = 0; j < NumberOfColumnsOriginal; j++) {
			for (int i = NumberOfRowsOriginal - 1; i >= 0; i--) {
				if (result[i][j] == 0) {
					downList[j] = i;
					break;

				}
			}

		}
		Vector<int[]> objNewVector = new Vector<int[]>();
		objNewVector.add(leftList);
		objNewVector.add(upList);
		objNewVector.add(rightList);
		objNewVector.add(downList);
		return objNewVector;
	}

}
