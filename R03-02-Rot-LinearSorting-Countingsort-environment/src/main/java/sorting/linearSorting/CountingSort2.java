package sorting.linearSorting;

import java.util.Arrays;

import sorting.AbstractSorting;

/**
 * Classe que implementa a estratégia de Counting Sort vista em sala. Procure evitar desperdicio de 
 * memoria alocando o array de contadores com o tamanho sendo o máximo inteiro presente no array 
 * a ser ordenado.  
 * 
 */
public class CountingSort2 extends AbstractSorting<Integer> {

	@Override
	public void sort(Integer[] array,int leftIndex, int rightIndex) {	
		if(leftIndex >= rightIndex || array == null || array.length == 0 || leftIndex < 0 || rightIndex < 0 || rightIndex >= array.length){
			return;
		}
		
		int[] c = new int[getMaior(array, leftIndex, rightIndex)+1];
		
		for(int i = leftIndex; i <= rightIndex; i++){
			c[array[i]]++;
		}
		
		for(int i = 1; i < c.length; i++){
			c[i] += c[i-1];
		}
		
		Integer[] b = Arrays.copyOf(array, array.length);
		
		for(int i = rightIndex; i >= leftIndex; i--){
			array[c[b[i]]-1] = b[i];
			c[b[i]]--;
		}
		
	}

	private int getMaior(Integer[] array, int leftIndex, int rightIndex){
		int maior = array[leftIndex];
		
		for(int i = leftIndex; i <= rightIndex; i++){
			if(array[i] > maior){
				maior = array[i];
			}
		}
		
		return maior;
	}

}
