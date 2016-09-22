package sorting.variationsOfSelectionsort;

import sorting.AbstractSorting;
import util.Util;

/**
 * This selection sort variation has two internal iterations. In the first, it takes the
 * smallest elements from the array, and puts it in the first position. In the second,
 * the iteration is done backwards, that is, from right to left, and this time the biggest
 * element is selected and stored in the last position. Then it repeats the process,
 * excluding the positions already filled in, until the whole array is ordered.
 */
public class BidirectionalSelectionSort<T extends Comparable<T>> extends AbstractSorting<T>{

	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
         boolean swapped;
         
         do{
             swapped = false;
             for(int i = leftIndex; i < rightIndex; i = i + 1){
                 if (array[i].compareTo(array[i + 1]) > 0) {
                     Util.swap(array, i, i+1);
                     swapped = true;
                 }
             }
             rightIndex = rightIndex - 1;
             for(int i = rightIndex; i > leftIndex; i = i - 1) {
                 if (array[i].compareTo(array[i - 1]) < 0){
                	 Util.swap(array, i, i-1);
                     swapped = true;
                 }
             }
             leftIndex = leftIndex + 1;
             
         }while(swapped);
	}
}
