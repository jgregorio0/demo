package demo;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface ProductMapper {

    @Mapping(target = "id", source = "product.id")
    @Mapping(target = "name", source = "product.name")
    @Mapping(target = "price", source = "product.price")
    @Mapping(target = "description", source = "product.description")
    ProductOutputDto toDto(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "inputDto.name")
    @Mapping(target = "price", source = "inputDto.price")
    @Mapping(target = "description", source = "inputDto.description")
    Product toEntity(ProductInputDto inputDto);
}
