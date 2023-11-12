package jobza.recommend.repository;

import jobza.recommend.entity.Employment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JobRepository extends MongoRepository<Employment, String> {

    Optional<Employment> findById(String id);
}
