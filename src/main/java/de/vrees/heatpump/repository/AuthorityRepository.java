package de.vrees.heatpump.repository;

import de.vrees.heatpump.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data MongoDB repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
