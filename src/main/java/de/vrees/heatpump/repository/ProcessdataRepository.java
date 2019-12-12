package de.vrees.heatpump.repository;
import de.vrees.heatpump.domain.Processdata;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Processdata entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcessdataRepository extends JpaRepository<Processdata, Long> {

}
