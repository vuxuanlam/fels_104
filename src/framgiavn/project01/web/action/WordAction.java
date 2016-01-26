package framgiavn.project01.web.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import framgiavn.project01.web.business.CategoryBusiness;
import framgiavn.project01.web.business.WordAnswerBusiness;
import framgiavn.project01.web.business.WordBusiness;
import framgiavn.project01.web.model.Category;
import framgiavn.project01.web.model.Word;
import framgiavn.project01.web.model.WordAnswer;
import framgiavn.project01.web.ulti.Constant;

public class WordAction extends ActionSupport implements SessionAware {

  private SessionMap            session            = null;
  private WordBusiness          wordBusiness       = null;
  private WordAnswerBusiness    wordAnswerBusiness = null;
  private CategoryBusiness      categoryBusiness   = null;
  private List<Word>            listWord           = null;
  private List<Category>        listCategory       = null;
  private List<WordAnswer>      listWordAnswer     = null;
  private Map<Word, WordAnswer> mapWordAnswer      = null;
  private int                   categoryIdFilter   = Constant.CATEGORY_DEFAULT;
  private int                   optionFilter       = Constant.OPTION_DEFAULT;

  public Map<Word, WordAnswer> getMapWordAnswer() {

    return this.mapWordAnswer;
  }

  public int getCategoryIdFilter() {

    return this.categoryIdFilter;
  }

  public int getOptionFilter() {

    return this.optionFilter;
  }

  public void setCategoryIdFilter(int categoryIdFilter) {

    this.categoryIdFilter = categoryIdFilter;
  }

  public void setOptionFilter(int optionFilter) {

    this.optionFilter = optionFilter;
  }

  public List<Word> getListWord() {

    return this.listWord;
  }

  public List<WordAnswer> getListWordAnswer() {

    return this.listWordAnswer;
  }

  public List<Category> getListCategory() {

    return listCategory;
  }

  public void setCategoryBusiness(CategoryBusiness categoryBusiness) {

    this.categoryBusiness = categoryBusiness;
  }

  public void setWordBusiness(WordBusiness wordBusiness) {

    this.wordBusiness = wordBusiness;
  }

  public void setWordAnswerBusiness(WordAnswerBusiness wordAnswerBusiness) {

    this.wordAnswerBusiness = wordAnswerBusiness;
  }

  @Override
  public void setSession(Map<String, Object> session) {

    this.session = (SessionMap) session;
  }

  public String listWord() {

    Word word;
    WordAnswer wordAnswer;
    Iterator listWordIterator;
    mapWordAnswer = new HashMap<Word, WordAnswer>();

    if (session.isEmpty()) {
      return NONE;
    }
    try {
      listWord = wordBusiness.findAllWordByCategoryId(categoryIdFilter);
      listCategory = categoryBusiness.listAll();
      listWordIterator = listWord.iterator();
      while (listWordIterator.hasNext()) {
        word = (Word) listWordIterator.next();
        wordAnswer = wordAnswerBusiness.getCorrectAnswer(word);
        mapWordAnswer.put(word, wordAnswer);
      }
      return SUCCESS;
    } catch (Exception e) {
      e.printStackTrace();
      return ERROR;
    }

  }

  public boolean compare(int a, int b) {

    return a == b;
  }
}
