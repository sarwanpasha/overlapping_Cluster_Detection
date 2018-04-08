/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 *
 * @author Pasha
 */
public class RemoveDuplicates {

    public RemoveDuplicates() {

    }
    public static ArrayList<String> new_edgeList;

    public static void remove_duplicates(ArrayList<String> edgeList) {
        for (int i = 0; i < edgeList.size(); i++) {

            for (int j = i + 1; j < edgeList.size(); j++) {
                if (edgeList.get(i).equals(edgeList.get(j))) {
                    edgeList.remove(j);
                    j--;
                }
            }

        }
        new_edgeList = edgeList;
    }

    public static ArrayList<String> Get_removed_duplicates() {
        return new_edgeList;
    }

    public static void File_Writter(ArrayList<String> edgelist)
    {
        BufferedWriter writer = null;
        try {
            //create a temporary file
            String Path = "E:\\MS Computer Science\\MS Thesis\\Java Code\\thesis1\\src\\thesis1\\Caltech36_edgelist2.txt";
            File logFile = new File(Path);

            // This will output the full path where the file will be written to...
//            System.out.println(logFile.getCanonicalPath());

            writer = new BufferedWriter(new FileWriter(logFile));
//            writer.write(edgelist);
            for (String str : edgelist) {
                writer.write(str);
                writer.write("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the writer regardless of what happens...
                writer.close();
            } catch (Exception e) {
            }
        }
    }
    public static void main(String[] args) {
        Graph G = new Graph("E:\\MS Computer Science\\MS Thesis\\Java Code\\thesis1\\src\\thesis1\\Caltech36_edgelist.txt");
//            RemoveDuplicates remove = new RemoveDuplicates();
        ArrayList<String> edgeList = G.get_Edge_List();
        remove_duplicates(edgeList);
        System.out.println(new_edgeList);
        File_Writter(new_edgeList);
    }
}
