package demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressJpaRespository extends JpaRepository<Address, Long> {
}
