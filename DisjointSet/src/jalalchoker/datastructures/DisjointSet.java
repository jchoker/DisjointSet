/**
 * Disjoint Set/ UnionFind data structure implementation. This code was inspired by the union find
 * implementation found in 'Algorithms Fourth Edition' by Robert Sedgewick and Kevin Wayne and also by www.hackerearth.com/practice/data-structures/disjoint-data-strutures
 * @date 01.07.2021
 * @author Jalal Choker, jalal.choker@gmail.com
 */
package jalalchoker.datastructures;

public class DisjointSet {	
	
	private	final int[] parents; // points to the parent of each element, if parents[i] = i then i is a root node
	private final int[] sz; // used to track the size of each of the components
	private final int size; // the total count of element this set represents
	private int components; // tracks the number of components in this disjoint set

	public DisjointSet(int size)
	{
	    if (size <= 0) throw new IllegalArgumentException("Size must be strictly positive");
	    
		this.size = size;
		this.components = size;
		this.parents = new int[size];
		this.sz = new int[size];
		this.initialize();
	}	
	
	private void initialize()
	{
		for (int i = 0; i < this.size; i++)
		{
			parents[i] = i; // link to itself (self root)
			sz[i] = 1; // each component is originally of size 1
		}
	}	
	
	// returns root of a given node
	/*O(N) if tree not balanced, O(logn) using weighted union without path compression, O(log * n) using path compression technique*/
	private int root(int n) // with path compression
	{		
		while (n != parents[n])
		{		
			// compress path along the way, updates root only when called on an element whose depth >= 2
			var parent = parents[n];
			var grandparent = parents[parent];			
			if (parent !=  grandparent) { // is parent <> grandparent
				
				parents[n] =  grandparent; // compress path
				sz[parent] -= sz[n]; // the parent subset size is decremented by the size of its child subset size since child now have a new parent			
			}
			n = parents[n];
		}		
		
		return n;
	}
	
	// returns true if both nodes have the same root (connected)
	public boolean find(int a, int b)
	{
		return root(a) == root(b);
	}
	
	// weighted union function: results in a balanced tree which reduces the worst case runtime of  root(int) from n to logn, and with path compression further to o(log * n)
	public void union(int a, int b)
	{
		var rootA = root(a); var rootB = root(b);
		if(rootA == rootB) return; // elements are already in the same group
		
		var sizeRootA = sz[rootA]; var sizeRootB = sz[rootB];		
		if (sizeRootA < sizeRootB) {
			parents[rootA] = rootB;
			sz[rootB] += sz[rootA];
		} else {
			parents[rootB] = rootA;
			sz[rootA] += sz[rootB];
		}
		this.components--; // Since the roots found are different we know that the number of components/sets has decreased by one
	}
	
	// returns the size of the components/set 'i' belongs to
	public int componentSize(int i) { return sz[root(i)]; }
	
	// returns the number of elements in this UnionFind/Disjoint set
	public int size() { return size; }
	
	// returns the number of remaining components/sets
	public int components() { return components; }
}
