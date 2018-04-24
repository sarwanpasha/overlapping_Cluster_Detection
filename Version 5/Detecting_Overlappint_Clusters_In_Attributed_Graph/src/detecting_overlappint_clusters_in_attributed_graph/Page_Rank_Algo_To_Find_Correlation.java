package detecting_overlappint_clusters_in_attributed_graph;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//import com.sun.glass.ui.Size;
//import Matrix.Matrix;
//import Matrix.MatrixMathematics;
//import Matrix.NoSquareException;
import java.io.BufferedWriter; 
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;
import static oracle.jrockit.jfr.events.Bits.intValue;
import Jama.Matrix;
import Jama.EigenvalueDecomposition;
import static detecting_overlappint_clusters_in_attributed_graph.Q_Modularity.EQ;
import java.util.Objects;
import java.util.Scanner;

public class Page_Rank_Algo_To_Find_Correlation {

  //Caltech Dataest
  static int Node_Count = 12;
  static int edgeList_Array_Count = 38;
  //Caltech Dataest
static int attribute_Count = 7;

static int cnum;
public int cluster;
public double[] atrributes;
public static int cluster_Number = 4;
//   //Rice Dataest
//  static int Node_Count = 4088;
//  static int edgeList_Array_Count = 369657;
//  //Rice Dataest
  
//       //American75 Dataest
//  static int Node_Count = 6387;
//  static int edgeList_Array_Count = 435325;
//  //American75 Dataest
  //
//  static int Node_Count = 6;
//  static int edgeList_Array_Count = 6;
////  //
//  //caltech Dataset Attributed Matrix Path
  static String attribute_Path = "E:\\MS Computer Science\\4rth Semester\\Big Data\\Project\\JavaCode\\Detecting_Overlappint_Clusters_In_Attributed_Graph\\src\\detecting_overlappint_clusters_in_attributed_graph\\caltech_attributes.txt";
//  //caltech Dataset Edge list path
  static String Path = "E:\\MS Computer Science\\4rth Semester\\Big Data\\Project\\JavaCode\\Detecting_Overlappint_Clusters_In_Attributed_Graph\\src\\detecting_overlappint_clusters_in_attributed_graph\\Caltech36_edgelist.txt";
//   static String Path = "E:\MS Computer Science\4rth Semester\Big Data\Project\JavaCode\overlapping_Cluster_Detection\src\detecting_overlappint_clusters_in_attributed_graph\\Caltech36_edgelist.txt";
//
////      //Rice Dataset Attributed Matrix Path
//  static String attribute_Path = "E:\\MS Computer Science\\MS Thesis\\Java Code\\thesis1\\src\\thesis1\\Rice_attributes.txt";
////  //Rice Dataset Edge list path
//  static String Path = "E:\\MS Computer Science\\MS Thesis\\Java Code\\thesis1\\src\\thesis1\\Rice31_edgelist.txt";
////
//          //American75 Dataset Attributed Matrix Path
//  static String attribute_Path = "E:\\MS Computer Science\\MS Thesis\\Java Code\\thesis1\\src\\thesis1\\American75_attributes.txt";
////  //American75 Dataset Edge list path
//  static String Path = "E:\\MS Computer Science\\MS Thesis\\Java Code\\thesis1\\src\\thesis1\\American75_edgelist.txt";
//  
  static int[][] attributeMatrix = new int[Node_Count][7];
  static int[][] edgeList_Array = new int[edgeList_Array_Count][2];
  static int[][] edgeList_Matrix = new int[Node_Count][Node_Count];
  static int[] a1 = new int[Node_Count];
  static int[] a2 = new int[Node_Count];
  static double[] Avg_membership_Degree_array = new double[Node_Count];
//  static int[] Hidden_Users = { 7, 9, 11, 13, 684,704, 712, 725,  743,  751};
//  static int[] Hidden_Users = IntStream.rangeClosed(1, Node_Count).toArray();
  

//static int[] Hidden_Users = IntStream.rangeClosed(1,100).toArray();

//  static int[] Hidden_Users_range = IntStream.rangeClosed(1, 769).toArray();
//  static int Hidden_Count = Hidden_Users.length;

//  static String[] Bin_Distribution_Array=new String[Hidden_Count];
  static String[] Global_unique_Occurences;
//  static int[] Global_a1=new int[Hidden_Count];
//  static int[] Global_a2=new int[Hidden_Count];
  
  static int success = 0;
  static int failure = 0;
//  static int[] a1_new = new int[Node_Count];
//  static int[] a2_new = new int[Node_Count];
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//      Graph G = new Graph(Path);
      Graph G = new Graph(Path);
      int numNodes = G.V();
      int numEdges = G.E();
      edgeList_Array = G.get_edgeList_Array();
      
     //////////////////////////////////
     // Adjacency Matrix populate here
     //////////////////////////////////
      for(int i=0;i<Node_Count; i++){
          for(int j=0;j<Node_Count; j++){
              edgeList_Matrix[i][j] = 0;
          }
      }
      for(int i=0;i<Node_Count; i++){
              edgeList_Matrix[edgeList_Array[i][0]][edgeList_Array[i][1]] = 1;
      }
      // //Print edgeList Matrix !!!
//      for(int i=0;i<Node_Count; i++){
//          String temp = "";
//          for(int j=0;j<Node_Count; j++){
//              temp = temp + edgeList_Matrix[i][j]+ " ";
//          }
//          System.err.println("edgeList_Matrix = " + temp);
//      }
      ////////////////////////////////////
      // Adjacency Matrix ends Here
      ////////////////////////////////////
      System.out.println("Number of vertices in G " + numNodes + "  " + (numEdges / 2));

