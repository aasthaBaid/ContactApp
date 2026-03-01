package com.search;

import java.util.List;

public interface SearchService<T> {
    List<T> search(String keyword, String searchType) throws Exception;
}