package com.wordnik.test.swagger.core.testdata

import javax.xml.bind.annotation.XmlRootElement._
import reflect.BeanProperty
import javax.xml.bind.annotation.XmlElement._
import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlElement, XmlRootElement}

/**
 * User: ramesh
 * Date: 2/23/12
 * Time: 12:05 PM
 */
@XmlRootElement(name = "sampleInput")
case class SampleInput (
    @XmlElement(name = "name") @BeanProperty
    var name: String,

    @XmlElement(name = "value") @BeanProperty
    var value: String
){
  def this() = this(null,null)
}