/* ShortestPath.java
   CSC 226 - Fall 2014
   Assignment 3 - Template for Dijkstra's Algorithm
   
   This template includes some testing code to help verify the implementation.
   To interactively provide test inputs, run the program with
	java ShortestPath
	
   To conveniently test the algorithm with a large input, create a text file
   containing one or more test graphs (in the format described below) and run
   the program with
	java ShortestPath file.txt
   where file.txt is replaced by the name of the text file.
   
   The input consists of a series of graphs in the following format:
   
    <number of vertices>
	<adjacency matrix row 1>
	...
	<adjacency matrix row n>
	
   Entry A[i][j] of the adjacency matrix gives the weight of the edge from 
   vertex i to vertex j (if A[i][j] is 0, then the edge does not exist).
   Note that since the graph is undirected, it is assumed that A[i][j]
   is always equal to A[j][i].
	
   An input file can contain an unlimited number of graphs; each will be 
   processed separately.


   B. Bird - 08/02/2014
*/

import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;
import java.io.File;

//Do not change the name of the ShortestPath class
public class ShortestPath{

	/* ShortestPath(G)
		Given an adjacency matrix for graph G, return the total weight
		of a minimum weight path from vertex 0 to vertex 1.
		
		If G[i][j] == 0, there is no edge between vertex i and vertex j
		If G[i][j] > 0, there is an edge between vertices i and j, and the
		value of G[i][j] gives the weight of the edge.
		No entries of G will be negative.
	*/
	static int ShortestPath(int[][] G){
		Heap h = new Heap();
		int numVerts = G.length;
		int totalWeight = 0;
		/* ... Your code here ... */
		Vert[] ref = new Vert[numVerts];
		for(int i = 0;i<numVerts;i++){
			ref[i]= new Vert(i);
		}
		
		int[] distance = new int[numVerts];
		
		for(int i = 1; i<numVerts;i++){
			distance[i] = 99999999;

		}
		distance[0] = 0;
		h.add(ref[0].val);

		
		while(h.getSize() != 0){
			//do heap stuff 
		 	int minVert = h.removeMin();
		 	//push neighbourts of minVert
		 	ref[minVert].inTree = true;
		 	int count2 = 0; //init 2nd counter
			
			for(int row = 0; row <numVerts;row++){
				count2++;		//each time we travel down a row, begin column by 1
				for(int column=count2; column< numVerts;column++){
					int edgeWeight =  G[row][column];
					
					if(edgeWeight > 0 && row == minVert && !ref[column].inTree){
						//neighbours are on this row.
						 //construct heap
						System.out.println(edgeWeight);
						h.add(column);
						
						
						if(distance[minVert]+ edgeWeight < distance[column]){
							distance[column] =  distance[minVert] + edgeWeight;
							
						}


					}
					
				}
				




			}



	}


	for(int weight: distance) {
		totalWeight+= weight == 99999999 ?   0 : weight ;
	}



		return totalWeight;
		
	}

public static class Edge{
		public Vert x;
		public Vert y;
		public int weight;
		//public boolean visited = false;
		public Edge(int a,int b,int weight, Vert[] ref){
			this.weight = weight;
			this.x = ref[a];
			this.y = ref[b];

			
		}

	}

	public static class Vert{
		public boolean inTree;
		public int val;
		public int distance;
		//public int rank= 0;
		public Vert(int val,int[] distance){
			this.val = val;
			this.inTree = false;
			this.distance = distance;
		}
		

	}

	public static class Heap{

	
	/* Constructor */
		
		HeapNode root;
		int size;
		public  Heap(){
			/* Your code here */
			
			root = null;
			size = 0;

				}

		
		/* removeMinimum()
		   Remove and return the minimum element of the heap, restoring the heap
		   properties as necessary. This method should have O(log n) running 
		   time.
		*/
		public int removeMin(){
			size = getSize();
			root = getRoot();
			HeapNode point = root;
			String binaryDirections = (Integer.toBinaryString(size));
			binaryDirections = binaryDirections.substring(1);
			int minElement = root.element;
			if (getRoot() == null)
				return -1; //end of heap.
			
			for(int i = 0; i<binaryDirections.length();i++){
					if(binaryDirections.charAt(i)==('1')){ //go right
							//System.out.println("right");
							point = point.rightChild;
						}else{ //go left
							//System.out.println("left");
							point = point.leftChild;
							
						}
						
			}
			
			root.element = point.element; //move last element to root
			
			if(size == 1){
				root = null;
				size--;
				return minElement;

			}

			//System.out.println("the root value is " +point.element);
			if(point.parent.rightChild!= null && point.parent.rightChild.element==point.element) 
				point.parent.rightChild = null;//unlink last element
			else if (point.parent.leftChild!= null && point.parent.leftChild.element == point.element)
				point.parent.leftChild = null;
			
		point = null;
		size--;
			
			bubbleDown();
			//System.out.println(minElement);
			return minElement;
		}

