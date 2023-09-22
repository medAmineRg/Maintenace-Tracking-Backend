package cmms.mme.service.work_order;

import cmms.mme.dto.PageDto;
import cmms.mme.dto.WorkOrderDto;
import javassist.NotFoundException;

import java.util.List;

public interface WorkOrderService {

    PageDto<WorkOrderDto> getWorkOrder(int start, int size);
    WorkOrderDto getWorkOrderById(long id) throws NotFoundException, javassist.NotFoundException;
    WorkOrderDto postWorkOrder(WorkOrderDto work);
    WorkOrderDto updateWorkOrder(WorkOrderDto work, long id) throws NotFoundException, javassist.NotFoundException;
    void deleteWorkOrder(List<Integer> IDs) throws NotFoundException, javassist.NotFoundException;

    List<WorkOrderDto> getWorkOrderByKey(String keyword);
}
