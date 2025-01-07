package Project40.gladiolen_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TshirtCountDto {
    private String job;
    private String sex;
    private String size;
    private int totalQuantity;
}
