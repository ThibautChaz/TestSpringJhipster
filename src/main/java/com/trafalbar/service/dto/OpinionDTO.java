package com.trafalbar.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Opinion entity.
 */
public class OpinionDTO implements Serializable {

    private Long id;

    private Integer score;

    @Size(max = 255)
    private String opinion;


    private Long authorId;

    private Long bottleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long personId) {
        this.authorId = personId;
    }

    public Long getBottleId() {
        return bottleId;
    }

    public void setBottleId(Long bottleId) {
        this.bottleId = bottleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OpinionDTO opinionDTO = (OpinionDTO) o;
        if (opinionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), opinionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OpinionDTO{" +
            "id=" + getId() +
            ", score=" + getScore() +
            ", opinion='" + getOpinion() + "'" +
            ", author=" + getAuthorId() +
            ", bottle=" + getBottleId() +
            "}";
    }
}
