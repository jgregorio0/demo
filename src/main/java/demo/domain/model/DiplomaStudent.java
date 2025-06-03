package demo.domain.model;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DiplomaStudent extends Student {

  private LocalDate diplomaDate;
}
