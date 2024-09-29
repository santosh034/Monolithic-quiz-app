package com.santosh.quizapp.dao;

import com.santosh.quizapp.model.Question;
import com.santosh.quizapp.repo.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuestionDao {

    @Autowired
    QuestionRepo questionRepo;

    public List<Question> getAllQuestions(){
        return questionRepo.findAll();
    }

    public void saveAllQuestion(List<Question> questions){
        questionRepo.saveAll(questions);
    }

    public List<Question> getQuestionsByCategory(String category){
        return questionRepo.findByCategory(category);
    }

    public List<Question> getQuestionsByDifficulty(String difficultyLevel){
        return questionRepo.findByDifficultyLevel(difficultyLevel);
    }

    public void addQuestion(Question question) throws Exception{
         questionRepo.save(question);
    }

    public List<Question>getRandomQuestionsByCategory(String category, int noOfQuestions){
        return questionRepo.getRandomQuestionsByCategory(category,noOfQuestions);
    }
}
