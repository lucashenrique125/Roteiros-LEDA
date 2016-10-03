package adt.bst;

import java.util.Arrays;

public class Main {
	public static void main(String[] args) {
		BSTImpl bst = new BSTImpl<Integer>();
		
		bst.insert(27);
		bst.insert(23);
		bst.insert(40);
		bst.insert(22);
		bst.insert(25);
		bst.insert(30);
		bst.insert(41);
		bst.insert(24);
		bst.insert(35);
		
		System.out.println(Arrays.toString(bst.preOrder()));
		
		System.out.println(bst.buscaEmLargura());
		
		System.out.println(bst.estatisticaDeOrdem(10));
	}
}
