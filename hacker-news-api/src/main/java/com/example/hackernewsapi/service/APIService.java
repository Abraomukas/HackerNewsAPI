package com.example.hackernewsapi.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class APIService {

    private final String HEADER = "*************";

    public void launchApp() {
        Scanner scanner = new Scanner(System.in);
        showMenu();
        int option = scanner.nextInt();
        switch (option) {
            case 1:
                fetchStoriesFeature(scanner);
                break;
            case 0:
                exitApp();
                break;
        }

    }

    private void showMenu() {
        System.out.println(HEADER + "\tMENU\t" + HEADER);
        System.out.println("PLEASE SELECT AN OPTION");
        System.out.println("PRESS [1] TO FETCH THE LATEST STORIES");
        System.out.println("PRESS [0] TO EXIT");
        System.out.println();
    }

    private void fetchStoriesFeature(Scanner scanner) {
        System.out.println(HEADER + "\tFETCH STORIES\t" + HEADER);
        System.out.println("HOW MANY STORIES WOULD YOU LIKE TO FETCH? (MAXIMUM 50)");
        System.out.println();

        int storiesToFetch = scanner.nextInt();

        if (storiesToFetch > 0 && storiesToFetch < 50) {
            System.out.println("HERE ARE THE TOP " + storiesToFetch + " STORIES");
            fetch_X_stories(storiesToFetch);
        } else {
            System.out.println("INCORRECT INPUT!");
            fetchStoriesFeature(scanner);
        }
    }

    private void exitApp() {
        System.exit(0);
    }

    private void fetch_X_stories(int storiesToFetch) {
        // String regex = "(\\[(\\d{8},){" + storiesToFetch + "})";
        // Pattern pattern = Pattern.compile(regex);
        String newAndTopStoriesURL = "https://hacker-news.firebaseio.com/v0/topstories.json";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(newAndTopStoriesURL)
                .get()
                .build();

        try {
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();

            // String jsonBody = new Gson().toJson(responseBody);
            // Matcher matcher = pattern.matcher(responseBody);
            // JsonObject jsonObject = new JsonParser().parse(jsonBody).getAsJsonObject();

            List<String> stories = fetchStories(responseBody, storiesToFetch);
            for (String s : stories) {
                fetchStory(s);
            }
            // System.out.println(Arrays.asList(fetchStories(responseBody, storiesToFetch)));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> fetchStories(String body, int storiesToFetch) {
        List<String> result = new ArrayList<>();

        body = body.replace("{\"mode\":\"full\",\"isActive\":false}", "");
        body = body.replace("[", "");

        String[] split = body.split(",");
        for (int i = 0; i < storiesToFetch; i++) {
            result.add(split[i]);
        }

        return result;
    }

    private void fetchStory(String storyId) {
        OkHttpClient client = new OkHttpClient();
        String storyByIdURL = "https://hacker-news.firebaseio.com/v0/item/" + storyId + ".json";

        Request storyRequest = new Request.Builder()
                .url(storyByIdURL)
                .get()
                .build();

        try {
            Response story = client.newCall(storyRequest).execute();
            String responseBody = story.body().string();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String prettyJSON = gson.toJson(responseBody);
            System.out.println(prettyJSON);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
