package cat.udl.eps.entsoftarch.bidirectionalassociations.repository;

import cat.udl.eps.entsoftarch.bidirectionalassociations.domain.Address;
import cat.udl.eps.entsoftarch.bidirectionalassociations.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByAddress(@Param("address") Address address);
}
