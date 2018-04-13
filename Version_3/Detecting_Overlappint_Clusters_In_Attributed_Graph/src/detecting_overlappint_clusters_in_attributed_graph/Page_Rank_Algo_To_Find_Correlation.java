package detecting_overlappint_clusters_in_attributed_graph;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//import com.sun.glass.ui.Size;
import Matrix.Matrix;
import Matrix.MatrixMathematics;
import Matrix.NoSquareException;
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

public class Page_Rank_Algo_To_Find_Correlation {

  //Caltech Dataest
  static int Node_Count = 12;
  static int edgeList_Array_Count = 38;
  //Caltech Dataest
  static int attribute_Count = 7;


//  //caltech Dataset Attributed Matrix Path
  static String attribute_Path = "E:\\MS Computer Science\\4rth Semester\\Big Data\\Project\\JavaCode\\Detecting_Overlappint_Clusters_In_Attributed_Graph\\src\\detecting_overlappint_clusters_in_attributed_graph\\caltech_attributes.txt";
//  //caltech Dataset Edge list path
  static String Path = "E:\\MS Computer Science\\4rth Semester\\Big Data\\Project\\JavaCode\\Detecting_Overlappint_Clusters_In_Attributed_Graph\\src\\detecting_overlappint_clusters_in_attributed_graph\\Caltech36_edgelist.txt";

  static int[][] attributeMatrix = new int[Node_Count][7];
  static int[][] edgeList_Array = new int[edgeList_Array_Count][2];
  static int[] a1 = new int[Node_Count];
  static int[] a2 = new int[Node_Count];

   static String[] Global_unique_Occurences;

  
  static int success = 0;
  static int failure = 0;

	/**
	 * @param args
	 */
public static void main(String[] args) throws NoSquareException {
      Graph G = new Graph(Path);
      int numNodes = G.V();
      int numEdges = G.E();
      edgeList_Array = G.get_edgeList_Array();
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
        NCD(SCD_Matrix, ACD_Matrix);
      
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
            Diagonal_Matrix_k[i][i] = 1/sum;
            for(int j=0; j<Node_Count; j++){
                if(j!=i){
                    Diagonal_Matrix_k[i][j] = 0; //k
                }
            }
        }
        for(int i=0; i<Node_Count; i++){
            String temp_3="";
           for(int j=0; j<Node_Count; j++){
               temp_3 = temp_3 + " " +  Diagonal_Matrix_k[i][j];
           } 
           System.err.println("Diagonal_Matrix_k[i][j] = " + temp_3);
        }
        ///////////////////////////////////////
        // Matrix Inverse Logic Start Here
        ///////////////////////////////////////
//        MatrixMathematics inverse = new MatrixMathematics();
// //        Laplace_Matrix = inverse.inverse((Matrix)Diagonal_Matrix_k) * Similarity_Matrix;

//         double [][] values = {{1, 2, 3, 1, 2, 3, 1, 2, 3, 1, 2, 3}, {0, 1, 4, 1, 2, 3, 1, 2, 3, 1, 2, 3}, 
//             {5, 6, 0, 1, 2, 3, 1, 2, 3, 1, 2, 3},
//         {1, 2, 3, 1, 2, 3, 1, 2, 3, 1, 2, 3}, {0, 1, 4, 1, 2, 3, 1, 2, 3, 1, 2, 3}, 
//         {5, 6, 0, 1, 2, 3, 1, 2, 3, 1, 2, 3},
//         {1, 2, 3, 1, 2, 3, 1, 2, 3, 1, 2, 3}, {0, 1, 4, 1, 2, 3, 1, 2, 3, 1, 2, 3}, 
//         {5, 6, 0, 1, 2, 3, 1, 2, 3, 1, 2, 3},
//         {1, 2, 3, 1, 2, 3, 1, 2, 3, 1, 2, 3}, {0, 1, 4, 1, 2, 3, 1, 2, 3, 1, 2, 3}, 
//         {5, 6, 0, 1, 2, 3, 1, 2, 3, 1, 2, 3}};
//         double [][] values_Inverse = new double[12][12];
//         Matrix mat = new Matrix(values); //Initialize matrix size
//         Matrix mat2 = new Matrix(12, 12);
//         mat2 = mat.array_To_Matrix();
//         Matrix mat3 = new Matrix(12, 12);
//         mat3 = inverse.inverse(mat2);
//         values_Inverse = mat3.print_Matrix(mat3);
//         for (int i = 0; i < 12; i++)
//        {
//            String ttt = "";
//            for (int j = 0; j < 12; j++)
//            {
//                ttt = ttt + values_Inverse[i][j] + " ";
//                
//            }
//            System.err.println("Matrix = " + ttt);
//        }
        ///////////////////////////////////////
        // Matrix Inverse Logic Ends Here
        ///////////////////////////////////////
//         System.out.println("Multiplying the matrices...");
//        for (int i = 0; i < Node_Count; i++)
//        {
//            for (int j = 0; j < Node_Count; j++)
//            {
//                for (int k = 0; k < Node_Count; k++)
//                {
//                    Laplace_Matrix[i][j] = Laplace_Matrix[i][j] + values_Inverse[i][k] * Similarity_Matrix[k][j];
//                }
//            }
//        }
        double[][] K_Diagonal_Matrix = new double[Node_Count][Node_Count];
        double[][] K_Diagonal_Matrix_Inverse = new double[Node_Count][Node_Count];
        double[] rowsum_Complete = new double[Node_Count];
        for(int i=0; i<Node_Count; i++){
            String temp_3="";
            double rowsum=0;
           for(int j=0; j<Node_Count; j++){
               temp_3 = temp_3 + " " +  NCD_Matrix[i][j];
               rowsum = rowsum + NCD_Matrix[i][j];
           } 
           rowsum_Complete[i] = rowsum;
//           System.err.println("NCD_Matrix[i][j] = " + temp_3);
        }
        for(int i=0; i<Node_Count; i++){
            K_Diagonal_Matrix[i][i] = rowsum_Complete[i];
//            System.err.println("K_Diagonal_Matrix = ");
        }
        for(int i=0; i<Node_Count; i++){
            String temp_3="";
           for(int j=0; j<Node_Count; j++){
               temp_3 = temp_3 + " " +  K_Diagonal_Matrix[i][j];
           } 
           System.err.println("K_Diagonal_Matrix[i][j] = " + temp_3);
        }
        
//        Matrix mat = new Matrix(K_Diagonal_Matrix); //Initialize matrix size
//         Matrix mat2 = new Matrix(Node_Count, Node_Count);
//         mat2 = mat.array_To_Matrix();
//         Matrix mat3 = new Matrix(Node_Count, Node_Count);
//         mat3 = inverse.inverse(mat2);
//         K_Diagonal_Matrix_Inverse = mat3.print_Matrix(mat3);
//         for (int i = 0; i < Node_Count; i++)
//        {
//            for (int j = 0; j < Node_Count; j++)
//            {
//                System.err.println("Matrix = " + K_Diagonal_Matrix_Inverse[i][j]);
//            }
//        }
    }//NCD function ends here!!
  
  
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
//           System.err.println("SCD_Matrix[i][j] = " + temp_3);
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
