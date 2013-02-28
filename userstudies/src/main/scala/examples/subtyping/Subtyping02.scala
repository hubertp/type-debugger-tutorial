package subtyping

trait Subtyping02 {

  abstract class Base
  class A extends Base
  class B extends Base
  class A1 extends A

  def m1(x: A)(func: A => B): B                  = ???
  def m2(func: A => Base): Base => A             = ???
  def m3(x: A, y: B)(func: A => B => Base): Base = ???
  def m4(x: A)(func: A => B): B                  = ???
  def m5(x: A, y: B)(func: A => B => B): Base    = ???


  def test: Unit = {

    val a = new A()
    val b = new B()

    m1(a)(_ => new B())
    m1(a)((x: Base) => new B())
    m2(m2(???))
    m3(a, b)((x: Base) => (y: Base) => new A())

    val f1: B => A  = ???
    val f2: A1 => B = ???

    m4(a)(f1)
    m4(a)(f2)

    m5(a)(a)
  }
}