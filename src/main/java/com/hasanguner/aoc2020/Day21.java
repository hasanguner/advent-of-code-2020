package com.hasanguner.aoc2020;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.hasanguner.aoc2020.Inputs.read;
import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.Map.entry;
import static java.util.function.Function.identity;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.Collectors.toUnmodifiableList;

public class Day21 {

    public static void main(String[] args) {
        var result = solve(read(21));
        System.out.println("Part 1 : " + result.getKey());
        System.out.println("Part 2 : " + result.getValue());
    }

    static Map.Entry<Long, String> solve(String[] input) {
        var foods = stream(input).map(Food::new).collect(toUnmodifiableList());
        var allergens = foods.stream().flatMap(it -> it.allergens.stream()).collect(toSet());
        var ingredientFrequencies = foods.stream()
                                         .flatMap(it -> it.ingredients.stream())
                                         .collect(groupingBy(identity(), counting()));
        var distinctIngredients = ingredientFrequencies.keySet();

        var allergenToPossibleIngredients = allergens.stream()
                                                     .collect(toMap(identity(), i -> new HashSet<>(distinctIngredients)));

        foods.forEach(food -> food.allergens.forEach(
                allergen -> distinctIngredients
                        .stream()
                        .filter(not(food.ingredients::contains))
                        .forEach(allergenToPossibleIngredients.get(allergen)::remove)
        ));
        var allergicIngredients = allergenToPossibleIngredients.values().stream().flatMap(Collection::stream).collect(toSet());
        var countOfAllergenFreeIngredients = distinctIngredients.stream()
                                                                .filter(not(allergicIngredients::contains))
                                                                .mapToLong(ingredientFrequencies::get)
                                                                .sum();

        var sortedAllergenToIngredients = new TreeMap<String, String>();
        for (int i = 0; i < allergens.size(); i++) {
            var allergenToIngredient = allergenToPossibleIngredients
                    .entrySet()
                    .stream()
                    .filter(kv -> kv.getValue().size() == 1)
                    .findFirst()
                    .orElseThrow();
            var allergen = allergenToIngredient.getKey();
            var dangerousIngredient = allergenToIngredient.getValue().iterator().next();
            sortedAllergenToIngredients.put(allergen, dangerousIngredient);
            allergenToPossibleIngredients.remove(allergen);
            allergenToPossibleIngredients.values().forEach(it -> it.remove(dangerousIngredient));
        }
        var dangerousIngredients = String.join(",", sortedAllergenToIngredients.values());
        return entry(countOfAllergenFreeIngredients, dangerousIngredients);
    }

    static class Food {
        final List<String> ingredients;
        final List<String> allergens;

        public Food(String line) {
            var part = line.split(" \\(contains ");
            var ingredientParts = part[0].split(" ");
            var allergenParts = part[1].substring(0, part[1].length() - 1).split(", ");
            this.ingredients = asList(ingredientParts);
            this.allergens = asList(allergenParts);
        }
    }

}

