public abstract class RecordBase {
    //all the validations

    protected static String requireNonBlank(String value, String field)
    {
        if (value == null)
        {
            throw new IllegalArgumentException(field + " must not be null");
        }
        String trimmed = value.trim();
        if (trimmed.isEmpty())
        {
            throw new IllegalArgumentException(field + " must not be blank");
        }
        return trimmed;
    }

    protected static int requireNonNegative(int value, String field)
    {
        if (value < 0)
        {
            throw new IllegalArgumentException(field + " must be >= 0");
        }
        return value;
    }

    protected static double requirePositive(double value, String field)
    {
        if (value <= 0.0)
        {
            throw new IllegalArgumentException(field + " must be > 0");
        }
        return value;
    }

    protected static void printDivider()
    {
        System.out.println("-------------------------------");
    }
}
