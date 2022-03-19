package br.edu.ifpb.infra.jsf.validator;

import br.edu.ifpb.domain.CPF;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@FacesValidator(value = "validator.data")
public class ValidatorData implements Validator {
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
        LocalDate dataVerificada = (LocalDate) value;
        LocalDate hoje = LocalDate.now();
        if(dataVerificada.compareTo(hoje) <= 0){
            return;
        }

        throw  new ValidatorException(
                new FacesMessage("Data inválida")
        );

    }
}
