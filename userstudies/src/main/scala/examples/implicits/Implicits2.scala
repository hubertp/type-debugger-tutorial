package userstudies
package implicits


trait Action[-T] {
  def apply(x: T): String
}

object Action {
  implicit val generalImpl: Action[Any] = new Action[Any] {
    def apply(x: Any): String = ???
  }
  implicit val longImpl: Action[Double] = new Action[Double] {
    def apply(x: Double): String = ???
  }
}

trait Implicits05 {

  def test01 {
    implicit val localImpl: Action[Int] = new Action[Int] {
      def apply(x: Int): String = ???
    }

    // Q: Will we get an ambiguity here
    // Q: If no, which implicit will get picked
    // Q: Is generalImpl implicit applicable here
    foo(1)
    foo(1.0)
  }

  def test02 {
    // Q: Will we get an imbiguity here
    // Q: Which implicit will get picked here
    // Q: Is generalImpl implicit applicable here
    foo(1)
    foo(1.0)
  }

  def foo[A](v: A)(implicit tc: Action[A]) = tc(v)

}

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
  implicit val generalImpl: GAction[Any, Any] = new GAction[Any, Any] {
    def apply(x: Any): Any = ???
    def retrieve: Any = ???
  }

  implicit val lessGeneralImpl: GAction[AnyRef, Any] = new GAction[AnyRef, Any] {
    def apply(x: AnyRef): Any = ???
    def retrieve: Any = ???
  }
}

trait Implicits06 {

  def identity[T](x: T): T = x
  def bar(x: Long): Any = x
  def foo[A, B](v: A)(implicit tc: GAction[A, B]) = tc(v)


  def test01 {
    implicit val someImpl: GAction[Int, Int] = new GAction[Int, Int] {
      def apply(x: Int): String = ???
      def retrieve: Int = ???
    }

    // same question to both
    // Q: Will we get an ambiguity here
    // Q: If no, which implicit will get picked (out of three)
    // Q: Is generalImpl implicit applicable here
    foo(1)
    foo(1.0)
    identity(foo(1))
    bar(foo(1))
  }
}
