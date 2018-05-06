//Source: https://github.com/patrykheciak/fuzzy-c-means-image-clustering
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package detecting_overlappint_clusters_in_attributed_graph;

/**
 * Stores the Elements attributes required for clustering and the cluster
 * number to which the Element has been assigned
 * 
 * @author Patryk Hêciak
 * @see FCM
 */
public class Element {

	/**
	 * The cluster number to which the element has been assigned.
	 */
	public int cluster;
	/**
	 * An array (vector) of attributes of the element.
	 */
	public double[] atrributes;
	
	public static Element setAttributes(double[] attributes){
		Element element = new Element();
		element.atrributes = attributes;
		return element;
	}
        
    public static void main(String[] args) {
//        // int[element number][attribute number]
//        double[][] elements = new double[5][2];
//        elements[0][0] = 1;
//        elements[0][1] = 2;
//        elements[1][0] = 9;
//        elements[1][1] = 1;
//        elements[2][0] = 2;
//        elements[2][1] = 5;
//        elements[3][0] = 8;
//        elements[3][1] = 2;
//        elements[4][0] = 0;
//        elements[4][1] = 0;
//
//        // create the algorithm object with given parametres
//        fuzzy_c_means fcm = new fuzzy_c_means(3, 2, 0.12, 20);
//// set the input for algorithm
//        fcm.setInput(elements);
//// run the algorith
//        fcm.runAlgorythm();
//// get the result
//        Element[] result = fcm.getElements();
//        
//        for (int i = 0; i < result.length; i++){
//	System.out.println("element " + i + " cluster " + result[i].cluster);
//        }
    }
}
