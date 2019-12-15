package de.vrees.heatpump.repository;
import de.vrees.heatpump.domain.FailureMessage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FailureMessage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FailureMessageRepository extends JpaRepository<FailureMessage, Long> {

}
