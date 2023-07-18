package collections;

import java.util.Objects;

public class Range<C extends Comparable> {
    private final C lowerEndpoint;
    private final C upperEndpoint;
    private final boolean lowerInclusive;
    private final boolean upperInclusive;
    private final boolean isEmpty;

    private Range(C lowerEndpoint, C upperEndpoint, boolean lowerInclusive, boolean upperInclusive, boolean isEmpty) {
        this.lowerEndpoint = lowerEndpoint;
        this.upperEndpoint = upperEndpoint;
        this.lowerInclusive = lowerInclusive;
        this.upperInclusive = upperInclusive;
        this.isEmpty = isEmpty;
    }

    public static <C extends Comparable<C>> Range open(C lowerEndpoint, C upperEndpoint) {
        Objects.requireNonNull(lowerEndpoint);
        Objects.requireNonNull(upperEndpoint);
        if (lowerEndpoint.compareTo(upperEndpoint) >= 0) {
            throw new IllegalArgumentException("Lower endpoint must be less than the upper endpoint");
        }
        return new Range<>(lowerEndpoint, upperEndpoint, false, false, false);
    }

    public static <C extends Comparable<C>> Range closed(C lowerEndpoint, C upperEndpoint) {
        Objects.requireNonNull(lowerEndpoint);
        Objects.requireNonNull(upperEndpoint);
        if (lowerEndpoint.compareTo(upperEndpoint) > 0) {
            throw new IllegalArgumentException("Lower endpoint must not be greater than the upper endpoint");
        }
        return new Range<>(lowerEndpoint, upperEndpoint, true, true, false);
    }

    public static <C extends Comparable<C>> Range openClosed(C lowerEndpoint, C upperEndpoint) {
        Objects.requireNonNull(lowerEndpoint);
        Objects.requireNonNull(upperEndpoint);
        if (lowerEndpoint.compareTo(upperEndpoint) == 0) {
            return new Range<>(lowerEndpoint, upperEndpoint, false, false, true);
        }
        if (lowerEndpoint.compareTo(upperEndpoint) > 0) {
            throw new IllegalArgumentException("Lower endpoint must be less than the upper endpoint");
        }
        return new Range<>(lowerEndpoint, upperEndpoint, false, true, false);
    }

    public static <C extends Comparable<C>> Range closedOpen(C lowerEndpoint, C upperEndpoint) {
        Objects.requireNonNull(lowerEndpoint);
        Objects.requireNonNull(upperEndpoint);
        if (lowerEndpoint.compareTo(upperEndpoint) == 0) {
            return new Range<>(lowerEndpoint, upperEndpoint, false, false, true);
        }
        if (lowerEndpoint.compareTo(upperEndpoint) > 0) {
            throw new IllegalArgumentException("Lower endpoint must be less than the upper endpoint");
        }
        return new Range<>(lowerEndpoint, upperEndpoint, true, false, false);
    }

    public static <C extends Comparable<C>> Range greaterThan(C lowerEndpoint) {
        Objects.requireNonNull(lowerEndpoint);
        return new Range<>(lowerEndpoint, null, false, false, false);
    }

    public static <C extends Comparable<C>> Range atLeast(C lowerEndpoint) {
        Objects.requireNonNull(lowerEndpoint);
        return new Range<>(lowerEndpoint, null, true, false, false);
    }

    public static <C extends Comparable<C>> Range lessThan(C upperEndpoint) {
        Objects.requireNonNull(upperEndpoint);
        return new Range<>(null, upperEndpoint, false, false, false);
    }

    public static <C extends Comparable<C>> Range atMost(C upperEndpoint) {
        Objects.requireNonNull(upperEndpoint);
        return new Range<>(null, upperEndpoint, false, true, false);
    }

    public static <C extends Comparable<C>> Range all() {
        return new Range<>(null, null, false, false, false);
    }

    public static <C extends Comparable<C>> Range empty() {
        return new Range<>(null, null, false, false, true);
    }

