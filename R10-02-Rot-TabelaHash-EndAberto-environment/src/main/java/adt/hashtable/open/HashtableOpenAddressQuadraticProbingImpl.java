package adt.hashtable.open;

import adt.hashtable.hashfunction.HashFunction;
import adt.hashtable.hashfunction.HashFunctionClosedAddress;
import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionOpenAddress;
import adt.hashtable.hashfunction.HashFunctionQuadraticProbing;

public class HashtableOpenAddressQuadraticProbingImpl<T extends Storable>
		extends AbstractHashtableOpenAddress<T> {

	public HashtableOpenAddressQuadraticProbingImpl(int size,
			HashFunctionClosedAddressMethod method, int c1, int c2) {
		super(size);
		hashFunction = new HashFunctionQuadraticProbing<T>(size, method, c1, c2);
		this.initiateInternalTable(size);
	}

	@Override
	public void insert(T element) {
	      if (element != null) {
	    	  if(isFull()){
	    		  throw new HashtableOverflowException();
	    	  }

	          int probe = 0;

	          while (probe < super.table.length) {
	             int hash = hash(element, probe);

	             if (super.table[hash] == null || (super.table[hash] instanceof DELETED)) {
	                super.table[hash] = element;

	                super.elements++;

	                return;
	             } else {
	                 probe++;
	                 super.COLLISIONS++;
	              }
	           }
	       }
	}

	@Override
	public void remove(T element) {
		if (element != null && !isEmpty()) {

	         int probe = 0;

	         while (probe < super.table.length) {
	            int hash = hash(element, probe);

	            if (super.table[hash] != null && super.table[hash].equals(element)) {
	               super.table[hash] = super.deletedElement;

	               super.elements--;
	               
	               return;
	            } else {
	               probe++;
	            }
	         }
	      }
	}

	@Override
	public T search(T element) {
	      if (element != null) {

	          int probe = 0;

	          while (probe < super.table.length) {
	             int hash = hash(element, probe);

	             if (super.table[hash] != null && super.table[hash].equals(element)) {
	                return (T) super.table[hash];
	             }

	             probe++;
	          }
	       }

	       return null;
	}

	@Override
	public int indexOf(T element) {
      if (element != null && this.contains(element)) {

          int probe = 0;

          while (probe < super.table.length) {
             int hash = hash(element, probe);

             if (super.table[hash] != null && super.table[hash].equals(element)) {
                return hash;
             }

             probe++;
          }

       }

       return -1;
	}
	

   public boolean contains(T element) {	
      return (this.search(element) != null);
   }

   private int hash(T element, int probe) {
	      int index = -1;

	      HashFunction hashFunction = super.getHashFunction();

	      if (hashFunction instanceof HashFunctionClosedAddress) {
	         index = ((HashFunctionClosedAddress) hashFunction).hash(element);
	      } else if (hashFunction instanceof HashFunctionOpenAddress) {
	         index = ((HashFunctionOpenAddress) hashFunction).hash(element, probe);
	      }

	      return index;
	   }
}
