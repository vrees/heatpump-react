package de.vrees.heatpump.repository;

import de.vrees.heatpump.domain.Processdata;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data MongoDB repository for the Processdata entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcessdataRepository extends MongoRepository<Processdata, String> {

    Optional<Processdata> findTopByOrderByTimestampDesc();
}
