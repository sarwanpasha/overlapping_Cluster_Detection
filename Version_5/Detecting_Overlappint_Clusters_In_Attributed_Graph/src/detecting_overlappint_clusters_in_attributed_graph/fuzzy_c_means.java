//Source: https://github.com/patrykheciak/fuzzy-c-means-image-clustering
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package detecting_overlappint_clusters_in_attributed_graph;
public class fuzzy_c_means {
private int clusters;
	private int m;
	private double epsilon;
	private int maxIterations;

	private double[][] X; // input array X
	private int attributes; // number of attributes
	private double[][] U; // partition matrix
	private double[][] V; // centres of clusters

	/**
	 * Creates FCM algorithm object.
	 * 
	 * @param clusters
	 *            the number of clusters
	 * @param m
	 *            fuzzy partition matrix exponent for controlling the degree of
	 *            fuzzy overlap, with m > 1. Fuzzy overlap refers to how fuzzy
	 *            the boundaries between clusters are, that is the number of
	 *            Elements that have significant membership in more than one
	 *            cluster.
	 * @param epsilon
	 *            must be greater than the maximum difference between the
	 *            corresponding elements of the previous and the current
	 *            partition matrix in order to perform the next iteration.
	 * @param maxIterations
	 *            maximum iterations number
	 */

    
	public fuzzy_c_means(int clusters, int m, double epsilon, int maxIterations) {
		this.clusters = clusters;
		this.m = m;
		this.epsilon = epsilon;
		this.maxIterations = maxIterations;
	}

	public void setInput(double[][] in) {
		X = in;
		attributes = X[0].length;
	}

	public void setInput(Element[] in) {
		int attributes = in[0].atrributes.length;
		double[][] a = new double[in.length][attributes];
		for (int i = 0; i < in.length; i++)
			for (int j = 0; j < attributes; j++)
				a[i][j] = in[i].atrributes[j];
		X = a;
		this.attributes = attributes;
	}

	public void runAlgorythm() {
		double maxDifference = 0;
		U = new double[X.length][clusters];
		fulfilRandomlyU(U);

		int loops = 0;
		do {
			V = centersOfClusters(U);
			double[][] D = distancesMatrix(V);
			if (eachNotNull(D)) {
				double[][] newU = newPartitionMatrix(D);
				maxDifference = maxDifference(U, newU);
				U = newU;
			}
		} while (++loops < maxIterations && maxDifference > epsilon);
	}
public double[][] get_U_i_j() {
    return U;
}

	public Element[] getElements() {
		Element[] result = new Element[X.length];
		for (int i = 0; i < result.length; i++)
			result[i] = new Element();
		for (int k = 0; k < result.length; k++) {
			double max = 0;
			int cluster = 0;
			for (int i = 0; i < clusters; i++) {
				double current = U[k][i];
				if (current > max) {
					max = current;
					cluster = i;
				}
			}
			result[k].cluster = cluster;
			result[k].atrributes = X[k];
		}
		return result;
	}

	public double[][] getCentersOfClusters() {
		return V;
	}
	
	private double maxDifference(double[][] arr1, double[][] arr2) {
		double maxDifference = Float.MIN_VALUE;
		for (int column = 0; column < arr1.length; column++) {
			for (int row = 0; row < arr1[0].length; row++) {
				float difference = (float) Math.abs(arr1[column][row] - arr2[column][row]);
				if (difference > maxDifference)
					maxDifference = difference;
			}
		}
		return maxDifference;
	}

	private boolean eachNotNull(double[][] arr) {
		for (int column = 0; column < arr.length; column++)
			for (int row = 0; row < arr[0].length; row++)
				if (arr[column][row] == 0)
					return false;
		return true;
	}

	private double[][] newPartitionMatrix(double[][] D) {
		double[][] newU = new double[X.length][clusters];
		for (int row = 0; row < clusters; row++)
			for (int column = 0; column < X.length; column++)
				newU[column][row] = u_ij(row, column, D);
		return newU;

	}

	private double u_ij(int row, int column, double[][] D) {
		double denominatorSum = 0;
		for (int s = 0; s < clusters; s++)
			denominatorSum += Math.pow(D[column][row] / D[column][s], 2 / (m - 1));
		return 1 / denominatorSum;
	}

	private double[][] distancesMatrix(double[][] V) {
		double[][] result = new double[X.length][clusters];
		for (int column = 0; column < X.length; column++)
			for (int row = 0; row < clusters; row++)
				result[column][row] = distance(column, row, V);
		return result;
	}

	private double distance(int column, int row, double[][] V) {
		double sum = 0;
		for (int i = 0; i < attributes; i++)
			sum += Math.pow(X[column][i] - V[i][row], 2);
		return (double) Math.sqrt(sum);
	}

	private double[][] centersOfClusters(double[][] partitionMatrix) {
		double[][] res = new double[attributes][clusters];
		for (int row = 0; row < clusters; row++)
			for (int column = 0; column < attributes; column++)
				res[column][row] = v_ij(row, column, partitionMatrix);
		return res;
	}

	private double v_ij(int column, int row, double[][] partitionMatrix) {
		double sumNumerator = 0;
		double sumDenominator = 0;
		for (int k = 0; k < X.length; k++) {
			double denominator = (double) Math.pow(partitionMatrix[k][column], m);
			double licznik = denominator * X[k][row];
			sumNumerator += licznik;
			sumDenominator += denominator;
		}
		return sumNumerator / sumDenominator;
	}

	private void fulfilRandomlyU(double[][] U) {
		for (int i = 0; i < U.length; i++) {
			double valueLeft = 1;
			for (int j = 0; j < U[0].length; j++) {
				if (j != U[0].length - 1) {
					double current = (double) (Math.random() * valueLeft);
					U[i][j] = current;
					valueLeft -= current;
				} else
					U[i][j] = valueLeft;
			}
		}
	}
}
