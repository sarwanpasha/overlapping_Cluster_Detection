/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package detecting_overlappint_clusters_in_attributed_graph;

import Jama.Matrix;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
/**
 *
 * @author Pasha
 */
public class Q_Modularity {
   public static void main(String []args){
    	Matrix Adjacent=null;
       Matrix moc=null;
    	try{
			FileReader in=new FileReader("C:\\Users\\Pasha\\Desktop\\ModularityQ-master\\adjacent.txt");
			FileReader in1=new FileReader("C:\\Users\\Pasha\\Desktop\\ModularityQ-master\\moc.txt");
			BufferedReader bReader=new BufferedReader(in);
			BufferedReader bReader1=new BufferedReader(in1);
			Adjacent=Matrix.read(bReader);
			moc=Matrix.read(bReader1);
			}catch(Exception ex){
				ex.printStackTrace();
			}
    	System.out.print("Community Clustering Effect："+EQ(Adjacent,moc));
    	
    }
	public static double EQ(Matrix Adjacent,Matrix MOC){
		int nodeNum=0;
		int vclusterNum=0;
		int wclusterNum=0;
		int vdegree=0;
		int wdegree=0;
		double sum=0;
		int edgeNum=0;
		ArrayList al=new ArrayList();
		for(int x=0;x<Adjacent.getRowDimension();x++){
			for(int y=0;y<Adjacent.getColumnDimension();y++){
				edgeNum+=Adjacent.get(x,y);
			}
		}
		edgeNum/=2;
		for(int i=0;i<MOC.getColumnDimension();i++){
			//Calculate community internal nodes
			for(int j=0;j<MOC.getRowDimension();j++){
				if(MOC.get(j, i)==1){
					nodeNum+=1;
					al.add(j);
				}	
			}
			for(int k=0;k<nodeNum;k++){
				for(int l=0;l<nodeNum;l++){
					//Calculate the number of communities to which the nodes v and w are 
                                        //affiliated
					for(int m=0;m<MOC.getColumnDimension();m++){
						vclusterNum+=(int)MOC.get((int) al.get(k), m);
						wclusterNum+=(int)MOC.get((int)al.get(l), m);
					}
					//Calculate the degree of node v, w
					for(int n=0;n<Adjacent.getColumnDimension();n++){
						vdegree+=Adjacent.get((int)al.get(k),n);
						wdegree+=Adjacent.get((int)al.get(l),n);
					}
					sum+=(1/((double)vclusterNum*wclusterNum))*(Adjacent.get((int) al.get(k), (int)al.get(l))-vdegree*wdegree/((double)2*edgeNum));
					vclusterNum=0;
                                        wclusterNum=0;
                                        vdegree=0;
                                        wdegree=0;
//					System.out.println("Current value："+sum);
				}
			}
			nodeNum=0;
		}
		return sum/(2*edgeNum);
	}
}
 