      ArrayList<String> edgeList = G.get_Edge_List();

//         System.out.println("Edge List = " + edgeList);
      String temp = G.toString();
//         System.out.println(temp);
        double[] inf_curr = new double[Node_Count];
        inf_curr = PageRank();
        
//        for(int p=0;p<Node_Count;p++){
//          System.err.println("inf_curr " + p + " = " + inf_curr[p]);
//      }
         double[][] SCD_Matrix = new double[Node_Count][Node_Count];
         double[][] ACD_Matrix = new double[Node_Count][Node_Count];
        SCD_Matrix = SCD(inf_curr);
        ACD_Matrix = ACD();
        
        double[][] U_i_j_Matrix_temp = new double[Node_Count][cluster_Number];
        U_i_j_Matrix_temp = NCD(SCD_Matrix, ACD_Matrix);
        
        String[] Non_Overlapping_Communities = new String[cluster_Number];
        Non_Overlapping_Communities = Get_Non_Overlapping_Communities(U_i_j_Matrix_temp);
        
        String[] Overlap_Cluster_Array = new String[cluster_Number];
        Overlap_Cluster_Array = Get_Overlapping_Communities(Non_Overlapping_Communities);

        for (int i = 0; i < cluster_Number; i++) {
            System.err.println("Overlap_Cluster_Array = " + Overlap_Cluster_Array[i]);
        }
        
        /////////////////////////////////////////////////////////////
        // Converting overlap and non overlap communities into matrix
        /////////////////////////////////////////////////////////////
        double[][] community_Matrix = new double[numNodes][cluster_Number];
        String tem="";
            for (int i = 0; i < cluster_Number; i++) {
                String[] splitStr2 = Overlap_Cluster_Array[i].split("\\s+");
                for (int j = 0; j < splitStr2.length; j++) {
                    if (splitStr2[j].equals("-1")) {
                    } else {
                        community_Matrix[Integer.parseInt(splitStr2[j])][i] = 1;
//                        tem = tem + community_Matrix[Integer.parseInt(splitStr2[j])][i] + " ";
                    }
                }

            }
            
            for (int i = 0; i < cluster_Number; i++) {
                String[] splitStr2 = Non_Overlapping_Communities[i].split("\\s+");
                for (int j = 0; j < splitStr2.length; j++) {
                    if (splitStr2[j].equals("-1")) {
                    } else {
                        community_Matrix[Integer.parseInt(splitStr2[j])][i] = 1;
//                        tem = tem + community_Matrix[Integer.parseInt(splitStr2[j])][i] + " ";
                    }
                }

            }
            for (int i = 0; i < numNodes; i++) {
                String tempppp="";
                for (int j = 0; j < cluster_Number; j++) {
                    tempppp = tempppp + (int)community_Matrix[i][j] + " ";
                    
                }
//                System.err.println("community_Matrix = " + tempppp);
            }
            
//            System.err.println("community_Matrix = " + tem);
        Q_Modularity a = new Q_Modularity(); 
            Matrix Adjacent=null;
       Matrix moc=null;
       double[][] edgeList_Matrix_temp = new double[Node_Count][Node_Count];
            for (int i = 0; i < Node_Count; i++) {
                for (int j = 0; j < Node_Count; j++) {
                edgeList_Matrix_temp[i][j] = edgeList_Matrix[i][j];
                }
            }
       Matrix community_Matrix_new = new Matrix(community_Matrix);
       Matrix edgeList_Matrix_new = new Matrix(edgeList_Matrix_temp);
       double ttemp = a.EQ(edgeList_Matrix_new,community_Matrix_new);
       
       System.out.print("Community Clustering Effect = "+ttemp);
       
