package com.four.brothers.runtou.repository;

import com.four.brothers.runtou.domain.FilterWord;
import com.four.brothers.runtou.repository.FilterWordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Import({FilterWordRepository.class})
@DataJpaTest
class FilterWordRepositoryTest {

  @Autowired
  private FilterWordRepository filterWordRepository;

  @DisplayName("filterword저장")
  @Test
  void saveWordTest() {
    //given
    String word1 = "창건";

    //when-then
    assertDoesNotThrow(
      () -> filterWordRepository.saveWord(word1)
    );
  }

  @DisplayName("모든 filterWord 조회")
  @Test
  void findAllTest(){
    //given
    String word1 = "창건";
    String word2 = "에잇";

    int nowPage = 1;
    int itemSize1 = 1;
    int itemSize2 = 2;

    filterWordRepository.saveWord(word1);
    filterWordRepository.saveWord(word2);

    //when-then
    assertAll(
      () -> {
            List<FilterWord> result = filterWordRepository.findAll(nowPage, itemSize1);
            assertSame(1, result.size());
      },
      () -> {
            List<FilterWord> result = filterWordRepository.findAll(nowPage, itemSize2);
            assertSame(2,result.size());
      },
      () -> {
            List<FilterWord> result = filterWordRepository.findAll(nowPage,itemSize1);
            assertNotSame(2,result.size());
      });
    }

    @DisplayName("pk값으로 단어 삭제")
    @Test
    void deleteFilterWordByIdTest(){
    //given
    String word1 = "word";

    long pk;
    filterWordRepository.saveWord(word1);
    filterWordRepository.findAll(1,1);

    pk = filterWordRepository.findAll(1,1).get(0).getId();

    //when-then
    assertDoesNotThrow(
      () -> filterWordRepository.deleteFilterWordById(pk)
    );

  }

}