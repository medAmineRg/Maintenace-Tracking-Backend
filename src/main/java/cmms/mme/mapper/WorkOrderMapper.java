package cmms.mme.mapper;

import cmms.mme.dto.PageDto;
import cmms.mme.dto.UserDto;
import cmms.mme.dto.WorkOrderDto;
import cmms.mme.entity.AppUser;
import cmms.mme.entity.WorkOrder;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper
public interface WorkOrderMapper {

    List<WorkOrderDto> workOrderEntityToDtoList(List<WorkOrder> workOrders);
    List<WorkOrder> workOrderDtoToEntityList(List<WorkOrderDto> workOrders);
    PageDto<WorkOrderDto> workOrderEntityToDtoPage(Page<WorkOrder> workOrders);
    WorkOrderDto workOrderEntityToDto(WorkOrder workOrders);
    WorkOrder workOrderDtoToEntity(WorkOrderDto workOrders);
}
