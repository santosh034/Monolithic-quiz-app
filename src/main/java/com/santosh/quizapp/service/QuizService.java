package com.santosh.quizapp.service;

import com.santosh.quizapp.dao.QuestionDao;
import com.santosh.quizapp.dao.QuizDao;
import com.santosh.quizapp.model.Question;
import com.santosh.quizapp.model.QuestionWrapper;
import com.santosh.quizapp.model.Quiz;
import com.santosh.quizapp.model.Response;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuizService {
    private static final Logger LOGGER= LoggerFactory.getLogger(QuizService.class);

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int noOfQuestions, String title) {
        try {
            List<Question> questionsList = questionDao.getRandomQuestionsByCategory(category, noOfQuestions);
            Quiz quiz = new Quiz();
            quiz.setTitle(title);
            quiz.setQuestions(questionsList);
            quizDao.save(quiz);
            return new ResponseEntity<>("SUCCESS", HttpStatus.CREATED);
        }catch (Exception e){
            LOGGER.error("Error creating quiz:"+title,e);
        }
        return new ResponseEntity<>("ERROR",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<QuestionWrapper>>getQuiz(int id){
        try{
            Optional<Quiz> quiz=quizDao.findById(id);
            List<Question> quizQuestions=quiz.get().getQuestions();
            List<QuestionWrapper> questionsForUser=new ArrayList<>();

            for(Question question:quizQuestions){
                QuestionWrapper questionWrapper=new QuestionWrapper(question.getId(),question.getQuestionTitle(),
                        question.getOption1(),question.getOption2(),question.getOption3(),question.getOption4());
                questionsForUser.add(questionWrapper);
            }
            return new ResponseEntity<>(questionsForUser,HttpStatus.OK);
        }catch (Exception e){
            LOGGER.error("Error getting questions for quiz: "+id);
            return  new ResponseEntity<>(Collections.emptyList(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Integer> getResult(int id, List<Response> responses) {
        Optional<Quiz> quiz=quizDao.findById(id);
        List<Question>questions=quiz.get().getQuestions();
        int result=0;
        int i=0;
        for(Response response: responses){
            if(response.getOption().equals(questions.get(i).getRightAnswer()))
                result++;

            i++;
        }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
