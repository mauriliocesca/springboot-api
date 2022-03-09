package br.com.api.models.dtos;

import br.com.api.models.Property;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class RegisterPropertyDto {

    @NotEmpty(message = "The name is required.")
    @Size(min = 2, max = 100, message = "The length of name must be between 2 and 100 characters.")
    private String name;

    @NotEmpty(message = "The pin is required.")
    @Size(min = 14, max = 14, message = "The lenght of pin must be 14")
    private Integer pin;

    public Property toProperty() {
        return new Property()
                .setName(name)
                .setPin(pin);
    }
}
