package cmms.mme.service.work_order;

import cmms.mme.dto.PageDto;
import cmms.mme.dto.UserDto;
import cmms.mme.dto.WorkOrderDto;
import cmms.mme.entity.*;
import cmms.mme.mapper.AssetMapper;
import cmms.mme.mapper.UserMapper;
import cmms.mme.mapper.WorkOrderMapper;
import cmms.mme.repository.WorkOrderRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class WorkOrderServiceImpl implements WorkOrderService {

    private final WorkOrderRepository workOrderRepository;

    private final static WorkOrderMapper workOrderMapper = Mappers.getMapper(WorkOrderMapper.class);
    private final static UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    private final static AssetMapper assetMapper = Mappers.getMapper(AssetMapper.class);



    @Override
    public PageDto<WorkOrderDto> getWorkOrder(int start, int size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        AppUser user = (AppUser) authentication.getPrincipal();
        List<AppRole> role = new ArrayList<>(((AppUser) authentication.getPrincipal()).getRole());
        if((Objects.equals(role.get(0).getAuthority(), "ROLE_LIMITED-TECHNICIAN"))) {
            return workOrderMapper.
                    workOrderEntityToDtoPage(workOrderRepository.findWorkOrderByAssignee(user.getId(),PageRequest.of( start,  size)));
        }
        return workOrderMapper.workOrderEntityToDtoPage(workOrderRepository.findAll(PageRequest.of( start,  size)));
    }

    @Override
    public WorkOrderDto getWorkOrderById(long id) throws NotFoundException {
        return workOrderMapper.workOrderEntityToDto(workOrderRepository.findById(id).orElseThrow(() -> new NotFoundException("Work Order Not Found")));
    }

    @Override
    public WorkOrderDto postWorkOrder(WorkOrderDto work) {
        System.out.println("work = " + work);
        WorkOrder wo = workOrderMapper.workOrderDtoToEntity(work);
        System.out.println("wo = " + wo);
        return workOrderMapper.workOrderEntityToDto(workOrderRepository.save(wo));
    }

    @Transactional @Override
    public WorkOrderDto updateWorkOrder(WorkOrderDto work,long id) throws NotFoundException {

        WorkOrder wo = workOrderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Work Order not found"));
        System.out.println(work);
        String title = work.getTitle();
        String description = work.getDescription();
        String status = work.getStatus();
        String priority = work.getPriority();
        LocalDateTime startOn = work.getStartOn();
        List<UserDto> userDtos = work.getAssignee();
        short duration = work.getEsDuration();
        Long asset = work.getAsset() != null ? work.getAsset().getId() : null;
        Long woAsset = wo.getAsset() != null ? wo.getAsset().getId() : 0;

        System.out.println(title+" " +description+" " + status +" " + priority +" " + startOn +" " + userDtos +" " + duration);
        if(title != null &&
            title.length() >= 15 &&
                !Objects.equals(title, wo.getTitle())
        ) {
            wo.setTitle(title);
        }

        if(description != null &&
                description.length() >= 50 &&
                !Objects.equals(description, wo.getDescription())
        ) {
            wo.setDescription(description);
        }

        if(status != null &&
                !Objects.equals(status, wo.getStatus())
        ) {
            if(Objects.equals(status, "Complete")) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                if (authentication == null || !authentication.isAuthenticated()) {
                    return null;
                }
                wo.setCompletedBy(((AppUser) authentication.getPrincipal()));
                wo.setCompletedAt(LocalDateTime.now());
            }
            wo.setStatus(Status.valueOf(status));

        }

        if(priority != null &&
                !Objects.equals(priority, wo.getPriority())
        ) {
            wo.setPriority(Priority.valueOf(priority));
        }

        if(startOn != null &&
        !Objects.equals(startOn, wo.getStartOn())) {
            System.out.println("startOn = " + startOn);
            wo.setStartOn(startOn);
        }

        if(userDtos != null
        ) {
            wo.setAssignee(userMapper.userDtoToEntityList(userDtos));
        }

        if(duration > 0 &&
                !Objects.equals(duration, wo.getEsDuration())
        ) {
            wo.setEsDuration(duration);
        }

        if(asset != null && !asset.equals(woAsset)) {
            wo.setAsset(assetMapper.assetDtoToEntity(work.getAsset()));
        }

        if(asset != null && asset == -1) {
            wo.setAsset(null);
        }
        return workOrderMapper.workOrderEntityToDto(wo);
    }

    @Override
    public void deleteWorkOrder(List<Integer> IDs) throws NotFoundException {
        for (long id:IDs) {
            WorkOrder wo = workOrderRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Work order not found"));
            workOrderRepository.delete(wo);
        }
    }

    @Override
    public List<WorkOrderDto> getWorkOrderByKey(String keyword) {
        return workOrderMapper.workOrderEntityToDtoList(workOrderRepository.search(keyword));
    }




}
