package com.wroclawroutes.routes.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(	name = "tags",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UQ_tag",
                        columnNames = {"name"}
                )
        }
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(min = 3, max = 20)
    private String name;
    @ManyToMany(mappedBy = "tags", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Route> routes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(id, tag.id) && Objects.equals(name, tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
