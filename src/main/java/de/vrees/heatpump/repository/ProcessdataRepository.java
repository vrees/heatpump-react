package de.vrees.heatpump.repository;

import de.vrees.heatpump.domain.Processdata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data MongoDB repository for the Processdata entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcessdataRepository extends JpaRepository<Processdata, String> {

    Optional<Processdata> findTopByOrderByTimestampDesc();
}
