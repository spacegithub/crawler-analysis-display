package com.github.anddd7.crawler.bilibili.client;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.github.anddd7.crawler.bilibili.client.response.SearchDataWrapper;
import com.github.anddd7.crawler.bilibili.controller.command.DateRangeCommand;
import com.github.anddd7.crawler.bilibili.controller.command.SearchByCategoryCommand;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

public class SearchClientTest {

  private RestTemplate restTemplate;
  private SearchClient searchClient;

  @Before
  public void setUp() {
    restTemplate = mock(RestTemplate.class);
    searchClient = new SearchClient(restTemplate);
  }

  @Test
  public void searchByCategory() {
    SearchByCategoryCommand command = SearchByCategoryCommand.builder()
        .categoryId("0")
        .pageNumber(1)
        .pageSize(10)
        .dateRange(
            DateRangeCommand.builder()
                .fromDate(LocalDate.of(2018, 5, 20))
                .toDate(LocalDate.of(2018, 5, 20))
                .build()
        )
        .build();
    String expectedUrl = "https://s.search.bilibili.com/cate/search?"
        + "main_ver=v3&"
        + "search_type=video&"
        + "view_type=hot_rank&"
        + "pic_size=160x100&"
        + "order=click&"
        + "copy_right=-1&"
        + "pagesize=10&"
        + "page=1&"
        + "cate_id=0&"
        + "time_from=20180520&"
        + "time_to=20180520";

    searchClient.searchByCategory(command);

    verify(restTemplate, times(1)).getForObject(expectedUrl, SearchDataWrapper.class);
  }
}