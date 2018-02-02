package main;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Map<Integer, List<Credit>> creditHistory = new HashMap<>(10);
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
                credit = credits.get(rand.nextInt(creditCount) );
                credit.setClosed(true);
                credit.setOverdue(rand.nextBoolean());
            } else {
                long id = Math.abs(rand.nextLong());
                credit = new Credit(id, rand.nextInt(1_000_000),
                        false, true, borrower + 1);
                creditHistory.get(borrower).add(credit);
            }
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost("http://localhost:8080/api/add/credit");

            List<NameValuePair> arguments = new ArrayList<>(3);
            arguments.add(new BasicNameValuePair("apikey", "secretapikey"));
            arguments.add(new BasicNameValuePair("id", String.valueOf(credit.getId())));
            arguments.add(new BasicNameValuePair("sum", String.valueOf(credit.getSum())));
            arguments.add(new BasicNameValuePair("isclosed", String.valueOf(credit.isClosed())));
            arguments.add(new BasicNameValuePair("isoverdue", String.valueOf(credit.isOverdue())));
            arguments.add(new BasicNameValuePair("borrower", String.valueOf(credit.getBorrower())));

            try {
                post.setEntity(new UrlEncodedFormEntity(arguments));
                HttpResponse response = client.execute(post);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Thread.sleep( 3 * 60 * 1000);
        }
        for (int i = 0; i < 10; i++) {
            System.out.println("=========" + i + "=========");
            List<Credit> creditsList = creditHistory.get(i);
            for (Credit credit: creditsList) {
                System.out.println(credit);
            }
            System.out.println("===================");
        }
    }
}
