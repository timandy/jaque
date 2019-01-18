package com.trigersoft.jaque.expression;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;

public class InvocationExpressionTest {
	@Test
	public void testParameterReplacementInToString() throws NoSuchMethodException, SecurityException {

		Expression firstInvocation = Expression.invoke(Expression.constant("Hello World"),
				String.class.getMethod("replace", CharSequence.class, CharSequence.class), Expression.parameter(String.class, 0), Expression.constant("Eddie"));

		assertEquals("Hello World.replace(P0, Eddie)", firstInvocation.toString());

		Expression secondInvocation = Expression.invoke(firstInvocation, String.class.getMethod("replace", CharSequence.class, CharSequence.class),
				Expression.constant("foo"), Expression.constant("bar"));

		assertEquals("Hello World.replace(P0, Eddie).replace(foo, bar)", secondInvocation.toString());
	}

	@Test
	public void compareComparableObjects() {
		LocalDate date1 = LocalDate.of(2016, 8, 23);
		LocalDate date2 = LocalDate.now();
		Expression comparison = Expression.binary(ExpressionType.GreaterThan, Expression.constant(date1), Expression.constant(date2));

		Object actual = comparison.visit(Interpreter.Instance).apply(null);
		assertEquals(date1.isAfter(date2), actual);

		comparison = Expression.binary(ExpressionType.LessThan, Expression.constant(date1), Expression.constant(date2));

		actual = comparison.visit(Interpreter.Instance).apply(null);
		assertEquals(date1.isBefore(date2), actual);
	}
}
