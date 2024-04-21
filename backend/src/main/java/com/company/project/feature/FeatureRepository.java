package com.company.project.feature;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Long> {
    Optional<Feature> findByName(String name);

    Feature findOneByName(String name);

    boolean existsByName(String name);
}
