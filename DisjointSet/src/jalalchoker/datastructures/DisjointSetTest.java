package jalalchoker.datastructures;

import org.junit.jupiter.api.Test;

// 30/6/2021
class DisjointSetTest {

	@Test
	 void test() {
		
		// subset size:	  1  1  1  1  1  1 1
		// roots:	      0  1  2  3  4  5 6
		// elements:  	  0  1  2  3  4  5 6
		var ds = new DisjointSet(7);
		
		// size:	  2  1  1  1  1  1  1
		// roots:	  0  0  2  3  4  5  6
		// elements:  0  1  2  3  4  5  6
		//		0
		//		 \
		//		  1
		ds.union(0, 1); // both subsets of same size initially, hence as per source code '1' gets connected to '0' and sz[0] becomes 2		
		
		// size:	  3  1  1  1  1  1  1
		// roots:	  0  0  0  3  4  5  6
		// elements:  0  1  2  3  4  5  6
		//		0
		//	   / \
		//	  1   2
		ds.union(1, 2); // subset rooted at 2 has smaller size, hence '2' gets connected to the root of '1' i.e subset rooted at '0' and sz[0] becomes 3	
		
		// size:	  4  1  1  1  1  1  1
		// roots:	  0  0  0  0  4  5  6
		// elements:  0  1  2  3  4  5  6
		//		0
		//	  / | \
		//	 1  2  3
		// notice how the tree is kept balanced, this reduces the complexity of the critical root(int) function from n to logn
		ds.union(3, 2); // subset rooted at 3 has smaller size, hence '3' gets connected to the root of '2' i.e subset rooted at '0' and sz[0] becomes 4	
		
		//		4
		//		 \
		//		  5
		ds.union(4, 5);		
		
		//		0 
		//	 / / \ \
		//	1 2   3 4
		//		     \
		//		      5
		ds.union(3, 5);		
		
		//		  0 
		//	 / / / \ \ \
		//	1 2 3   4 5 6
		// root(int) is called on 5 which has a depth >= 2 hence its path is compressed so that its now directly connected to the ultimate root '0' thereby keeping runtime of root() log * n,
		// but sz[] is stale, since sz[4] <> 2 anymore as 5 is now directly connected to '0'
		ds.union(5, 6);		
	}
}
