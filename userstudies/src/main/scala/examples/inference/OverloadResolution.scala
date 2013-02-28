package inference 

trait OverloadedResolution {


  def test1 {

    class Test {
      def foo(arg: Any) {}
      def foo(arg: Int) {}
      def foo(arg: AnyRef) {}
    }

    val t = new Test()
    t.foo(1)  
    t.foo("abc")
  }

  def test2 {

    class Ambiguous {
      def foo(x: Any, y: Int) {}
      def foo(x: Int, y: Any) {}
    }

    val t = new Ambiguous()
    t.foo(1, 2)

    val arg1: Any = 1
    t.foo(arg1, 2)
  }

  def test3 {
    class Calculation {
      def square(x: Double) {}
      def square(y: Float) {}
    }

    val c = new Calculation()
    c.square(1)
    c.square(1.0)
  }

  def test4 {

    class Base1 {
      def square(x: Int): Int = ???
    }

    class SubBase1 extends Base1 {
      def square(x: Double): Double = ???
    }

    val t = new SubBase1()

    val x: Int = 1
    val y: Double = 2.0

    t.square(x)
    t.square(y)

  }

  def test5 {

    class Base2 {
      def square(x: Double): Double = ???
    }

    class SubBase2 extends Base2 {
      def square(x: Int): Int = ???
    }

    val t = new SubBase2()

    val x: Int = 1
    val y: Double = 2.0
    val z: Long = 3

    t.square(x)
    t.square(y)
    t.square(z)
  }
  // remove?
  def test7 {

    class Base3
    class SubBase3 extends Base3
    class Test3 {
      def foo(x: Base3) {}
      def foo(x: SubBase3) {}
    }

    val t = new Test3()

    val x = new SubBase3()
    val y: Base3 = new SubBase3()

    t.foo(x)
    t.foo(y)
  }

  def test8 {

    class Base4 {
      def foo(x: Int, y: String = "") {}
      def foo(y: Int) {}
    }

    val t = new Base4()
    t.foo(1)
    t.foo(1,"")
  }

  def test9 {

    class Base5 {
      def foo(x: Int, y: Double, z: Int = 0) {}
      def foo(x: Int, y: Int) {}
    }

    val t = new Base5()
    t.foo(1, 2)
    t.foo(1, 2.0)
  }

  def test10 {
    class Base6 {
      def foo(x: Base6) {}
    }

    class SubBase6 extends Base6 {
      def foo(x: SubBase6) {}
    }

    val x = new Base6()
    val y = new SubBase6()

    val t = new SubBase6()
    t.foo(x)
    t.foo(y)
  }

  def test11 {

    class Base7 {
      def foo(x: SubBase7) {}
    }

    class SubBase7 extends Base7 {
      def foo(x: Base7) {}
    }

    val x = new Base7()
    val y = new SubBase7()

    val t = new SubBase7()
    t.foo(x)
    t.foo(y)
  }

  def test12 {

    class Base8 {
      def foo(s: String): String = ???
      def foo: String = ???
    }
    class SubBase8 extends Base8 {
      override def foo(s: String): String = ???
    }

    val x = new SubBase8()

    x.foo
    x.foo("")

  }


  def test13 {
    class A
    class B extends A

    class C {
      def foo(x: B)    = ???
      def foo(x: => B) = ???
      def bar(x: A)    = ???
      def bar(x: B)    = ???
    }

    class D {
      def bar(x: A)    = ???
      def bar(x: => B) = ???
    }

    val c = new C()
 
    c.foo(new B())
    c.bar(new A())
    c.bar(new B())

    val d = new D()
    d.bar(new A())
    d.bar(new B())

  }

  def test14 {
    class Simple {
      def foo(x: Int) = ???
      def foo(x: Double) = ???
    }

    class Foo {
      def foo(x: (Int, Double)) = ???
    }

    class Bar extends Foo {
      def foo(x: (Int, AnyVal)) = ???
    }

    val s = new Simple()
    s.foo(1)

    val c = new Bar()

    c.foo(1, 2.0)
    c.foo(1, 1)
  }

}