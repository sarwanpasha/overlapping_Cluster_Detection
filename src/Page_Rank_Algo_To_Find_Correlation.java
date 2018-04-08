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
  static int Node_Count = 769;
  static int edgeList_Array_Count = 33313;
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
  static String attribute_Path = "E:\\MS Computer Science\\MS Thesis\\Java Code\\thesis1\\src\\thesis1\\caltech_attributes.txt";
//  //caltech Dataset Edge list path
  static String Path = "E:\\MS Computer Science\\MS Thesis\\Java Code\\thesis1\\src\\thesis1\\Caltech36_edgelist2.txt";
//   static String Path = "E:\\MS Computer Science\\MS Thesis\\Java Code\\thesis1\\src\\thesis1\\Caltech36_edgelist.txt";
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
  static int[] Hidden_Users = IntStream.rangeClosed(1, Node_Count).toArray();
  

//static int[] Hidden_Users = IntStream.rangeClosed(1,100).toArray();

//  static int[] Hidden_Users_range = IntStream.rangeClosed(1, 769).toArray();
  static int Hidden_Count = Hidden_Users.length;

  static String[] Bin_Distribution_Array=new String[Hidden_Count];
  static String[] Global_unique_Occurences;
  static int[] Global_a1=new int[Hidden_Count];
  static int[] Global_a2=new int[Hidden_Count];
  
  static int success = 0;
  static int failure = 0;
//  static int[] a1_new = new int[Node_Count];
//  static int[] a2_new = new int[Node_Count];
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//      Graph G = new Graph("E:\\MS Computer Science\\MS Thesis\\Java Code\\thesis1\\src\\thesis1\\cl.txt");
      Graph G = new Graph(Path);

      int numNodes = G.V();
      int numEdges = G.E();
      System.out.println("Number of vertices in G " + numNodes + "  " + (numEdges / 2));

      ArrayList<String> edgeList = G.get_Edge_List();

//         System.out.println("Edge List = " + edgeList);
      String temp = G.toString();
//         System.out.println(temp);

      ////////////////////////////////////////
      //Page rank algo logic Starts Here
      ////////////////////////////////////////
      int t = 0;
      int N = Node_Count;
      int[] inf_curr = new int[Node_Count];
      int[] inf_next = new int[Node_Count];
      
      for(int i=0; i<Node_Count; i++){
    	  inf_curr[i] = 1/N;
    	  double epsilon = 0.001;
          int res = 1;
          double c = 0.85;
          while(res > epsilon){
        	  for(int j=0; j<Node_Count; j++){
        		  inf_next[j] = 0;
        	  }
        	  for(int j=0; j<Node_Count; j++){
        		  //Computing friends of jth node
        		  ArrayList Immediate_friends = new ArrayList();
        		// Immediate Friends of Node 3 (Hidden Node) -> y1, y2, ...
        		  Immediate_friends = Friend_Finder((Hidden_Users[i])); 
        		  Collections.sort(Immediate_friends);
        		  for(int k=0; k<Immediate_friends.size(); k++){
        			  inf_next[j] = inf_curr[j] + inf_curr[k] * (1/Immediate_friends.size());
        		  }
        	  }
        	  for(int j=0; j<Node_Count; j++){
        		  inf_next[j] = (int) (c * inf_next[j] + (1 - c) * (1/N));
        	  }
        	  res = Math.abs(inf_curr[i] - inf_next[i]);
        	  inf_curr[i] = inf_next[i];
        	  t++;
          }
      }
      
      ////////////////////////////////////////
      //Page rank algo logic Ends Here!!!
      ////////////////////////////////////////
	}
	
    public static ArrayList Friend_Finder(int Hidden_Node) {
        //        Hidden_Users;
//            int[] friends;
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
        Set<String> hs = (Set<String>) new HashMap<>();
        hs.addAll(friends);
        friends.clear();
        friends.addAll(hs);
//        System.out.println("New Contents of friends: " + friends);

        return friends;
    }

}
