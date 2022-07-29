package jalalchoker.datastructures;

// 29/6/2021
// as per https://www.hackerearth.com/practice/data-structures/disjoint-data-strutures/basics-of-disjoint-data-structures/tutorial/

public class DisjointSetDraft {	
	
	private	final int[] ds;
	private final int[] sz;
	
	public DisjointSetDraft(int size)
	{
		this.size = size;
		this.ds = new int[size];
		this.sz = new int[size];
		this.initialize();
	}
	
	private final int size;
	
	private void initialize()
	{
		for (int i = 0; i < this.size; i++)
		{
			ds[i] = i; sz[i] = 1;
		}
	}	
	
	// returns true if both nodes have the same root
	public boolean find(int a, int b)
	{
		//return ds[a] == ds[b];
		return root(a) == root(b);
	}
	
	// returns root of a given node
	private int root(int n)
	{
		/*O(N) if tree not balanced otherwise O(logn) using weighted union*/
		// while (n != ds[n]) n = ds[n];		
		
		// approach 2: path compression, contributed by a 3rd party author		
		var prnt = ds[n];
		while (n != prnt)
		{		
			/* compress path along the way, updates root only when called on an element whose depth >= 2
			  apparently not bug free as  when a node parent's changes to the grandparent,
			  the parent subset size should be decremented by the size of the node's subset
			  a message was sent to the author of this code block */
			
			ds[n] =  ds[prnt]; // compress path
			n = ds[n];
			prnt = ds[n];
		}
		
		// approach 2: presumably now it's bug free, contributed by me
		while (n != ds[n])
		{		
			// compress path along the way, updates root only when called on an element whose depth >= 2			
			var grandparent = ds[ds[n]];			
			if (ds[n] !=  grandparent) { // is parent <> grandparent
				
				var parent = ds[n];
				sz[parent] -= sz[n]; // the parent subset size is decremented by the size of its child subset			
				ds[n] =  grandparent;				
			}
			n = ds[n];
		}		
		
		return n;
	}	

	public void union(int a, int b)
	{	
		// approach 1
		//change all entries from arr[ A ] to arr[ B ]. o(n)
		//		var tmp = ds[a];		
		//		for (int i = 0; i < this.size; i++)
		//		{
		//			if (ds[i] == tmp) {
		//				ds[i] = ds[b];
		//			}
		//		}
		
		// approach 2
		/*modified union function where we connect the elements by changing the root of one of the elements, o(n)*/
		// var rootA = root(a); var rootB = root(b);		
		// ds[rootA] = rootB;		
		
		// approach 3
		// modified union function i.e weighted union results in a balanced tree which reduces the runtime of union to logn instead of n
		var rootA = root(a); var rootB = root(b);		
		var sizeRootA = sz[rootA]; var sizeRootB = sz[rootB];		
		if (sizeRootA < sizeRootB) {
			ds[rootA] = rootB;
			sz[rootB] += sz[rootA];
		} else {
			ds[rootB] = rootA;
			sz[rootA] += sz[rootB];
		}		
	}
}
