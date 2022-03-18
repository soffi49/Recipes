package com.recipes.backend.repo.domain;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tag")
public class TagDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tag_id")
    private long tagId;

    @NotNull
    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "tagSet")
    private Set<RecipeDTO> recipeSet;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagDTO tagDTO = (TagDTO) o;
        return name.equals(tagDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
