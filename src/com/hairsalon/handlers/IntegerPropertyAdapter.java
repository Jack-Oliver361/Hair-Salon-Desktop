/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hairsalon.handlers;

import com.google.gson.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.IntegerProperty;

import java.lang.reflect.Type;

public class IntegerPropertyAdapter implements
        JsonSerializer<IntegerProperty>, 
        JsonDeserializer<IntegerProperty> {
    @Override
    public JsonElement serialize(
            IntegerProperty property, 
            Type type, 
            JsonSerializationContext jsonSerializationContext
    ) {
        return new JsonPrimitive(
                property.getValue()
        );
    }

    @Override
    public IntegerProperty deserialize(
            JsonElement json, 
            Type type, 
            JsonDeserializationContext jsonDeserializationContext
    ) throws JsonParseException {
        return new SimpleIntegerProperty(
                json.getAsJsonPrimitive().getAsInt()
        );
    }
}