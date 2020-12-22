package com.hasanguner.aoc2020;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static com.hasanguner.aoc2020.Inputs.read;
import static java.lang.Integer.parseInt;
import static java.util.Arrays.stream;
import static java.util.Map.entry;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toCollection;

public class Day22 {

    public static void main(String[] args) {
        var input = read(22);
        System.out.println("Part 1 : " + part1(input));
        System.out.println("Part 2 : " + part2(input));
    }

    static long part1(String[] input) {
        var decks = getDecks(input);
        return score(playCombat(new LinkedList<>(decks.getKey()), new LinkedList<>(decks.getValue())));
    }

    static long part2(String[] input) {
        var decks = getDecks(input);
        return score(playRecursiveCombat(new LinkedList<>(decks.getKey()), new LinkedList<>(decks.getValue())));
    }

    private static Map.Entry<List<Integer>, List<Integer>> getDecks(String[] input) {
        var iterator = stream(input).iterator();
        var player1 = new ArrayList<Integer>();
        var player2 = new ArrayList<Integer>();
        iterator.next();
        while (iterator.hasNext()) {
            var line = iterator.next();
            if (line.isEmpty()) {
                break;
            }
            player1.add(parseInt(line));
        }
        iterator.next();
        while (iterator.hasNext()) {
            player2.add(parseInt(iterator.next()));
        }
        return entry(player1, player2);
    }

    private static LinkedList<Integer> playCombat(LinkedList<Integer> deckOfPlayer1, LinkedList<Integer> deckOfPlayer2) {
        while (!deckOfPlayer1.isEmpty() && !deckOfPlayer2.isEmpty()) {
            var p1 = deckOfPlayer1.pop();
            var p2 = deckOfPlayer2.pop();
            reconcile(p1, p2, deckOfPlayer1, deckOfPlayer2, p1 > p2);
        }
        return getWinner(deckOfPlayer1, deckOfPlayer2);
    }

    private static LinkedList<Integer> playRecursiveCombat(LinkedList<Integer> deckOfPlayer1, LinkedList<Integer> deckOfPlayer2) {
        var roundHashes = new HashSet<String>();
        while (!deckOfPlayer1.isEmpty() && !deckOfPlayer2.isEmpty()) {
            var roundHash = roundHash(deckOfPlayer1, deckOfPlayer2);
            if (roundHashes.contains(roundHash)) {
                return deckOfPlayer1;
            }
            roundHashes.add(roundHash);
            var cardOfP1 = deckOfPlayer1.pop();
            var cardOfP2 = deckOfPlayer2.pop();
            if (cardOfP1 <= deckOfPlayer1.size() && cardOfP2 <= deckOfPlayer2.size()) {
                var subGameDeckOfP1 = deckOfPlayer1.stream().limit(cardOfP1).collect(toCollection(LinkedList::new));
                var sumGameDeckOfP2 = deckOfPlayer2.stream().limit(cardOfP2).collect(toCollection(LinkedList::new));
                var winner = playRecursiveCombat(subGameDeckOfP1, sumGameDeckOfP2);
                reconcile(cardOfP1, cardOfP2, deckOfPlayer1, deckOfPlayer2, winner == subGameDeckOfP1);
            } else {
                reconcile(cardOfP1, cardOfP2, deckOfPlayer1, deckOfPlayer2, cardOfP1 > cardOfP2);
            }
        }

        return getWinner(deckOfPlayer1, deckOfPlayer2);
    }

    private static LinkedList<Integer> getWinner(LinkedList<Integer> deckOfPlayer1, LinkedList<Integer> deckOfPlayer2) {
        return Optional.of(deckOfPlayer1).filter(not(List::isEmpty)).orElse(deckOfPlayer2);
    }

    private static long score(LinkedList<Integer> winner) {
        var ix = new AtomicLong(winner.size());
        return winner.stream()
                     .mapToLong(c -> c * ix.getAndDecrement())
                     .sum();
    }

    private static void reconcile(Integer cardOfPlayer1, Integer cardOfPlayer2, List<Integer> deckOfPlayer1, List<Integer> deckOfPlayer2, boolean p1Win) {
        if (p1Win) {
            deckOfPlayer2.remove(cardOfPlayer2);
            deckOfPlayer1.add(cardOfPlayer1);
            deckOfPlayer1.add(cardOfPlayer2);
        } else {
            deckOfPlayer1.remove(cardOfPlayer1);
            deckOfPlayer2.add(cardOfPlayer2);
            deckOfPlayer2.add(cardOfPlayer1);
        }
    }

    private static String roundHash(List<Integer> player1, List<Integer> player2) {
        return hash(player1) + "$" + hash(player2);
    }

    private static String hash(List<Integer> player) {
        return player.stream().map(String::valueOf).collect(joining(","));
    }
}
