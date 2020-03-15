package com.sksamuel.scapegoat.inspections.collections

import com.sksamuel.scapegoat.PluginRunner
import org.scalatest.OneInstancePerTest
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

/** @author Stephen Samuel */
class NegativeSeqPadTest extends AnyFreeSpec with Matchers with PluginRunner with OneInstancePerTest {

  override val inspections = Seq(new NegativeSeqPad)

  "self assignment" - {
    "should report warning" - {
      val code = """class Test {
                     Seq(1,2,3).padTo(-1, 4)
                    } """.stripMargin

      compileCodeSnippet(code)
      compiler.scapegoat.feedback.warnings.size shouldBe 1
    }
  }
}
