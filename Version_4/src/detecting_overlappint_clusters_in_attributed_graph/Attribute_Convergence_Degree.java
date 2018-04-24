/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package detecting_overlappint_clusters_in_attributed_graph;

import java.util.ArrayList;

	
public class Attribute_Convergence_Degree {
      //Caltech Dataest
	  static int Node_Count = 769;
	  static int edgeList_Array_Count = 33313;
	  //Caltech Dataest
	  static int[][] edgeList_Array = new int[edgeList_Array_Count][2];
          
          static int[] a1 = new int[Node_Count];
          static int[] a2 = new int[Node_Count];
          static int attribute_Count = 7;
          static int[][] attributeMatrix = new int[Node_Count][attribute_Count];
	
	//  //caltech Dataset Attributed Matrix Path
	  static String attribute_Path = "E:\\MS Computer Science\\MS Thesis\\Java Code\\thesis1\\src\\thesis1\\caltech_attributes.txt";
	//  //caltech Dataset Edge list path
	  static String Path = "E:\\MS Computer Science\\MS Thesis\\Java Code\\thesis1\\src\\thesis1\\Caltech36_edgelist2.txt";
	

    public static void main(String[] args) {
    // TODO Auto-generated method stub
        ACD();
    }
    public static void ACD(){
        int[] visited = new int[Node_Count];
        print_Attributes();
        double[][] ACD_Matrix = new double[Node_Count][Node_Count];
        Graph G = new Graph(Path);
        int numNodes = G.V();
        int numEdges = G.E();
        System.out.println("Number of vertices in G " + numNodes + "  " + (numEdges / 2));

        ArrayList<String> edgeList = G.get_Edge_List();
	System.out.println("Edge List = " + edgeList);
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
        
    } 

	/*   */
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
        
/* a method to check if targetValue is contained in arr */
    static boolean contains(int[] arr, int targetValue) {
        for(int s: arr){
            if(s == targetValue)
                return true;
        }
        return false;
    }
//method contains() ends here  
}
