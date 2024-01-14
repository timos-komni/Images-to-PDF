package swati4star.createpdf.util.lambda

/**
 * A custom Implementation of [java.util.function.Consumer].
 * Used because API Level 24 is required to use the java variant.
 *
 * @param <T> the type of the input to the operation
 */
interface Consumer<T> {
    /**
     * Performs this operation on the given argument.
     *
     * @param t the input argument
     */
    fun accept(t: T)
}
