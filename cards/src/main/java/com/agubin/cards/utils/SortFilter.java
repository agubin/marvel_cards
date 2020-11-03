package com.agubin.cards.utils;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SortFilter {

    public static List<? extends Orderly> sortAndFilter(List<? extends Orderly> orderly, Map<String, String> params) {
        if (!orderly.isEmpty()) {
            orderly = filterByNameContain(orderly, params.getOrDefault("nameContain", ""));
        }
        if (!orderly.isEmpty()) {
            order(orderly, params.getOrDefault("orderBy", ""));
        }
        return orderly;
    }

    private static List<? extends Orderly> filterByNameContain(List<? extends Orderly> orderly, String pattern) {
        List<? extends Orderly> filteredOrderly = orderly;
        if (!pattern.isEmpty()) {

            filteredOrderly = orderly.stream()
                    .filter((obj) -> obj.nameLikeValue().contains(pattern))
                    .collect(Collectors.toList());
        }
        return filteredOrderly;
    }

    private static void order(List<? extends Orderly> orderly, String condition) {
        if (!condition.isEmpty()) {
            orderly.sort(Orderly.getComparator(condition));
        }
    }
}
