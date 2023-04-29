package com.xprotec.app.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MongoTestDataFile {

    /**
     * Name of the mongoDB JSON test file with extension
     * @return
     */
    String value();

    /**
     * Class type of the objects stored in the MongoDB test file
     */
    Class classType();

    /**
     * Name of the MongoDB collection for the test objects
     */
    String colecctionName();
}
