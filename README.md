## UC11: Create and Manage Tags

1. Created `Tag` class in `com.contacts`.
2. Contacts can have one or multiple tags.
3. Tags are stored using `Set<Tag>` to ensure uniqueness.
4. Tags are persisted as pipe-separated values (`family|work`).

### Concepts Applied

- Composition (Contact has Tags)
- `Set` collection for uniqueness
- Overriding `equals()` and `hashCode()`
- Object relationship management
- File persistence formatting
