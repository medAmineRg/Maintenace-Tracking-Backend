package cmms.mme.dto;

import cmms.mme.entity.Status;
import lombok.Data;

@Data
public class WOStatus {
    private Status status;
    private Long nbr;

    public WOStatus(Status status, Long nbr) {
        this.status = status;
        this.nbr = nbr;
    }

    @Override
    public String toString() {
        return "WOStatus{" +
                "status=" + status +
                ", nbr=" + nbr +
                '}';
    }
}
