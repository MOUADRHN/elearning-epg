package ma.epg.elearning.service;
import lombok.RequiredArgsConstructor;
import ma.epg.elearning.entity.*;
import ma.epg.elearning.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service @RequiredArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final QuizResultRepository quizResultRepository;

    public Quiz findById(Long id) { return quizRepository.findById(id).orElseThrow(); }

    @Transactional
    public QuizResult soumettre(Long quizId, Long etudiantId, Map<Long, String> reponses) {
        Quiz quiz = findById(quizId);
        List<Question> questions = questionRepository.findByQuizId(quizId);
        int bonnes = 0; int points = 0;
        for (Question q : questions) {
            String rep = reponses.get(q.getId());
            if (rep != null && rep.equalsIgnoreCase(q.getBonneReponse())) {
                bonnes++; points += q.getPoints();
            }
        }
        double score = !questions.isEmpty() ? (double) bonnes / questions.size() * 20 : 0;
        QuizResult result = new QuizResult();
        result.setScore(score); result.setPointsGagnes(points); result.setCompleteLe(LocalDateTime.now());
        Etudiant etudiant = new Etudiant(); etudiant.setId(etudiantId);
        result.setEtudiant(etudiant); result.setQuiz(quiz);
        return quizResultRepository.save(result);
    }
}
