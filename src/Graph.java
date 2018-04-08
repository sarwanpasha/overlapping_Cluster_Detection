/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//package thesis1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 *  The <tt>Graph</tt> class represents an undirected graph of vertices
 *  named 0 through <em>V</em> - 1.
 *  It supports the following two primary operations: add an edge to the graph,
 *  iterate over all of the vertices adjacent to a vertex. It also provides
 *  methods for returning the number of vertices <em>V</em> and the number
 *  of edges <em>E</em>. Parallel edges and self-loops are permitted.
 *  <p>
 *  This implementation uses an adjacency-lists representation, which 
 *  is a vertex-indexed array of {@link Bag} objects.
 *  All operations take constant time (in the worst case) except
 *  iterating over the vertices adjacent to a given vertex, which takes
 *  time proportional to the number of such vertices.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/41undirected">Section 4.1</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class Graph {
    private int V;
    private int E;
    private Bag<Integer>[] adj;
    double [] BC;
    double [][] dependencies;
    
    ArrayList<String> EdgeList= new ArrayList<String>();
    //Caltech Dataest
    static int Node_Count = 770;
    static int edgeList_Array_Count = 33313;
    //Caltech Dataest
    
//     //Rice Dataest
//    static int Node_Count = 4088;
//    static int edgeList_Array_Count = 369657;
//    //Rice Dataest
    
