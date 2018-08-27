package com.revz.test;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.matching.UrlPattern;
import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.InputStream;
import java.util.Scanner;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;

public class SimpleTest {

    private static final int PORT = 8083;
    private static WireMockServer wireMockServer = new WireMockServer(PORT);

    private static final String EVENTS_PATH = "/events?id=390";
    private static final String EVENTS_PATH1 = "/event1?id=390";
    private static final String APPLICATION_JSON = "application/json";
    private static final String GAME_ODDS = getEventJson("/event_0.json");
    private static final String GAME_ODDS1 = getEventJson("/event_1.json");


    @BeforeClass
    public static void setup(){
        wireMockServer.start();
        RestAssured.port = PORT;
        configureFor("localhost", PORT);
        stubFor(get(urlEqualTo(EVENTS_PATH)).willReturn(
                aResponse().withStatus(200)
                        .withHeader("Content-Type", APPLICATION_JSON)
                        .withBody(GAME_ODDS)));

        stubFor(get(urlEqualTo(EVENTS_PATH1)).willReturn(
                aResponse().withStatus(200)
                        .withHeader("Content-Type", APPLICATION_JSON)
                        .withBody(GAME_ODDS)));
    }


    @Test
    public void simple_test(){
        get(EVENTS_PATH).then()
                .assertThat().statusCode(200);
    }

    @Test
    public void givenUrl_whenSuccessOnGetsResponseAndJsonHasRequiredKV_thenCorrect() {

        get("/event1?id=390").then().statusCode(200).assertThat()
                .body("id", equalTo("390"));
    }

    @Test
    public void givenUrl_whenSuccessOnGetsResponseAndJsonHasRequiredKV_thenCorrect1() {

        get("/events?id=390").then().statusCode(200).assertThat()
                .body("data.countryId", equalTo(35));
    }

    private static String getEventJson(String filename) {
        String json = inputStreamToString(
                SimpleTest.class.getResourceAsStream(filename));
        System.out.println(json);
        return json;
    }

    static String inputStreamToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
