package net.scarlettsystems.android.fraction;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * An immutable class for storing rational number values in their discrete states, with automatic
 * reduction of fractions (e.g., 2/4 becomes 1/2), and basic mathematical operations between other
 * Fraction objects and primitive number values.
 * <br><br>
 * {@code Fraction} internally uses {@link BigInteger} to store values to ensure precision and
 * prevent overflow when dealing with large numerators and denominators.
 *
 * @author Shane Scarlett
 */
@SuppressWarnings("WeakerAccess")
public class Fraction
{
	private BigInteger mNumerator;
	private BigInteger mDenominator;

	/**
	 * Construct a fraction with specified number values.
	 *
	 * @param numerator numerator as long
	 * @param denominator denominator as long
	 */
	public Fraction(long numerator, long denominator)
	{
		this(BigInteger.valueOf(numerator), BigInteger.valueOf(denominator));
	}

	/**
	 * Construct a fraction with specified {@link BigInteger} values.
	 *
	 * @param numerator numerator as {@link BigInteger}
	 * @param denominator denominator as {@link BigInteger}
	 */
	public Fraction(BigInteger numerator, BigInteger denominator)
	{
		if (denominator.equals(BigInteger.ZERO))
		{
			throw new IllegalArgumentException("Invalid input. Denominator cannot be zero.");
		}
		mNumerator = numerator.abs();
		mDenominator = denominator.abs();
		int sign = numerator.signum() * denominator.signum();
		if(sign < 0)
		{
			mNumerator = mNumerator.negate();
		}
		reduce();
	}

	/**
	 * Get the numerator of the fraction.
	 *
	 * @return numerator as {@link BigInteger}
	 */
	public BigInteger getNumerator()
	{
		return mNumerator;
	}

	/**
	 * Get the denominator of the fraction.
	 *
	 * @return denominator as {@link BigInteger}
	 */
	public BigInteger getDenominator()
	{
		return mDenominator;
	}

