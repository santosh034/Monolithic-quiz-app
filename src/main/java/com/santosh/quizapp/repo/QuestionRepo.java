package com.santosh.quizapp.repo;

import com.santosh.quizapp.model.Question;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.hibernate.annotations.processing.HQL;
import org.hibernate.annotations.processing.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Integer> {
    public List<Question> findByCategory(String category);
    public List<Question> findByDifficultyLevel(String difficultyLevel);

    @Query(value = "Select * from question where category=:category order by RANDOM() Limit :numOfQuestions",nativeQuery = true)
    public List<Question> getRandomQuestionsByCategory(String category,int numOfQuestions);
}