       Average_Conductance(Overlap_Cluster_Array);
       
       
    }//Main Function Ends Here
  
    public static void Average_Conductance(String[] Overlapping_Communities_temp) {

        int[] counter_1 = new int[cluster_Number];
        int[] counter_2 = new int[cluster_Number];
        for (int i = 0; i < cluster_Number; i++) {
            ArrayList<Integer> Community_friends = new ArrayList();
            ArrayList<Integer> Community_friends_temp = new ArrayList();
            String[] splitStr2 = Overlapping_Communities_temp[i].split("\\s+");
            for (int j = 0; j < splitStr2.length; j++) {
                if (splitStr2[j].equals("-1")) {
                } else {
                    Community_friends = Friend_Finder(Integer.parseInt(splitStr2[j]));
                    Community_friends_temp.addAll(Community_friends);

                }
            }
            
            ///////////////////////////////////////
            // Removing duplicates from arraylist starts here
            ///////////////////////////////////////
            Object[] st = Community_friends_temp.toArray();
            for (Object s : st) {
                if (Community_friends_temp.indexOf(s) != Community_friends_temp.lastIndexOf(s)) {
                    Community_friends_temp.remove(Community_friends_temp.lastIndexOf(s));
                }
            }
            Collections.sort(Community_friends_temp);
            ///////////////////////////////////////
            // Removing duplicates from arraylist ends here
            ///////////////////////////////////////
            int counter=0;
            for (int k = 0; k < cluster_Number; k++) {
                if (k != i) {
                    String[] splitStr3 = Overlapping_Communities_temp[k].split("\\s+");
                    for (int j = 0; j < splitStr3.length; j++) {
                        if (splitStr3[j].equals("-1")) {
                        } else {
                            if (Community_friends_temp.contains(Integer.parseInt(splitStr3[j])) == true) {
                                counter++;
                            }
                            else{
//                                System.err.println("Community_friends_temp = " + Community_friends_temp + 
//                                        " AND original value = " + splitStr3[j]);
                            }
                        }
                    }
                }
            }
            counter_1[i] = counter;
//            System.err.println("Counter for " + i + " = " + counter);

        }
        
        //Denumerator logic starts here!!!
        for (int i = 0; i < cluster_Number; i++) {
            int temp_Counter=0;
            String[] splitStr2 = Overlapping_Communities_temp[i].split("\\s+");
            for (int j = 0; j < splitStr2.length; j++) {
                ArrayList<Integer> Community_friends = new ArrayList();
            ArrayList<Integer> Community_friends_temp = new ArrayList();
                if (splitStr2[j].equals("-1")) {
                } else {
                    Community_friends = Friend_Finder(Integer.parseInt(splitStr2[j]));
                    Community_friends_temp.addAll(Community_friends);

                    ///////////////////////////////////////
                    // Removing duplicates from arraylist starts here
                    ///////////////////////////////////////
                    Object[] st = Community_friends_temp.toArray();
                    for (Object s : st) {
                        if (Community_friends_temp.indexOf(s) != Community_friends_temp.lastIndexOf(s)) {
                            Community_friends_temp.remove(Community_friends_temp.lastIndexOf(s));
                        }
                    }
                    Collections.sort(Community_friends_temp);
                    ///////////////////////////////////////
                    // Removing duplicates from arraylist ends here
                    ///////////////////////////////////////
                    for (int k = 0; k < splitStr2.length; k++) {
                        if(Community_friends_temp.contains(Integer.parseInt(splitStr2[k]))){
                            temp_Counter++;
                        }
                    }
                }
            }
            counter_2[i] = temp_Counter;
//            System.err.println("temp_Counter for " + i + " = " + temp_Counter);
        }
        
        double Final_Conductance = 0;
        double[] Final_Conductance_array = new double[cluster_Number];
        int Min_counter_2=0;
        for (int i = 0; i < cluster_Number; i++) {
            if(counter_2[i]<counter_1[i]){
                Min_counter_2 = counter_2[i];
            }
            else{
                Min_counter_2 = counter_1[i];
            }
            if(Min_counter_2!=0){
            Final_Conductance = (double)counter_1[i]/Min_counter_2;
            Final_Conductance_array[i] = Final_Conductance;
            }
            else{
                Final_Conductance=0;
                Final_Conductance_array[i] = Final_Conductance;
            }
        }
            ////////////////////////////////////////////////
            // Average conductance logic starts here
            ////////////////////////////////////////////////
            int community_Counter = 0;
            int Outter_community_Counter = 0;
            double AC_Left = 0;
            double AC_Right = 0;
            for (int i = 0; i < cluster_Number; i++) {
            String[] splitStr2 = Overlapping_Communities_temp[i].split("\\s+");
            for (int j = 0; j < splitStr2.length; j++) {
                if (splitStr2[j].equals("-1")) {
                } else {
                    community_Counter++;
                }
            }
            Outter_community_Counter = Outter_community_Counter + community_Counter;
            AC_Left = AC_Left + Outter_community_Counter;
            AC_Right =  AC_Right + (double)(AC_Left * Final_Conductance_array[i]);
        }
            double ttempp=0;
            if(AC_Left!=0){
            ttempp = (double)1/AC_Left;
            }
            double ttempp_2 = (double)ttempp * AC_Right;
            System.err.println("Average Conductance = " + ttempp_2);
            ////////////////////////////////////////////////
            // Average conductance logic Ends here
            ////////////////////////////////////////////////
        
    }//Average Conductance function ends here!!!
    
    
    
