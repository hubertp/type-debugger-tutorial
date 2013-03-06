package implicits

// compile together with package.scala

trait Action[-T] {
  def apply(x: T): String
}

object Action {
  implicit val generalImpl: Action[Any] = ???
  implicit val longImpl: Action[Double] = ???
}

trait Implicits05 {

  def test01 {
    implicit val localImpl: Action[Int] = ???

    foo(1)    // 1
    foo(1.0)  // 2
  }

  def test02 {
    foo(1)    // 3
    foo(1.0)  // 4
  }

  def foo[A](v: A)(implicit tc: Action[A]) = tc(v)

}

// ----------------------------------------------------

trait BasicAction[+S] {
  def retrieve: S
}

object BasicAction {
  implicit def basicImpl[T]: GAction[T, Any] = ???
}

trait GAction[-T, +S] extends BasicAction[S] {
  def apply(x: T): S
  def retrieve: S
}

object GAction {
  implicit val generalImpl: GAction[Any, Any] = ???

  implicit val lessGeneralImpl: GAction[AnyRef, Any] = ???
}

trait Implicits06 {

  def identity[T](x: T): T = x
  def bar(x: Long): Any = x
  def foo[A, B](v: A)(implicit tc: GAction[A, B]) = tc(v)


  def test01 {
    implicit val someImpl: GAction[Int, Int] = ???

    foo(1)
    foo(1.0)
    identity(foo(1))
    bar(foo(1))
  }
}
