package com.github.murillenda.sccon.geospatial.api.converter;

import com.github.murillenda.sccon.geospatial.domain.model.FormatoIdade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StringToFormatoIdadeConverter implements Converter<String, FormatoIdade> {

    @Override
    public FormatoIdade convert(String source) {
        try {
            return FormatoIdade.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.warn("Formato de idade incorreto, valor do output deve ser 'days', 'months' ou 'years'");
            // Captura MethodArgumentTypeMismatchException no ConverterService com o retorno "null"
            return null;
        }
    }
}