//    public static void Q_Modularity(int numEdges, String[] Overlap_Cluster_Array){
//        double temp=0;
//        temp= (double)1/ (2 * numEdges);
//        double community_Counter=0;
//        
//        for(int i=0; i<Node_Count; i++){
//            int node_i_Counter=0;
//            int node_j_Counter=0;
//            for(int j=0; j<Node_Count; j++){
//                String[] splitStr2 = Overlap_Cluster_Array[j].split("\\s+");
//                for(int k=0; k<splitStr2.length; k++){
//                    if(splitStr2[k].equals(i)){
//                        node_i_Counter++;
//                    }
//                    if(splitStr2[k].equals(j)){
//                        node_j_Counter++;
//                    }
//                }
//                community_Counter = (double)1/(node_i_Counter * node_j_Counter);
//                
//            }
//        }
//    }
    public static String[] Get_Overlapping_Communities(String[] Non_Overlapping_Communities){
        double Avg_membership_Degree = 0;
        String[] Overlap_Cluster_Array = new String[cluster_Number];
        for(int j=0; j<cluster_Number; j++){
           Overlap_Cluster_Array[j] = "-1";
       }
         for(int i=0; i<Node_Count; i++){
//             String wer="";
             for(int j=0; j<cluster_Number; j++){
                 int temp=0;
                 temp = link(i,j,Non_Overlapping_Communities);
                 if(temp > Avg_membership_Degree_array[i]){
                     temp = (int)Avg_membership_Degree_array[i];
                     Overlap_Cluster_Array[j] = Overlap_Cluster_Array[j] + " " + i;
//                     wer = wer + Overlap_Cluster_Array[j] + " ";
                 }
             }
//             System.err.println("Final Overlapping vertices = " + wer);
         }     
         return Overlap_Cluster_Array;
    }
    public static int link(int vertex, int j_th_Cluster,String[] Non_Overlapping_Communities){
        int link_Count=0;
        String[] splitStr2 = Non_Overlapping_Communities[0].split("\\s+");

        ArrayList<Integer> Non_Overlap = new ArrayList();
        ArrayList<Integer> intersection = new ArrayList();
        for(int i=j_th_Cluster; i<j_th_Cluster+1; i++){
            String[] splitStr = Non_Overlapping_Communities[i].split("\\s+");
            for(int j=0; j<splitStr.length; j++){
//                int tt = Integer.parseInt(splitStr[j]);
                if(splitStr[j].equals("null")){
            
                }
                else{
                    Non_Overlap.add(Integer.parseInt(splitStr[j]));
                }
            }
        }
//        System.err.println("Non_Overlap = " + Non_Overlap);
        ArrayList Immediate_friends = new ArrayList();
       Immediate_friends = Friend_Finder(vertex);
//       System.err.println("Immediate_friends = " + Immediate_friends.size());
       intersection = interesect(Non_Overlap,Immediate_friends);
//       System.err.println("intersection size = " + intersection.size());
//       System.err.println("intersection = " + intersection);
       link_Count = intersection.size();
        return link_Count;
    }
    
    private  static ArrayList<Integer> interesect(ArrayList<Integer> f, ArrayList<Integer> s) { 
    ArrayList<Integer> res = new ArrayList<Integer>();

    int i = 0, j = 0; 
    while (i != f.size() && j != s.size()) { 

        if (f.get(i) < s.get(j)) {
            i ++;
        } else if (f.get(i) > s.get(j)) { 
            j ++;
        } else { 
            res.add(f.get(i)); 
            i ++;  j ++;
        }
    }


    return res; 
}
   public static String[] Get_Non_Overlapping_Communities(double[][] U_i_j_Matrix){
       int max_u = 0;
       int label=0;
       int Comm_v_Counter=0;
       String[] Cluster_Array = new String[cluster_Number];
       for(int j=0; j<cluster_Number; j++){
           Cluster_Array[j] = "-1";
       }
       double[] comm = new double[Node_Count];
       double Avg_membership_Degree = 0;
       
       for(int i=0; i<Node_Count; i++){
           Comm_v_Counter=0;
           String tempp="";
          for(int j=0; j<cluster_Number; j++){
              if(U_i_j_Matrix[i][j]>max_u && U_i_j_Matrix[i][j]>0.1){
                  label = j;
                  comm[i] = comm[i] + 1;
                  Comm_v_Counter++;
              }
          }
          Cluster_Array[label] = Cluster_Array[label] + " " + i;
          
//          System.err.println("Cluster_Array = " + Cluster_Array[label]);
          Avg_membership_Degree = AMD(i,Comm_v_Counter);
          Avg_membership_Degree_array[i] = Avg_membership_Degree;
//           System.err.println("Avg_membership_Degree = " + Avg_membership_Degree);
       }
       for(int i=0;i<cluster_Number; i++){
           System.err.println("Non overlapping community = " + Cluster_Array[i]);
       }
       
       return Cluster_Array;
   }
   
   public static double AMD(int v_i, int Comm_v_Counter){
       double Average_Membership_Degree = 0;
       ArrayList Immediate_friends = new ArrayList();
       Immediate_friends = Friend_Finder(v_i);
       
       Average_Membership_Degree = (double)(Immediate_friends.size() / Comm_v_Counter);
       
       return Average_Membership_Degree;

   }
  public static double[][] NCD(double[][] SCD_Matrix,double[][] ACD_Matrix){
        int[] visited = new int[Node_Count];
        double alpha = 0.5;
        double[][] NCD_Matrix = new double[Node_Count][Node_Count];
        double[][] Similarity_Matrix = new double[Node_Count][Node_Count];
        double[][] Diagonal_Matrix_k = new double[Node_Count][Node_Count];
        double[][] Laplace_Matrix = new double[Node_Count][Node_Count];
        for(int i=0; i<Node_Count; i++){
            String NCD_Temp = "";
            double sum = 0;
            for(int j=0; j<Node_Count; j++){
                boolean j_in_Visited_Array = contains(visited,j);
                if(!(j==i) && j_in_Visited_Array==false){
                   NCD_Matrix[i][j] = alpha * SCD_Matrix[i][j] + (1 - alpha) * ACD_Matrix[i][j];
                }
                else if(i==j){
                    NCD_Matrix[i][j] = 1;
                }
                NCD_Temp = NCD_Temp + NCD_Matrix[i][j] + " ";
                //Phase 4 Starts Here!!
               Similarity_Matrix[i][j] =  NCD_Matrix[i][j];
               sum = sum + NCD_Matrix[i][j];
            }
//            System.err.println("NCD_Temp = " + NCD_Temp);
            Diagonal_Matrix_k[i][i] = 1/sum;
            for(int k=0; k<Node_Count;k++){
               Laplace_Matrix[i][k] =  Similarity_Matrix[i][k] * Diagonal_Matrix_k[i][i];
            }
//            System.err.println("sum = " + sum);
            for(int j=0; j<Node_Count; j++){
                if(j!=i){
                    Diagonal_Matrix_k[i][j] = 0; //k
                }
            }
        }
        for(int i=0; i<Node_Count; i++){
            String temp_3="";
           for(int j=0; j<Node_Count; j++){
               temp_3 = temp_3 + " " +  Laplace_Matrix[i][j];
           }
//           System.err.println("Laplace_Matrix[i][j] = " + temp_3);
        }
        ///////////////////////////////////////
        // Eigen Value Logic Start Here
        ///////////////////////////////////////
//         double [][] values_Inverse = new double[12][12];
//         Matrix mat = new Matrix(Laplace_Matrix); //Initialize matrix size
//         Matrix mat2 = new Matrix(12, 12);
//         mat2 = mat.array_To_Matrix();
         Matrix eigen_Matrix = new Matrix(Laplace_Matrix);
         int row_Dimention = eigen_Matrix.getRowDimension();
         int Column_Dimention = eigen_Matrix.getColumnDimension();
         Eigenvalues a = new Eigenvalues(row_Dimention,Column_Dimention);
         double[][] Eigen_Vector = new double[row_Dimention][Column_Dimention];
         Eigen_Vector = a.Compute_Eigen_values(eigen_Matrix);
         
      //   //Display Eigen vector!!!
//         for(int u=0;u<row_Dimention; u++){
//             String temp = "";
//            for(int y=0;y<row_Dimention; y++){
//                temp = temp + Eigen_Vector[u][y] + " ";
//            } 
//             System.err.println("Eigen_Vector = " + temp);
//         }
         
         double[][] Eigen_Values = new double[row_Dimention][Column_Dimention];
         double[] Eigen_Values_array = new double[row_Dimention];
         Eigen_Values = a.get_Eigen_Value_Matrix();
          //Display Eigen Values!!!
         for(int u=0;u<row_Dimention; u++){
             String temp = "";
            for(int y=0;y<row_Dimention; y++){
                temp = temp + Eigen_Values[u][y] + " ";
            } 
            Eigen_Values_array[u] = Eigen_Values[u][u];
//             System.err.println("Eigen_Values_array = " + Eigen_Values_array[u]);
         }
         int k=4;
         double[] top_k_Eigen_Values = new double[k];
         top_k_Eigen_Values = indexesOfTopElements(Eigen_Values_array,k);
         
//         for(int u=0; u<k; u++){
//             System.err.println("top_k_Eigen_Values = " + top_k_Eigen_Values[u]);
//         }
         
         double[][] k_Row_Vectors = new double[k][Column_Dimention];
         for(int u=0; u<k; u++){
             String temp = "";
             for(int y=0; y<Column_Dimention; y++){
                 k_Row_Vectors[u][y] = Eigen_Vector[(int)top_k_Eigen_Values[u]][y];
                 temp = temp + k_Row_Vectors[u][y] + " ";
             }
             System.err.println("k_Row_Vectors = " + temp);
         }
         double[][] k_Row_Vectors_new = new double[Column_Dimention][k];
         for(int u=0; u<Column_Dimention; u++){
             String temp = "";
             for(int y=0; y<k; y++){
                 k_Row_Vectors_new[u][y] = k_Row_Vectors[y][u];
                 temp = temp + k_Row_Vectors_new[u][y] + " ";
             }
//             System.err.println("k_Row_Vectors new = " + temp);
         }
         
        double epsilon = 0.001;
        double res = 1;
        double Membership_Degree_Of_Vertex_i_And_Cluster_j_temp = 1/k;
        
        
        
        double[] Column_Vector = new double[Node_Count];
        double[] Cluster_j = new double[cluster_Number];
        double centroid_i=0;
        double[] centroid_i_array= new double[k];
        double Column_Vector_avg=0;
        for(int row=0; row<k;row++){
            for(int column=0; column<Node_Count;column++){
                Column_Vector[row] = k_Row_Vectors_new[column][row];
                Column_Vector_avg = Column_Vector_avg +  Column_Vector[row];
            }//Column loop ends here
            centroid_i = Column_Vector_avg/Node_Count;
            centroid_i_array[row] = centroid_i;
        }
        
        //////////////////////////////////////
        // Fuzzy C mean Logic Starts Here!!
        //////////////////////////////////////
        
//          int[element number][attribute number]
//        int[][] elements = new int[5][2];
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
double[][] U_i_j_Matrix_temp = new double[Node_Count][cluster_Number];
         double[][] elements;
         elements = k_Row_Vectors_new;
        // create the algorithm object with given parametres
        //Parameters (int clusters, int m, double epsilon, int maxIterations)
        fuzzy_c_means fcm = new fuzzy_c_means(4, 2, 0.001, 20);
// set the input for algorithm
        fcm.setInput(elements);
// run the algorith
        fcm.runAlgorythm();
// get updated U_i_j
        U_i_j_Matrix_temp = fcm.get_U_i_j();
// get the result
        Element[] result = fcm.getElements();
        
        for (int i = 0; i < result.length; i++){
//	System.out.println("element " + i + " cluster " + result[i].cluster);
        }
        
        for(int i=0; i<Node_Count; i++){
            String temppp="";
            for(int j=0; j<cluster_Number; j++){
                temppp = temppp + U_i_j_Matrix_temp[i][j] + " ";
            }
//            System.err.println("U_i_j_Matrix_temp = " + temppp);
        }

//        double[] c_i = new double[Node_Count];
//        double[][] U_i_j_Matrix_temp = new double[Node_Count][cluster_Number];
//        double[][] U_i_j_Matrix = new double[Node_Count][cluster_Number];
//        for(int i=0; i<Node_Count; i++){
//            String temppp="";
//            for(int j=0; j<cluster_Number; j++){
//                U_i_j_Matrix[i][j] = (double)1/k;
//                temppp = temppp + U_i_j_Matrix[i][j] + " ";
//            }
//            System.err.println("U_i_j_Matrix Initial = " + temppp);
//        }
//        while(res>epsilon){
//            System.err.println("res before = " + res);
//            U_i_j_Matrix_temp = U_i_j_Matrix;
//            for(int i=0;i<Node_Count;i++){
//                double min=1000;
//                int min_index=0;
//                double c=0;
//                for(int j=0;j<k;j++){
//                   c = Math.abs(Eigen_Values_array[i] - centroid_i_array[j]);
//                   if(c<min){
//                       min=c;
//                       min_index=j;
//                   }
//                }
//                c_i[i] = centroid_i_array[min_index];
//            }
//            int m=2;
//            
//            for(int i=0; i<Node_Count; i++){
//                String temppp="";
//                for(int j=0; j< cluster_Number; j++){
//                    U_i_j_Matrix_temp =  Membership_Degree_Of_Vertex_i_And_Cluster_j(cluster_Number, 
//                   centroid_i_array ,m,c_i);
//                    
//                    temppp = temppp + U_i_j_Matrix_temp[i][j] + " ";
//                    
//            Find_Jth_Community(Node_Count,k,cluster_Number, k_Row_Vectors_new,Laplace_Matrix
//            , c_i,U_i_j_Matrix_temp);
//                }
//                System.err.println("U_i_j_Matrix_temp = " + temppp);
//            }
//            
//            ///////////////////////////////////////////////////////
//            // Frobenius matrix norm logic start here
//            ///////////////////////////////////////////////////////
//           double Frobenius_norm_Of_Matrix_U_i_j_Matrix_temp_1=0;
//           double Frobenius_norm_Of_Matrix_U_i_j_Matrix_1=0;
//           double Frobenius_norm_Of_Matrix_U_i_j_Matrix_temp=0;
//           double Frobenius_norm_Of_Matrix_U_i_j_Matrix=0;
//           for(int r=0; r<Node_Count; r++){
//               for(int t=0; t<cluster_Number; t++){
//                   Frobenius_norm_Of_Matrix_U_i_j_Matrix_temp_1 = Math.abs(Frobenius_norm_Of_Matrix_U_i_j_Matrix_temp_1) + 
//                           Math.abs(Math.pow(U_i_j_Matrix_temp[r][t],2));
//                   Frobenius_norm_Of_Matrix_U_i_j_Matrix_1 = Math.abs(Frobenius_norm_Of_Matrix_U_i_j_Matrix_1) + 
//                           Math.abs(Math.pow(U_i_j_Matrix[r][t],2));
//               }
//           }
//            ///////////////////////////////////////////////////////
//            // Frobenius matrix norm logic start here
//            ///////////////////////////////////////////////////////
//           Frobenius_norm_Of_Matrix_U_i_j_Matrix_temp = Math.pow(Frobenius_norm_Of_Matrix_U_i_j_Matrix_temp_1,0.5);
//           Frobenius_norm_Of_Matrix_U_i_j_Matrix = Math.pow(Frobenius_norm_Of_Matrix_U_i_j_Matrix_1,0.5); 
//           ///////////////////////////////////////////////////////
//            // Frobenius matrix norm logic Ends here
//            ///////////////////////////////////////////////////////
//           res = Math.abs(Frobenius_norm_Of_Matrix_U_i_j_Matrix_temp - Frobenius_norm_Of_Matrix_U_i_j_Matrix);
//           
//           System.err.println("Frobenius_norm_Of_Matrix_U_i_j_Matrix_temp = " + 
//                   Frobenius_norm_Of_Matrix_U_i_j_Matrix_temp);
//           
//           System.err.println("Frobenius_norm_Of_Matrix_U_i_j_Matrix = " + 
//                   Frobenius_norm_Of_Matrix_U_i_j_Matrix);
//           
//           System.err.println("res after = " + res);
//        } // while loop ends here!!
        
        //////////////////////////////////////
        // Fuzzy C mean Logic Ends Here!!
        //////////////////////////////////////
         
        return U_i_j_Matrix_temp;

    }//NCD function ends here!!

	
	
	public static Element setAttributes(double[] attributes){
		Element element = new Element();
		element.atrributes = attributes;
		return element;
	}
  static double[][] Membership_Degree_Of_Vertex_i_And_Cluster_j( 
      int cluster_Number, double[] c_j, int m, double[] Initial_c_i){
//      double[] Row_Avg_c_i = new double[Node_Count];
      double temp=0;
      double temp_2=0;
      
      double temp_power;
      double temp_Numerator=0;
      double temp_Denumerator=0;
      double[][] U_i_j_Matrix = new double[Node_Count][cluster_Number];
      temp_power = (double)2/(m-1);
      for (int i = 0; i < Node_Count; i++) {
          String tt="";
//          for (int j = 0; j < Node_Count; j++) {
//              temp = temp + Laplace_Matrix_temp[i][j];
//          }
//          Row_Avg_c_i[i] = temp / Node_Count;
          for (int j = 0; j < cluster_Number; j++) {
              double temp_3=0;
              for (int k = 0; k < cluster_Number; k++) {
                  temp_Numerator = Math.abs(Initial_c_i[i] - c_j[j]);
//                  System.err.println("In Membership degree function!!! temp_Numerator = " + temp_Numerator);
                  temp_Denumerator = Math.abs(Initial_c_i[i] - c_j[k]);
//                  System.err.println("In Membership degree function!!! temp_Denumerator = " + temp_Denumerator);
                  if(temp_Denumerator!=0){
                  temp_2 = (double)temp_Numerator / temp_Denumerator;
                  }
                  else{
                      temp_2=0;
                  }
                  temp_3 = temp_3 + Math.pow(temp_2, temp_power);
              }
              if(temp_3!=0){
              U_i_j_Matrix[i][j] = (double)1 / temp_3;
              }
              else{
                  U_i_j_Matrix[i][j]=0;
              }
              tt = tt + U_i_j_Matrix[i][j] + " ";
              
          }
//            System.err.println("In Membership degree function!!! U ij = " + tt);
      }
      return U_i_j_Matrix;
      
  }
  
  static void Find_Jth_Community(int n, int k, int cluster_Number,double[][] k_Row_Vectors,
          double[][] Laplace_Matrix, double[] Initial_c_i, double[][] U_i_j_Matrix_new){
    double[][] Membership_Degree_Matrix_u = {{0,0,0,1},{0,0,0,1},{0,0,0,1},{0,0,0,1},
                                                {0,0,0,1},{0,0,0,1},{0,0,0,1},{0,0,0,1},
                                                {0,0,0,1},{0,0,0,1},{0,0,0,1},{0,0,0,1}};
    int m=2;
     double[] Column_Vector = new double[Node_Count];
     double[] Cluster_j = new double[cluster_Number];
     double centroid_i=0;
     double Column_Vector_avg=0;
//    for(int i=0; i<cluster_Number;i++){
        
        for(int row=0; row<k;row++){
//            for(int column=0; column<Node_Count;column++){
//                Column_Vector[row] = k_Row_Vectors[column][row];
//                Column_Vector_avg = Column_Vector_avg +  Column_Vector[row];
//            }//Column loop ends here
//            centroid_i = Column_Vector_avg/Node_Count;
            double tempp;
            double tempp_2;
            double tempp_3=0;
            double tempp_4;
            double tempp_5=0;
            double Cluster_j_temp;
            for(int clust=0;clust<Node_Count ; clust++){
                for(int q=0; q<cluster_Number;q++){
//                    System.err.println("Node Count = " + Node_Count + " clust = " + clust + " q = " + q);
                   tempp = Membership_Degree_Matrix_u[clust][q];
                   tempp_2 = Math.pow(tempp,m);
                   tempp_5 = tempp_5 + tempp_2;
                   tempp_3 = tempp_3 + tempp_2 * Initial_c_i[clust];
                }
                
            }
            if(tempp_5!=0){
            Cluster_j[row] = tempp_3 / tempp_5;
            }
            else{
                Cluster_j[row]=0;
            }
//                System.err.println("Cluster_j for "+ row + " = " + Cluster_j[row]);
            
        } //Row loop ends here
//        Membership_Degree_Of_Vertex_i_And_Cluster_j(Laplace_Matrix,cluster_Number,Cluster_j,m);
//    }
//    for(int k=1; k<){
//        
//    }
      
  }
  
  
  

  static double[] indexesOfTopElements(double[] orig, int nummax) {
        double[] copy = Arrays.copyOf(orig,orig.length);
        Arrays.sort(copy);
        double[] honey = Arrays.copyOfRange(copy,copy.length - nummax, copy.length);
        double[] result = new double[nummax];
        int resultPos = 0;
        for(int i = 0; i < orig.length; i++) {
            double onTrial = orig[i];
            int index = Arrays.binarySearch(honey,onTrial);
            if(index < 0) continue;
            result[resultPos++] = i;
        }
        return result;
    }
  
 public static double[][] ACD(){
        int[] visited = new int[Node_Count];
        print_Attributes();
        double[][] ACD_Matrix = new double[Node_Count][Node_Count];
        Graph G = new Graph(Path);
        int numNodes = G.V();
        int numEdges = G.E();

        ArrayList<String> edgeList = G.get_Edge_List();

//           System.out.println("Edge List = " + edgeList);
        String temp = G.toString();
        
	edgeList_Array = G.get_edgeList_Array();
        
        for(int i=0; i<Node_Count; i++){
            for(int j=0; j<Node_Count; j++){
                boolean j_in_Visited_Array = contains(visited,j);
                if(!(j==i) && j_in_Visited_Array==false){
                    double distance = 0;
                    String[] a2_str = new String[a2.length];
                    for (int k = 0; k < attribute_Count; k++) {
                        a1[k] = attributeMatrix[i][k];
                        a2[k] = attributeMatrix[j][k];
                        a2_str[k] = String.valueOf(a2[k]);
//                      System.out.println("A1 = " + a1[i] + " A2 = " + a2[i]);
                    }
                    int temp_i = 1;
                    while(temp_i < a1.length){
                        distance = distance + Math.pow((a1[temp_i] - a2[temp_i]),2);
                        temp_i++;
                    }
                    distance = Math.sqrt(distance);
                    
                    ACD_Matrix[i][j] = 1 / (1 + distance);
                }
                else if(i==j){
                        ACD_Matrix[i][j] = 1;
                    }
            }
            
        }
        
                for(int i=0; i<Node_Count; i++){
            String temp_3="";
           for(int j=0; j<Node_Count; j++){
               temp_3 = temp_3 + " " +  ACD_Matrix[i][j];
           } 
//           System.err.println("ACD_Matrix[i][j] = " + temp_3);
        }
                return ACD_Matrix;
    }
        public static void print_Attributes() {
        Graph G = new Graph();
        G.Read_Attributed_Graph(attribute_Path);

        attributeMatrix = G.get_Attributed_Graph();

        StringBuilder stringBuilder = new StringBuilder();
        String NEWLINE = System.getProperty("line.separator");
        for (int i = 0; i < Node_Count - 1; i++) {
            for (int j = 0; j < 7; j++) {
                stringBuilder.append(attributeMatrix[i][j]);
                stringBuilder.append(" ");
            }
            stringBuilder.append(NEWLINE);
        }
        String finalString = stringBuilder.toString();
//        System.out.println(finalString);
    }
        
        
        
