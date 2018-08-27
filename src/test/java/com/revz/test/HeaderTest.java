package com.revz.test;

import io.restassured.mapper.factory.DefaultGsonObjectMapperFactory;
import io.restassured.mapper.factory.GsonObjectMapperFactory;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class HeaderTest {


    @Test
    public void test_status() throws IOException {

        //Given
        HttpUriRequest request = new HttpGet("https://samples.openweathermap.org/data/2.5/forecast?id=524901&appid=916077398e8492c5c357033633a7aa78");
        //When
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        //Then
        assertThat(
                response.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_OK)
        );
    }

    @Test
    public void test_header() throws IOException {
        //Given
        HttpUriRequest request = new HttpGet("https://samples.openweathermap.org/data/2.5/forecast?id=524901&appid=916077398e8492c5c357033633a7aa78");
        //When
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        //Then
        assertThat(
                response.getEntity().getContentType().getValue(),
                equalTo("application/json; charset=utf-8")
        );
    }


}
