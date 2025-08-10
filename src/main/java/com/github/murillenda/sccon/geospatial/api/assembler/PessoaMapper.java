package com.github.murillenda.sccon.geospatial.api.assembler;

import com.github.murillenda.sccon.geospatial.api.dto.input.PessoaPatchInputDTO;
import com.github.murillenda.sccon.geospatial.api.dto.input.PessoaPostInputDTO;
import com.github.murillenda.sccon.geospatial.api.dto.input.PessoaPutInputDTO;
import com.github.murillenda.sccon.geospatial.api.dto.output.PessoaOutputDTO;
import com.github.murillenda.sccon.geospatial.domain.model.Pessoa;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PessoaMapper {

    Pessoa toDomainObject(PessoaPostInputDTO inputDTO);
    Pessoa toDomainObject(PessoaPutInputDTO inputDTO);

    PessoaOutputDTO toOutputDTO(Pessoa pessoa);

    List<PessoaOutputDTO> toOutputDTOList(List<Pessoa> pessoas);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void atualizarPessoaFromPatchDTO(PessoaPatchInputDTO patchDTO, @MappingTarget Pessoa pessoa);

}
