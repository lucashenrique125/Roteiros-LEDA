package adt.bst;

import java.util.ArrayList;
import java.util.List;

public class BSTImpl<T extends Comparable<T>> implements BST<T> {

	protected BSTNode<T> root;

	public BSTImpl() {
		root = new BSTNode<T>();
	}

	public BSTNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return root.isEmpty();
	}

	@Override
	public int height() {
		return this.height(this.getRoot());
	}

	private int height(BSTNode<T> node) {
  	   if (node.isEmpty()) {
  		  return -1;
  	   }
  	   
  	 return Math.max(this.height((BSTNode<T>) node.getRight()), this.height((BSTNode<T>) node.getLeft())) + 1;
    }

	@Override
    public BSTNode<T> search(T element) {
        if (element != null) {
           return this.search(element, this.getRoot());
       }
       return new BSTNode<T>();
    }

	private BSTNode<T> search(T element, BSTNode<T> node) {
  	   if (node.isEmpty()) {
  		   return node;
  	   } else if (element.compareTo(node.getData()) > 0) {
  		   return this.search(element, (BSTNode<T>) node.getRight());
  	   } else if (element.compareTo(node.getData()) < 0) {
  		   return this.search(element, (BSTNode<T>) node.getLeft());
  	   } else {
  		   return node;
  	   }
     }

	@Override
     public void insert(T element) {
  	   if (element != null) {
  		   this.insert(element, this.getRoot(), new BSTNode<T>());
  	   }
     } 

	private void insert(T element, BSTNode<T> node, BSTNode<T> parent) {
  	   if (node.isEmpty()) {
  		   node.setData(element);
  		   node.setLeft(new BSTNode<T>());
  		   node.setRight(new BSTNode<T>());
  		   node.setParent(parent);
  	   } else if (element.compareTo(node.getData()) > 0) {
  		   this.insert(element, (BSTNode<T>) node.getRight(), node);
  	   } else if (element.compareTo(node.getData()) < 0) {
  		   this.insert(element, (BSTNode<T>) node.getLeft(), node);
  	   }
     }
	@Override
     public BSTNode<T> maximum() {
        if (!this.isEmpty()) {
           if (this.getRoot().getRight().isEmpty()) {
              return this.getRoot();
           } else {
              return this.maximum(this.getRoot());
           }
        }
        return null;
     } 
	private BSTNode<T> maximum(BSTNode<T> node) {
		if (node.getRight().isEmpty()) {
			return node;
		} else {
			return this.maximum((BSTNode<T>) node.getRight());
		}
	}

	@Override
	public BSTNode<T> minimum() {
		if (!this.isEmpty()) {
			if (this.getRoot().getLeft().isEmpty()) {
				return this.getRoot();
			} else {
				return this.minimum(this.getRoot());
			}
		}
		return null;
	}

	private BSTNode<T> minimum(BSTNode<T> node) {
		if (node.getLeft().isEmpty()) {
			return node;
		} else {
			return this.minimum((BSTNode<T>) node.getLeft());
		}
	}

	@Override
	public BSTNode<T> sucessor(T element) {
		if (element != null) {
			BSTNode<T> node = this.search(element);
			if (node.isEmpty()) {
				return null;
			}
			if (!node.getRight().isEmpty()) {
				return this.minimum((BSTNode<T>) node.getRight());
			}
			if (this.isLeftChild(node)) {
				return (BSTNode<T>) node.getParent();
			}
			while (!node.isEmpty() && this.isRightChild(node)) {
				node = (BSTNode<T>) node.getParent();
			}
			if (node.getData().equals(this.getRoot().getData())) {
				return null;
			}
			return (BSTNode<T>) node.getParent();
		}
		return null;
	}

	@Override
	public BSTNode<T> predecessor(T element) {
		if (element != null) {
			BSTNode<T> node = this.search(element);
			if (node.isEmpty()) {
				return null;
			}
			if (!node.getLeft().isEmpty()) {
				return this.maximum((BSTNode<T>) node.getLeft());
			}
			if (this.isRightChild(node)) {
				return (BSTNode<T>) node.getParent();
			}
			while (!node.isEmpty() && this.isLeftChild(node)) {
				node = (BSTNode<T>) node.getParent();
			}
			if (node.getData().equals(this.getRoot().getData())) {
				return null;
			}
			return (BSTNode<T>) node.getParent();
		}
		return null;
	}

	protected boolean isRightChild(BSTNode<T> node) {
		if (node == null || node.isEmpty()) {
			return false;
		}
		return node.equals(node.getParent().getRight());
	}

	protected boolean isLeftChild(BSTNode<T> node) {
		if (node == null || node.isEmpty()) {
			return false;
		}
		return node.equals(node.getParent().getLeft());
	}
	
	@Override
 	public void remove(T element) {
 		if (element != null) {
 			BSTNode<T> node = search(element);
 			if (node != null) {
 				remove(node);
 			}
	 	}
	}

    public void remove(BSTNode<T> node) {
 	   if (node != null) {
 		   if (!node.isEmpty()) {
 			   int degree = this.degree(node);
 			   
 			   if (degree == 0) {
 				   this.removeLeaf(node);
 			   } else if (degree == 1) {
 				   this.removeOneDegreeNode(node);
 			   } else {
 				   this.removeTwoDegreeNode(node);
 			   }
 		   }
 	   }
    }

	private int degree(BSTNode<T> node) {
	   if (node == null || node.isEmpty()) {
 		   return -1;
 	   } else if (node.isLeaf()) {
 		   return 0;
 	   } else if (!node.getLeft().isEmpty() && !node.getRight().isEmpty()) {
 		   return 2;
 	   } else {
 		   return 1;
 	   }
    }

	private void removeLeaf(BSTNode<T> node) {
 	   node.setData(null);
    }

	private void removeOneDegreeNode(BSTNode<T> node) {
 	   if (node.getParent() == null || node.getParent().isEmpty()) {
 		   if (!node.getLeft().isEmpty()) {
 			   node.getLeft().setParent(null);
 			   this.root = (BSTNode<T>) node.getLeft();
 			   return;
 		   } else {
 			   node.getRight().setParent(null);
 			   this.root = (BSTNode<T>) node.getRight();
			   return;
 		   }
 	   }
   BSTNode<T> aux;
 	   if (!node.getRight().isEmpty()) {
 		   aux = (BSTNode<T>) node.getRight();
 	   } else {
 		   aux = (BSTNode<T>) node.getLeft();
	   }
 	   
 	   aux.setParent(node.getParent());
	   if (node.equals(node.getParent().getLeft())) {
 		   node.getParent().setLeft(aux);
 	   } else {
 		   node.getParent().setRight(aux);
 	   }
    }

	private void removeTwoDegreeNode(BSTNode<T> node) {
 	   BSTNode<T> sucessor = this.sucessor(node.getData());
 	   if (sucessor != null) {
 		   node.setData(sucessor.getData());
 		   int degree = this.degree(sucessor);
 		   if (degree == 0) {
 			   this.removeLeaf(sucessor);
 		   } else if (degree == 1) {
 			   this.removeOneDegreeNode(sucessor);
 		   } else {
 			   this.removeTwoDegreeNode(sucessor);
 		   }
	   }
    }

	@Override
 	public T[] preOrder() {
 		List<T> list = new ArrayList<>(this.size());
 		this.preOrder(this.getRoot(), list);
 		return list.toArray((T[]) new Comparable[this.size()]);
	}

	private void preOrder(BSTNode<T> node, List<T> list) {
		if (!node.isEmpty()) {
			list.add(node.getData());
			this.preOrder((BSTNode<T>) node.getLeft(), list);
			this.preOrder((BSTNode<T>) node.getRight(), list);
		}
	}

	@Override
	public T[] order() {
		List<T> list = new ArrayList<>(this.size());
		this.order(this.getRoot(), list);
		return list.toArray((T[]) new Comparable[this.size()]);
	}

	private void order(BSTNode<T> node, List<T> list) {
		if (!node.isEmpty()) {
			this.order((BSTNode<T>) node.getLeft(), list);
			list.add(node.getData());
			this.order((BSTNode<T>) node.getRight(), list);
		}
	}

	@Override
	public T[] postOrder() {
		List<T> list = new ArrayList<>(this.size());
		this.postOrder(this.getRoot(), list);
		return list.toArray((T[]) new Comparable[this.size()]);
	}

	private void postOrder(BSTNode<T> node, List<T> list) {
		if (!node.isEmpty()) {
			this.postOrder((BSTNode<T>) node.getLeft(), list);
			this.postOrder((BSTNode<T>) node.getRight(), list);
			list.add(node.getData());
		}
	}

	@Override
	public int size() {
		return size(root);
	}

	private int size(BSTNode<T> node) {
		int result = 0;

		if (!node.isEmpty()) {
			result = 1 + size((BSTNode<T>) node.getLeft()) + size((BSTNode<T>) node.getRight());
		}
		return result;
	}

}