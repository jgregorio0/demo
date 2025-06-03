package demo.persistence.repository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import demo.api.StudentMapper;
import demo.domain.StudentRepository;
import demo.domain.exception.DomainException;
import demo.domain.model.DiplomaStudent;
import demo.domain.model.ElearningStudent;
import demo.domain.model.Student;
import demo.persistence.model.StudentEntity;
import demo.persistence.projection.OrderDiplomaStudentProjection;
import demo.persistence.projection.OrderElarningStudentProjection;
import demo.persistence.projection.OrderStudentProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StudentRepositoryImpl implements StudentRepository {

  private final StudentJpaRepository studentJpaRepository;

  @Override
  public Map<Long, List<Student>> readOrderIdWithStudentsByOrderIds(final Set<Long> ids) throws DomainException {
    final List<OrderStudentProjection> orderStudents = studentJpaRepository.findOrderStudentByOrderIds(ids);
    return orderStudents.stream()
                        .collect(Collectors.groupingBy(
                            OrderStudentProjection::getOrderId,
                            orderStudentCollector));
  }

  @Override
  public Map<Long, List<ElearningStudent>> readOrderIdWithElearningStudentsByOrderIds(final Set<Long> ids) throws DomainException {
    final List<OrderElarningStudentProjection> orderStudents = studentJpaRepository.findOrderElearningStudentByOrderIds(ids);
    return orderStudents.stream()
                        .collect(Collectors.groupingBy(
                            OrderElarningStudentProjection::getOrderId,
                            orderElearningStudentCollector));
  }

  @Override
  public Map<Long, List<DiplomaStudent>> readOrderIdWithDiplomaStudentsByOrderIds(final Set<Long> ids) throws DomainException {
    final List<OrderDiplomaStudentProjection> orderStudents = studentJpaRepository.findOrderDiplomaStudentByOrderIds(ids);
    return orderStudents.stream()
                        .collect(Collectors.groupingBy(
                            OrderDiplomaStudentProjection::getOrderId,
                            orderDiplomaStudentCollector));
  }

  private final Collector<OrderStudentProjection, ?, List<Student>> orderStudentCollector = Collectors.mapping(
      os -> StudentMapper.INSTANCE.mapStudentEntityToStudent(os.getStudent()),
      Collectors.toList());

  private final Collector<OrderElarningStudentProjection, ?, List<ElearningStudent>> orderElearningStudentCollector = Collectors.mapping(
      os -> StudentMapper.INSTANCE.mapStudentEntityToElearningStudent(os.getStudent(), os.getElearning()),
      Collectors.toList());

  private final Collector<OrderDiplomaStudentProjection, ?, List<DiplomaStudent>> orderDiplomaStudentCollector = Collectors.mapping(
      os -> StudentMapper.INSTANCE.mapStudentEntityToDiplomaStudent(os.getStudent(), os.getDiploma()),
      Collectors.toList());
}