    public boolean contains(C value) {
        Objects.requireNonNull(value);
        boolean greaterThanLower = lowerEndpoint == null || (lowerInclusive ? value.compareTo(lowerEndpoint) >= 0 : value.compareTo(lowerEndpoint) > 0);
        boolean lessThanUpper = upperEndpoint == null || (upperInclusive ? value.compareTo(upperEndpoint) <= 0 : value.compareTo(upperEndpoint) < 0);
        return greaterThanLower && lessThanUpper;
    }

    public boolean encloses(Range<C> other) {
        if (isEmpty && other.isEmpty) {
            return false;
        }
        if (other.isEmpty) {
            return true;
        }

        boolean lowerEnclosed = false;
        boolean upperEnclosed = false;

        if (lowerEndpoint == null) {
            lowerEnclosed = true;
        } else if (other.lowerEndpoint != null) {
            int comparison = lowerEndpoint.compareTo(other.lowerEndpoint);
            if (comparison < 0 || (comparison == 0 && (lowerInclusive || !other.lowerInclusive))) {
                lowerEnclosed = true;
            } else if (comparison == 0 && lowerInclusive && !other.lowerInclusive) {
                return false;
            }
        }

        if (upperEndpoint == null) {
            upperEnclosed = true;
        } else if (other.upperEndpoint != null) {
            int comparison = upperEndpoint.compareTo(other.upperEndpoint);
            if (comparison > 0 || (comparison == 0 && (upperInclusive || !other.upperInclusive))) {
                upperEnclosed = true;
            } else if (comparison == 0 && upperInclusive && !other.upperInclusive) {
                return false;
            }
        }

        return lowerEnclosed && upperEnclosed;
    }

    public Range<C> span(Range<C> other) {
        if (isEmpty() && other.isEmpty()) {
            return emptyRange();
        }

        if (this.encloses(other)) {
            return this;
        } else if (other.encloses(this)) {
            return other;
        }

        if (upperEndpoint != null && other.lowerEndpoint != null && upperEndpoint.compareTo(other.lowerEndpoint) == -1 && upperInclusive && other.lowerInclusive) {
            return new Range<>(lowerEndpoint, other.upperEndpoint, lowerInclusive, other.upperInclusive, false);
        }

        C newLowerEndpoint;
        boolean newLowerInclusive;

        if (lowerEndpoint == null || other.lowerEndpoint == null) {
            newLowerEndpoint = null;
            newLowerInclusive = false;
        } else {
            int comparison = lowerEndpoint.compareTo(other.lowerEndpoint);
            if (comparison < 0) {
                newLowerEndpoint = lowerEndpoint;
                newLowerInclusive = lowerInclusive;
            } else if (comparison == 0) {
                newLowerEndpoint = lowerEndpoint;
                newLowerInclusive = lowerInclusive || other.lowerInclusive;
            } else {
                newLowerEndpoint = other.lowerEndpoint;
                newLowerInclusive = other.lowerInclusive;
            }
        }

        C newUpperEndpoint;
        boolean newUpperInclusive;

        if (upperEndpoint == null || other.upperEndpoint == null) {
            newUpperEndpoint = null;
            newUpperInclusive = false;
        } else {
            int comparison = upperEndpoint.compareTo(other.upperEndpoint);
            if (comparison > 0) {
                newUpperEndpoint = upperEndpoint;
                newUpperInclusive = upperInclusive;
            } else if (comparison == 0) {
                newUpperEndpoint = upperEndpoint;
                newUpperInclusive = upperInclusive || other.upperInclusive;
            } else {
                newUpperEndpoint = other.upperEndpoint;
                newUpperInclusive = other.upperInclusive;
            }
        }

        if (newLowerEndpoint != null && newUpperEndpoint != null && newLowerEndpoint.compareTo(newUpperEndpoint) > 0) {
            return emptyRange();
        }

        if (newLowerEndpoint == null && newUpperEndpoint == null) {
            return all();
        }

        return new Range<>(newLowerEndpoint, newUpperEndpoint, newLowerInclusive, newUpperInclusive, false);
    }

