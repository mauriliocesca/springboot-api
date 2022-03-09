package br.com.api.models.dtos;

import br.com.api.constraints.CompareDate;
import br.com.api.models.Contract;
import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

@Data
@CompareDate(before = "initialDate", after = "finalDate", message = "The initial date must be lower than the final date")
public class CreateContractDto {

    @NotNull(message = "The initial date is required.")
    @FutureOrPresent(message = "The initial date must be today or in the future.")
    private Date finalDate;

    @NotNull(message = "The final date is required.")
    @FutureOrPresent(message = "The final date must be today or in the future.")
    private Date initialDate;

    @NotNull(message = "The user's Id is required.")
    @Positive(message = "The user's Id must be greater than 0")
    private Integer userId;

    @NotNull(message = "The property's Id is required.")
    @Positive(message = "The property's Id must be greater than 0")
    private Integer propertyId;

    @NotNull(message = "The laboratory's Id is required.")
    @Positive(message = "The laboratory's Id must be greater than 0")
    private Integer laboratoryId;

    private String note;

    public Contract toContract() {
        return new Contract()
                .setFinalDate(finalDate)
                .setInitialDate(initialDate)
                .setNote(note);
    }
}
