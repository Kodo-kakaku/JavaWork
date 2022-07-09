package org.gradle;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Multiset<String> multiset = HashMultiset.create(
                List.of("Gradle, let's be friends!", "Hello Java!"));
        for (String str : multiset) {
            System.out.println(str);
        }
    }
}