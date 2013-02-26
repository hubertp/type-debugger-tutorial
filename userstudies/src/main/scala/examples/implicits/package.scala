package ch.epfl.lamp
package userstudies

package object implicits {
  implicit val packageObjectAction: Action[Int] = new Action[Int] {
    def apply(x: Int): String = ???
  }

  implicit val packageObjectGAction: GAction[Int, Any] = new GAction[Int, Any] {
    def apply(x: Int): Any = ???
  }
}