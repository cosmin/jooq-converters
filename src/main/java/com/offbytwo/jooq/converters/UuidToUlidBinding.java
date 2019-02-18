package com.offbytwo.jooq.converters;

import com.offbytwo.ulid.ULID;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Types;
import java.util.UUID;

import static org.jooq.tools.Convert.convert;

public class UuidToUlidBinding implements Binding<UUID, ULID.Value> {
    @Override
    public Converter<UUID, ULID.Value> converter() {
        return new UuidToUlidConverter();
    }

    @Override
    public void sql(BindingSQLContext<ULID.Value> ctx) throws SQLException {
        ctx.render().visit(DSL.val(ctx.convert(converter()).value()));
    }

    @Override
    public void register(BindingRegisterContext<ULID.Value> ctx) throws SQLException {
        ctx.statement().registerOutParameter(ctx.index(), Types.VARCHAR);
    }

    @Override
    public void set(BindingSetStatementContext<ULID.Value> ctx) throws SQLException {
        ctx.statement().setObject(
                ctx.index(),
                convert(ctx.convert(converter()).value(), UUID.class));
    }

    @Override
    public void get(BindingGetResultSetContext<ULID.Value> ctx) throws SQLException {
        ctx.convert(converter()).value(ctx.resultSet().getObject(ctx.index(), UUID.class));
    }

    @Override
    public void get(BindingGetStatementContext<ULID.Value> ctx) throws SQLException {
        ctx.convert(converter()).value(ctx.statement().getObject(ctx.index(), UUID.class));
    }

    // The below methods aren't needed in PostgreSQL:

    @Override
    public void set(BindingSetSQLOutputContext<ULID.Value> ctx) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void get(BindingGetSQLInputContext<ULID.Value> ctx) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
}
