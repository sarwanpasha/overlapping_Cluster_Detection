import detecting_overlappint_clusters_in_attributed_graph.Graph;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Structure_Convergence_Degree {
	  //Caltech Dataest
  static int Node_Count = 12;
  static int edgeList_Array_Count = 38;
	  //Caltech Dataest
	  static int[][] edgeList_Array = new int[edgeList_Array_Count][2];
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
//  //caltech Dataset Attributed Matrix Path
  static String attribute_Path = "E:\\MS Computer Science\\4rth Semester\\Big Data\\Project\\JavaCode\\overlapping_Cluster_Detection\\src\\detecting_overlappint_clusters_in_attributed_graph\\caltech_attributes.txt";
//  //caltech Dataset Edge list path
  static String Path = "E:\\MS Computer Science\\4rth Semester\\Big Data\\Project\\JavaCode\\overlapping_Cluster_Detection\\src\\detecting_overlappint_clusters_in_attributed_graph\\Caltech36_edgelist.txt";
//   static String Path = "E:\MS Computer Science\4rth Semester\Big Data\Project\JavaCode\overlapping_Cluster_Detection\src\detecting_overlappint_clusters_in_attributed_graph\\Caltech36_edgelist.txt";
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
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        Graph G = new Graph(Path);
	edgeList_Array = G.get_edgeList_Array();
//        for(int i=0; i<edgeList_Array_Count; i++){
//            for(int j=0; j<2; j++){
//                 System.err.println("edgeList_Array = " + edgeList_Array[i][j]);
//            }
//        }
//        System.err.println("Friends = " + Friend_Finder(2));
       
	}
	
	public void SCD(int[]inf_current){
		Graph G = new Graph(Path);
		edgeList_Array = G.get_edgeList_Array();
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
				x = Math.sqrt(x);
			}
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
						y = Math.sqrt(y);
					}     
					
					int z=0;
					Immediate_friends_3 = (ArrayList) intersection(Immediate_friends,Immediate_friends_2);
					for(int k=0; k<Immediate_friends_3.size(); k++){
						z = z + inf_current[(int) Immediate_friends_3.get(k)];
					}
					SCD_Matrix[i][j] = (int) (z / (x * y));
				}
			}
		visited[i] = i;	
		}
	}
	
	   public <T> List<T> intersection(List<T> list1, List<T> list2) {
	        List<T> list = new ArrayList<T>();

	        for (T t : list1) {
	            if(list2.contains(t)) {
	                list.add(t);
	            }
	        }

	        return list;
	    }
	boolean contains(int[] arr, int targetValue) {
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
