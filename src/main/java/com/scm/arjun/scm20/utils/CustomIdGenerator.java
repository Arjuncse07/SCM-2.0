package com.scm.arjun.scm20.utils;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.util.UUID;

public class CustomIdGenerator  implements IdentifierGenerator {


    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) {
        return "A"+ UUID.randomUUID().toString().substring(0,7);
    }
}
