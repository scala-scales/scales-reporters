package com.sksamuel.scapegoat.inspections.unnecessary

import com.sksamuel.scapegoat.PluginRunner
import com.sksamuel.scapegoat.inspections.NoOpOverride
import org.scalatest.OneInstancePerTest
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

/** @author Stephen Samuel */
class NoOpOverrideTest extends AnyFreeSpec with Matchers with PluginRunner with OneInstancePerTest {

  override val inspections = Seq(new NoOpOverride)

  "UnnecessaryOverride" - {
    "should report warning" - {
      "when overriding method to just call super" in {

        val code =
          """class Test {
            |  override def finalize() {
            |    super.finalize()
            |  }
            |}
          """.stripMargin

        compileCodeSnippet(code)
        compiler.scapegoat.feedback.warnings.size shouldBe 1
      }
    }
    "should not report warning" - {
      "when overriding method with super call and other code" in {

        val code =
          """class Test {
            |  override def finalize() {
            |    super.finalize()
            |    println("sam")
            |  }
            |}
          """.stripMargin

        compileCodeSnippet(code)
        compiler.scapegoat.feedback.warnings.size shouldBe 0
      }
      "when overriding method calls super with different args" in {

        val code =
          """
            |class A {
            |  def method(what: String) = "A " + what
            |}
            |class B extends A {
            |  override def method(what: String) = super.method("something else than " + what)
            |}
          """.stripMargin

        compileCodeSnippet(code)
        compiler.scapegoat.feedback.warnings.size shouldBe 0
      }

    }
  }
}
