package cmms.mme.controller;


import cmms.mme.dto.APIResponse;
import cmms.mme.dto.PageDto;
import cmms.mme.dto.WorkOrderDto;
import cmms.mme.service.work_order.WorkOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javassist.NotFoundException;

import javax.validation.Valid;
import java.util.List;

@RestController @RequestMapping("v1") @RequiredArgsConstructor
public class WorkOrder {

    private final WorkOrderService workOrderService;


    @GetMapping(path = "/work-order")
    public APIResponse<PageDto<WorkOrderDto>> getWorkOrder(@RequestParam(name ="start", required = false) Integer start, @RequestParam(name="size", required = false) Integer size) {

        int st = (start != null) ? start : 0;
        int sz = (size != null) ? size : 10;
        PageDto<WorkOrderDto> work = workOrderService.getWorkOrder(st, sz);
        return new APIResponse<>(work.getSize(), work);

    }

    @GetMapping(path = "/work-order/search")
    public List<WorkOrderDto> getWorkOrderByKeyword(@RequestParam(name="keyword", required = false) String keyword) {
        return workOrderService.getWorkOrderByKey(keyword);
    }

    @GetMapping(path = "/work-order/{id}")
    public WorkOrderDto getWorkOrderById(@PathVariable("id") long id) throws NotFoundException {

        return workOrderService.getWorkOrderById(id);
    }

    @PostMapping(path = "/work-order")
    public WorkOrderDto postWorkOrder(@Valid @RequestBody WorkOrderDto work) {

        return workOrderService.postWorkOrder(work);
    }

    @PutMapping(path = "/work-order/{id}")
    public WorkOrderDto updateWorkOrder(@Valid @RequestBody WorkOrderDto work, @PathVariable("id") long id) throws NotFoundException {
        return workOrderService.updateWorkOrder(work, id);
    }

    @DeleteMapping("/work-order")
    public void deleteWorkOrder(@RequestParam(name = "id") List<Integer> listIDs) throws NotFoundException {

        workOrderService.deleteWorkOrder(listIDs);
    }
}
