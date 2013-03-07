package object implicits {

  implicit val packageObjectFAction: FAction[Int, Any] = new FAction[Int, Any] {
    def apply(x: Int): Any = ???
    def retrieve: Any = ???
  }
}