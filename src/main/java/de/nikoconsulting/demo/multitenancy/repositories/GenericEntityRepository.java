package de.nikoconsulting.demo.multitenancy.repositories;

import de.nikoconsulting.demo.multitenancy.model.GenericEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenericEntityRepository extends JpaRepository<GenericEntity, Long> {

}