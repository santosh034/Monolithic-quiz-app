package com.santosh.quizapp.controller;

import com.santosh.quizapp.model.Question;
import com.santosh.quizapp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("getAllQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        return questionService.getAllQuestions();
    }

    @GetMapping("loadQuestions")
    public ResponseEntity<String> loadQuestions(){
        return questionService.loadQuestions();
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category){
        return questionService.getQuestionsByCategory(category);
    }

    @GetMapping("difficulty/{level}")
    public ResponseEntity<List<Question>> getQuestionsByDifficulty(@PathVariable(value = "level") String difficultyLevel){
        return questionService.getQuestionsByDifficulty(difficultyLevel);
    }

    @PostMapping("addQuestion")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
        return questionService.addQuestion(question);
    }
}
