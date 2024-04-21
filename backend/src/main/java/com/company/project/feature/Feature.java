package com.company.project.feature;

import com.company.project.user.User;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "features")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Feature {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    @NotBlank
    private String name;

    @Builder.Default
    @ManyToMany(mappedBy = "features")
    private Set<User> users = new HashSet<>();

    public Feature(String name) {
        this.name = name;
    }
}
