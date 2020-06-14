package com.codenation.curso.central.error.models;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QLog is a Querydsl query type for Log
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLog extends EntityPathBase<Log> {

    private static final long serialVersionUID = 1859612478L;

    public static final QLog log = new QLog("log");

    public final DatePath<java.time.LocalDate> date = createDate("date", java.time.LocalDate.class);

    public final StringPath description = createString("description");

    public final EnumPath<ErrorLevelsEnum> errorLevel = createEnum("errorLevel", ErrorLevelsEnum.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath logDoEvento = createString("logDoEvento");

    public final StringPath origin = createString("origin");

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    public QLog(String variable) {
        super(Log.class, forVariable(variable));
    }

    public QLog(Path<? extends Log> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLog(PathMetadata metadata) {
        super(Log.class, metadata);
    }

}

