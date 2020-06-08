package com.sksamuel.scapegoat.io

import com.sksamuel.scapegoat.{Feedback, Warning}

import scala.xml.Node

object ScalastyleReportWriter extends ReportWriter {

  private val checkstyleVersion = "5.0"
  private val scapegoat = "scapegoat"

  override protected val fileName = "scapegoat-scalastyle.xml"

  private def toXML(feedback: Feedback): Node =
    <checkstyle version={checkstyleVersion} generatedBy={scapegoat}>
      {feedback.warningsWithMinimalLevel.groupBy(_.sourceFileFull).map(fileToXml)}
    </checkstyle>

  private def fileToXml(fileWarningMapEntry: (String, Seq[Warning])): Node = {
    val (file, warnings) = fileWarningMapEntry
    <file name={file}>
      {warnings.map(warningToXml)}
    </file>
  }

  private def warningToXml(warning: Warning) =
    <error line={warning.line.toString} message={warning.text} severity={warning.level.toString} source={
      warning.inspection
    } snippet={warning.snippet.orNull} explanation={warning.explanation}></error>

  override protected def generate(feedback: Feedback): String = toXML(feedback).toString()
}