	/**
	 * Check for equality between two Fraction objects.
	 *
	 * @param obj object to check
	 * @return equality as a boolean value
	 */
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof Fraction)){return false;}
		Fraction compare = (Fraction)obj;
		return compare.getNumerator().equals(this.getNumerator())
				&& compare.getDenominator().equals(this.getDenominator());
	}

	/**
	 * Get a string representation of the fraction.
	 *
	 * @return fraction displayed as a string
	 */
	@Override
	public String toString()
	{
		return getNumerator().toString() + "/" + getDenominator().toString();
	}

	/**
	 * Get a double representation of the current fraction.
	 *
	 * @param decimalDigits precision in digits
	 * @return decimal approximation of the fraction
	 */
	public double getValue(int decimalDigits)
	{
		if(decimalDigits < 0)
		{
			throw new IllegalArgumentException("Precision must be a positive integer.");
		}
		return new BigDecimal(getNumerator())
				.divide(new BigDecimal(getDenominator()),  decimalDigits, RoundingMode.HALF_EVEN)
				.doubleValue();
	}

	/**
	 * Add two fractions.
	 *
	 * @param addend fraction to add to the current object
	 * @return resultant fraction
	 */
	public Fraction add(Fraction addend)
	{
		BigInteger lcm = lcm(mDenominator, addend.getDenominator());
		BigInteger numeratorA = getNumerator().multiply(lcm.divide(getDenominator()));
		BigInteger numeratorB = addend.getNumerator().multiply(lcm.divide(addend.getDenominator()));
		return new Fraction(numeratorA.add(numeratorB), lcm).reduce();
	}

	/**
	 * Add a {@link BigInteger} to the current fraction.
	 *
	 * @param addend BigInteger to add to the current object
	 * @return resultant fraction
	 */
	public Fraction add(BigInteger addend)
	{
		BigInteger numerator = addend.multiply(getDenominator());
		return new Fraction(getNumerator().add(numerator), getDenominator()).reduce();
	}

	/**
	 * Add a value to the current fraction.
	 *
	 * @param addend long to add to the current object
	 * @return resultant fraction
	 */
	public Fraction add(long addend)
	{
		return add(BigInteger.valueOf(addend));
	}

	/**
	 * Subtract a fraction from the current fraction.
	 *
	 * @param subtrahend fraction to subtract from the current object
	 * @return resultant fraction
	 */
	public Fraction subtract(Fraction subtrahend)
	{
		BigInteger lcm = lcm(mDenominator, subtrahend.getDenominator());
		BigInteger numeratorA = getNumerator().multiply(lcm.divide(getDenominator()));
		BigInteger numeratorB = subtrahend.getNumerator().multiply(lcm.divide(subtrahend.getDenominator()));
		return new Fraction(numeratorA.subtract(numeratorB), lcm).reduce();
	}

	/**
	 * Subtract a {@link BigInteger} from the current fraction.
	 *
	 * @param subtrahend BigInteger to subtract from the current object
	 * @return resultant fraction
	 */
	public Fraction subtract(BigInteger subtrahend)
	{
		BigInteger numerator = subtrahend.multiply(getDenominator());
		return new Fraction(getNumerator().subtract(numerator), getDenominator()).reduce();
	}

	/**
	 * Subtract a value from the current fraction.
	 *
	 * @param subtrahend long to subtract from the current object
	 * @return resultant fraction
	 */
	public Fraction subtract(long subtrahend)
	{
		return subtract(BigInteger.valueOf(subtrahend));
	}

	/**
	 * Multiply a fraction to the current fraction.
	 *
	 * @param multiplicand fraction to multiply to the current object
	 * @return resultant fraction
	 */
	public Fraction multiply(Fraction multiplicand)
	{
		return new Fraction(getNumerator().multiply(multiplicand.getNumerator()), getDenominator().multiply(multiplicand.getDenominator())).reduce();
	}

	/**
	 * Multiply a {@link BigInteger} to the current fraction.
	 *
	 * @param multiplicand BigInteger to multiply to the current object
	 * @return resultant fraction
	 */
	public Fraction multiply(BigInteger multiplicand)
	{
		return new Fraction(getNumerator().multiply(multiplicand), getDenominator()).reduce();
	}

	/**
	 * Multiply a value to the current fraction.
	 *
	 * @param multiplicand long to multiply to the current object
	 * @return resultant fraction
	 */
	public Fraction multiply(long multiplicand)
	{
		return multiply(BigInteger.valueOf(multiplicand));
	}

	/**
	 * Divide the current fraction by another fraction.
	 *
	 * @param divisor fraction to divide the current object by
	 * @return resultant fraction
	 */
	public Fraction divide(Fraction divisor)
	{
		return new Fraction(getNumerator().multiply(divisor.getDenominator()), getDenominator().multiply(divisor.getNumerator())).reduce();
	}

	/**
	 * Divide the current fraction by another {@link BigInteger}.
	 *
	 * @param divisor BigInteger to divide the current object by
	 * @return resultant fraction
	 */
	public Fraction divide(BigInteger divisor)
	{
		return new Fraction(getNumerator(), getDenominator().multiply(divisor)).reduce();
	}

	/**
	 * Divide the current fraction by another value.
	 *
	 * @param divisor long to divide the current object by
	 * @return resultant fraction
	 */
	public Fraction divide(long divisor)
	{
		return divide(BigInteger.valueOf(divisor));
	}

	/**
	 * Raise the current fraction by a specified integer exponent.
	 *
	 * @param exponent integer exponent
	 * @return resultant fraction
	 */
	public Fraction pow(int exponent)
	{
		return new Fraction(getNumerator().pow(exponent), getDenominator().pow(exponent)).reduce();
	}

	/**
	 * Take the natural logarithm of the current fraction.
	 *
	 * @return log base {@code e}
	 */
	public double log()
	{
		return logBigInteger(getNumerator()) - logBigInteger(getDenominator());
	}

	/**
	 * Find the lowest common multiple of two numbers.
	 *
	 * @param d1 first number
	 * @param d2 second number
	 * @return the lowest common multiple
	 */
	private BigInteger lcm(BigInteger d1, BigInteger d2)
	{
		BigInteger factor = d1;
		while(!d1.mod(d2).equals(BigInteger.ZERO))
		{
			d1 = d1.add(factor);
		}
		return d1;
	}

	/**
	 * Internal function to reduce the fraction to its simplest form.
	 *
	 * @return self object for command chaining
	 */
	private Fraction reduce()
	{
		BigInteger common;
		BigInteger numerator = getNumerator().abs();
		BigInteger denominator = getDenominator().abs();
		if(numerator.compareTo(denominator) > 0)
		{
			common = numerator.gcd(denominator);
		}
		else if(numerator.compareTo(denominator) < 0)
		{
			common = denominator.gcd(numerator);
		}
		else
		{
			common = numerator;
		}
		mNumerator = getNumerator().divide(common);
		mDenominator = getDenominator().divide(common);
		return this;
	}

	/**
	 * Internal function to take the natural logarithm of a BigInteger.
	 *
	 * @return natural log as double
	 */
	private static double logBigInteger(BigInteger val)
	{
		double LOG2 = Math.log(2.0);
		int blex = val.bitLength() - 1022; // any value in 60..1023 is ok
		if (blex > 0)
			val = val.shiftRight(blex);
		double res = Math.log(val.doubleValue());
		return (blex > 0 ? res + blex * LOG2 : res);
	}
}
