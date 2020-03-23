package com.sksamuel.scapegoat.inspections.collections

import com.sksamuel.scapegoat._

/**
 * @author Sanjiv Sahayam
 *
 * Inspired by Intellij inspection that does:
 *   myMap.get(key).getOrElse(defaultValue) –> myMap.getOrElse(key, defaultValue)
 */
class MapGetAndGetOrElse
    extends Inspection(
      text = "Use of Map.get().getOrElse instead of Map.getOrElse",
      defaultLevel = Levels.Error,
      description = "Checks whether Map.get().getOrElse() can be simplified to Map.getOrElse().",
      explanation =
        "Map.get(key).getOrElse(value) can be replaced with Map.getOrElse(key, value), which is more concise."
    ) {

  def inspector(context: InspectionContext): Inspector = new Inspector(context) {
    override def postTyperTraverser = new context.Traverser {

      import context.global._

      override def inspect(tree: Tree): Unit = {
        tree match {
          case Apply(
              TypeApply(Select(Apply(Select(left, TermName("get")), List(key)), TermName("getOrElse")), _),
              List(defaultValue)
              ) if isMap(left) =>
            context.warn(tree.pos, self, tree.toString.take(500))
          case _ => continue(tree)
        }
      }
    }
  }
}
