package com.santosh.quizapp.service;

import com.santosh.quizapp.dao.QuestionDao;
import com.santosh.quizapp.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class QuestionService {
    private static final Logger logger = LoggerFactory.getLogger(QuestionService.class);

    @Autowired
    QuestionDao questionDao;

    public static List<Question> getHardCodedQuestions() {
        List<Question> questions = new ArrayList<>();

        // Java Questions
        questions.add(createQuestion("What is the default value of a boolean variable in Java?",
                "true", "false", "0", "null", "false", "Java", "Easy"));
        questions.add(createQuestion("Which of the following is a valid declaration of a float variable in Java?",
                "float f = 10.0;", "float f = 10;", "float f = 10.0f;", "float f = \"10.0\";", "float f = 10.0f;", "Java", "Easy"));
        questions.add(createQuestion("What is the output of System.out.println(5 + 5 + \"5\")?",
                "105", "55", "510", "10", "105", "Java", "Medium"));
        questions.add(createQuestion("Which keyword is used to prevent method overriding in Java?",
                "final", "static", "super", "private", "final", "Java", "Medium"));
        questions.add(createQuestion("How do you declare an array of strings in Java?",
                "String[] arr;", "String arr[];", "Both a and b", "None of the above", "Both a and b", "Java", "High"));
        questions.add(createQuestion("What is the result of 10 / 4 in Java?",
                "2.5", "2", "2.0", "2.4", "2", "Java", "High"));

        // Spring Questions
        questions.add(createQuestion("What is the default scope of a Spring bean?",
                "Singleton", "Prototype", "Request", "Session", "Singleton", "Spring", "Easy"));
        questions.add(createQuestion("Which annotation is used to create a Spring Boot application?",
                "@SpringBootApplication", "@Configuration", "@EnableAutoConfiguration", "@Component", "@SpringBootApplication", "Spring", "Easy"));
        questions.add(createQuestion("How can you inject a bean into another bean in Spring?",
                "Using @Autowired", "Using @Inject", "Using @Resource", "All of the above", "All of the above", "Spring", "Medium"));
        questions.add(createQuestion("What does @Transactional annotation do in Spring?",
                "Marks a method to be transaction-aware", "Manages database transactions automatically", "Prevents transaction rollback", "None of the above", "Manages database transactions automatically", "Spring", "Medium"));
        questions.add(createQuestion("What is the purpose of @Qualifier annotation in Spring?",
                "To provide additional metadata to a bean", "To specify which bean to inject when multiple beans of the same type exist", "To mark a bean as a primary bean", "To create a bean in the prototype scope", "To specify which bean to inject when multiple beans of the same type exist", "Spring", "High"));
        questions.add(createQuestion("Which Spring module provides support for transaction management?",
                "Spring Data", "Spring MVC", "Spring AOP", "Spring TX", "Spring TX", "Spring", "High"));

        // React Questions
        questions.add(createQuestion("What is the purpose of props in React?",
                "To manage component state", "To pass data from parent to child components", "To handle component lifecycle", "To style components", "To pass data from parent to child components", "React", "Easy"));
        questions.add(createQuestion("Which method is used to render a React component to the DOM?",
                "React.render()", "ReactDOM.render()", "Component.render()", "DOM.render()", "ReactDOM.render()", "React", "Easy"));
        questions.add(createQuestion("What is the difference between state and props in React?",
                "State is mutable and props are immutable", "Props are mutable and state is immutable", "Both are immutable", "Both are mutable", "State is mutable and props are immutable", "React", "Medium"));
        questions.add(createQuestion("What does the useEffect hook do in React?",
                "It handles component state", "It performs side effects in function components", "It manages component styling", "It sets the initial render", "It performs side effects in function components", "React", "Medium"));
        questions.add(createQuestion("What is React’s Context API used for?",
                "To handle component styles", "To manage global state and pass data without prop drilling", "To perform AJAX requests", "To manage component lifecycle", "To manage global state and pass data without prop drilling", "React", "High"));
        questions.add(createQuestion("How do you prevent a component from re-rendering in React?",
                "Use shouldComponentUpdate", "Use React.memo", "Use PureComponent", "All of the above", "All of the above", "React", "High"));

        return questions;
    }

    private static Question createQuestion(String questionTitle, String option1, String option2, String option3, String option4, String rightAnswer, String category, String difficultyLevel){
        Question question = new Question();
        question.setQuestionTitle(questionTitle);
        question.setOption1(option1);
        question.setOption2(option2);
        question.setOption3(option3);
        question.setOption4(option4);
        question.setRightAnswer(rightAnswer);
        question.setCategory(category);
        question.setDifficultyLevel(difficultyLevel);
        return question;
    }

    public ResponseEntity<String> loadQuestions() {
        try {
            List<Question> allQuestions = getHardCodedQuestions();
            questionDao.saveAllQuestion(allQuestions);
            return new ResponseEntity<>("Success",HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Failed to Load Question into Sample Quiz App", e);
            return new ResponseEntity<>("ERROR",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            List<Question>allQuestions= questionDao.getAllQuestions();
            return new ResponseEntity<>(allQuestions, HttpStatus.OK);
        }catch (Exception e){
            logger.error("Failed to get Questions", e);
            return new ResponseEntity<>(Collections.emptyList(),HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category){
        try {
            List<Question>questionsByCategory= questionDao.getQuestionsByCategory(category);
            return new ResponseEntity<>(questionsByCategory, HttpStatus.OK);
        }catch (Exception e){
            logger.error("Failed to get Questions by category: "+category,e);
            return new ResponseEntity<>(Collections.emptyList(),HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<List<Question>> getQuestionsByDifficulty(String difficultyLevel){
        try {
            List<Question>questionsByDifficulty= questionDao.getQuestionsByDifficulty(difficultyLevel);
            return new ResponseEntity<>(questionsByDifficulty, HttpStatus.OK);
        }catch (Exception e){
            logger.error("Failed to get Questions by Difficulty Level: "+difficultyLevel,e);
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> addQuestion(Question question){
        try {
            questionDao.addQuestion(question);
            return new ResponseEntity<>("Success", HttpStatus.CREATED);
        }catch (Exception e){
            logger.error("Error adding question to quiz");
            return new ResponseEntity<>("Error",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
