package inference 

trait OverloadedResolution {


  def test01 {

    class Test {
      def foo(arg: Any) {}     // 1
      def foo(arg: Int) {}     // 2
      def foo(arg: AnyRef) {}  // 3
    }

    val t = new Test()
    t.foo(1)      // A
    t.foo("abc")  // B
  }

  def test02 {

    class Ambiguous {
      def foo(x: Any, y: Int) {} // 1
      def foo(x: Int, y: Int) {} // 2
    }

    val t = new Ambiguous()
    t.foo(1, 2)    // A
    t.foo(1.0, 2)  // B
  }

  def test03 {
    class Calculation {
      def square(x: Double) {}   // 1
      def square(y: Float) {}    // 2
    }

    val c = new Calculation()
    c.square(1)    // A
    c.square(1.0)  // B
  }

  def test04 {

    class Base {

      def square(x: Int): Int = ???        // 1

      def root(x: Double): Double = ???    // 3

    }

    class SubBase extends Base {

      def square(x: Double): Double = ???  // 2

      def root(x: Int): Double = ???       // 4

    }

    val t = new SubBase()

    val x: Int = 1
    val y: Double = 2.0

    t.square(x)   // A
    t.square(y)   // B

    t.root(x)     // C
    t.root(y)     // D

  }

  def test05 {

    class Base {
      def foo(x: Int, y: Int = 0) {}  // 1
      def foo(x: Int) {}              // 2
    }

    val t = new Base()
    t.foo(1)       // A
    t.foo(x = 1)   // B
  }

  def test06 {

    class Base {
      def foo(x: Int, y: Double, z: Int = 0) {}  // 1
      def foo(x: Int, y: AnyVal) {}              // 2
    }

    val t = new Base()
    t.foo(1, 2)     // A
    t.foo(1, 2.0)   // B
  }

  def test07 {
    class Base {
      def foo(x: Base) {}      // 1
    }

    class SubBase extends Base {
      def foo(x: SubBase) {}   // 2
    }

    val x = new Base()
    val y = new SubBase()

    val t = new SubBase()
    t.foo(x)   // A
    t.foo(y)   // B
  }

  def test08 {

    class Base {
      def foo(x: SubBase) {}    // 1
    }

    class SubBase extends Base {
      def foo(x: Base) {}       // 2
    }

    val x = new Base()
    val y = new SubBase()

    val t = new SubBase()
    t.foo(x)  // A
    t.foo(y)  // B
  }

  def test09 {

    class Base {
      def foo(s: String): String = ???           // 1
      def foo: String = ???                      // 2
    }
    class SubBase extends Base {
      override def foo(s: String): String = ???  // 3
    }

    val x = new SubBase()

    x.foo       // A
    x.foo("")   // B

  }


  def test10 {
    class A
    class B extends A

    class C {
      def foo(x: B)    = ???  // 1
      def foo(x: => B) = ???  // 2
      def foo(x: A)    = ???  // 3
    }

    class D {
      def bar(x: A)    = ???  // 4
      def bar(x: => B) = ???  // 5
    }

    val c = new C()
 
    c.foo(new B())  // A

    val d = new D()
    d.bar(new A())  // B
    d.bar(new B())  // C

  }

  def test11 {

    class Foo {
      def foo(x: (Int, Double)) = ???  // 1

      def bar(x: (Int, Double)) = ???  // 3
    }

    class Bar extends Foo {
      def foo(x: (Int, AnyVal)) = ???  // 2

      def bar(x: Int, y: AnyVal) = ??? // 4
      def bar(x: (Int, AnyVal)) = ???  // 5
    }

    val c = new Bar()

    c.foo(1, 2.0)   // A
    c.foo(1, 1)     // B

    c.bar(1, 2.0)   // C
    c.bar(1, 1)     // D
  }

}