package sorting.linearSorting;

import java.util.Arrays;

import sorting.AbstractSorting;

/**
 * Classe que implementa do Counting Sort vista em sala. 
 * Desta vez este algoritmo deve satisfazer os seguitnes requisitos:
 * - Alocar o tamanho minimo possivel para o array de contadores (C)
 * - Ser capaz de ordenar arrays contendo numeros negativos
 */
public class ExtendedCountingSort extends AbstractSorting<Integer> {

	@Override
	public void sort(Integer[] array,int leftIndex, int rightIndex) {	
		if(array == null || array.length == 0 || leftIndex > rightIndex){
			return;
		}
		
		int maior = getMaior(array, leftIndex, rightIndex);
		int menor = getMenor(array, leftIndex, rightIndex);
		
		int[] c = new int[maior - menor + 1];
		
		for(int i = leftIndex; i <= rightIndex; i++){
			c[array[i]-menor]++;
		}
		
		for(int i = 1; i < c.length; i++){
			c[i] += c[i-1];
		}
		
		Integer[] b = Arrays.copyOf(array, array.length);
				
		for(int j = rightIndex; j >= leftIndex; j--){
			array[c[b[j]-menor]-1] = b[j];
			c[b[j]-menor]--;
		}
		
	}

	private int getMenor(Integer[] array, int leftIndex, int rightIndex) {
		int menor = array[leftIndex];
		
		for(int i = leftIndex + 1; i <= rightIndex; i++){
			if(array[i] < menor){
				menor = array[i];
			}
		}
		
		return menor;
	}

	private int getMaior(Integer[] array, int leftIndex, int rightIndex){
		int maior = array[leftIndex];
		
		for(int i = leftIndex + 1; i <= rightIndex; i++){
			if(array[i] > maior){
				maior = array[i];
			}
		}
		
		return maior;
	}
}
