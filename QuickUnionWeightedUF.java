public class QuickUnionWeightedUF
{
    private int[] id;
    private int[] sz;
    private int[] large;
    // constr.
    public QuickUnionWeightedUF(int N)
    {
        id = new int[N];
        sz = new int[N];
        large = new int[N];
        for (int i = 0; i < N; i++)
        {
            id[i] = i;
            sz[i] = 1;
            large[i] = i;
        }
    }
    private int root(int i)
    {
        while (i != id[i])
        {
            id[i] = id[id[i]]; // path compression, flattens the tree
            i = id[i];
        }
        return i;
    }
    public boolean connected(int p, int q)
    {
        return root(p) == root(q);
    }
    public void union(int p, int q)
    {
        int i = root(p);
        int j = root(q);
        if (i == j) return;
        if (sz[i] < sz[j])
        {
            id[i] = j;
            sz[j] += sz[i];
            if (large[i] > large[j])
            {
                large[j] = large[i]; // only check j since its the new root
            }
        }
        else {
            id[j] = i;
            sz[i] += sz[j];
            if (large[i] < large[j])
            {
                large[i] = large[j];
            }
        }
    }

    public int find(int q)
    {
        return large[root(q)];
    }

    public static void main(String[] args) {
        QuickUnionWeightedUF arr = new QuickUnionWeightedUF(10);
        arr.union(0,1);
        arr.union(3,4);
        arr.union(2,5);
        System.out.println(arr.find(3)); // prints 4
        arr.union(2,3);
        System.out.println(arr.find(2)); // prints 5
        arr.union(5, 9);
        System.out.println(arr.find(3)); // prints 9
        System.out.println(arr.find(4)); // prints 9
        System.out.println(arr.find(2)); // prints 9
    }
}
