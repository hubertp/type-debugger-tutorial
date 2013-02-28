package inference

trait Existentials01 {

  trait Writer[ -A ] { def write( a: A ) : Unit }

  case class ToStore[A](v: A, writer: Writer[A])

  def process[T](value: T, writer: Writer[T]) = ???

  def test1(elem: ToStore[_]) {
    elem match {
      case ToStore(v1, w1) =>
        process(v1, w1)
    }
  }

  def test2(elem: ToStore[_]) {

    val ToStore(v1, w1) = elem
    
    process(v1, w1)

  }

  def test3(elem: ToStore[_]) {

    process(elem.v, elem.writer)

  }

}