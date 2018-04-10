/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package detecting_overlappint_clusters_in_attributed_graph;

import Matrix.Matrix;
import Matrix.MatrixMathematics;
import Matrix.NoSquareException;

/**
 *
 * @author Pasha
 */

public class Node_Convergence_Degree {
       //Caltech Dataest
	  static int Node_Count = 769;
	  static int edgeList_Array_Count = 33313;
	  //Caltech Dataest
	  static int[][] edgeList_Array = new int[edgeList_Array_Count][2];
          
          static int[] a1 = new int[Node_Count];
          static int[] a2 = new int[Node_Count];
          static int attribute_Count = 7;
          static int[][] attributeMatrix = new int[Node_Count][attribute_Count];
	//   //Rice Dataest
	//  static int Node_Count = 4088;
	//  static int edgeList_Array_Count = 369657;
	//  //Rice Dataest
	  
//	       //American75 Dataest
	//  static int Node_Count = 6387;
	//  static int edgeList_Array_Count = 435325;
	//  //American75 Dataest
	  //
	//  static int Node_Count = 6;
	//  static int edgeList_Array_Count = 6;
	////  //
	//  //caltech Dataset Attributed Matrix Path
	  static String attribute_Path = "E:\\MS Computer Science\\MS Thesis\\Java Code\\thesis1\\src\\thesis1\\caltech_attributes.txt";
	//  //caltech Dataset Edge list path
	  static String Path = "E:\\MS Computer Science\\MS Thesis\\Java Code\\thesis1\\src\\thesis1\\Caltech36_edgelist2.txt";
	//   static String Path = "E:\\MS Computer Science\\MS Thesis\\Java Code\\thesis1\\src\\thesis1\\Caltech36_edgelist.txt";
	//
////	      //Rice Dataset Attributed Matrix Path
	//  static String attribute_Path = "E:\\MS Computer Science\\MS Thesis\\Java Code\\thesis1\\src\\thesis1\\Rice_attributes.txt";
	////  //Rice Dataset Edge list path
	//  static String Path = "E:\\MS Computer Science\\MS Thesis\\Java Code\\thesis1\\src\\thesis1\\Rice31_edgelist.txt";
	////
//	          //American75 Dataset Attributed Matrix Path
	//  static String attribute_Path = "E:\\MS Computer Science\\MS Thesis\\Java Code\\thesis1\\src\\thesis1\\American75_attributes.txt";
	////  //American75 Dataset Edge list path
	//  static String Path = "E:\\MS Computer Science\\MS Thesis\\Java Code\\thesis1\\src\\thesis1\\American75_edgelist.txt";
	//     
      public static void main(String[] args) throws NoSquareException {
    // TODO Auto-generated method stub
//        NCD();
        MatrixMathematics inverse = new MatrixMathematics();
//        Laplace_Matrix = inverse.inverse((Matrix)Diagonal_Matrix_k) * Similarity_Matrix;

         double [][] values = {{1, 2, 3}, {0, 1, 4}, {5, 6, 0}};
         double [][] values_Inverse = new double[3][3];
         Matrix mat = new Matrix(values); //Initialize matrix size
         Matrix mat2 = new Matrix(3, 3);
         mat2 = mat.array_To_Matrix();
         Matrix mat3 = new Matrix(3, 3);
         mat3 = inverse.inverse(mat2);
         values_Inverse = mat3.print_Matrix(mat3);
         for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                System.err.println("Matrix = " + values_Inverse[i][j]);
            }
        }
    }  
      
    public static void NCD(double[][] SCD_Matrix,double[][] ACD_Matrix) throws NoSquareException{
        int[] visited = new int[Node_Count];
        double alpha = 0.5;
        double[][] NCD_Matrix = new double[Node_Count][Node_Count];
        double[][] Similarity_Matrix = new double[Node_Count][Node_Count];
        double[][] Diagonal_Matrix_k = new double[Node_Count][Node_Count];
        double[][] Laplace_Matrix = new double[Node_Count][Node_Count];
        for(int i=0; i<Node_Count; i++){
            double sum = 0;
            for(int j=0; j<Node_Count; j++){
                boolean j_in_Visited_Array = contains(visited,j);
                if(!(j==i) && j_in_Visited_Array==false){
                   NCD_Matrix[i][j] = alpha * SCD_Matrix[i][j] + (1 - alpha) * ACD_Matrix[i][j];
                }
                //Phase 4 Starts Here!!
               Similarity_Matrix[i][j] =  NCD_Matrix[i][j];
               sum = sum + NCD_Matrix[i][j];
            }
            Diagonal_Matrix_k[i][i] = sum;
            for(int j=0; j<Node_Count; j++){
                if(j!=i){
                    Diagonal_Matrix_k[i][j] = 0;
                }
            }
        }
        MatrixMathematics inverse = new MatrixMathematics();
//        Laplace_Matrix = inverse.inverse((Matrix)Diagonal_Matrix_k) * Similarity_Matrix;
         double [][] values = {{1, 1, 2}, {2, 4, -3}, {3, 6, -5}};
         Matrix mat = new Matrix(values); //Initialize matrix size
         Matrix mat2 = new Matrix(3, 3);
         mat2 = mat.array_To_Matrix();
         inverse.inverse(mat2);
         
//        inverse.multiplyByConstant(values);
//        Laplace_Matrix = 
        
    }//NCD function ends here!!


    static boolean contains(int[] arr, int targetValue) {
        for(int s: arr){
            if(s == targetValue)
                return true;
        }
        return false;
    }
}
