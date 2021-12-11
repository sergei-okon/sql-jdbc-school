package ua.com.foxminded.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class InitialData {

    private static final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String numeric = "0123456789";

    public List<String> createFullName() throws IOException {
        List<String> fullNames = new ArrayList<>();

        List<String> names;
        try {
            names = Files.lines(Paths.get("src/main/resources/names.txt")).collect(Collectors.toList());
//            Collections.shuffle(names);
        } catch (IOException e) {
            throw new IOException("Error: names file not found");
        }

        List<String> surnames;
        try {
            surnames = Files.lines(Paths.get("src/main/resources/surnames.txt")).collect(Collectors.toList());
//            Collections.shuffle(surnames);
        } catch (IOException e) {
            throw new IOException("Error: surnames file not found");
        }

        int count = 0;
        for (int i = 0; count < 200; i++) {
            Random random = new Random();
            String strTemp = surnames.get(i) + " " + names.get(random.nextInt(20));
            fullNames.add(strTemp);
            count++;

            if (i == 19) {
                i = 0;
            }
        }
        return fullNames;
    }

    public List<String> createCourses() throws IOException {
        List<String> courses;
        try {
            courses = Files.lines(Paths.get("src/main/resources/courses.txt")).collect(Collectors.toList());
        } catch (IOException e) {
            throw new IOException("Error: course file not found");
        }
        return courses;
    }

    public List<String> createGroups(int numberGroups, int lengthWords, int lengthNumeric) {
        List<String> groups = new ArrayList<>();

        for (int i = 0; i < numberGroups; i++) {
            groups.add(this.generateOneGroupsName(lengthWords, lengthNumeric));
        }
        return groups;
    }

    private String generateOneGroupsName(int lengthWords, int lengthNumeric) {

        Random r = new Random();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < lengthWords; i++) {
            result.append(alphabet.charAt(r.nextInt(alphabet.length())));
        }

        result.append("-");

        for (int i = 0; i < lengthNumeric; i++) {
            result.append(numeric.charAt(r.nextInt(numeric.length())));
        }
        return result.toString();
    }
}