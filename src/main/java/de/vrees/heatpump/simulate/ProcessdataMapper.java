package de.vrees.heatpump.simulate;

import de.vrees.heatpump.domain.Processdata;
import de.vrees.heatpump.slaves.beckhoff.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProcessdataMapper {

    Processdata map(Processdata in);

    Processdata map(EL3122 el3122, EL1008 el1008, EL2008 el2008, EL3204_1 el3204_1, EL3064 eL3064, EL1008 el10081, EL3204_2 el3204_2);
}
