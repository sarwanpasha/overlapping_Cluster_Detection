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
  static int Node_Count = 10;
  static int edgeList_Array_Count = 11;
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

      ////////////////////////////////////////
      //Page rank algo logic Starts Here
      ////////////////////////////////////////
      //int t = 0;
      double N = Node_Count;
      double[] inf_curr = new double[Node_Count];
      double[] inf_next = new double[Node_Count];
      
      for(int i=0; i<Node_Count; i++){
    	  inf_curr[i] = 1/N;
          
//          System.err.println("inf_curr = " + inf_curr[i] + " N = " + N);
      }
    	  double epsilon = 0.001;
          double res = 1;
          double c = 0.85;
          while(res > epsilon){
        	  for(int j=0; j<Node_Count; j++){
        		  inf_next[j] = 0;
        	  }
        	  for(int j=0; j<Node_Count; j++){
        		  //Computing friends of jth node
        		  ArrayList Immediate_friends = new ArrayList();
        		// Immediate Friends of Node 3 (Hidden Node) -> y1, y2, ...
        		  Immediate_friends = Friend_Finder(j); 
        		  Collections.sort(Immediate_friends);
//                          System.err.println("Immediate_friends" + Immediate_friends);
        		  for(int k=0; k<Immediate_friends.size(); k++){
        			  inf_next[j] = inf_curr[j] + 
                                          inf_curr[Integer.parseInt(Immediate_friends.get(k).toString())] * 
                                          (1/Immediate_friends.size());
//                                  System.err.println("Immediate_friends = " + Immediate_friends.size());
//                                  System.err.println("inf_curr j = " + inf_curr[j]);
//                                  System.err.println("inf_curr k = " + inf_curr[k]);
        		  }
        	  }
                  
        	  for(int j=0; j<Node_Count; j++){
        		  inf_next[j] = (int) (c * inf_next[j] + (1 - c) * (1/N));
        	  }
                  
        	  res = Math.abs(inf_curr[1] - inf_next[1]);
        	  inf_curr[1] = inf_next[1];
        	  //t++;
//                  }
        }
      
      for(int p=0;p<Node_Count;p++){
          System.err.println("inf_next " + p + " = " + inf_curr[p]);
      }
      ////////////////////////////////////////
      //Page rank algo logic Ends Here!!!
      ////////////////////////////////////////
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
