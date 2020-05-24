package mw.ars.commons.model;

import lombok.Value;

import java.util.UUID;
@Value
public class CustomerId{
    private UUID id;

    private CustomerId(UUID id){
        this.id=id;
    }

    public static CustomerId of(UUID id){
        return new CustomerId(id);
    }
}
