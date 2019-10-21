package com.tomdaly.hotel.data.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "PROFANITY")
public class Profanity {
  @Id
  @Column(name = "PROFANITY_ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "WORD")
  private final String word;

  public Profanity() {
    this.word = "";
  }

  public Profanity(String word) {
    this.word = word;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getWord() {
    return word;
  }

  @Override
  public String toString() {
    return "Profanity{" + "id=" + id + ", word='" + word + '\'' + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Profanity)) return false;
    Profanity that = (Profanity) o;
    return Objects.equals(word, that.word);
  }

  @Override
  public int hashCode() {
    return Objects.hash(word);
  }
}
