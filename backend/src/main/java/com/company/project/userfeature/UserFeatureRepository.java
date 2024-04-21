package com.company.project.userfeature;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFeatureRepository extends JpaRepository<UserFeature, Long> {

    @Query(
            "SELECT uf FROM UserFeature uf JOIN uf.user u JOIN uf.feature f WHERE u.email = :email"
                    + " AND f.name = :featureName")
    Optional<UserFeature> findByUserEmailAndFeatureName(
            @Param("email") String email, @Param("featureName") String featureName);

    @Query(
            "SELECT COUNT(uf) > 0 FROM UserFeature uf JOIN uf.user u JOIN uf.feature f WHERE"
                    + " u.email = :email AND f.name = :featureName")
    boolean existsByUserEmailAndFeatureName(
            @Param("email") String email, @Param("featureName") String featureName);
}
