package jobza.recommend.repository;

import jobza.recommend.entity.Employment;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JobRepository extends MongoRepository<Employment, String> {

//    @Query("{ 'wantedAuthNo' : ?0 }")
//    Optional<Employment> findByWantedAuthNo(String wantedAuthNo);
    Optional<Employment> findByWantedAuthNo(String wantedAuthNo);

//    @Query("{ '_id' : ?0 }")
    Optional<Employment> findById(String id);
}
