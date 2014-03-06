package p1;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE_USE, ElementType.METHOD })
@interface NonNull {

}
public class A {

	public void mA1(@NonNull A this, @NonNull B b) {
		b.mB1();
		System.out.println(b + "j");
		System.out.println(this.mA2());
	}
	
	public int mA2() { return 10;}

}