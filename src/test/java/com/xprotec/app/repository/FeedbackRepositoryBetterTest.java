package com.xprotec.app.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xprotec.app.document.Feedback;
import com.xprotec.app.util.MongoSpringExtension;
import com.xprotec.app.util.MongoTestDataFile;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@DataMongoTest
@ExtendWith(MongoSpringExtension.class)
public class FeedbackRepositoryBetterTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    public MongoTemplate getMongoTemplate(){
        return  mongoTemplate;
    }
    private static File JSON_DATA = Paths.get("src", "test", "resources", "data","data.json").toFile();

    //Course 15

    @Autowired
    private FeedbackRepository feedbackRepository;


    @BeforeEach
    public void beforeAch() throws IOException {
        Feedback[] feedbacks = new ObjectMapper().readValue(JSON_DATA,Feedback[].class);

        Arrays.stream(feedbacks).forEach(mongoTemplate::save);
    }

    @AfterEach
    public void afterEach() {
        mongoTemplate.dropCollection("Feedback");
    }

    @Test
    @DisplayName("Find all feedback with better_data.json dataset")
    @MongoTestDataFile(value = "better_data.json", classType = Feedback.class, colecctionName = "Feedback")
    public void testGetFeedbackWithNewDataset(){
        //When
        List<Feedback> feedbackList = feedbackRepository.findAll();

        //Then
        Assertions.assertEquals(6, feedbackList.size());
    }
}
