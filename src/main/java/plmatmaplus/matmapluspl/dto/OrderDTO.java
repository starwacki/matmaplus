package plmatmaplus.matmapluspl.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderDTO {

    private double cartTotal;

    private boolean isActivePromoCode;

    private double promoValue;

    private double endCartTotal;
}
