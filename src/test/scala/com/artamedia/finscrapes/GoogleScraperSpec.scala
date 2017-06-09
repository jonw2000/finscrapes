package com.artamedia.finscrapes

import java.time.LocalDate

import org.mockito.ArgumentMatchers._
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.specs2.mutable.Specification

import scala.io.Source

class GoogleScraperSpec extends Specification with MockitoSugar {

  "GoogleScraperSpec" should {

    "extract data from some html" in {
      val start = LocalDate.parse("2016-01-01")
      val end = LocalDate.parse("2017-01-01")
      val provider = mock[WebProvider]
      val html = getHtml("LCOP")

      when(provider.get(anyString())).thenReturn(html)
      val gs = GoogleScraper("LCOP.L", start, end, provider)
      val actual = gs.get
      actual.size mustEqual 30
      actual.head mustEqual Tick("LCOP.L", LocalDate.parse("2017-06-09"), 6.3)
      actual.last mustEqual Tick("LCOP.L", LocalDate.parse("2017-04-27"), 6.13)
    }
  }

  def getHtml(symbol: String): String = Source.fromURL(getClass.getResource(s"/$symbol.html")).mkString
}
