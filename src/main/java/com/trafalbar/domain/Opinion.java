package com.trafalbar.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Opinion.
 */
@Entity
@Table(name = "opinion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Opinion implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "score")
    private Integer score;

    @Size(max = 255)
    @Column(name = "opinion", length = 255)
    private String opinion;

    @ManyToOne
    @JsonIgnoreProperties("people")
    private Person author;

    @ManyToOne
    @JsonIgnoreProperties("bottles")
    private Bottle bottle;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public Opinion score(Integer score) {
        this.score = score;
        return this;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getOpinion() {
        return opinion;
    }

    public Opinion opinion(String opinion) {
        this.opinion = opinion;
        return this;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public Person getAuthor() {
        return author;
    }

    public Opinion author(Person person) {
        this.author = person;
        return this;
    }

    public void setAuthor(Person person) {
        this.author = person;
    }

    public Bottle getBottle() {
        return bottle;
    }

    public Opinion bottle(Bottle bottle) {
        this.bottle = bottle;
        return this;
    }

    public void setBottle(Bottle bottle) {
        this.bottle = bottle;
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
        Opinion opinion = (Opinion) o;
        if (opinion.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), opinion.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Opinion{" +
            "id=" + getId() +
            ", score=" + getScore() +
            ", opinion='" + getOpinion() + "'" +
            "}";
    }
}
