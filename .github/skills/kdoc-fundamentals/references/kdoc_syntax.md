KDoc Syntax Reference

KDoc basics and recommended practices for repository-wide consistency.

- Use /** ... */ before declarations.
- Lead with a one-line summary; follow with details and tags.
- Common tags: @param, @return, @throws, @sample.

Example:
/**
 * Sorts the list in ascending order using the specified comparator.
 *
 * @param list the list to sort
 * @param comparator comparator used for ordering
 */
fun <T> sort(list: MutableList<T>, comparator: Comparator<T>) { /*...*/ }