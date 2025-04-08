package it.interno.order.mapper;

import it.interno.common.lib.model.OrderDto;
import it.interno.order.entity.Order;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface OrderMapper {

    @Mappings(
      @Mapping(target = "tsInserimento",
              expression = "java(orderDto.getTsInserimento() != null ? it.interno.common.lib.util.Utility.convertStringToTimestamp(orderDto.getTsInserimento()) : null)")
    )
    Order toEntity(OrderDto orderDto) ;

    @InheritInverseConfiguration
    @Mappings(
      @Mapping(target = "tsInserimento",
              expression = "java(order.getTsInserimento() != null ? it.interno.common.lib.util.Utility.convertTimestampToString(order.getTsInserimento()) : null)")
    )
    OrderDto toDto(Order order) ;

}