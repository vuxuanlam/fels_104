package framgiavn.project01.web.dao.impl;

import java.util.List;

import org.hibernate.Query;

import framgiavn.project01.web.dao.WordAnswerDAO;
import framgiavn.project01.web.model.Word;
import framgiavn.project01.web.model.WordAnswer;
import framgiavn.project01.web.ulti.Logit2;

public class WordAnswerDAOImpl extends GenericDAOImpl<WordAnswer, Integer>
    implements WordAnswerDAO {

  public WordAnswerDAOImpl() {

    super(WordAnswer.class);
  }

  private static final Logit2 log = Logit2.getInstance(WordAnswerDAOImpl.class);

  @Override
  public List<WordAnswer> findByWord(Object value) throws Exception {

    try {
      Query query = getSession().getNamedQuery("WordAnswer.findByWord");
      query.setParameter("value", value);
      return (List<WordAnswer>) query.list();
    } catch (Exception e) {
      log.error("error find by property");
      throw e;

    }
  }

  @Override
  public WordAnswer getCorrectAnswer(Word word) throws Exception {

    try {
      Query query = getSession().getNamedQuery("WordAnswer.getCorrectAnswer");
      query.setParameter("value", word.getWordId());
      return (WordAnswer) query.uniqueResult();
    } catch (Exception e) {
      log.error("error find by property");
      throw e;

    }
  }
}
