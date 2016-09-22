package sorting.linearSorting;

import java.util.Arrays;

import sorting.AbstractSorting;

/**
 * Classe que implementa a estratégia de Counting Sort vista em sala. Procure evitar desperdicio de 
 * memoria alocando o array de contadores com o tamanho sendo o máximo inteiro presente no array 
 * a ser ordenado.  
 * 
 */
public class CountingSort extends AbstractSorting<Integer> {

	@Override
	public void sort(Integer[] array,int leftIndex, int rightIndex) {	
		if(array == null || array.length == 0 || leftIndex > rightIndex){
			return;
		}
		
		int[] c = new int[getMaior(array, leftIndex, rightIndex) + 1];
		
		for(int i = leftIndex; i <= rightIndex; i++){
			c[array[i]]++;
		}
		
		for(int i = 1; i < c.length; i++){
			c[i] += c[i-1];
		}
		
		Integer[] b = Arrays.copyOf(array, array.length);
				
		for(int j = rightIndex; j >= leftIndex; j--){
			array[c[b[j]]-1] = b[j];
			c[b[j]]--;
		}
		
	}

	private int getMaior(Integer[] array, int leftIndex, int rightIndex){
		int maior = 0;
		
		for(int i = leftIndex; i <= rightIndex; i++){
			if(array[i] > maior){
				maior = array[i];
			}
		}
		
		return maior;
	}

}