//    //American75 Dataest
//    static int Node_Count = 6387;
//    static int edgeList_Array_Count = 435325;
//    //American75 Dataest
    
    
//    static int Node_Count = 6;
//    static int edgeList_Array_Count = 6;
    static int [][] Attribute = new int[Node_Count][7];
    public static int Counter =1;

    static int[][] edgeList_Array=new int[edgeList_Array_Count][2];
    /**
     * Initializes an empty graph with <tt>V</tt> vertices and 0 edges.
     * param V the number of vertices
     * @throws java.lang.IllegalArgumentException if <tt>V</tt> < 0
     */
    public Graph(int V) {
        if (V < 0) throw new IllegalArgumentException("Number of vertices must be nonnegative");
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Integer>();
        }
        this.BC = new double [this.V];
        this.dependencies = new double[this.V][this.V];
        for(int v=0;v<this.V;v++){
           this.BC[v]=0.0;
        }
        for(int v=0;v<this.V;v++){
               for(int w=0;w<this.V; w++){
                   this.dependencies[v][w]=0.0;
               }
        }
    }

    /**
     * Initializes a new graph that is a deep copy of <tt>G</tt>.
     * @param G the graph to copy
     */
    public Graph(Graph G) {
        this(G.V());
        this.E = G.E();
        this.BC = new double [this.V];
        this.dependencies = new double[this.V][this.V];   
        for (int v = 0; v < G.V(); v++) {
            // reverse so that adjacency list is in same order as original
            Stack<Integer> reverse = new Stack<Integer>();
            for (int w : G.adj[v]) {
                reverse.push(w);
            }
            for (int w : reverse) {
                adj[v].add(w);
            }
        }
        for(int v=0;v<this.V;v++){
           this.BC[v]=0.0;
        }
        for(int v=0;v<this.V;v++){
               for(int w=0;w<this.V; w++){
                   this.dependencies[v][w]=0.0;
               }
        }
    }
    public Graph(){
        
    }
    public Graph(String filename){
        Set<Integer> vertexSet = new HashSet<Integer>();
        ArrayList<String> edgeList= new ArrayList<String>();
        this.V = 0;
        this.E = 0;
        //Graph G = new Graph(1);
        try{
            FileReader filein = new FileReader(filename);
            BufferedReader graphFile = new BufferedReader(filein);
            String inLine;
            int edgeList_Counter = 0;
            while( (inLine = graphFile.readLine()) != null)
            {
                String[] parts = inLine.split("\\s");
                try{
                    if(parts.length < 2){
                        System.err.println("Line Format is wrong: Skipping " + inLine);
                        continue;
                        }
                    for (String part : parts){
                        vertexSet.add(Integer.parseInt(parts[0]));
                        vertexSet.add(Integer.parseInt(parts[1]));                    
                    }
                   edgeList.add(parts[0]+" "+parts[1]); 
                       edgeList_Array[edgeList_Counter][0] = Integer.parseInt(parts[0]);
                       edgeList_Array[edgeList_Counter][1] = Integer.parseInt(parts[1]);
//                       System.err.println("First Friend = " + edgeList_Array[edgeList_Counter][0] + "Second Friend = " + edgeList_Array[edgeList_Counter][1]); 
                }
                catch( NumberFormatException e ){
                    System.err.println("Line Format is wrong: Skipping " + inLine); 
                }
                edgeList_Counter++;
            }
            //System.out.println("Number of vertices is " + vertexSet.size());
            this.V = vertexSet.size();
            this.E=edgeList.size();
            this.BC = new double [this.V];
            this.dependencies = new double[this.V][this.V];
            for(int v=0;v<this.V;v++){
                this.BC[v]=0.0;
            }
            for(int v=0;v<this.V;v++){
                for(int w=0;w<this.V; w++){
                    this.dependencies[v][w]=0.0;
                }
            }
            this.adj = (Bag<Integer>[]) new Bag[this.V];
            for (int v = 0; v < this.V; v++) {
                adj[v] = new Bag<Integer>();
            }
            for(String ed : edgeList){
                String[] parts = ed.split("\\s");
                try{
                    this.addEdge(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
                }
                catch( NumberFormatException e ){
                    System.err.println("Line Format is wrong: Skipping " + inLine); 
                }
            }
            //G = Gtemp;
            //System.out.println("Num Vertices and Edges in G = " + this.V() + "  " + this.E() );
            set_Edge_List(edgeList);
        }
        catch( IOException e ){
            System.err.println( e );
        }
    }
    
    public int[][] get_edgeList_Array()
    {
        return edgeList_Array;
    }
    
    public void Read_Attributed_Graph(String filename)
    {
        
        try {
            FileReader filein = new FileReader(filename);
            BufferedReader AttributeFile = new BufferedReader(filein);
            String inLine;
             
            while ((inLine = AttributeFile.readLine()) != null ) 
            {
                
                String[] parts = inLine.split("\\s");
//                System.err.println("Parts = " + parts[6]);
                try 
                {
                    if (parts.length < 7) {
                    }
                    else 
                    {
                            for (int j = 0; j < parts.length; j++) 
                            {
                                Attribute[Counter-1][j] = Integer.parseInt(parts[j]);
//                                System.err.println("Added Value = " + parts[j]);
                            }
                    }
                } 
                catch (Exception e) 
                {
                    System.out.println("Error4 " + e + " in line " + inLine);
                }
                Counter++;
                
            }
                        
        } 
        catch (Exception e) 
        {
            System.err.println("Error = " + e + e.getStackTrace()[0].getLineNumber());
        }
    }
    
    public int[][] get_Attributed_Graph()
    {
        return Attribute;
    }
    /**
     * Returns the number of vertices in the graph.
     * @return the number of vertices in the graph
     */
    public int V() {
        return V;
    }

    /**
     * Returns the number of edges in the graph.
     * @return the number of edges in the graph
     */
    public int E() {
        return E;
    }

    public void set_Edge_List(ArrayList<String> edgeList)
    {
        EdgeList = edgeList;
    }
        
    public ArrayList<String> get_Edge_List()
    {
        return EdgeList;
    }
    /**
     * Adds the undirected edge v-w to the graph.
     * @param v one vertex in the edge
     * @param w the other vertex in the edge
     * @throws java.lang.IndexOutOfBoundsException unless both 0 <= v < V and 0 <= w < V
     */
    public void addEdge(int v, int w) {
        if (v < 0 || v >= V) 
        {
            System.out.println("Value of v = " + v);
            System.out.println("Value of V = " + V);
            throw new IndexOutOfBoundsException();
        }
        if (w < 0 || w >= V) 
        {
            System.out.println("Value of w = " + w);
            System.out.println("Value of V = " + V);
            throw new IndexOutOfBoundsException();
        }
        E++;
        adj[v].add(w);
        adj[w].add(v);
    }

    /**
     * Returns the vertices adjacent to vertex <tt>v</tt>.
     * @return the vertices adjacent to vertex <tt>v</tt> as an Iterable
     * @param v the vertex
     * @throws java.lang.IndexOutOfBoundsException unless 0 <= v < V
     */
    public Iterable<Integer> adj(int v) {
        if (v < 0 || v >= V) throw new IndexOutOfBoundsException();
        return adj[v];
    }

    /**
     * Returns a string representation of the graph.
     * This method takes time proportional to <em>E</em> + <em>V</em>.
     * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
     *    followed by the <em>V</em> adjacency lists
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        String NEWLINE = System.getProperty("line.separator");
        s.append(V + " vertices, " + E + " edges " + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (int w : adj[v]) {
                s.append(w + " ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

   public void computeDependcies(Bag<Integer> sourcevertices){
       for(int s : sourcevertices){
           Stack<Integer> stack = new Stack<Integer>();
           Bag<Integer>[] predecessors = new Bag[this.V];
           for (int v = 0; v < V; v++) {
               predecessors[v] = new Bag<Integer>();
           }
           int sigma[] = new int[this.V]; //Number of paths going from this s to other vertices
           int dist[]= new int[this.V]; //Distance from s to all other vertices
           Queue<Integer> queue = new Queue<Integer>();     
           for(int v=0;v<this.V;v++){
               sigma[v]=0;
               dist[v]=-1;
           }
           sigma[s]=1;
           dist[s]=0;
           queue.enqueue(s);
           while(queue.isEmpty()!=true){
               int v=queue.dequeue();
               stack.push(v);
               for(int w : adj[v]){
                   if(dist[w] == -1){
                       queue.enqueue(w);
                       dist[w]=dist[v]+1;                       
                   }
                   if(dist[w]==dist[v]+1){
                       sigma[w] +=sigma[v];
                       predecessors[w].add(v);                       
                   }
               }
           } //BFS from one source finished, predecessors are correct, and dist is correct
           while(stack.isEmpty()!=true){
               int w = stack.pop();
               for(int v:predecessors[w]){
                   if(v!=s){
                       this.dependencies[s][v]+= ((double)sigma[v]/(double)sigma[w])*(1.0+this.dependencies[s][w]);
                   }
               }
                if(w!=s){
                       this.BC[w]+=this.dependencies[s][w];
                }
            }          
       }//dependecies of s on all are computed    
   }
   
   public void printBC(){
       double normalizer = (this.V()-1) * (this.V()-2) / 2.0;
       System.out.println("Printing BC as copmputed with normalizing and dividing by 2\n");
       for(int v=0;v<this.V;v++){
           System.out.println("BC["+v+"] : "+(this.BC[v])/normalizer/2.0);
       }
              
       //Sanity check, sum of column i of the dependcies matrix should add up to BC[i]
       double sum=0.0;
       for(int v=0;v<this.V;v++){
           sum=0.0;
           for(int w=0;w<this.V;w++){
               sum += this.dependencies[w][v];
            }
           System.out.print("\nSum of dependencies : " + sum/normalizer/2.0 + "      BC["+v+"]: "+this.BC[v]/normalizer/2.0);
       }
       
       /*//Printting dependencies matrix
       System.out.println("\n Dependencies Matrix");
       for(int v=0;v<this.V;v++){
           for(int w=0;w<this.V;w++){
               System.out.print(String.format( " %.3f , ", this.dependencies[v][w]));
            }
           System.out.println("   :");
       }
               */
   }

}



