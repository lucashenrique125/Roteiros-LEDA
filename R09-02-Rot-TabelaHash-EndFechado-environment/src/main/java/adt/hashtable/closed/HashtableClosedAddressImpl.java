package adt.hashtable.closed;

import java.util.LinkedList;

import adt.hashtable.hashfunction.HashFunction;
import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionDivisionMethod;
import adt.hashtable.hashfunction.HashFunctionFactory;
import adt.hashtable.hashfunction.HashFunctionMultiplicationMethod;
import util.Util;

public class HashtableClosedAddressImpl<T> extends
		AbstractHashtableClosedAddress<T> {

	/**
	 * A hash table with closed address works with a hash function with closed
	 * address. Such a function can follow one of these methods: DIVISION or
	 * MULTIPLICATION. In the DIVISION method, it is useful to change the size
	 * of the table to an integer that is prime. This can be achieved by
	 * producing such a prime number that is bigger and close to the desired
	 * size.
	 * 
	 * For doing that, you have auxiliary methods: Util.isPrime and
	 * getPrimeAbove as documented bellow.
	 * 
	 * The length of the internal table must be the immediate prime number
	 * greater than the given size. For example, if size=10 then the length must
	 * be 11. If size=20, the length must be 23. You must implement this idea in
	 * the auxiliary method getPrimeAbove(int size) and use it.
	 * 
	 * @param desiredSize
	 * @param method
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public HashtableClosedAddressImpl(int desiredSize,
			HashFunctionClosedAddressMethod method) {
		int realSize = desiredSize;

		if (method == HashFunctionClosedAddressMethod.DIVISION) {
			realSize = this.getPrimeAbove(desiredSize); // real size must the
														// the immediate prime
														// above
		}
		initiateInternalTable(realSize);
		HashFunction function = HashFunctionFactory.createHashFunction(method,
				realSize);
		this.hashFunction = function;
	}

	// AUXILIARY
	/**
	 * It returns the prime number that is closest (and greater) to the given
	 * number. You can use the method Util.isPrime to check if a number is
	 * prime.
	 */
	int getPrimeAbove(int number) {
		while(!Util.isPrime(number)){
			number++;
		}
		
		return number;
	}

	@Override
	public void insert(T element) {
		if(element != null){
			int hash = hash(element);
			
			if(table[hash] == null){
				table[hash] = new LinkedList<T>();
				
				((LinkedList<T>) table[hash]).addLast(element);
			}else{
				super.COLLISIONS++;
				((LinkedList<T>) table[hash]).addLast(element);
			}
			
			super.elements++;
		}
	}

	@Override
	public void remove(T element) {
		if(element != null){
			int hash = hash(element);
			
			if(table[hash] != null){
				if(((LinkedList<T>) table[hash]).remove(element)){ // se a lista continha o elemento que foi removido
					super.elements--;
					
					if(!((LinkedList<T>) table[hash]).isEmpty()){ // se nao ficar vazia, tinha mais de um elemento (entao havia colisoes)
						super.COLLISIONS--;
					}
				}
			}
		}
	}

	@Override
	public T search(T element) {
		if(element != null){
			for(Object obj: table){
				if(obj != null){
					if(((LinkedList<T>) obj).contains(element)){
						return element;
					}
				}
			}
		}
		
		return null;
	}

	@Override
	public int indexOf(T element) {
		if(contains(element)){
			return hash(element);
		}

		return -1;
	}
	
	public int hash(T element){
		HashFunction hashFunction = this.getHashFunction();
		int hash = 0;
		
		if(hashFunction instanceof HashFunctionDivisionMethod){
			hash = ((HashFunctionDivisionMethod) hashFunction).hash(element);
		}else if(hashFunction instanceof HashFunctionMultiplicationMethod){
			hash = ((HashFunctionMultiplicationMethod) hashFunction).hash(element);
		}
		
		return hash;
	}

	public boolean contains(T element){
		if(element != null){
			int hash = hash(element);
			
			if(table[hash] != null){
				LinkedList<T> list = (LinkedList<T>) table[hash];
				
				return list.contains(element);
			}
		}
		
		return false;
	}
}
