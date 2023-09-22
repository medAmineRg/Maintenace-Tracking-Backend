package cmms.mme.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnalyticsDto{

    private Long technicianCount;
    private Long workOrderCount;
    private Long completedWoCount;
    private Long assetCount;
    private List<WOStatus> woStatus;
    private List<WOByMonth> woByMonth;
    private int woForToday;
    private int woForThisWeek;
    private int woForThisMonth;


    @Override
    public String toString() {
        return "AnalyticsDto{" +
                "technicianCount=" + technicianCount +
                ", workOrderCount=" + workOrderCount +
                ", completedWoCount=" + completedWoCount +
                ", assetCount=" + assetCount +
                ", woStatus=" + woStatus +
                ", woByMonth=" + woByMonth +
                '}';
    }
}
