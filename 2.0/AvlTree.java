import java.util.ArrayList;



/**
 * This class is the complete and tested implementation of an AVL-tree.
 */
public class AvlTree {

  public class AvlNode {

     public AvlNode left;
     public AvlNode right;
     public AvlNode parent;
     public int key;
     public int count;
     public int balance;

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
  * Add a new element with key "k" into the tree.
  * 
  * @param k
  *            The key of the new node.
  */
 public void insert(int k, int c) {
  // create new node
  AvlNode n = new AvlNode(k, c);
  // start recursive procedure for inserting the node
  insertAVL(this.root,n);
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
    if(p.left==null) {
     p.left = q;
     q.parent = p;
     
     // Node is inserted now, continue checking the balance
     recursiveBalance(p);
    } else {
     insertAVL(p.left,q);
    }
    
   } else if(q.key>p.key) {
    if(p.right==null) {
     p.right = q;
     q.parent = p;
     
     // Node is inserted now, continue checking the balance
     recursiveBalance(p);
    } else {
     insertAVL(p.right,q);
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
  
  // we do not use the balance in this class, but the store it anyway
  setBalance(cur);
  int balance = cur.balance;
  
  // check the balance
  if(balance==-2) {
   
   if(height(cur.left.left)>=height(cur.left.right)) {
    cur = rotateRight(cur);
   } else {
    cur = doubleRotateLeftRight(cur);
   }
  } else if(balance==2) {
   if(height(cur.right.right)>=height(cur.right.left)) {
    cur = rotateLeft(cur);
   } else {
    cur = doubleRotateRightLeft(cur);
   }
  }
  
  // we did not reach the root yet
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
   // (the value does not exist in this tree, so nothing to do)der Wert existiert nicht in diesem Baum, daher ist nichts zu tun 
   return;
  } else {
   if(p.key>q)  {
    removeAVL(p.left,q);
   } else if(p.key<q) {
    removeAVL(p.right,q);
   } else if(p.key==q) {
    // we found the node in the tree.. now lets go on!
    removeFoundNode(p);
   }
  }
 }
 
 /**
  * Removes a node from a AVL-Tree, while balancing will be done if necessary.
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
   // q has two children --> will be replaced by successor
   r = successor(q);
   q.key = r.key;
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
  * 
  * @param n
  *            The node for the rotation.
  * 
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
  * @param n
  *            The node for the rotation
  * 
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
  * search function to search whether there is a node whose key is i.
  * @param i The key of the node.
  * @return The found node.
  */
 public AvlNode search(int i)
  {
    AvlNode current = this.root;

    while (current.key!=i)
    {
      if (i<current.key) {
        current = current.left; /*go left subtree to search*/
      } else {
        current = current.right; /*go right subtree to search*/
      }

      if (current==null) {
        break; /*reach a leaf without found node, so return null*/
      }
    }
    return current; /*we found the node*/
  }
 
/************************** 6 Required Functions *********************************/
 
 /***********************************************************************/
 /*  FUNCTION: Increase*/
 /**/
 /*  INPUTS:  i is the key value and m is the count of the node need to increased by */
 /**/
 /*  OUTPUT:  the final count of the node */
 /**/
 /*  Modifies:  tree*/
 /**/
 /*  RESULT:  Makes a node and inserts it into the tree if not already present and prints its count or if the node is present, change its count and print*/
 /***********************************************************************/
  public void increase(int i, int j)
  {
    AvlNode foundNode;
    foundNode = search(i);
    if (foundNode==null) {
      insert(i,j);
      System.out.println(j);
    } else {
      foundNode.count = foundNode.count + j;
      System.out.println(foundNode.count);
    }
  }