    public boolean isEmpty() {
        if (lowerEndpoint != null && upperEndpoint != null) {
            int comparison = lowerEndpoint.compareTo(upperEndpoint);
            return comparison > 0 || (comparison == 0 && !(lowerInclusive && upperInclusive));
        }
        return false;
    }

    public Range<C> intersection(Range<C> other) {
        if (isEmpty() || other.isEmpty()) {
            return emptyRange();
        }

        C newLowerEndpoint;
        boolean newLowerInclusive;

        if (lowerEndpoint == null) {
            newLowerEndpoint = other.lowerEndpoint;
            newLowerInclusive = other.lowerInclusive;
        } else if (other.lowerEndpoint == null) {
            newLowerEndpoint = lowerEndpoint;
            newLowerInclusive = lowerInclusive;
        } else {
            int comparison = lowerEndpoint.compareTo(other.lowerEndpoint);
            if (comparison > 0) {
                newLowerEndpoint = lowerEndpoint;
                newLowerInclusive = lowerInclusive;
            } else if (comparison < 0) {
                newLowerEndpoint = other.lowerEndpoint;
                newLowerInclusive = other.lowerInclusive;
            } else {
                newLowerEndpoint = lowerEndpoint;
                newLowerInclusive = lowerInclusive && other.lowerInclusive;
            }
        }

        C newUpperEndpoint;
        boolean newUpperInclusive;

        if (upperEndpoint == null) {
            newUpperEndpoint = other.upperEndpoint;
            newUpperInclusive = other.upperInclusive;
        } else if (other.upperEndpoint == null) {
            newUpperEndpoint = upperEndpoint;
            newUpperInclusive = upperInclusive;
        } else {
            int comparison = upperEndpoint.compareTo(other.upperEndpoint);
            if (comparison < 0) {
                newUpperEndpoint = upperEndpoint;
                newUpperInclusive = upperInclusive;
            } else if (comparison > 0) {
                newUpperEndpoint = other.upperEndpoint;
                newUpperInclusive = other.upperInclusive;
            } else {
                newUpperEndpoint = upperEndpoint;
                newUpperInclusive = upperInclusive && other.upperInclusive;
            }
        }

        if (newLowerEndpoint != null && newUpperEndpoint != null && newLowerEndpoint.compareTo(newUpperEndpoint) > 0) {
            return emptyRange();
        }

        if (newLowerEndpoint == null && newUpperEndpoint == null) {
            return all();
        }

        if (newLowerEndpoint != null && newUpperEndpoint != null && newLowerEndpoint.compareTo(newUpperEndpoint) == 0 && !(newLowerInclusive && newUpperInclusive)) {
            return emptyRange();
        }

        return new Range<>(newLowerEndpoint, newUpperEndpoint, newLowerInclusive, newUpperInclusive, false);
    }

    static <C extends Comparable<C>> Range emptyRange() {
        return new Range<>(null, null, false, false, true);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Range<?> range = (Range<?>) o;
        return lowerInclusive == range.lowerInclusive && upperInclusive == range.upperInclusive && Objects.equals(lowerEndpoint, range.lowerEndpoint) && Objects.equals(upperEndpoint, range.upperEndpoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lowerEndpoint, upperEndpoint, lowerInclusive, upperInclusive);
    }

    @Override
    public String toString() {
        if (isEmpty) {
            return "EMPTY";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(lowerInclusive ? "[" : "(");
        sb.append(lowerEndpoint != null ? lowerEndpoint.toString() : "-INF");
        sb.append(", ");
        sb.append(upperEndpoint != null ? upperEndpoint.toString() : "INF");
        sb.append(upperInclusive ? "]" : ")");
        return sb.toString();
    }
}