public static double[][] SCD(double[]inf_current){
//		Graph G = new Graph(Path);
//		edgeList_Array = G.get_edgeList_Array();
        int[] visited = new int[Node_Count];
        double[][] SCD_Matrix = new double[Node_Count][Node_Count];
        ArrayList Immediate_friends = new ArrayList();
        ArrayList Immediate_friends_2 = new ArrayList();
        ArrayList Immediate_friends_3 = new ArrayList();
        for(int i=0; i<Node_Count; i++){
//            System.err.println("i = " + i);
                double x=0;
                 //Computing friends of jth node

        // Immediate Friends of Node 3 (Hidden Node) -> y1, y2, ...
          Immediate_friends = Friend_Finder(i); 
          Collections.sort(Immediate_friends);
                for(int j=0; j<Immediate_friends.size(); j++){
                        int temp_size = (int) Immediate_friends.get(j);
                        x = x + inf_current[temp_size];
                }
                x = Math.sqrt(x);
//                int[] temp_1 = new int[1];
//                temp_1[0] = i;

                for(int j=0; j<Node_Count; j++){
//                    System.err.println("j = " + j);
//                    System.err.println("visited = " + visited[j]);
                        boolean j_in_Visited_Array = contains(visited,j);
                        double y = 0;
                        if(j_in_Visited_Array==false){

                                //Computing friends of jth node

                                // Immediate Friends of Node 3 (Hidden Node) -> y1, y2, ...
                                Immediate_friends_2 = Friend_Finder(j); 
                                  Collections.sort(Immediate_friends_2);
                                for(int k=0; k<Immediate_friends_2.size(); k++){
                                        y = y + inf_current[(int) Immediate_friends_2.get(k)];
                                }     
                                y = Math.sqrt(y);

                                double z=0;
                                Immediate_friends_3 = (ArrayList) intersection(Immediate_friends,Immediate_friends_2);
//					System.err.println("Immediate_friends_3 = " + Immediate_friends_3);
                                for(int k=0; k<Immediate_friends_3.size(); k++){
                                    int temp = (int) Immediate_friends_3.get(k);
//                                            System.err.println("inf_current = " + temp);
                                    double temp_2 = (double)inf_current[temp];
                                        z = z + temp_2;
                                }
//                                System.err.println("0 check = " + (x * y));
//                                System.err.println("i = " + i + " j = " + j);
//                                if(i==j){
//                                    System.err.println("z = " + z + " (x) =  " + x + " y = " + y);
//                                    }
//                                System.err.println("visited[i] = " + visited[i]);
                                if((x * y)!=0){
                                    
                                SCD_Matrix[i][j] = (double) (z / (x * y));
                                SCD_Matrix[j][i] = (double) (z / (x * y));
                                }
                                else{
                                    SCD_Matrix[i][j]=0;
                                }
                        }
                        	
                }
        visited[i] = i;
        }
        
        for(int i=0; i<Node_Count; i++){
            String temp_3="";
           for(int j=0; j<Node_Count; j++){
               temp_3 = temp_3 + " " +  SCD_Matrix[i][j];
           } 
           System.err.println("SCD_Matrix[i][j] = " + temp_3);
        }
        return SCD_Matrix;
}
        
        
        public static double[] PageRank(){
                  ////////////////////////////////////////
      //Page rank algo logic Starts Here
      ////////////////////////////////////////
      //int t = 0;
      double N = Node_Count;
      double[] inf_curr = new double[Node_Count];
      double[] inf_next = new double[Node_Count];
      
      for(int i=1; i<Node_Count; i++){
    	  inf_curr[i] = 1/N;
          
//          System.err.println("inf_curr = " + inf_curr[i] + " N = " + N);
      }
    	  double epsilon = 0.001;
          double res = 1;
          double c = 0.85;
          while(res > epsilon){
        	  for(int j=1; j<Node_Count; j++){
        		  inf_next[j] = 0;
        	  }
        	  for(int j=1; j<Node_Count; j++){
        		  //Computing friends of jth node
        		  ArrayList Immediate_friends = new ArrayList();
        		// Immediate Friends of Node 3 (Hidden Node) -> y1, y2, ...
        		  Immediate_friends = Friend_Finder(j); 
        		  Collections.sort(Immediate_friends);
//                          System.err.println("Immediate_friends" + Immediate_friends);
        		  for(int k=1; k<Immediate_friends.size(); k++){
//                              System.err.println("Immediate_friends  = " + Immediate_friends.get(k).toString());
                                double temp_2 = (double)1/Immediate_friends.size();
        			  inf_next[j] = inf_next[j] + 
                                          inf_curr[Integer.parseInt(Immediate_friends.get(k).toString())-1] * 
                                          (temp_2);
//                                  System.err.println("test j = " +  inf_curr[Integer.parseInt(Immediate_friends.get(k).toString())-1]
//                                   + " -- = " + temp_2);
//                                  System.err.println("inf_curr = " + inf_next[j]);
        		  }
        	  }
                  
        	  for(int j=1; j<Node_Count; j++){
        		  inf_next[j] = (double) (c * inf_next[j] + (1 - c) * (1/N));
//                          System.err.println("inf_next = " + inf_next[j]);
        	  }
                   double sum=0;
                  for(int y=1; y<Node_Count; y++){
        	  sum = sum + Math.abs(inf_curr[y] - inf_next[y]);
        	  inf_curr[y] = inf_next[y];
                  }
//                  System.err.println("sum = " + sum);
                  res = sum;
                  
//                  for(int y=0; y<Node_Count; y++){
//                      System.err.println("inf_curr!! = " + inf_curr[y]);
//                  }
        	  //t++;
//                  }
        }
      
//      for(int p=0;p<Node_Count;p++){
//          System.err.println("inf_curr " + p + " = " + inf_curr[p]);
//      }
      ////////////////////////////////////////
      //Page rank algo logic Ends Here!!!
      ////////////////////////////////////////
      return inf_curr;
        }
        
        

	
	   public static <T> List<T> intersection(List<T> list1, List<T> list2) {
	        List<T> list = new ArrayList<T>();

	        for (T t : list1) {
	            if(list2.contains(t)) {
	                list.add(t);
	            }
	        }

	        return list;
	    }
	public static boolean contains(int[] arr, int targetValue) {
	    for(int s: arr){
	        if(s == targetValue)
	            return true;
	    }
	    return false;
	}
    /* Union of multiple arrays */
    public static int[] unionArrays(int[]... arrays)
    {
        int maxSize = 0;
        int counter = 0;

        for(int[] array : arrays) maxSize += array.length;
        int[] accumulator = new int[maxSize];

        for(int[] array : arrays)
            for(int i : array)
                if(!isDuplicated(accumulator, counter, i))
                    accumulator[counter++] = i;

        int[] result = new int[counter];
        for(int i = 0; i < counter; i++) result[i] = accumulator[i];

        return result;
    }

    public static boolean isDuplicated(int[] array, int counter, int value)
    {
        for(int i = 0; i < counter; i++) if(array[i] == value) return true;
        return false;
    }
    
    public static ArrayList Friend_Finder(int Hidden_Node) {
        //        Hidden_Users;
//            int[] friends;
//        System.err.println("Hidden_Node = " + Hidden_Node);
        ArrayList friends = new ArrayList();
        for (int i = 0; i < edgeList_Array_Count; i++) {
            if (edgeList_Array[i][0] != edgeList_Array[i][1]) {
                if (edgeList_Array[i][0] == Hidden_Node) {
                    friends.add(edgeList_Array[i][1]);
                }
                if (edgeList_Array[i][1] == Hidden_Node) {
                    friends.add(edgeList_Array[i][0]);
                }
            }
        }
//        System.out.println("Contents of friends: " + friends);
        Set<String> hs = new HashSet<>();
        hs.addAll(friends);
        friends.clear();
        friends.addAll(hs);
//        System.out.println("New Contents of friends: " + friends);

        return friends;
    }
}
