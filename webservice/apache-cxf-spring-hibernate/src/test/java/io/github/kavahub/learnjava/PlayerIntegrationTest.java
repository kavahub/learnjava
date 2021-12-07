package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.bind.JAXB;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * 需要手工运行服务端，有多种方式运行，请参考 README.md 文档
 * 
 * 
 * @author PinWei Wan
 * @since 1.0.1
 */
public class PlayerIntegrationTest {
    private static final String BASE_URL = "http://localhost:9080/apache-cxf-spring-hibernate/services/players/";
    private static CloseableHttpClient client;

    @BeforeAll
    public static void createClient() {
        client = HttpClients.createDefault();
    }

    @AfterAll
    public static void closeClient() throws IOException {
        client.close();
    }

    @Test
    public void givenPlayId_whenGet_thenReturenPlayer() throws IOException {
        final int playerId = 1;

        final HttpGet httpGet = new HttpGet(BASE_URL + playerId);
        final HttpResponse response = client.execute(httpGet);

        assertEquals(200, response.getStatusLine().getStatusCode());

        final PlayerType player = JAXB.unmarshal(new InputStreamReader(response.getEntity().getContent()),
                PlayerType.class);
        assertEquals("Sachin Tendulkar", player.getName());
    }

    @Test
    public void givenPlayId_whenGet_thenNotFound() throws IOException {
        final int playerId = 0;

        final HttpGet httpGet = new HttpGet(BASE_URL + playerId);
        final HttpResponse response = client.execute(httpGet);

        assertEquals(404, response.getStatusLine().getStatusCode());
    }

    @Test
    public void givenPlayId_whenDelete_thenOk() throws IOException {
        final int playerId = 2;

        final HttpDelete httpDelete = new HttpDelete(BASE_URL + playerId);
        final HttpResponse response = client.execute(httpDelete);

        assertEquals(204, response.getStatusLine().getStatusCode());
    }

    @Test
    public void givenPlayId_whenDelete_thenNotFound() throws IOException {
        final int playerId = 0;

        final HttpDelete httpDelete = new HttpDelete(BASE_URL + playerId);
        final HttpResponse response = client.execute(httpDelete);

        assertEquals(404, response.getStatusLine().getStatusCode());
    }

    @Test
    public void givenPlayerType_whenPut_thenOk() throws IOException {
        final int playerId = 3;

        final HttpPut httpPut = new HttpPut(BASE_URL + playerId);
        httpPut.setHeader("Content-Type", "application/xml");
        
        final Path xmlFile = Paths.get("src", "test", "resources", "changed_player.xml");
        httpPut.setEntity(new StringEntity(Files.readString(xmlFile), "UTF-8"));
        final HttpResponse response = client.execute(httpPut);

        assertEquals(204, response.getStatusLine().getStatusCode());

        PlayerType player = getPlayer(playerId);
        assertNotNull(player);
        assertEquals("李四", player.getName());
    }

    @Test
    public void givenPlayerType_whenPut_thenNotFound() throws IOException {
        final int playerId = 0;

        final HttpPut httpPut = new HttpPut(BASE_URL + playerId);
        httpPut.setHeader("Content-Type", "application/xml");
        
        final Path xmlFile = Paths.get("src", "test", "resources", "changed_player.xml");
        httpPut.setEntity(new StringEntity(Files.readString(xmlFile), "UTF-8"));
        final HttpResponse response = client.execute(httpPut);

        assertEquals(404, response.getStatusLine().getStatusCode());
    }

    @Test
    public void givenPlayerType_whenPost_thenNotFound() throws IOException {
        final HttpPost httpPost = new HttpPost(BASE_URL);
        httpPost.setHeader("Content-Type", "application/xml");
        
        final Path xmlFile = Paths.get("src", "test", "resources", "create_player.xml");
        httpPost.setEntity(new StringEntity(Files.readString(xmlFile), "UTF-8"));
        final HttpResponse response = client.execute(httpPost);

        assertEquals(201, response.getStatusLine().getStatusCode());

        final int playerId = Integer.parseInt(EntityUtils.toString(response.getEntity()));
        final PlayerType player = getPlayer(playerId);
        assertEquals("王五", player.getName());
    }

    private PlayerType getPlayer(int playerId) throws IOException {
        final URL url = new URL(BASE_URL + playerId);
        final InputStream input = url.openStream();
        return JAXB.unmarshal(new InputStreamReader(input), PlayerType.class);
    }
}
