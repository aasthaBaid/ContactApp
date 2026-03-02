## UC9: Search Users

1. Created `com.search` package.
2. Implemented `SearchService` interface.
3. Implemented `UserSearchService`.
4. Users can search by:
   - Name
   - Email
   - Phone
5. Supports partial matching.

### Concepts Applied

- Interface-based design
- Polymorphism (`SearchService<User>`)
- `equalsIgnoreCase()` and `contains()`
- Loop-based filtering
- Separation of concerns
