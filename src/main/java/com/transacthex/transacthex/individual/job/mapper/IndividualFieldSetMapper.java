package com.transacthex.transacthex.individual.job.mapper;

import com.transacthex.transacthex.individual.job.model.IndividualDto;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import java.time.LocalDate;
import java.util.Date;

public class IndividualFieldSetMapper implements FieldSetMapper<IndividualDto> {
    @Override
    public IndividualDto mapFieldSet(FieldSet fieldSet) throws BindException {
        Long id = fieldSet.readLong(0);
        String firstName = fieldSet.readString(1);
        String lastName = fieldSet.readString(2);
        String email = fieldSet.readString(3);
        String date = fieldSet.readString(4);
        String status = fieldSet.readString(5);
        return new IndividualDto(id,firstName,lastName,email, date,status);
    }
}
