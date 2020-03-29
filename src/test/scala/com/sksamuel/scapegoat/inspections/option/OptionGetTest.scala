package com.sksamuel.scapegoat.inspections.option

import com.sksamuel.scapegoat.InspectionTest

/** @author Stephen Samuel */
class OptionGetTest extends InspectionTest {

  override val inspections = Seq(new OptionGet)

  "option.get use" - {
    "should report warning" in {

      val code = """class Test {
                      val o = Option("sammy")
                      o.get
                    } """.stripMargin

      compileCodeSnippet(code)
      compiler.scapegoat.feedback.warnings.size shouldBe 1
    }
  }
}
