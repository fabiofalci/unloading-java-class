package test;

import java.lang.instrument.UnmodifiableClassException;

import instrument.SimpleMain;

/**
 * @author Fabio Falci
 * 
 */
public class TestClassLoader {

	public TestClassLoader() {
		System.out.println("TestClassLoader()");
	}

	public static void main(String[] args) {
		Foo t0 = new Foo();
		t0.foo();
		try {
			SimpleMain.staticInstrumentation.retransformClasses(Foo.class);
		} catch (UnmodifiableClassException e) {
			e.printStackTrace();
		}
		Foo t1 = new Foo();
		t0.foo();
		t1.foo();
		

		System.out.println(t0 + "\n" + t1);
		System.out.println(t0.getClass().getClassLoader() + "\n"
				+ t1.getClass().getClassLoader());
	}
}
