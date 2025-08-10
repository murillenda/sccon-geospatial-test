package com.github.murillenda.sccon.geospatial.api.converter;

import com.github.murillenda.sccon.geospatial.domain.model.FormatoSalario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StringToFormatoSalarioConverter implements Converter<String, FormatoSalario> {

    @Override
    public FormatoSalario convert(String source) {
        try {
            return FormatoSalario.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.error("Formato de salário incorreto, valor do output deve ser 'full' ou 'min'");
            // Captura MethodArgumentTypeMismatchException no ConverterService com o retorno "null"
            return null;
        }
    }

}
