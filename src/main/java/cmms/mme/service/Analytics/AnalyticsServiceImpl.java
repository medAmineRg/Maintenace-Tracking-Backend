package cmms.mme.service.Analytics;

import cmms.mme.dto.AnalyticsDto;
import cmms.mme.dto.WOByMonth;
import cmms.mme.dto.WOStatus;
import cmms.mme.repository.AssetRepository;
import cmms.mme.repository.UserRepository;
import cmms.mme.repository.WorkOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service @RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {
    private final WorkOrderRepository workOrderRepo;
    private final UserRepository userRepo;
    private final AssetRepository assetRepo;


    @Override
    public AnalyticsDto getAnalytics() {

        Long woCount = workOrderRepo.count();
        Long completedWoCount = workOrderRepo.completedWoCount();
        Long assetCount = assetRepo.count();
        Long userCount = userRepo.count();
        List<WOStatus> woStatus = workOrderRepo.woStatus();
        List<WOByMonth> woByMonth = workOrderRepo.woByMonth();
        int woForToday = workOrderRepo.woForToday();
        int woForThisWeek = workOrderRepo.woForThisWeek();
        int woForThisMonth = workOrderRepo.woForThisMonth();
        AnalyticsDto analyticsDto = new AnalyticsDto(userCount, woCount, completedWoCount, assetCount, woStatus, woByMonth, woForToday, woForThisWeek, woForThisMonth);
        return analyticsDto;
    }
}
