package com.sksamuel.scapegoat.inspections.string

import com.sksamuel.scapegoat.PluginRunner
import org.scalatest.OneInstancePerTest
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

/** @author Stephen Samuel */
class ArraysInFormatTest extends AnyFreeSpec with Matchers with PluginRunner with OneInstancePerTest {

  override val inspections = Seq(new ArraysInFormat)

  "use of array in format" - {
    "should report warning" in {
      val code =
        """class Test {
            val array = Array("sam")
            "%s".format(array)
            "%s".format(Array("inline"))
            "%s".format(List("I'm deep")) // list is fine
          } """.stripMargin

      compileCodeSnippet(code)
      compiler.scapegoat.feedback.warnings.size shouldBe 2
    }
  }
}