		public void bubbleDown(){
			HeapNode point = getRoot();
			int lesserElementChild = 0;
			
			


			while(lesserElementChild>-1){
				lesserElementChild = getLesserComparison(point);
				
				if(lesserElementChild == -1) break;
				

				if(lesserElementChild < point.element ){ //then we must swap elements
					
					//System.out.println("time to swap "+  point.element +" and "+lesserElementChild);
					
					int temp = point.element;
					point.element = lesserElementChild;
					lesserElementChild = temp; //put lesser element in node
					
					if(point.element == point.leftChild.element){
						point.leftChild.element = lesserElementChild;
						point = point.leftChild;
						}
					else {
						point.rightChild.element = lesserElementChild; //swap complete
						point = point.rightChild;
					}

				}else{
					return;

				

				}


			}
			
			
		}

		public int getLesserComparison(HeapNode parent){

			if(parent.leftChild ==null) return -1;
			
			else if(parent.rightChild == null || parent.leftChild.element < parent.rightChild.element) return parent.leftChild.element;

			else return parent.rightChild.element;


		}
		
		/* add(e)
		   Add the provided element to the heap, restoring heap properties as
		   necessary. This method should have O(log n) running time.
		*/
		public void add(int element){
			/* Your code here */
			HeapNode point = getRoot();
			if(size == 0){
				HeapNode h = new HeapNode(element);
				root = h;
				size++;
				//System.out.println("root");
			}

			else{
				String binaryDirections = (Integer.toBinaryString(size+1));
				binaryDirections = binaryDirections.substring(1);
					for(int i = 0; i<binaryDirections.length();i++){
					
						if(binaryDirections.charAt(i)==('1')){
							
							//System.out.println("right");
							if(point.rightChild==null){
								point.rightChild = new HeapNode(element);
								point.rightChild.parent = point;
								bubbleUp(point.rightChild);
							}else
								point = point.rightChild;
							
						
						}else{
							
							//System.out.println("left");
							if(point.leftChild==null){
								point.leftChild = new HeapNode(element);
								point.leftChild.parent = point;
								
								bubbleUp(point.leftChild);
							}else
								point = point.leftChild;
							
						}
						
					}

				size++;		

			}
				
			
		}

		public void bubbleUp(HeapNode last){
			
			
			while(last!=getRoot()){
				//System.out.println("the last element: " + last.element+ "and the parent: "+last.parent.element);
				
				if(last.parent.element > last.element ){
					//System.out.print("time fo a swap mawfuka");
					int temp = last.element;
					last.element = last.parent.element;
					last.parent.element = temp;
					last = last.parent;
				}
				else{
					//System.out.print("no way hoessay");
					
					break;

				}
			}
			
		}
		
		
		/* getSize()
		   Returns the total number of nodes in the tree.
		*/
		public int getSize(){
			/* Your code here */
			return size;
		}
		
		/* getRoot()
		   Return a pointer to the root of the heap (or null if the tree is 
		   empty).
		*/
		public HeapNode getRoot(){
			/* Your code here */
			return root;
		}
	
	}	//end of heap class

	public static class HeapNode{
		//Integer value contained in this node.
		int element;
		//Pointer to the parent of this node (or null if this node is the root
		//of the tree).
		public HeapNode parent;
		//Pointers so the left and right children of this node (or null if the
		//child is missing).
		public HeapNode leftChild;
		public HeapNode rightChild;
		
		public HeapNode(int element){
			this.element = element;
		}
	}
	/* main()
	   Contains code to test the ShortestPath function. You may modify the
	   testing code if needed, but nothing in this function will be considered
	   during marking, and the testing process used for marking will not
	   execute any of the code below.
	*/
	public static void main(String[] args){
		Scanner s;
		if (args.length > 0){
			try{
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e){
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n",args[0]);
		}else{
			s = new Scanner(System.in);
			System.out.printf("Reading input values from stdin.\n");
		}
		
		int graphNum = 0;
		double totalTimeSeconds = 0;
		
		//Read graphs until EOF is encountered (or an error occurs)
		while(true){
			graphNum++;
			if(graphNum != 1 && !s.hasNextInt())
				break;
			System.out.printf("Reading graph %d\n",graphNum);
			int n = s.nextInt();
			int[][] G = new int[n][n];
			int valuesRead = 0;
			for (int i = 0; i < n && s.hasNextInt(); i++){
				for (int j = 0; j < n && s.hasNextInt(); j++){
					G[i][j] = s.nextInt();
					valuesRead++;
				}
			}
			if (valuesRead < n*n){
				System.out.printf("Adjacency matrix for graph %d contains too few values.\n",graphNum);
				break;
			}
			long startTime = System.currentTimeMillis();
			
			int totalWeight = ShortestPath(G);
			long endTime = System.currentTimeMillis();
			totalTimeSeconds += (endTime-startTime)/1000.0;
			
			System.out.printf("Graph %d: Minimum weight of a 0-1 path is %d\n",graphNum,totalWeight);
		}
		graphNum--;
		System.out.printf("Processed %d graph%s.\nAverage Time (seconds): %.2f\n",graphNum,(graphNum != 1)?"s":"",(graphNum>0)?totalTimeSeconds/graphNum:0);
	}
}