package cat.udl.eps.entsoftarch.bidirectionalassociations.repository;

import cat.udl.eps.entsoftarch.bidirectionalassociations.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PersonRepository extends JpaRepository<Person, Long> {
}
