import java.util.ArrayList;


/**
 * This class is the complete and tested implementation of an AVL-tree.
 */
public class AvlTree {

  public class AvlNode {

    public AvlNode left;                 //left child
    public AvlNode right;                //right child
    public AvlNode parent;               //parent node
    public int key;                      //ID value
    public int count;                    //count number 
    public int balance;                  //balance factor

    public AvlNode(int k, int c) {
      left = right = parent = null;
      balance = 0;
      key = k;
      count = c;
    }
    
    public String toString() {
      return "" + key;
    }
  }
 
  protected AvlNode root; // the root node
 
/***************************** Core Functions ************************************/
  
  /**
  * Initialize a new AVL tree, all keys and counts are stored in arrayKey and arrayCount respectively.
  * 
  * @param arrayKey Array stores all keys
  * @param arrayCount Array stores all count numbers
  * @param n Length of array above
  */
  public void initial(int [] arrayKey, int [] arrayCount, int n) {
    this.root = initialTree(arrayKey, arrayCount, 0, n-1);                  // start recursive procedure from the root for initializing tree
  }

  /**
  * Actuall initialization opearation function.
  * 
  * @param arrayKey Array stores all keys.
  * @param arrayCount Array stores all count numbers.
  * @param start The first index in array.
  * @param end The last index in array.
  */  
  private AvlNode initialTree(int [] arrayKey, int [] arrayCount, int start, int end) {
    if (start > end) {                                                      //base case, to avoid overflow
      return null;
    }
    int mid = start + (end - start)/2;
    AvlNode node = new AvlNode(arrayKey[mid], arrayCount[mid]);             //get the middle element and make it root
    node.left = initialTree(arrayKey, arrayCount, start, mid-1);            //recursively construct the left subtree and make it left child of root
    if (node.left!=null) {                                                  //until leaf, connect the root of left subtree to its parent node
      node.left.parent = node;
    }
    node.right = initialTree(arrayKey,arrayCount, mid+1, end);              //recursively construct the right subtree and make it right child of root
    if (node.right!=null) {                                                 //until leaf, connect the root of right subtree to its parent node
      node.right.parent = node;
    }
    return node;
  }

  /**
  * Add a new element with key "k" into the tree.
  * 
  * @param k The key of the new node.         
  */
  public void insert(int k, int c) {
    AvlNode n = new AvlNode(k, c);        // create new node
    insertAVL(this.root,n);               // start recursive procedure for inserting the node
  }
 
  /**
  * Recursive method to insert a node into a tree.
  * 
  * @param p The node currently compared, usually you start with the root.
  * @param q The node to be inserted.
  */
  public void insertAVL(AvlNode p, AvlNode q) {
    // If  node to compare is null, the node is inserted. If the root is null, it is the root of the tree.
    if(p==null) {
      this.root=q;
    } else {
      // If compare node is smaller, continue with the left node
      if(q.key<p.key) {
        if(p.left==null) {       //if there is no left node, then let it be 
          p.left = q;
          q.parent = p;      
          // Node is inserted now, continue checking the balance
          recursiveBalance(p);
        } else {
          insertAVL(p.left,q);   //there is a left node, continue to insert to the left position
        }
      } else if(q.key>p.key) {
        if(p.right==null) {      //if there is no right node, just insert it
          p.right = q;
          q.parent = p;
          // Node is inserted now, continue checking the balance
          recursiveBalance(p);
        } else {
          insertAVL(p.right,q);  //there is a right node, continue follow right path
        }
      } else {
        // do nothing: This node already exists
      }
    }
  }
 
  /**
  * Check the balance for each node recursivly and call required methods for balancing the tree until the root is reached.
  * 
  * @param cur : The node to check the balance for, usually you start with the parent of a leaf.
  */
  public void recursiveBalance(AvlNode cur) {    
    setBalance(cur);                                       // we do not use the balance in this class, but store it anyway
    int balance = cur.balance;
  
    // check the balance, and according to different condition, use different rotation type
    if(balance==-2) {
      if(height(cur.left.left)>=height(cur.left.right)) {
        cur = rotateRight(cur);              //LL rotate
      } else {
        cur = doubleRotateLeftRight(cur);    //LR rotate
      }
    } else if(balance==2) {
      if(height(cur.right.right)>=height(cur.right.left)) {
        cur = rotateLeft(cur);               //RR rotate
      } else {
        cur = doubleRotateRightLeft(cur);    //RL rotate
      }
    }
  
    // we did not reach the root yet, continue go up to check balance condition
    if(cur.parent!=null) {
      recursiveBalance(cur.parent);
    } else {
      this.root = cur;
    }
  }

