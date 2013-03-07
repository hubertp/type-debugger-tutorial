package implicits

// compile together with package.scala
// ----------------------------------------------------

trait BasicAction[+S] {
  def retrieve: S
}

object BasicAction {
  implicit def basicImpl[T]: FAction[T, Any] = ???
}

trait FAction[-T, +S] extends BasicAction[S] {
  def apply(x: T): S
  def retrieve: S
}

object FAction {
  implicit val generalImpl: FAction[Any, Any] = ???

  implicit val otherGeneralImpl: FAction[AnyRef, Any] = ???
}

trait Implicits06 {

  def identity[T](x: T): T = x
  def bar(x: Long): Any = x
  def foo[A, B](v: A)(implicit tc: FAction[A, B]) = tc(v)


  def test01 {
    implicit val someImpl: FAction[Int, Int] = ???

    foo(1.0)
    identity(foo(1))
    bar(foo(1))
  }
}
