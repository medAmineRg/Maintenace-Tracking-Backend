package cmms.mme.dto;

import lombok.Data;
@Data
public class WOByMonth {

    private int month;
    private long nbr;

    public WOByMonth(int month, long nbr) {
        this.month = month;
        this.nbr = nbr;
    }

    @Override
    public String toString() {
        return "WOByMonth{" +
                "month=" + month +
                ", nbr=" + nbr +
                '}';
    }
}
