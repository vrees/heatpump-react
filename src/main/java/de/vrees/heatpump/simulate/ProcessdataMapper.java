package de.vrees.heatpump.simulate;

import de.vrees.heatpump.domain.Processdata;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProcessdataMapper {

    Processdata map(Processdata in);
}
