package com.santosh.quizapp.controller;

import com.santosh.quizapp.model.Question;
import com.santosh.quizapp.model.QuestionWrapper;
import com.santosh.quizapp.model.Response;
import com.santosh.quizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("createQuiz")
    public ResponseEntity<String> createQuiz(@RequestParam("category") String category,@RequestParam("noOfQuestions") int noOfQuestions,@RequestParam("title") String title){
        return quizService.createQuiz(category,noOfQuestions,title);
    }

    @GetMapping("getQuiz/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsForQuiz(@PathVariable int id){
        return quizService.getQuiz(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> getQuestionsForQuiz(@PathVariable int id, @RequestBody List<Response> responses){
        return quizService.getResult(id,responses);
    }
}
