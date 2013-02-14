class Show(v: Int) {
  def show() { println("[value] " + v) }
}

object Test {

  def foo(): Unit = {
    val x: Int = 12
    x.show()
  }

  def bar(): Unit = {
    implicit def conv(a: Int): Show = new Show(a)

    val x: Int = 12
    x.show()
  }
}