  /**
  * Removes a node from the tree, if it is existent.
  */
  public void remove(int k) {
    // First we must find the node, after this we can delete it.
    removeAVL(this.root,k);
  }
 
  /**
  * Finds a node and calls a method to remove the node.
  * 
  * @param p The node to start the search.
  * @param q The KEY of node to remove.
  */
  public void removeAVL(AvlNode p,int q) {
    if(p==null) {
      return;                   //the root does not exist in this tree, so nothing to do
    } else {
      if(p.key>q)  {
        removeAVL(p.left,q);    //if this node's key is larger than the key we are searching for, go left subtree to search again
      } else if(p.key<q) {
        removeAVL(p.right,q);   //if this node's key is smaller than the key we are searching for, go right subtree to search again
      } else if(p.key==q) {     
        removeFoundNode(p);     // we found the node in the tree, now lets go on to delete it
      }
    }
  }
 
  /**
  * Removes a node from a AVL Tree, while balancing will be done if necessary.
  * 
  * @param q The node to be removed.
  */
  public void removeFoundNode(AvlNode q) {
    AvlNode r;
    // at least one child of q, q will be removed directly
    if(q.left==null || q.right==null) {
      // the root is deleted
      if(q.parent==null) {
        this.root=null;
        q=null;
        return;
      }
      r = q;
    } else {
      // q has two children, so this position will be replaced by successor
      r = successor(q);
      q.key = r.key;
      q.count = r.count;
    }
  
    AvlNode p;
    if(r.left!=null) {
      p = r.left;
    } else {
      p = r.right;
    }
  
    if(p!=null) {
      p.parent = r.parent;
    }
  
    if(r.parent==null) {
      this.root = p;
    } else {
      if(r==r.parent.left) {
      r.parent.left=p;
    } else {
      r.parent.right = p;
    }
    // balancing must be done until the root is reached.
    recursiveBalance(r.parent);
    }
    r = null;
  }
 
  /**
  * Left rotation using the given node, i.e. RR rotate.
  *
  * @param n The node for the rotation.
  * @return The root of the rotated tree.
  */
  public AvlNode rotateLeft(AvlNode n) {
    AvlNode v = n.right;
    v.parent = n.parent;
    n.right = v.left;
  
    if(n.right!=null) {
      n.right.parent=n;
    } 
  
    v.left = n;
    n.parent = v;
  
    if(v.parent!=null) {
      if(v.parent.right==n) {
      v.parent.right = v;
    } else if(v.parent.left==n) {
      v.parent.left = v;
    }
  }
  
    setBalance(n);
    setBalance(v);
  
    return v;
  }
 
  /**
  * Right rotation using the given node, i.e. LL rotate.
  *
  * @param n The node for the rotation.
  * @return The root of the new rotated tree.
  */
  public AvlNode rotateRight(AvlNode n) {
    AvlNode v = n.left;
    v.parent = n.parent;
    n.left = v.right;
  
    if(n.left!=null) {
      n.left.parent=n;
    }
  
    v.right = n;
    n.parent = v;
  
    if(v.parent!=null) {
      if(v.parent.right==n) {
        v.parent.right = v;
      } else if(v.parent.left==n) {
        v.parent.left = v;
      }
    }
  
    setBalance(n);
    setBalance(v);
  
    return v;
  }

  /**
  * LR rotation
  *
  * @param u The node for the rotation.
  * @return The root after the double rotation.
  */
  public AvlNode doubleRotateLeftRight(AvlNode u) {
    u.left = rotateLeft(u.left);
    return rotateRight(u);
  }
 
  /**
  * RL rotation
  * @param u The node for the rotation.
  * @return The root after the double rotation.
  */
  public AvlNode doubleRotateRightLeft(AvlNode u) {
    u.right = rotateRight(u.right);
    return rotateLeft(u);
  }


  /**
  * Search function to search whether there is a node whose key is i.
  *
  * @param i The key of the node which we are searching for.
  * @return The found node or null if we cannot find it.
  */
  public AvlNode search(int i) {
    AvlNode current = this.root;

    while (current.key!=i) {
      if (i<current.key) {
        current = current.left;            //go left subtree to search
      } else {
        current = current.right;           //go right subtree to search
      }
      if (current==null) {
        break;                             //reach a leaf without found node, so return null
      }
    }
    return current;                        //we found the node
  }
 
/************************** 6 Required Functions *********************************/
 
