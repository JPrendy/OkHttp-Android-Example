package com.example.mockingapi;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOError;
import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    /**
     *
     * A very rough example of how to use okhttp webserver to mock responses, don't use in production code, video for reference https://www.youtube.com/watch?v=P97mHqN9R00
     */
    @Test
    public void testMockWebServer() throws Exception {
        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse().setBody("yo dog"));
        server.start();

        HttpUrl baseUrl = server.url("/v1/chat/");
        String request = sendGetRequest(new OkHttpClient(), baseUrl);
        Assert.assertEquals("yo dog", request);
    }

    private String sendGetRequest(OkHttpClient okHttpClient, HttpUrl base) throws IOException {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), "hi there");
        Request request = new Request.Builder()
                .post(body)
                .url(base)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        return response.body().string();
    }

//    public void test() throws Exception {
//        // Create a MockWebServer. These are lean enough that you can create a new
//        // instance for every unit test.
//        MockWebServer server = new MockWebServer();
//
//        // Schedule some responses.
//        server.enqueue(new MockResponse().setBody("hello, world!"));
//        server.enqueue(new MockResponse().setBody("sup, bra?"));
//        server.enqueue(new MockResponse().setBody("yo dog"));
//
//        // Start the server.
//        server.start();
//
//        // Ask the server for its URL. You'll need this to make HTTP requests.
//        HttpUrl baseUrl = server.url("/v1/chat/");
//
//        // Exercise your application code, which should make those HTTP requests.
//        // Responses are returned in the same order that they are enqueued.
//        Chat chat = new callOkHTTP();
//
//        chat.loadMore();
//        assertEquals("hello, world!", chat.messages());
//
//        chat.loadMore();
//        chat.loadMore();
//        assertEquals(""
//                + "hello, world!\n"
//                + "sup, bra?\n"
//                + "yo dog", chat.messages());
//
//
//        // Shut down the server. Instances cannot be reused.
//        server.shutdown();
//    }
}

