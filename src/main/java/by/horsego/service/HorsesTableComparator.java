package by.horsego.service;

import java.util.Comparator;

/**
 * The class implements the interface {@link Comparator} to sort from larger to smaller.
 *
 * @author Mikita Masukhranau
 * @version 1.0
 */

public class HorsesTableComparator implements Comparator<Integer> {

    @Override
    public int compare(Integer horseId1, Integer horseId2) {
        return horseId2 - horseId1;
    }
}
