package detecting_overlappint_clusters_in_attributed_graph;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//import com.sun.glass.ui.Size;
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
  static String attribute_Path = "E:\\MS Computer Science\\4rth Semester\\Big Data\\Project\\JavaCode\\overlapping_Cluster_Detection\\src\\detecting_overlappint_clusters_in_attributed_graph\\caltech_attributes.txt";
//  //caltech Dataset Edge list path
  static String Path = "E:\\MS Computer Science\\4rth Semester\\Big Data\\Project\\JavaCode\\overlapping_Cluster_Detection\\src\\detecting_overlappint_clusters_in_attributed_graph\\Caltech36_edgelist.txt";
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
  static int[] a1 = new int[Node_Count];
  static int[] a2 = new int[Node_Count];
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
        SCD(inf_curr);

      
	}
        
    	public static void SCD(double[]inf_current){
//		Graph G = new Graph(Path);
//		edgeList_Array = G.get_edgeList_Array();
		int[] visited = new int[Node_Count];
		double[][] SCD_Matrix = new double[Node_Count][Node_Count];
		ArrayList Immediate_friends = new ArrayList();
		ArrayList Immediate_friends_2 = new ArrayList();
		ArrayList Immediate_friends_3 = new ArrayList();
		for(int i=0; i<Node_Count; i++){
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
			int[] temp_1 = new int[1];
			temp_1[0] = i;
			
			for(int j=0; j<Node_Count; j++){
				boolean j_in_Visited_Array = contains(visited,j);
				double y = 0;
				if(!(j==i) && j_in_Visited_Array==false){
					
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
					SCD_Matrix[i][j] = (double) (z / (x * y));
				}
			}
		visited[i] = i;	

		}
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
