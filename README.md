Scapegoat
==========

Scapegoat is a Scala static code anaylzer, what is more colloquially known as a code lint tool or linter. Scapegoat works in a similar vein to Java's [FindBugs](http://findbugs.sourceforge.net/) or [checkstyles](http://checkstyle.sourceforge.net/), or Scala's [Scalastyle](https://github.com/scalastyle/scalastyle).

A static code anaylzer is a tool that flag suspicious langage usage in code. This can include behavior likely to lead or bugs, non idiomatic usage of a language, or just code that doesn't conform to specified style guidelines.

**What's the difference between this project and Scalastyle (or others)?**

Scalastyle is a similar linting tool which focuses mostly on enforcing style/code standards. There's no problems running multiple analysis tools on the same codebase. In fact it could be beneficial as the total set of possible warnings is the union of the inspections of all the enabled tools. The worst case is that the same warnings might be generated by multiple tools.

### Usage
Scapegoat is developed as a scala compiler plugin, which can then be used inside your build tool.

See: [sbt-scapegoat](https://github.com/sksamuel/sbt-scapegoat) for SBT integration.

### Screenshot

Here is a screen shot of the type of report scapegoat generates.

![screenshot](https://raw.githubusercontent.com/sksamuel/scapegoat/master/screenshot1.png)

### Inspections

The currently implemented inspections are as follows. Most of the descriptions need to be completed, but they should be self explanatary anyway from the name.

|Name|Description|
|----|-----------|
| As instance of | description needed |
| Blocking actor | check for use Await.result and Await.ready in actor |
| Broken oddness | checks for a % 2 == 1 for oddness, but this breaks for negative numbers |
| Catching NPE | checks for case clause catching NPE |
| Collection promotion to any | description needed |
| Comparing floating point types | description needed |
| Comparing unrelated types | description needed |
| Comparison with self | description needed |
| Constant if | checks for code where the expression on conditionals compiles to a constant |
| Either get | description needed |
| Empty Catch Block | description needed |
| Empty If Block | description needed |
| Empty Synchronized Block | description needed |
| Empty Method | description needed |
| Empty Try Block | description needed |
| Expresson as statement | executing an expression as a statement could be a mistake |
| filter.size instead of count | .filter(x => Bool).size can be replaced more concisely with with count(x => Bool) |
| Iterable.head | checks for unsafe use of iterable.head |
| Java conversions use | description needed |
| Mod one | checks for redundant x % 1 which always returns 0 |
| Null use | checks for use of null |
| Option Get | checks for improper use of Option.get |
| Parameterless method returns unit | checks for procedures (methods returning unit) that don't declare a params list |
| Prefer set empty | checks for use of Set.empty rather than Set() |
| Prefer seq empty | checks for use of Seq.empty rather than Seq() |
| Redundant finalizer | checks for empty finalizers |
| Return use | using return in Scala is not recommended |
| Try get | Try.get should be replaced with safe patter matching |
| Unused method parameter | checks for unused method parameters |
| Var use | description needed |
| Var use | description needed |
| While true | description needed |

### Inspections

##### Arrays to string

Checks for explicit toString calls on arrays. Since toString on an array does not perform a deep toString, like say scala's List, this is usually a mistake.

##### ComparingUnrelatedTypes

Checks for equality comparions that cannot suceed because the types are unrelated. Eg `"string" == BigDecimal(1.0)`. The scala compiler has a less strict version of this inspection.

##### ConstantIf

Checks for if statements where the condition is always true or false. Not only checks for the boolean literals, but also any expression that the compiler is able to turn into a constant value. Eg, `if (0 < 1) then else that`

##### IllegalFormatString

Checks for a format string that is not invalid, such as invalid conversions, invalid flags, etc. Eg, `"% s"`, `"%qs"`, `%.-4f"`

##### IncorrectNumberOfArgsToFormat

Checks for an incorrect number of arguments to String.format. Eg, `"%s %s %f".format("need", "three")` flags an error because the format string specifies 3 parameters but the call only provides 2.

##### List size

Checks for .size on an instance of List. Eg, `val a = List(1,2,3); a.size`

*Rationale* List.size is O(n) so for performance reasons if .size is needed on a list that could be large, consider using an alternative with O(1), eg Array, Vector or ListBuffer.

##### Redundant finalizer

Checks for empty finalizers. This is redundant code and should be removed. Eg, `override def finalize : Unit = { }`

##### UnnecessaryReturnUse

Checks for use of return in a function or method. Since the final expression of a block is always the return value, using return is unnecessary. Eg, `def foo = { println("hello"); return 12; }`

##### While true

Checks for code that uses a `while(true)` or `do { } while(true)` block.

*Rationale*: This type of code is usually not meant for production as it will not return normally. If you need to loop until interrupted then consider using a flag.

### Other static analysis tools:

* Linter (Scala) - https://github.com/HairyFotr/linter
* WartRemover (Scala) - https://github.com/typelevel/wartremover
* ScalaStyle (Scala) - https://github.com/scalastyle/scalastyle/wiki
* Findbugs(JVM) - http://findbugs.sourceforge.net/bugDescriptions.html
* CheckStyle(Java) - http://checkstyle.sourceforge.net/availablechecks.html
* PMD(Java) - http://pmd.sourceforge.net/pmd-5.0.3/rules/index.html
* Error-prone(Java) - https://code.google.com/p/error-prone/wiki/BugPatterns
* CodeNarc(Groovy) - http://codenarc.sourceforge.net/codenarc-rule-index.html
* PVS-Studio(C++) - http://www.viva64.com/en/d/
* Coverity(C++) - http://www.slideshare.net/Coverity/static-analysis-primer-22874326 (6,7)
* CppCheck(C++) - http://cppcheck.sourceforge.net/
* OCLint (C++/ObjC) - http://docs.oclint.org/en/dev/rules/index.html
* JSLink (Javascript) - http://www.jshint.com/
* JavascriptLink (Javascript) - http://www.javascriptlint.com/
* ClosureLinkter (Javascript) - https://developers.google.com/closure/utilities/
