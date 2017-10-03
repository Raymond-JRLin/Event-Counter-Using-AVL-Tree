import java.util.ArrayList;





public class AVLTree
{

/** define a AVL tree node **/
	public class Node 
	{
		public Node left;
        public Node right;
        public Node parent;
        public int key;
        public int count;
        public int bf;

        public Node(int k, int c) 
        {
        	left = right = parent = null;
    	  	bf = 0;
   	  		key = k;
      		count = c;
     	}
     	public String toString() {
      	return "" + key;
     	}
    }


	protected Node root;

/*to calculate a node's balance factor*/
private void calbf(Node rec) 
{
	rec.bf=height(rec.left)-height(rec.right);
}

/*to calculate a node's height*/
private int height(Node rec) 
{
	if (rec==null){
		return -1;
	}
	if (rec.left==null && rec.right==null){
	    return 0;
	} else if (rec.left==null) {
	    return height(rec.right)+1;
	} else if (rec.right==null) {
	    return height(rec.left)+1;
	} else { //if (rec.left!=null && rec.right!=null)
		return max(height(rec.left),height(rec.right))+1;
	}
}

/*max function for calculating height*/
private int max(int a, int b)
{
	if (a>=b){
		return a;
	}
	else {
		return b;
	}
}

/* check whether need to do balancing and which kind need to be done*/
public void RecBalance(Node rec)
{
	calbf(rec);
	int bf = rec.bf;

	/*go to check balance */
	if(bf==-2){
		if(height(rec.left.left)>=height(rec.left.right)) {
			rec = LLrotate(rec);
		} else {
			rec = LRrotate(rec);
		} 
	} else if (bf==2) {
		if (height(rec.right.right)>=height(rec.right.left)) {
			rec = RRrotate(rec);
		} else {
			rec = RLrotate(rec);
		}
	}

	/*keep going up to the root*/
	if(rec.parent!=null){
		RecBalance(rec.parent);
	} else{
		this.root = rec;
    }
}

/* LL rotate 对应的是rotateRight*/
public Node LLrotate(Node n)
{
	Node m = n.left;
	m.parent = n.parent;
	n.left = m.right;
	if (n.left!=null){
		n.left.parent = n;
	}
	m.right = n;
	n.right = m;
	/* change parent's values*/
	if (m.parent!=null){
		if (m.parent.right==n) {
			m.parent.right = m;
		} else if (m.parent.left==n) {
			m.parent.left = m;
		}
	}
	calbf(n);
	calbf(m);
	return m;
}

/* RR rotate 对应的是rotateLeft*/
public Node RRrotate(Node n){
	Node m = n.right;
    m.parent = n.parent;
    n.right = m.left;
    if(n.right!=null){
    	n.right.parent=n;
    }
    m.left = n;
    n.parent = m;
    if(m.parent!=null){
    	if(m.parent.right==n) {
    		m.parent.right = m;
        } else if(m.parent.left==n) {
    	m.parent.left = m;
        }
    }
  
    calbf(n);
    calbf(m);
    return m;
 }

/*LR rotate =RR+LL*/
public Node LRrotate(Node i){
	i.left = RRrotate(i.left);
	return LLrotate(i);
}

/*RL rotate =LL+RR*/
public Node RLrotate(Node i){
	i.right = LLrotate(i.right);
	return RRrotate(i);
}

/* create a node into tree, key is k and count is c*/
public void Insert(int k, int c) 
{
	Node n = new Node(k,c);
	InsertTree(this.root,n);
}

/*!!!cannot be used for initial the tree, but in increase!!!*/
/*insert the new node into the tree and check balance*/
/*x is compared node now, y is the node to be inserted*/
public void InsertTree(Node x, Node y) 
{
	if (x==null) {
		this.root=y; /*if there is no node to be compared, then insert the new node as root*/
	} else {
		if (x.key>y.key){ /*if insert a smaller node*/
			if (x.left==null){ /*if node's left is empty, insert directly*/
				x.left = y;
				y.parent = x;
				RecBalance(x); /*check balance and do balancing if necessary*/
			} else {
				InsertTree(x.left,y); /*compare y and x's left node, do insertion again*/
			}
		} else if (y.key>x.key) { /*if inesrt a larger node*/
			if (x.right==null) { /*if node's right is empty, insert directly*/
				x.right = y;
				y.parent = x;
				RecBalance(x); /*check balance and do balancing if necessary*/
			} else {
				InsertTree(x.right,y); /*compare y and x's right node, do insertion again*/
			}
		} else {
			/*nothing*/
		}
	}
}

/*remove operation */
public void Remove(int x)
{
	RemoveTree(this.root,x); 
}

/* to find which node to be removed*/
public void RemoveTree(Node p, int q) 
{
	if (p==null) {
		return;
	} else {
		if (p.key>q) {
			RemoveTree(p.left,q); /*go left*/
		} else if (p.key<q) {
			RemoveTree(p.right,q); /*go right*/
		} else if (p.key==q) {
			RemoveNode(p); /*found the node to be removed, then delete it*/
		}
	}
}

/*do the exact remove to the node we found*/
public void RemoveNode(Node q)
{
	Node r;
	if (q.left==null || q.right==null) {
		if (q.parent==null) {
			this.root=null;
			q=null;
			return;
		}
		r=q;
	} else {
		r = Suc(q);
		q.key = r.key;
	}
	Node p;
	if (r.left != null) {
		p = r.left;
	} else {
		p = r.right;
	}
	if (p!=null) {
		p.parent=r.parent;
	}
	if (r.parent==null) {
		this.root=p;
	} else {
		if (r==r.parent.left) {
			r.parent.left=p;
		} else {
			r.parent.right=p;
		}
		RecBalance(r.parent);
	}
	r=null;
}

/*to find a node's successor*/
public Node Suc(Node q)
{
	if (q.right!=null) {
		Node r = q.right;
		while(r.left!=null) 
		{
			r=r.left;
		}
		return r;
	} else {
		Node p = q.parent;
		while (p!=null && q==p.right)
		{
			q=p;
			p=q.parent;
		}
		return p;
	}
}

/* find function: to find node with key j, begin wiht the root and compare with it*/
public Node Find(int j){
	Node current = this.root; /*give root node to current to compare from the root*/
	while (current.key!=j)
	{
		if (current==null) { /*cannot find*/
			return null;
		} else {
			if (j<current.key) { 
				current = current.left; /*go left child of current to find*/
			} else {
				current = current.right; /*go left child of current to find*/
			}
		}	 
	}
	return current;
}

/***********************************************************************/
/*  FUNCTION: Increase*/
/**/
/*  INPUTS:  i is the key value and m is the count of the node need to increased by */
/**/
/*  OUTPUT:  the final count of the node */
/**/
/*  Modifies:  tree*/
/**/
/*  RESULT:  Makes a node and inserts it into the tree if not already present and prints its count
 or if the node is present, change its count and print*/
/***********************************************************************/
public void Increase(int i, int m)
{
	Node foundnode;
	foundnode = Find(i);
	if (foundnode==null) {
		Insert(i,m);
	} else {
		foundnode.count=foundnode.count + m;
	}
	System.out.println(foundnode.count);
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
public void Reduce(int i,int m)
{
	Node foundnode;
	foundnode = Find(i);
	if (foundnode==null) {
		System.out.println(0);
	} else {
		foundnode.count=foundnode.count - m;
		if (foundnode.count<=0) {
			Remove(i);
		} else {
			System.out.println(foundnode.count);
		}
	}
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
public void Count(int x)
{
	Node foundnode;
	foundnode = Find(x);
	if (foundnode!=null) {
		System.out.println(foundnode.count);
	} else {
		System.out.println(0);
	}
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
public void InRange(int x, int y)
{
	Range(this.root, x, y);
}

public void Range(Node p, int x, int y)
{
	if (p==null) {
		return;
	} else {
		if (x<p.key) {
			Range(p.left,x,y);
		} 
		if (y>p.key) {
			Range(p.right,x,y);
		} 
		if (x<=p.key && y>=p.key) {
		 	System.out.print(p.count + " ");
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
public void Next(int i)
{
	ToNext(this.root, i);
}

public void ToNext(Node p, int x)
{
	int k=0;
	int c=0;
	while(p!=null)
	{
		if (x>p.key) {
			p=p.right;
		} else if (x<p.key) {
			k=p.key;
			c=p.count;
			p=p.left;
		} else if (x==p.key) {
			if (p.right==null) {
				System.out.println(k + " " + c); //wrong!!! ?
				break;
			} else { /* keep going from the left subtree to find min node key*/
				p=p.right;
				k=LeftMin(p).key;
				c=LeftMin(p).count;
				System.out.println(k + " " + c);
				break;
			}
		} else {
			System.out.println("0 0");
			break;
		}
	}
}

/*LeftMin is used to find the min node of left subtree of a node p */
private Node LeftMin(Node p)
{
	while (p!=null)
	{
		Node r = p;
		p=p.left;
	}
	return r;
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
/*  RESULT:  Print ID and count of the event with greatest ID that is less than i. Print 0 0 if there is no previous ID.*/
/***********************************************************************/
public void Previous(int i)
{
	Previous(this.root, i);
}

public void ToPrevious(Node p, int x)
{
	int k=0;
	int c=0;
	while(p!=null)
	{
		if (x<p.key) {
			p=p.left;
		} else if (x>p.key) {
			k=p.key;
			c=p.count;
			p=p.right;
		} else if (x==p.key) {
			if (p.left==null) {
				System.out.println(k + " " + c);
				break;
			} else { /* keep going from the right subtree to find mmax node key*/
				p=p.left;
				k=RightMax(p).key;
				c=RightMax(p).count;
				System.out.println(k + " " + c);
				break;
			}
		} else {
			System.out.println("0 0");
			break;
		}
	}
}

/*RightMax is used to find the max node of right subtree of a node p */
private Node RightMax(Node p)
{
	while (p!=null)
	{
		Node r=p;
		p=p.right;
	}
	return r;
}



}