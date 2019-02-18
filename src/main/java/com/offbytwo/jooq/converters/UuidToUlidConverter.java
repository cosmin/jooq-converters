package com.offbytwo.jooq.converters;


import com.offbytwo.ulid.ULID;
import org.jooq.Converter;

import java.util.UUID;

public class UuidToUlidConverter implements Converter<UUID, ULID.Value> {
    @Override
    public ULID.Value from(UUID uuid) {
        return ULID.fromUUID(uuid);
    }

    @Override
    public UUID to(ULID.Value value) {
        return value.toUUID();
    }

    @Override
    public Class<UUID> fromType() {
        return UUID.class;
    }

    @Override
    public Class<ULID.Value> toType() {
        return ULID.Value.class;
    }
}
