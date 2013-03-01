package subtyping

import scala.language.higherKinds

trait TreeLike[N, This[X] <: TreeLike[X, This]] {
  
  type NodeT <: BaseNode[N]

  trait BaseNode[T <: N] {
    def value: T
    def nodes: Set[NodeT]
  }

}

abstract class Tree[N] extends TreeLike[N, Tree] { }


package mutable {
  trait ITree[N] extends Tree[N]
               with TreeLike[N, ITree] {

    type NodeT <: BaseNode[N]

    trait BaseNode[T <: N] extends super.BaseNode[T] {
      def nodes: collection.mutable.Seq[NodeT]
    }
  }

}