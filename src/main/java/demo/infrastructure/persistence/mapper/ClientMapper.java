package demo.infrastructure.persistence.mapper;

import demo.domain.model.Client;
import demo.infrastructure.persistence.model.ClientEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Objects;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id")
    @Mapping(target = "name")
    @Mapping(target = "cif")
    Client toDto(ClientEntity entity);

    default ClientEntity toEntity(Long clientId) {
        if (Objects.isNull(clientId)) {
            return null;
        }
        return ClientEntity.builder()
                .id(clientId)
                .build();
    }
}
