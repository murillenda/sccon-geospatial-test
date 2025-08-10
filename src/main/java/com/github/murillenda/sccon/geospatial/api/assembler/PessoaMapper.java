package com.github.murillenda.sccon.geospatial.api.assembler;

import com.github.murillenda.sccon.geospatial.api.dto.input.PessoaPostInputDTO;
import com.github.murillenda.sccon.geospatial.api.dto.output.PessoaOutputDTO;
import com.github.murillenda.sccon.geospatial.domain.model.Pessoa;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PessoaMapper {

    Pessoa toDomainObject(PessoaPostInputDTO inputDTO);
    PessoaOutputDTO toOutputDTO(Pessoa pessoa);

    List<PessoaOutputDTO> toOutputDTOList(List<Pessoa> pessoas);

}
