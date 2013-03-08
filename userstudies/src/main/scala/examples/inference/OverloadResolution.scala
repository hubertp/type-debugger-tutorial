package inference 

import scala.language.implicitConversions
import scala.language.reflectiveCalls


trait OverloadedResolution {

  def test01 {

    class Base {

      def square(x: Int): Int     = ???    // 1

      def root(x: Double): Double = ???    // 3

    }

    class SubBase extends Base {

      def square(x: Double): Double = ???  // 2

      def root(x: Int): Double = ???       // 4

    }

    val t = new SubBase()

    val x: Int = 1

    t.square(x)   // A

    t.root(x)     // C

  }

  def test02 {

    class Base {
      def foo(x: Int, y: Double, z: Int = 0) {}  // 1
      def foo(x: Int, y: AnyVal) {}              // 2
    }

    val t = new Base()
    t.foo(1, 2)
  }

  def test03 {
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
    d.bar(new B())  // C

  }

  def test04 {
    abstract class Base[T] {
      def v: T
    }
    object Base {
      implicit def b2Int(b: Base[Int]): Int        = b.v   // 1
      implicit def b2T[T <: AnyVal](b: Base[T]): T = ???   // 2
    }

    class A(val v: Int) extends Base[Int]
    object A {
      implicit def a2Int(a: A): Int       = a.v  // 3
      implicit def a2Double(a: A): Double = a.v  // 4
      implicit def a2b[T](a: A): T        = ???  // 5
    }

    class B(val v: AnyRef) extends Base[AnyRef]

    class Foo {
      def foo(x: Int) = ???        // x
    }

    class Bar extends Foo {
      def foo(x: AnyVal) = ???     // y
    }


    def test {
      val a = new A(1)
      val b = new B(new Integer(1))

      val c = new Bar()
      c.foo(a)   // A
      c.foo(b)   // B
    }
  }

  def test05 {
    class Base[T]
    class NatA[T] extends Base[T]
    class NatB[T] extends Base[T]
    class AA[A]

    implicit def conv1(i: Int) = new NatA[Int]
    implicit def conv2(i: Int) = new NatB[Int]
    implicit def conv3(op: AA[String]) = new Base[String]
    implicit def conv4(op: AA[Int])    = new Base[Int]

    def aFunc[A](a: NatA[A])  = new AA[String]   // 1
    def aFunc[A](a: NatB[A])  = new AA[Int]      // 2

    def bFunc[T](e1: Base[T]) = e1

    def test01(p: Int): Base[Int] = {
     val x = aFunc(p)  // A
     bFunc(x)
    }

    def test02(p: Int): Base[Int] =
     bFunc(aFunc(p))   // B
  }
}