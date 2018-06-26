package net.scarlettsystems.android.fraction;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

@SuppressWarnings("WeakerAccess")
public class Fraction
{
	private BigInteger mNumerator;
	private BigInteger mDenominator;

	public Fraction(long numerator, long denominator)
	{
		this(BigInteger.valueOf(numerator), BigInteger.valueOf(denominator));
	}

	public Fraction(BigInteger numerator, BigInteger denominator)
	{
		mNumerator = numerator.abs();
		mDenominator = denominator.abs();
		int sign = numerator.signum() * denominator.signum();
		if(sign < 0)
		{
			mNumerator = mNumerator.negate();
		}
		reduce();
	}

	public BigInteger getNumerator()
	{
		return mNumerator;
	}

	public BigInteger getDenominator()
	{
		return mDenominator;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof Fraction)){return false;}
		Fraction compare = (Fraction)obj;
		return compare.getNumerator().equals(this.getNumerator())
				&& compare.getDenominator().equals(this.getDenominator());
	}

	public Fraction add(Fraction b)
	{
		if (mDenominator.equals(BigInteger.ZERO) || (b.getDenominator().equals(BigInteger.ZERO)))
		{
			throw new IllegalArgumentException("Invalid input. Denominator cannot be zero.");
		}
		BigInteger lcm = lcm(mDenominator, b.getDenominator());
		BigInteger numeratorA = getNumerator().multiply(lcm.divide(getDenominator()));
		BigInteger numeratorB = b.getNumerator().multiply(lcm.divide(b.getDenominator()));
		return new Fraction(numeratorA.add(numeratorB), lcm).reduce();
	}

	public Fraction add(BigInteger b)
	{
		BigInteger numerator = b.multiply(getDenominator());
		return new Fraction(getNumerator().add(numerator), getDenominator()).reduce();
	}

	public Fraction add(long b)
	{
		return add(BigInteger.valueOf(b));
	}

	public Fraction subtract(Fraction b)
	{
		if (mDenominator.equals(BigInteger.ZERO) || (b.getDenominator().equals(BigInteger.ZERO)))
		{
			throw new IllegalArgumentException("Invalid input. Denominator cannot be zero.");
		}
		BigInteger lcm = lcm(mDenominator, b.getDenominator());
		BigInteger numeratorA = getNumerator().multiply(lcm.divide(getDenominator()));
		BigInteger numeratorB = b.getNumerator().multiply(lcm.divide(b.getDenominator()));
		return new Fraction(numeratorA.subtract(numeratorB), lcm).reduce();
	}

	public Fraction subtract(BigInteger b)
	{
		BigInteger numerator = b.multiply(getDenominator());
		return new Fraction(getNumerator().subtract(numerator), getDenominator()).reduce();
	}

	public Fraction subtract(long b)
	{
		return subtract(BigInteger.valueOf(b));
	}

	public Fraction multiply(Fraction b)
	{
		if (mDenominator.equals(BigInteger.ZERO) || (b.getDenominator().equals(BigInteger.ZERO)))
		{
			throw new IllegalArgumentException("Invalid input. Denominator cannot be zero.");
		}
		return new Fraction(getNumerator().multiply(b.getNumerator()), getDenominator().multiply(b.getDenominator())).reduce();
	}

	public Fraction multiply(BigInteger b)
	{
		return new Fraction(getNumerator().multiply(b), getDenominator()).reduce();
	}

	public Fraction multiply(long b)
	{
		return multiply(BigInteger.valueOf(b));
	}

	public Fraction divide(Fraction b)
	{
		if (mDenominator.equals(BigInteger.ZERO) || (b.getDenominator().equals(BigInteger.ZERO)))
		{
			throw new IllegalArgumentException("Invalid input. Denominator cannot be zero.");
		}
		return new Fraction(getNumerator().multiply(b.getDenominator()), getDenominator().multiply(b.getNumerator())).reduce();
	}

	public Fraction divide(BigInteger b)
	{
		return new Fraction(getNumerator(), getDenominator().multiply(b)).reduce();
	}

	public Fraction divide(long b)
	{
		return divide(BigInteger.valueOf(b));
	}

	public String toString()
	{
		return getNumerator().toString() + "/" + getDenominator().toString();
	}

	public double getValue(int decimalDigits)
	{
		return new BigDecimal(getNumerator())
				.divide(new BigDecimal(getDenominator()),  decimalDigits, RoundingMode.HALF_EVEN)
				.doubleValue();
	}

	public BigInteger lcm(BigInteger d1, BigInteger d2)
	{
		BigInteger factor = d1;
		while(!d1.mod(d2).equals(BigInteger.ZERO))
		{
			d1 = d1.add(factor);
		}
		return d1;
	}

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
}
