package framgiavn.project01.web.business;

import java.util.List;

import framgiavn.project01.web.model.Word;

public interface WordBusiness {

  public List<Word> listAll() throws Exception;

  public List<Word> findAllWordByCategoryId(int category_id) throws Exception;
}
