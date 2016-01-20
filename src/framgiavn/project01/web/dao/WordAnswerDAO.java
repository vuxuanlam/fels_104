package framgiavn.project01.web.dao;

import java.util.List;

import framgiavn.project01.web.model.Word;
import framgiavn.project01.web.model.WordAnswer;

public interface WordAnswerDAO extends GenericDAO<WordAnswer, Integer> {

  public List<WordAnswer> findByWord(Object value) throws Exception;

  public WordAnswer getCorrectAnswer(Word word) throws Exception;
}
