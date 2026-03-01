package com.search;

import java.util.List;

public interface FilterService<T> {
    List<T> filter(String userEmail, String filterType, String value);
}