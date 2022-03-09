package br.com.api.models.dtos;

import br.com.api.models.Laboratory;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class RegisterLaboratoryDto {

    @NotEmpty(message = "The name is required.")
    @Size(min = 2, max = 100, message = "The length of name must be between 2 and 100 characters.")
    private String name;

    public Laboratory toLaboratory() {
        return new Laboratory()
                .setName(name);
    }
}
