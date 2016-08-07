package it.thomasjohansen.publish;

import it.thomasjohansen.publish.model.Title;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.GET;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@RunWith(SpringRunner.class)
public class PublishControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void index() {
        List<Title> titles =
                restTemplate.exchange("/", GET, null, new ParameterizedTypeReference<List<Title>>(){}).getBody();
    }

}
