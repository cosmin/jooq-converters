package com.offbytwo.jooq.converters;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.NullNode;
import org.jooq.Converter;
import org.jooq.JSON;

import java.io.IOException;

public class PostgresJSONJacksonJsonNodeConverter implements Converter<JSON, JsonNode> {
    @Override
    public JsonNode from(JSON t) {
        try {
            return t == null
                    ? NullNode.instance
                    : new ObjectMapper().readTree(t + "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JSON to(JsonNode u) {
        try {
            return u == null || u.equals(NullNode.instance)
                    ? null
                    : JSONB.valueOf(new ObjectMapper().writeValueAsString(u));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Class<JSON> fromType() {
        return JSON.class;
    }

    @Override
    public Class<JsonNode> toType() {
        return JsonNode.class;
    }
};