  /**
  *  FUNCTION: Increase
  *  INPUTS:  i is the key value and j is the count of the node need to increased by 
  *  OUTPUT:  the final count of the node after increase or insertion
  *  Modifies:  tree
  *  RESULT:  Makes a node and inserts it into the tree if not already present and prints its count or if the node is present, change its count and print
  */
  public void increase(int i, int j)
  {
    AvlNode foundNode;
    foundNode = search(i);                                  //first we ensure whether it is already present
    
    if (foundNode==null) {
      insert(i,j);                                          //it's not existed, insert it into the tree
      System.out.println("increase " + i + " " + j);
      System.out.println(j);
    } else {
      foundNode.count = foundNode.count + j;                //it's existed, increase its count number
      System.out.println("increase " + i + " " + j);
      System.out.println(foundNode.count);
    }
  }


  /**
  *  FUNCTION: Reduce
  *  INPUTS:  i is the key value and j is the count of the node need to reduced by
  *  OUTPUT:  the final count of the node after reduce or 0 if its count was less than 1
  *  Modifies:  tree
  *  RESULT:  remove a node if its count is less than 1 or it's not existed and print 0 or reduce this node's count by j and print new count
  */
  public void reduce(int i,int j) {
    AvlNode foundNode;
    foundNode = search(i);                                  //first we ensure whether it is already present
    
    if (foundNode==null) {
      System.out.println("reduce " + i + " " + j);
      System.out.println(0);                                //it's not existed, just print 0
    } else {
      foundNode.count=foundNode.count - j;                  //it's existed, reduce its count number
      
      if (foundNode.count<=0) {                             
        removeFoundNode(foundNode);                         //after reduce, if it is less than 1, we to remove it
        System.out.println("reduce " + i + " " + j);
        System.out.println(0);
      } else {
        System.out.println("reduce " + i + " " + j);        //after reduce, if it is not less than 1, print its count
        System.out.println(foundNode.count);
      }
    }
  }

  /**
  *  FUNCTION: Next
  *  INPUTS:  i 
  *  OUTPUT:  the key and count of the nodes which meet the requirement 
  *  Modifies:  none
  *  RESULT:  print the ID and count of the node with lowest ID that is greater than i. Print "0 0" if there is no next ID.
  */
  public void next(int i) {
    searchNext(this.root, i);
  }

  /**
  * Actuall next function.
  *
  * @param p The root node.
  * @param i 
  */
  private void searchNext(AvlNode p, int i) {
    int k = 0;
    int c = 0;
    while (p!=null){                        //if the root is not null
      if(i<p.key) {                         //if the node's key is larger than i, go left and records its key and count
        k = p.key;
        c = p.count;
        p = p.left;
      } else if (i>p.key) {                 //if the node's key is smaller than i, go right and don't need to records because it doesn't satisfy next meaning
        p=p.right;
      } else {                              //now we found the exact node with key i
          if(p.right == null) {           
            ;                               //if the node has no right subtree, so next key is on the top and the last one we recorded
          } else {                          //if the node has right subtree, keep search until we reach leftmost in the right subtree, which is the smallest one in right subtree
            p=p.right;
            while(p!=null) {                //as long as there is a right child, go left down and record the info
            	k = p.key;
              c = p.count;
              p = p.left;
            }
          }
          break;
      }
    }
    System.out.println("next " + i);
    System.out.println(k + " " + c);
  }

  /**
  *  FUNCTION: Count
  *  INPUTS:  i is the key value of the node we are searching for
  *  OUTPUT:  the count number of the node with key value of i
  *  Modifies:  none
  *  RESULT:  print the found node's count, if not present print 0
  */
  public void count(int i)
  {
   AvlNode foundNode;
   foundNode = search(i);                          //first we need to search this node
   if (foundNode!=null) {                          //if it's existed, print its count 
      System.out.println("count " + i);
      System.out.println(foundNode.count);
    } else {                                       //if it's not existed, just print 0
      System.out.println("count " + i);
      System.out.println(0);
    }
  }

  /**
  *  FUNCTION: Previous
  *  INPUTS:  i
  *  OUTPUT:  the key and count of the nodes which meet the requirement 
  *  Modifies:  tree
  *  RESULT:  Print ID and count of the node with greatest ID that is less than i. Print 0 0 if there is no previous ID.
  */
  public void previous(int i) {
    searchPrevious(this.root, i);
  }

