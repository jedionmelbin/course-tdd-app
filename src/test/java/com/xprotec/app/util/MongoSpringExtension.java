package com.xprotec.app.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public class MongoSpringExtension implements BeforeEachCallback, AfterEachCallback {

    private static Path JSON_PATH_FILE = Paths.get("src", "test", "resources", "data");

    private ObjectMapper objectMapper = new ObjectMapper();

    /***
     * This callback method be called before eacj test execution
     * @param extensionContext
     * @throws Exception
     */
    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        extensionContext.getTestMethod().ifPresent(method -> {
            // Load test file using the  annotation argument
            MongoTestDataFile mongoTestDataFile = method.getAnnotation(MongoTestDataFile.class);

            // Load the MongoTemplate to impot test data
            getMongoTemplate(extensionContext).ifPresent(mongoTemplate -> {
                try {
                    List objects = objectMapper.readValue(JSON_PATH_FILE.resolve(mongoTestDataFile.value()).toFile(),
                            objectMapper.getTypeFactory().constructCollectionType(List.class, mongoTestDataFile.classType()));

                    objects.forEach(mongoTemplate::save);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
    }

    private Optional<MongoTemplate> getMongoTemplate(ExtensionContext context) {
        Optional<Class<?>> testClass = context.getTestClass();
        if (testClass.isPresent()) {
            Class<?> c = testClass.get();
            try {
                // Find the getMongoTemplate method on the test name
                Method method = c.getMethod("getMongoTemplate", null);

                Optional<Object> testInstance = context.getTestInstance();
                if (testInstance.isPresent()) {
                    return Optional.of((MongoTemplate) method.invoke(testInstance.get(), null));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }

    /***
     * This callback method will be called after each test execution
     * @param extensionContext
     * @throws Exception
     */
    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
            extensionContext.getTestMethod().ifPresent(method -> {
                //Load the MongoDataFile annotation value from the method
                MongoTestDataFile mongoTestDataFile = method.getAnnotation(MongoTestDataFile.class);

                // Get MongoTemplate  from context to drop the test collecton
                Optional<MongoTemplate> mongoTemplate  =  getMongoTemplate(extensionContext);
                mongoTemplate.ifPresent(t-> t.dropCollection(mongoTestDataFile.colecctionName()));
            });
    }

}
