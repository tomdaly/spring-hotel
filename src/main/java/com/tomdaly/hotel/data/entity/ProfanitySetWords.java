package com.tomdaly.hotel.data.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "PROFANITYSETWORDS")
public class ProfanitySetWords {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "PROFANITYSETWORDS_ID")
  private long id;

  @Column(name = "PROFANITYSET_ID")
  private final long profanitySetId;

  @Column(name = "PROFANITY_ID")
  private final long profanityId;

  public ProfanitySetWords() {
    this.profanitySetId = 0L;
    this.profanityId = 0L;
  }

  public ProfanitySetWords(long profanitySetId, long profanityId) {
    this.profanitySetId = profanitySetId;
    this.profanityId = profanityId;
  }

  public long getId() {
    return id;
  }

  public long getProfanitySetId() {
    return profanitySetId;
  }

  public long getProfanityId() {
    return profanityId;
  }

  @Override
  public String toString() {
    return "ProfanitySetWords{"
        + "profanitySetId="
        + profanitySetId
        + ", profanityId="
        + profanityId
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ProfanitySetWords)) return false;
    ProfanitySetWords that = (ProfanitySetWords) o;
    return profanitySetId == that.profanitySetId && profanityId == that.profanityId;
  }

  @Override
  public int hashCode() {
    return Objects.hash(profanitySetId, profanityId);
  }
}