  /**
  * Actuall previous function.
  *
  * @param p The root node.
  * @param i 
  */
  private void searchPrevious(AvlNode p, int i) {
    int k = 0;
    int c = 0;
    while (p!=null) {                       //if the root is not null
      if(i<p.key) {                         //if the node's key is smaller than i, go left and don't need to records because it doesn't satisfy next meaning
        p = p.left;
      } else if (i>p.key) {                 //if the node's key is larger than i, go right and records its key and count
        k = p.key;
        c = p.count;
        p=p.right;
      } else {                              //now we found the exact node with key i
        if(p.left == null) {
          ;                                 //if the node has no left subtree, so next key is on the top and the last one we recorded
        } else {                            //if the node has left subtree, keep search until we reach rightmost in the left subtree, which is the largest one in the left subtree
          p=p.left;
          while(p!=null) {                  //as long as there is a left child, go right down and record the info
        	  k = p.key;
            c = p.count;
            p = p.right;
          }
        }
          break;
      }
    }
    System.out.println("previous " + i);
    System.out.println(k + " " + c);
  }
 
  /**
  *  FUNCTION: Inrange
  *  INPUTS:  [x, y], we assume that x <= y
  *  OUTPUT:  the count of all nodes which meet the requirement 
  *  Modifies:  tree
  *  RESULT:  print all nodes' count: x<=count<=y
  */
  public void inRange(int x, int y) {
    System.out.println("inrange " + x + " " + y);
    searchRange(this.root, x, y);
    System.out.println(" ");
  }

  /**
  * Actuall inRange function using inOrder trasversal.
  *
  * @param p The root node.
  * @param x the lower bond.
  * @param y the upper bond
  */
  private void searchRange(AvlNode p, int x, int y) {
    if (p==null) {
      return;
    }
    if (x<p.key) {                                   //if the node's key is larger than lower bond, recursively search in its left subtree
      searchRange(p.left,x,y);
    } 
    if (x<=p.key && y>=p.key) {                      //if the node's key is exactly located between x and y inclusively, print it
      System.out.print(p.count + " ");
    }
    if (y>p.key) {                                   //if the node's key is smaller than upper bond, recursively search in its right subtree
      searchRange(p.right,x,y);
    } 
  }

/***************************** Helper Functions ************************************/
  
  /**
  * Returns the successor of a given node in the tree (search recursively). It's used in remove methods.
  *
  * @param q The predecessor.
  * @return The successor of node q.
  */
  public AvlNode successor(AvlNode q) {
    if(q.right!=null) {
      AvlNode r = q.right;
      while(r.left!=null) {
        r = r.left;
      }
      return r;
    } else {
      AvlNode p = q.parent;
      while(p!=null && q==p.right) {
      q = p;
      p = q.parent;
      }
      return p;
    }
  }

  /**
  * Calculating the "height" of a node.
  * @param cur
  * @return The height of a node (-1, if node is not existent e.g. null).
  */
  private int height(AvlNode cur) {
    if(cur==null) {
    return -1;
    }
    if(cur.left==null && cur.right==null) {
    return 0;
    } else if(cur.left==null) {                                  //if a node don't have left subtree, its height is 1 more than its right subtree
      return 1+height(cur.right);
    } else if(cur.right==null) {                                 //if a node don't have right subtree, its height is 1 more than its left subtree
      return 1+height(cur.left);
    } else {
      return 1+maximum(height(cur.left),height(cur.right));      //if a node have two children, its height is 1 more than the larger one's height
    }
  }
 
  /**
  * It's a common maximun method to compare two integers and eturn the maximum of two integers.
  * @a one of two number compared
  * @b another of two number compared
  */
  private int maximum(int a, int b) {
    if(a>=b) {
      return a;
    } else {
      return b;
    }
  }

  /** 
  * Only for debugging purposes. Gives all information about a node.  
  * @param n The node to write information about.
  */
  public void debug(AvlNode n) {
    int l = 0;
    int r = 0;
    int p = 0;
    if(n.left!=null) {
      l = n.left.key;
    }
    if(n.right!=null) {
      r = n.right.key;
    }
    if(n.parent!=null) {
      p = n.parent.key;
    }
  
    System.out.println("Left: "+l+" Key: "+n+" Right: "+r+" Parent: "+p+" Balance: "+n.balance);
  
    if(n.left!=null) {
      debug(n.left);
    }
    if(n.right!=null) {
      debug(n.right);
    }
  }
  
  /**
  * It's a simple method to calculate a node's balance factor.
  * @cur the node whose balance factor we plan to calculate.
  */
  private void setBalance(AvlNode cur) {
    cur.balance = height(cur.right)-height(cur.left);
  }

}