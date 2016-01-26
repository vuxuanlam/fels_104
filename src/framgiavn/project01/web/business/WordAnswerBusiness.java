package framgiavn.project01.web.business;

import java.util.List;

import framgiavn.project01.web.model.Word;
import framgiavn.project01.web.model.WordAnswer;

public interface WordAnswerBusiness {

  public List<WordAnswer> listAll() throws Exception;

  public List<WordAnswer> findAllAnswerByWordId(int word_id) throws Exception;

  public WordAnswer getCorrectAnswer(Word word) throws Exception;
}
