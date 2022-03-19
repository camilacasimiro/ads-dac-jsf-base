package br.edu.ifpb.infra.jsf.converter;

import java.time.LocalDate;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value="converter.data", forClass=LocalDate.class)
public class ConverterData implements Converter{

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if(value == null) return null;
        LocalDate dataDeNascimento = LocalDate.of(
                Integer.parseInt(value.substring(0, 4)), //ano
                Integer.parseInt(value.substring(5, 7)), //mês
                Integer.parseInt(value.substring(8, 10)) //dia
        );
        LocalDate dataAtual = LocalDate.now();
        System.out.println(dataAtual);
        return dataDeNascimento;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
        if (value == null) return null;
        LocalDate dataDeNascimento = (LocalDate) value;
        String barra;
        if (dataDeNascimento.getMonthValue() >= 10) {
            barra = "/";
        } else {
            barra = "/0";
        }

        return dataDeNascimento.getYear() + barra + dataDeNascimento.getMonthValue()+ "/" + dataDeNascimento.getDayOfMonth();
    }

}