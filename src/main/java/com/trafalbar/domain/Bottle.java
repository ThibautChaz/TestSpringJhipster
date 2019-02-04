package com.trafalbar.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Bottle.
 */
@Entity
@Table(name = "bottle")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Bottle implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Size(max = 255)
    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "age")
    private Integer age;

    @Column(name = "jhi_degree")
    private Double degree;

    @Lob
    @Column(name = "picture")
    private byte[] picture;

    @Column(name = "picture_content_type")
    private String pictureContentType;

    @Size(max = 100)
    @Column(name = "origin", length = 100)
    private String origin;

    @Size(max = 100)
    @Column(name = "mouth", length = 100)
    private String mouth;

    @Size(max = 100)
    @Column(name = "nose", length = 100)
    private String nose;

    @Size(max = 100)
    @Column(name = "raw_material", length = 100)
    private String rawMaterial;

    @OneToMany(mappedBy = "bottle")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Opinion> opinions = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Bottle name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Bottle description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAge() {
        return age;
    }

    public Bottle age(Integer age) {
        this.age = age;
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getDegree() {
        return degree;
    }

    public Bottle degree(Double degree) {
        this.degree = degree;
        return this;
    }

    public void setDegree(Double degree) {
        this.degree = degree;
    }

    public byte[] getPicture() {
        return picture;
    }

    public Bottle picture(byte[] picture) {
        this.picture = picture;
        return this;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getPictureContentType() {
        return pictureContentType;
    }

    public Bottle pictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
        return this;
    }

    public void setPictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
    }

    public String getOrigin() {
        return origin;
    }

    public Bottle origin(String origin) {
        this.origin = origin;
        return this;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getMouth() {
        return mouth;
    }

    public Bottle mouth(String mouth) {
        this.mouth = mouth;
        return this;
    }

    public void setMouth(String mouth) {
        this.mouth = mouth;
    }

    public String getNose() {
        return nose;
    }

    public Bottle nose(String nose) {
        this.nose = nose;
        return this;
    }

    public void setNose(String nose) {
        this.nose = nose;
    }

    public String getRawMaterial() {
        return rawMaterial;
    }

    public Bottle rawMaterial(String rawMaterial) {
        this.rawMaterial = rawMaterial;
        return this;
    }

    public void setRawMaterial(String rawMaterial) {
        this.rawMaterial = rawMaterial;
    }

    public Set<Opinion> getOpinions() {
        return opinions;
    }

    public Bottle opinions(Set<Opinion> opinions) {
        this.opinions = opinions;
        return this;
    }

    public Bottle addOpinion(Opinion opinion) {
        this.opinions.add(opinion);
        opinion.setBottle(this);
        return this;
    }

    public Bottle removeOpinion(Opinion opinion) {
        this.opinions.remove(opinion);
        opinion.setBottle(null);
        return this;
    }

    public void setOpinions(Set<Opinion> opinions) {
        this.opinions = opinions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Bottle bottle = (Bottle) o;
        if (bottle.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bottle.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Bottle{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", age=" + getAge() +
            ", degree=" + getDegree() +
            ", picture='" + getPicture() + "'" +
            ", pictureContentType='" + getPictureContentType() + "'" +
            ", origin='" + getOrigin() + "'" +
            ", mouth='" + getMouth() + "'" +
            ", nose='" + getNose() + "'" +
            ", rawMaterial='" + getRawMaterial() + "'" +
            "}";
    }
}
