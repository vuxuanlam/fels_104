package framgiavn.project01.web.dao;

import java.util.List;

import framgiavn.project01.web.model.Word;

public interface WordDAO extends GenericDAO<Word, Integer> {

  public List<Word> findByCategory(Object value)
      throws Exception;
}
