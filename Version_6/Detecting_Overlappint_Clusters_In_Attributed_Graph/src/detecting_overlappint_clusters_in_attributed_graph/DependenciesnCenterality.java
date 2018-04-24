/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package detecting_overlappint_clusters_in_attributed_graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

/**
 *
 * @author Imdadullah Khan
 */
public class DependenciesnCenterality {
    private static final int INFINITY = Integer.MAX_VALUE;
    public static void main(String[] args) {
        Graph G = new Graph("E:\\MS Computer Science\\MS Thesis\\Java Code\\thesis1\\src\\thesis1\\Caltech36_edgelist.txt");
        int numNodes = G.V();
        System.out.println("Number of vertices in G " + G.V() + "  "+ G.E());
        //G.addEdge(0, 3);
        //Print BC values
        Bag<Integer> sources = new Bag();
        for(int v=0;v<G.V();v++){
            sources.add(v);
        }
        G.computeDependcies(sources);
        G.printBC();
        
        //double normalizer = (G.V()-1) * (G.V()-2) * 2.0;
        //for(int v = 0; v < G.V(); v++) {
            //System.out.println("BC["+v+"]: "+(BC[v]/normalizer) + "      BC["+v+"]: "+(BC[v]/2) + "      BC["+v+"]: "+(BC[v]));
        //}
    }
}
