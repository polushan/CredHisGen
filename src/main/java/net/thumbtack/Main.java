package net.thumbtack;

import com.google.gson.Gson;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Map<Integer, List<Credit>> creditHistory = new HashMap<>(10);
        List<String> flags = new ArrayList<>(10);
        InputStream input = Main.class.getClassLoader().getResourceAsStream("flags.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            flags.add(line);
        }
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            creditHistory.put(i, new ArrayList<>());
        }
        for (int i = 0; i < 160; i++) {
            Credit credit = null;
            int borrower = i % 10;
            if ((i / 10) % 2 == 1 && i > 20) {
                List<Credit> credits = creditHistory.get(borrower);
                int creditCount = credits.size();
                credit = credits.get(rand.nextInt(creditCount));
                credit.setClosed(true);
                credit.setOverdue(rand.nextBoolean());
            } else {
                long id = Math.abs(rand.nextLong());
                credit = new Credit(id, rand.nextInt(1_000_000),
                        false, true, borrower + 1, flags.get(borrower));
                creditHistory.get(borrower).add(credit);
            }
            HttpClient client = HttpClientBuilder.create().build();
            String url = args.length > 0 ? args[0] : "http://localhost:8080";
            HttpPost post = new HttpPost(url + "/api/add/credit");

            try {
                post.setHeader("Content-type", "application/json");
                StringEntity stringEntity = new StringEntity(new Gson().toJson(credit));
                post.setEntity(stringEntity);
                client.execute(post);
                System.out.println(credit);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Thread.sleep( 3 * 60 * 1000);
        }
    }
}
