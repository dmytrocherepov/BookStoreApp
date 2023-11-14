package com.example.bookstoreapp.mapper;

import com.example.bookstoreapp.config.MapperConfig;
import com.example.bookstoreapp.dto.Order.OrderDto;
import com.example.bookstoreapp.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class , uses = OrderItemMapper.class)
public interface OrderMapper {
    @Mapping(source = "user.id" , target = "userId")
    OrderDto toDto(Order order);
}
