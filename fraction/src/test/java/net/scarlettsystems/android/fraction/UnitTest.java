package net.scarlettsystems.android.fraction;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;

public class UnitTest
{
	@Test
	public void creation()
	{
		assertEquals(new Fraction(1,1), new Fraction(BigInteger.ONE, BigInteger.ONE));
		assertEquals(new Fraction(Long.MAX_VALUE,Long.MAX_VALUE), new Fraction(BigInteger.valueOf(Long.MAX_VALUE), BigInteger.valueOf(Long.MAX_VALUE)));
		assertEquals(new Fraction(5,15), new Fraction(1, 3));
		assertEquals(new Fraction(1,2), new Fraction(-1, -2));
		assertEquals(new Fraction(-1,2), new Fraction(1, -2));
	}

	@Test
	public void addition()
	{
		assertEquals(new Fraction(7,6), new Fraction(1, 2).add(new Fraction(2,3)));
		assertEquals(new Fraction(167128,5697), new Fraction(541, 633).add(new Fraction(769,27)));


		assertEquals(new Fraction(3,2), new Fraction(1, 2).add(1));
	}

	@Test
	public void subtraction()
	{
		assertEquals(new Fraction(-1,6), new Fraction(1, 2).subtract(new Fraction(2,3)));
		assertEquals(new Fraction(-3739,464), new Fraction(286, 754).subtract(new Fraction(945,112)));

		assertEquals(new Fraction(-1,2), new Fraction(1, 2).subtract(1));
	}

	@Test
	public void multiplication()
	{
		assertEquals(new Fraction(373703,57536), new Fraction(583, 232).multiply(new Fraction(641,248)));
		assertEquals(new Fraction(-25,153), new Fraction(5, 48).multiply(new Fraction(-80,51)));

		assertEquals(new Fraction(21,5), new Fraction(3, 5).multiply(7));
	}

	@Test
	public void division()
	{
		assertEquals(new Fraction(63,496), new Fraction(42, 62).divide(new Fraction(16,3)));
		assertEquals(new Fraction(89042,4505), new Fraction(633, 85).divide(new Fraction(318,844)));

		assertEquals(new Fraction(17,185), new Fraction(17, 37).divide(5));
	}

	@Test
	public void power()
	{
		assertEquals(new Fraction(4,9), new Fraction(2, 3).pow(2));
		assertEquals(new Fraction(8,343), new Fraction(2, 7).pow(3));
	}

	@Test
	public void logarithm()
	{
		assertEquals(-0.69314718, new Fraction(1, 2).log(), 0.000001);
		assertEquals(5.540008986684, new Fraction(12312578345234L, 48345234856L).log(), 0.000001);
	}

	@Test
	public void value()
	{
		assertEquals(0.5, new Fraction(1,2).getValue(1), 0.000001);
		assertEquals(0.33333, new Fraction(1,3).getValue(5), 0.000001);
	}
}
