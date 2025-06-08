package demo.domain.model;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder(toBuilder = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Group { //TODO JG devolver Group sin dependencias

  private Long id;

  private String number;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Group group = (Group) o;
    return Objects.equals(id, group.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
