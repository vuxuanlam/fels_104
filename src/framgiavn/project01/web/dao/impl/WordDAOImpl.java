package framgiavn.project01.web.dao.impl;

import java.util.List;

import org.hibernate.Query;

import framgiavn.project01.web.dao.WordDAO;
import framgiavn.project01.web.model.Word;
import framgiavn.project01.web.ulti.Logit2;

public class WordDAOImpl extends GenericDAOImpl<Word, Integer> implements
    WordDAO {

  public WordDAOImpl() {

    super(Word.class);
  }

  private static final Logit2 log = Logit2.getInstance(WordDAOImpl.class);

  @SuppressWarnings("unused")
  @Override
  public List<Word> findByCategory(Object value)
      throws Exception {

    try {
      Query query = getSession().getNamedQuery("Word.findByCategory");
      query.setParameter("value", value);
      return query.list();
    } catch (Exception e) {
      log.error("error find by proerty");
      throw e;

    }
  }
}
