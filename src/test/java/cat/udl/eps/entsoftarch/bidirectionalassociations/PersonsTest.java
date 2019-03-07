package cat.udl.eps.entsoftarch.bidirectionalassociations;

import cat.udl.eps.entsoftarch.bidirectionalassociations.domain.Person;
import cat.udl.eps.entsoftarch.bidirectionalassociations.repository.PersonRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.client.Traverson;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockMvcClientHttpRequestFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collection;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.springframework.hateoas.client.Hop.rel;

// https://www.programcreek.com/java-api-examples/index.php?api=org.springframework.hateoas.client.Traverson

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class PersonsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonRepository personRepository;

    private ParameterizedTypeReference<Resources<Person>> personType =
            new ParameterizedTypeReference<Resources<Person>>() {};

    private Traverson traverson = new Traverson(
            URI.create("http://localhost:8080"),
            MediaTypes.HAL_JSON);

    @Before
    public void setUp() throws Exception {
        MockMvcClientHttpRequestFactory requestFactory =
                new MockMvcClientHttpRequestFactory(mockMvc);
        traverson.setRestOperations(new RestTemplate(requestFactory));
    }

    @Test
    public void no_persons() {
        Resources<Person> personResources =
                traverson.follow("persons").toObject(personType);

        Collection<Person> persons = personResources.getContent();

        assertTrue(persons.isEmpty());
    }

    @Test
    public void a_single_person() {

        Person person = new Person();
        person.setName("John Doe");
        personRepository.save(person);
        personRepository.flush();

        Resources<Person> personResources =
                traverson.follow("persons").toObject(personType);

        Collection<Person> persons = personResources.getContent();

        assertEquals(1, persons.size());
    }
}
