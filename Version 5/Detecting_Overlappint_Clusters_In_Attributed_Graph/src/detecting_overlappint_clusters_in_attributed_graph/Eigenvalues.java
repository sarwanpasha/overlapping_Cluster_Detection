/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package detecting_overlappint_clusters_in_attributed_graph;

/******************************************************************************
 *  Compilation:  javac -classpath .:jama.jar Eigenvalues.java
 *  Execution:    java -classpath .:jama.jar Eigenvalues
 *  Dependencies: jama.jar
 *  
 *  Test client for computing eigenvalues and eigenvectors of a real
 *  symmetric matrix A = V D V^T.
 *  
 *       http://math.nist.gov/javanumerics/jama/
 *       http://math.nist.gov/javanumerics/jama/Jama-1.0.1.jar
 *
 ******************************************************************************/

import Jama.Matrix;
import Jama.EigenvalueDecomposition;

public class Eigenvalues {
    public static int row_Dimention;
    public static int Column_Dimention;
    public static double[][] Eigen_Value;
   public static void main(String[] args) { 
//       Compute_Eigen_values();
   }
   
   public Eigenvalues(int row_Dimention_2, int Column_Dimention_2){
       row_Dimention = row_Dimention_2;
       Column_Dimention = Column_Dimention_2;
   }
   public static void set_Eigen_Value_Matrix(double[][] temp){
       Eigen_Value = temp;
   }
   public static double[][] get_Eigen_Value_Matrix(){
       return Eigen_Value;
   }
    public static double[][] Compute_Eigen_values(Matrix A) {
//        int N = 5;
        double[][] Eigen_Vector = new double[row_Dimention][Column_Dimention];
        
        // create a symmetric positive definite matrix
//        Matrix A = Matrix.random(N, N);
//        A = A.transpose().times(A);

        // compute the spectral decomposition
        EigenvalueDecomposition e = A.eig();
        Matrix V = e.getV();
        Matrix D = e.getD();

//        System.out.println("A =");
//        A.print(9, 6);
//        System.out.println("D =");
//        D.print(9, 6);
        Eigen_Value = D.getArray();//Matrix to array conversion!!! (Getting Eigen Values)
        set_Eigen_Value_Matrix(Eigen_Value);
//        System.out.println("V =");
       Eigen_Vector =  V.getArray();//Matrix to array conversion!!! (Getting Eigen Vector)
//        V.print(9, 6);

        // check that V is orthogonal
//        System.out.println("||V * V^T - I|| = ");
//        System.out.println(V.times(V.transpose()).minus(Matrix.identity(row_Dimention, Column_Dimention)).normInf());

        // check that A V = D V
//        System.out.println("||AV - DV|| = ");
//        System.out.println(A.times(V).minus(V.times(D)).normInf());
        
        return Eigen_Vector;
    }
}