 /***********************************************************************/
 /*  FUNCTION: Reduce*/
 /**/
 /*  INPUTS:  i is the key value and m is the count of the node need to reduced by */
 /**/
 /*  OUTPUT:  the final count of the node */
 /**/
 /*  Modifies:  tree*/
 /**/
 /*  RESULT:  remove a node if its count is less than 1 or it's not existed and print 0 or reduce this node's count by m and print new count*/
 /***********************************************************************/
 public void reduce(int i,int j)
 {
   AvlNode foundNode;
   foundNode = search(i);
   if (foundNode==null) {
      System.out.println(0);
    } else {
      foundNode.count=foundNode.count - j;
      if (foundNode.count<=0) {
        removeFoundNode(foundNode);
        System.out.println(0);
      } else {
        System.out.println(foundNode.count);
      }
    }
  }

/***********************************************************************/
/*  FUNCTION: Next*/
/**/
/*  INPUTS:  i*/
/**/
/*  OUTPUT:  the key and count of the nodes which meet the requirement */
/**/
/*  Modifies:  none*/
/**/
/*  RESULT:  print the ID and count of the node with lowest ID that is greater than i. Print "0 0" if there is no next ID.*/
/***********************************************************************/
  public void next(int i)
  {
    searchNext(this.root, i);
  }
  private void searchNext(AvlNode p, int i)
  {
    int k = 0;
    int c = 0;
    while (p!=null)
    {
      if(i<p.key) {
        k = p.key;
        c = p.count;
        p = p.left;
      } else if (i>p.key) {
        p=p.right;
      } else {
          if(p.right == null) {
            ;//The node has no right subtree, so next ID is previous record
          } else {
            p=p.right;
            while(p!=null) 
            {
              k = p.key;
              c = p.count;
              p = p.left;
            }
          }
          break;
      }
    }
    System.out.println(k + " " + c);
  }

/***********************************************************************/
/*  FUNCTION: Count*/
/**/
/*  INPUTS:  x is the key value */
/**/
/*  OUTPUT:  the final count of the node */
/**/
/*  Modifies:  none*/
/**/
/*  RESULT:  print the found node's count, if not present print 0*/
/***********************************************************************/
  public void count(int i)
  {
   AvlNode foundNode;
   foundNode = search(i);
   if (foundNode!=null) {
      System.out.println(foundNode.count);
    } else {
      System.out.println(0);
    }
  }

/***********************************************************************/
/*  FUNCTION: Previous*/
/**/
/*  INPUTS:  i*/
/**/
/*  OUTPUT:  the key and count of the nodes which meet the requirement */
/**/
/*  Modifies:  tree*/
/**/
/*  RESULT:  Print ID and count of the node with greatest ID that is less than i. Print 0 0 if there is no previous ID.*/
/***********************************************************************/
public void previous(int i)
  {
    searchPrevious(this.root, i);
  }
private void searchPrevious(AvlNode p, int i)
{
  int k = 0;
  int c = 0;
  while (p!=null)
  {
    if(i<p.key) {
      p = p.left;
    } else if (i>p.key) {
      k = p.key;
      c = p.count;
      p=p.right;
    } else {
        if(p.left == null) {
          ;//The node has no left subtree, so previous ID is previous record
        } else {
          p=p.left;
          while(p!=null) 
          {
            k = p.key;
            c = p.count;
            p = p.right;
          }
        }
          break;
    }
  }
  System.out.println(k + " " + c);
}
 
/***********************************************************************/
/*  FUNCTION: Inrange*/
/**/
/*  INPUTS:  x y, x <= y*/
/**/
/*  OUTPUT:  the count of the nodes which meet the requirement */
/**/
/*  Modifies:  tree*/
/**/
/*  RESULT:  print all nodes' count: x<=count<=y*/
/***********************************************************************/
public void inRange(int x, int y)
  {
    if (search(x)!=null) {
      System.out.print(search(x).count + " ");
    }
    searchRange(this.root, x, y);
  }

private void searchRange(AvlNode p, int x, int y)
  {
    int i = x;
    int j = y;
    AvlNode r = p;
    while (p!=null) 
    {
      if (i<p.key) {
          r = p;
          p = p.left;
        } else if (i>p.key) {
          p=p.right;
        } else {
          r = p;
          break;
        } 
    }
    if (r.key<j) {
      System.out.print(r.count + " "); 
      searchRange(r, i, j);
    } else if (r.key == j) {
      System.out.println(r.count + " ");
    }
  }

/***************************** Helper Functions ************************************/
  
  /**
  * Returns the successor of a given node in the tree (search recursively).
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
  * 
  * @param cur
  * @return The height of a node (-1, if node is not existent e.g. NULL).
  */
 private int height(AvlNode cur) {
  if(cur==null) {
   return -1;
  }
  if(cur.left==null && cur.right==null) {
   return 0;
  } else if(cur.left==null) {
   return 1+height(cur.right);
  } else if(cur.right==null) {
   return 1+height(cur.left);
  } else {
   return 1+maximum(height(cur.left),height(cur.right));
  }
 }
 
 /**
  * Return the maximum of two integers.
  */
 private int maximum(int a, int b) {
  if(a>=b) {
   return a;
  } else {
   return b;
  }
 }

 public void clear() {
    destroy(this.root);
    root = null;
  }

  private void destroy(AvlNode tree) {
    if (tree==null)
      return ;

    if (tree.left != null)
      destroy(tree.left);
    if (tree.right != null)
      destroy(tree.right);

    tree=null;
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
 
 private void setBalance(AvlNode cur) {
  cur.balance = height(cur.right)-height(cur.left);
 }
 
 /**
  * Calculates the Inorder traversal of this tree.
  * 
  * @return A Array-List of the tree in inorder traversal.
  */
 
}



